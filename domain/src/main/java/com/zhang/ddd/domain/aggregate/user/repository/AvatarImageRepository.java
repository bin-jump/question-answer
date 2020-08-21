package com.zhang.ddd.domain.aggregate.user.repository;

import java.io.InputStream;

public interface AvatarImageRepository {

    String nameHashImage(String name);

    String saveImage(String userName, String fileName, long size, InputStream input);

    void removeImage(String path);

}
