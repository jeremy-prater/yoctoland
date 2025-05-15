SUMMARY = "TS-4900 Utils"
DESCRIPTION = "Technologic Systems utilities, gpioctl, tshwctl, nvramctl"
AUTHOR = "Mark Featherston <mark@embeddedarm.com>"
HOMEPAGE = "http://www.embeddedarm.com/"
SECTION = "base"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI = "git://github.com/embeddedarm/ts4900-utils.git;branch=master \
           file://tssilomon.service \
"
SRCREV = "6bed161354df7967d8a8646c7b0f81a26d764204"
PV = "1.0.0+git${SRCPV}"
S = "${WORKDIR}/git"

DEPENDS += "libgpiod libtool automake"
RDEPENDS:${PN} = "bash libgpiod"

inherit autotools systemd

do_install:append() {
    install -m 0755 ${S}/script/tssilomon ${D}${bindir}
    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
        install -d ${D}${systemd_unitdir}/system
        install -m 0644 ${WORKDIR}/tssilomon.service ${D}${systemd_unitdir}/system

        sed -i -e 's#@BINDIR@#${bindir}#g' ${D}${systemd_unitdir}/system/tssilomon.service
    fi
}

PACKAGES:prepend = "${PN}-tshwctl "

FILES:${PN}-tshwctl = "${bindir}/tshwctl"

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE:${PN} = "tssilomon.service"
SYSTEMD_AUTO_ENABLE = "enable"
