
FROM fauria/vsftpd

RUN yum -y update \
    && yum -y install epel-release \
    && yum -y install nginx \
    && mkdir -p /home/vsftpd/ftpuser/images

COPY conf.d /etc/nginx/default.d
COPY startall.sh /usr/sbin/
RUN chmod +x /usr/sbin/startall.sh

EXPOSE 80

CMD ["/usr/sbin/startall.sh"]

