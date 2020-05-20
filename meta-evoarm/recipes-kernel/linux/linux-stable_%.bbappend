#LINUX_VERSION = "5.3"

FILESEXTRAPATHS_prepend := "${THISDIR}/linux-stable-${LINUX_VERSION}:"
SRC_URI += "file://defconfig"