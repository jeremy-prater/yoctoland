#!/bin/bash

sudo umount /mnt/wb
sudo mkfs.ext4 /dev/$1
sudo mount /dev/$1 /mnt/wb
sudo tar -C /mnt/wb -xJf /work/evoarm/build/tmp/deploy/images/wandboard/evo1-wandboard.tar.xz 
sync
sudo umount /mnt/wb