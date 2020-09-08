package com.zhang.ddd.infrastructure.storage;

import java.util.Arrays;
import java.util.List;
import com.zhang.ddd.domain.aggregate.user.repository.AvatarImageRepository;
import com.zhang.ddd.domain.exception.InvalidOperationException;
import com.zhang.ddd.infrastructure.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.net.ftp.FTP;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.UUID;

@Component
@Slf4j
public class AvatarImageRepositoryImpl implements AvatarImageRepository {

    static final long SIZE_LIMIT = 1024 * 500;

    static final List<String> IMAGE_EXT = Arrays.asList("png", "jpg", "jpeg");

    @Value("${ftp.host}")
    private String server;

    @Value("${ftp.port}")
    private int port;

    @Value("${ftp.username}")
    private String user;

    @Value("${ftp.password}")
    private String password;

    @Value("${ftp.filepath}")
    public String filePath;

    @Override
    public String nameHashImage(String name) {
        String hash = MD5Util.md5Hex(name);
        return String.format("https://www.gravatar.com/avatar/%s.jpg?s=400&d=identicon", hash);
    }

    @Override
    public String saveImage(String userName, String fileName, long size, InputStream input) {

        String res = saveImageInternal(userName, fileName, size, input);
        if (StringUtils.isEmpty(res)) {
            throw new RuntimeException("FTP save image failed.");
        }

        return res;
    }

    @Override
    public void removeImage(String path) {
        if (!path.startsWith(filePath)) {
            return;
        }
        try (FtpClient client = FtpClient.newInstance(server, port, user, password)) {
            boolean exist = client.getClient().deleteFile(path);
        } catch (IOException e) {
            log.error("Save user image failed.");
            throw new RuntimeException("FTP delete image failed.");
        }
    }

    private String saveImageInternal(String name, String fileName, long size, InputStream input) {
        if (size > SIZE_LIMIT) {
            throw new InvalidOperationException(MessageFormat
                    .format("Image file size cannot exceed {0}.", SIZE_LIMIT));
        }
        if (!validImage(fileName)) {
            throw new InvalidOperationException("Not a valid image.");
        }

        final String uuid = UUID.randomUUID().toString().replace("-", "");
        fileName = MD5Util.md5Hex(name) + "-" + uuid + "." + FilenameUtils.getExtension(fileName);

        try (FtpClient client = FtpClient.newInstance(server, port, user, password)) {

            // this should not fail as the directory should be set before start application
            if (!client.getClient().changeWorkingDirectory(filePath)) {
                throw new IllegalStateException("No valid location to upload:" + client.getClient().getReplyCode());
            }

            client.getClient().enterLocalPassiveMode();
            client.getClient().setFileType(FTP.BINARY_FILE_TYPE);

            if (!client.getClient().storeFile(fileName, input)) {
                throw new RuntimeException(String.format("Ftp StoreFile failed: %s, %s",
                        client.getClient().getReplyCode(),
                        client.getClient().getReplyString()));
            }
            fileName = filePath + "/" + fileName;
        } catch (Exception e) {
            log.error("Save user image failed. with exception: " + e);
            return null;
        }

        return fileName;
    }

    private boolean validImage(String fileName) {
        String ext = FilenameUtils.getExtension(fileName).toLowerCase();
        for (String s : IMAGE_EXT) {
            if (ext.equals(s)) {
                return true;
            }
        }
        return false;
    }
}
