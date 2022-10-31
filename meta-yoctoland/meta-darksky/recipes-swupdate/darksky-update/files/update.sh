#!/bin/sh

if [ $# -lt 1 ]; then
    exit 0
fi

function get_boot_device() {
    for i in $(cat /proc/cmdline); do
        case "$i" in
        root=*)
            ROOT="${i#root=}"
            ;;
        esac
    done
}

function get_update_part() {
    OFFSET=$((${#ROOT} - 1))
    CURRENT_PART=${ROOT:$OFFSET:1}
    if [ $CURRENT_PART -eq "3" ]; then
        UPDATE_PART="2"
    else
        UPDATE_PART="3"
    fi
}

function get_update_block_device() {
    UPDATE_ROOT=${ROOT%?}${UPDATE_PART}
}

echo "Running update script in mode : $1"

if [ $1 == "preinst" ]; then

    # get current root device
    get_boot_device
    echo Booted from $ROOT...
    # now get the block device to be updated
    get_update_part
    get_update_block_device
    echo Updating $UPDATE_ROOT...
    # create symlink for update convenience
    ln -sf $UPDATE_ROOT /dev/update
fi

if [ $1 == "postinst" ]; then
    get_boot_device
    get_update_part
    echo "Installing boot files"

    if mountpoint -q /boot
    then
        echo "/boot is already mounted!"
    else
        echo "Mounting /boot"
        mount /dev/mmcblk0p1 /boot
    fi

    BOOTFILES="/tmp/rpi4-boot.tar.gz"

    cd /boot

    INSTALLED_BOOT="none"
    if test -f "/boot/version"; then
        INSTALLED_BOOT=$(</boot/version)
    fi
    NEW_BOOT=`sha256sum $BOOTFILES | cut -d " " -f 1`

    echo "Installed boot version : $INSTALLED_BOOT"
    echo "New boot version : $NEW_BOOT"

    if [ "$INSTALLED_BOOT" = "$NEW_BOOT" ]; then
        echo "Boot loader not updated..."
    else
        echo "Updating boot loader!"
        # ls -l
        # ls -l ${BOOTFILES}
        rm -rf /boot/*
        tar -xvzf $BOOTFILES
        echo $NEW_BOOT > /boot/version
    fi

    echo Setting boot partition to ${UPDATE_PART}
    sed -i "s/mmcblk0p${CURRENT_PART}/mmcblk0p${UPDATE_PART}/g" "/boot/cmdline.txt"

    sync
    reboot
fi
