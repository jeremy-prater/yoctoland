FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://0001-add-ts7970-board.patch"
SRC_URI += "file://0002-gpt-add-part-uuid-and-part-num-subcommands.patch"
SRC_URI += "file://u-boot-ts-patch/configs/ts7970-q-2g-1000mhz-c_defconfig"
SRC_URI += "file://u-boot-ts-patch/"

do_copy_ts_patch() {
    echo "Adding TS7970 specific files!"
    cp -rav ${WORKDIR}/u-boot-ts-patch/* ${S}
}

addtask do_copy_ts_patch after do_unpack before do_patch

COMPATIBLE_MACHINE = "ts-7970"
UBOOT_SUFFIX = "imx"