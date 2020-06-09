#!/bin/bash

docker run  -v /work:/work -v /etc/passwd/:/etc/passwd:ro  -v /etc/group/:/etc/group:ro -v /home/$USER/:/home/$USER:rw -e USER=$USER --user=$UID:`id -u $USER` -ti --rm yoctoland bash