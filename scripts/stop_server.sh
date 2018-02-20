systemctl stop webserver.service
rm /etc/systemd/system/webserver.service

cp /home/ubuntu/webserver.service /etc/systemd/system/
rm /home/ubuntu/webserver-0.0.1-SNAPSHOT.jar
systemctl daemon-reload