SUMMARY = "DarkySky system"

IMAGE_FEATURES += "package-management"
IMAGE_LINGUAS = "en-us"

inherit image

IMAGE_FSTYPES = "ext4.gz"

CORE_OS = " \
    openssh openssh-keygen openssh-sftp-server \
    packagegroup-core-boot \
    tzdata \
    swupdate \
    swupdate-www \
    swupdate-tools \
    darksky-server \
    darksky-version \
    linux-firmware \
    darksky-network-config \
    udev-rules \
    udev-automount \
    gpsd \
    connman \
"

KERNEL_EXTRA_INSTALL = " \
    kernel-modules \
"

DEPENDS += "bootfiles"

WIFI = " \
    crda \
    iw \
    linux-firmware-rpidistro-bcm43455 \
    wpa-supplicant \
"


BLUETOOTH_SUPPORT = " \
    bluez5 \
    bluez5-obex \
    bluez5-noinst-tools \
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
    rng-tools \
    sysfsutils \
    tcpdump \
    util-linux \
    util-linux-blkid \
    unzip \
    wget \
    zip \
    vim \
    tmux \
    htop \
"

IMAGE_INSTALL += " \
    ${CORE_OS} \
    ${DEV_SDK_INSTALL} \
    ${EXTRA_TOOLS_INSTALL} \
    ${KERNEL_EXTRA_INSTALL} \
    ${WIFI} \
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

do_image:append() {
    import os
    import pathlib
    import tarfile
    import shutil

    deploydir = d.getVar('DEPLOY_DIR_IMAGE', True)

    os.chdir(deploydir)
    print("Creating rpi4-64 boot image : " , os.getcwd())

    print("Removing existing boot folder")
    shutil.rmtree('./boot', ignore_errors=True)

    print('Copying boot firmware')
    shutil.copytree('./bcm2835-bootfiles/', './boot/')

    print('Creating overlay directory')
    pathlib.Path("./boot/overlays").mkdir(parents=True, exist_ok=True)

    print('Copying kernel')
    shutil.copyfile('Image', './boot/Image')

    deployFiles = [f for f in os.listdir('.') if os.path.isfile(os.path.join('.', f))]

    print('Copying devicetrees')
    for dFile in deployFiles:
        if dFile.endswith('.dtbo'):
            print('dtbo : ', dFile)
            shutil.copyfile(dFile, './boot/overlays/'+ dFile)

        if dFile.endswith('.dtb'):
            print('dtb : ', dFile)
            shutil.copyfile(dFile, './boot/' + dFile)

    with open("./boot/config.txt", "a") as configFile:
        print("enable_uart=1", file=configFile)
        print("kernel=Image", file=configFile)

    if os.path.isfile('rpi4-boot.tar.gz'):
        print ("Removing existing rpi4-boot.tar.gz")
        os.remove('rpi4-boot.tar.gz')

    def make_tarfile(output_filename, source_dir):
        print("Creating tarfile : ", output_filename)
        with tarfile.open(output_filename, "w:gz") as tar:
            tar.add(source_dir, arcname=os.path.basename(source_dir))

    os.chdir('./boot')
    make_tarfile('../rpi4-boot.tar.gz', '.')
}

ROOTFS_POSTPROCESS_COMMAND += " \
    remove_blacklist_files ; \
    set_local_timezone ; \
    disable_bootlogd ; \
    create_opt_dir ; \
"

export IMAGE_BASENAME = "darksky"
 