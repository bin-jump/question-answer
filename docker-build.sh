APP_IMAGE_NAME="my/smart-post"
FILE_SERVER_NAME="my/file-server"

sh build.sh

docker rmi $APP_IMAGE_NAME -f
docker rmi $FILE_SERVER_NAME -f

docker build --no-cache -t $APP_IMAGE_NAME -f Dockerfile .
sh -c "cd docker/file_server && docker build -t ${FILE_SERVER_NAME} -f Dockerfile ."
