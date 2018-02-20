systemctl stop webserver.service
rm /etc/systemd/system/webserver.service

cp /home/ubuntu/webserver.service /etc/systemd/system/
systemctl daemon-reload