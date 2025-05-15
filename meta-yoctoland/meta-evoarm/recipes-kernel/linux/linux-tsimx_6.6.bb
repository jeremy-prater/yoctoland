require recipes-kernel/linux/linux-yocto.inc

DEPENDS += "kern-tools-native xz-native bc-native lzop-native"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

SRCBRANCH = "master"
KERNEL_SRC ?= "git://git.kernel.org/pub/scm/linux/kernel/git/torvalds/linux.git"
SRC_URI = "${KERNEL_SRC};branch=${SRCBRANCH}"
SRCREV = "v6.9"
LINUX_VERSION ?= "6.9"

PV = "${LINUX_VERSION}+git${SRCPV}"

SRC_URI += "file://imx6qdl-ts7970.dtsi"
SRC_URI += "file://ts7970_defconfig"
SRC_URI += "file://0001-Add-port-of-max3100-ts-driver-for-uarts-on-TS-7970-F.patch"

KERNEL_DEFCONFIG = "${WORKDIR}/ts7970_defconfig"

KERNEL_FEATURES:remove = "cfg/fs/vfat.scc"

do_patch:append() {
   # Update imx6qdl-ts7970.dtsi
   cp ${WORKDIR}/imx6qdl-ts7970.dtsi ${S}/arch/arm/boot/dts/nxp/imx/imx6qdl-ts7970.dtsi
}

COMPATIBLE_MACHINE = "ts-7970"

# addtask shared_workdir_fixmodulesymvers after do_compile before do_shared_workdir

# This is to work around an issue that has been fixed upstream
# See : 
# https://github.com/openembedded/openembedded-core/commit/cd2d62a08a1dfcd890a03ee55132b6d6c65f5ab7
#
# kernel.bbclass: Fix Module.symvers support

# Starting from v5.8-rc1 commit 269a535ca931 (modpost: generate
# vmlinux.symvers and reuse it for the second modpost"), kernel will
# generate new vmlinux.symvers instead of dumping all the vmlinux symbols
# into Module.symvers in the first pass.

# Error log:
#     'run.do_shared_workdir.16614' failed with exit code 1:
#     DEBUG: cp: cannot stat 'Module.symvers': No such file or directory

# This change will check the file Module.symvers existence before copying it.

# Signed-off-by: Lili Li <lili.li@intel.com>
# Signed-off-by: Richard Purdie <richard.purdie@linuxfoundation.org>

# do_shared_workdir_fixmodulesymvers () {
#    echo "do_shared_workdir_fixmodulesymvers"
#    touch ${B}/Module.symvers
# }
