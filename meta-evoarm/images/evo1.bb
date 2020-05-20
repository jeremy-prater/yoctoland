SUMMARY = "The EVO ARM image"

IMAGE_FEATURES += "package-management"
IMAGE_LINGUAS = "en-us"

inherit image

IMAGE_FSTYPES = "ext4.gz"

CORE_OS = " \
    openssh openssh-keygen openssh-sftp-server \
    packagegroup-core-boot \
    term-prompt \
    tzdata \
    u-boot-scr \
    swupdate \
    swupdate-www \
    swupdate-tools \
    linux-firmware \
    evoarm-bsp \
    evoarm-network-config \
    udev-rules \
    udev-automount \
"

KERNEL_EXTRA_INSTALL = " \
    kernel-modules \
"

WIFI_SUPPORT = " \
    bcm4329-nvram-config \
    bcm4330-nvram-config \
    crda \
    iw \
    rfkill \
    wpa-supplicant \
    connman \
    connman-client \
"

BLUETOOTH_SUPPORT = " \
    bluez5 \
    bluez5-obex \
    bluez5-noinst-tools \
    firmware-brcm43xx \
"

AUDIO_SUPPORT = " \
    pulseaudio \
"


DEV_SDK_INSTALL = " \
    binutils \
    binutils-symlinks \
    coreutils \
    cpp \
    cpp-symlinks \
    diffutils \
    elfutils elfutils-binutils \
    file \
    gcc \
    gcc-symlinks \
    gdb \
    g++ \
    g++-symlinks \
    gettext \
    git \
    ldd \
    libstdc++ \
    libstdc++-dev \
    libtool \
    ltrace \
    make \
    perl-modules \
    pkgconfig \
    python3-modules \
    strace \
"

EXTRA_TOOLS_INSTALL = " \
    bzip2 \
    curl \
    dosfstools \
    e2fsprogs-mke2fs \
    ethtool \
    fbset \
    findutils \
    firewall \
    grep \
    i2c-tools \
    ifupdown \
    iperf3 \
    iproute2 \
    iptables \
    less \
    lsof \
    netcat-openbsd \
    nmap \
    ntp ntp-tickadj \
    parted \
    procps \
    rndaddtoentcnt \
    rng-tools \
    root-upgrader \
    sysfsutils \
    tcpdump \
    util-linux \
    util-linux-blkid \
    unzip \
    wget \
    zip \
    vim \
"

IMAGE_INSTALL += " \
    ${CORE_OS} \
    ${DEV_SDK_INSTALL} \
    ${EXTRA_TOOLS_INSTALL} \
    ${KERNEL_EXTRA_INSTALL} \
    ${WIFI_SUPPORT} \
    ${BLUETOOTH_SUPPORT} \
    ${AUDIO_SUPPORT} \
"

IMAGE_FILE_BLACKLIST += " \
    /etc/init.d/hwclock.sh \
"

remove_blacklist_files() {
    for i in ${IMAGE_FILE_BLACKLIST}; do
        rm -rf ${IMAGE_ROOTFS}$i
    done
}

set_local_timezone() {
    ln -sf /usr/share/zoneinfo/EST5EDT ${IMAGE_ROOTFS}/etc/localtime
}

disable_bootlogd() {
    echo BOOTLOGD_ENABLE=no > ${IMAGE_ROOTFS}/etc/default/bootlogd
}

create_opt_dir() {
    mkdir -p ${IMAGE_ROOTFS}/opt
}

ROOTFS_POSTPROCESS_COMMAND += " \
    remove_blacklist_files ; \
    set_local_timezone ; \
    disable_bootlogd ; \
    create_opt_dir ; \
"

export IMAGE_BASENAME = "evo1"
