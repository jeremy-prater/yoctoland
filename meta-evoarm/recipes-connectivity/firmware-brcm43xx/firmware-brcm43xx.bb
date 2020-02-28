LICENSE="CLOSED"

inherit systemd

SRC_URI += "file://etc/conf.d/bcm43xx"
SRC_URI += "file://usr/bin/brcm_patchram_plus"
SRC_URI += "file://usr/lib/firmware/brcm/bcm4329.hcd"
SRC_URI += "file://usr/lib/firmware/brcm/bcm4330.hcd"
#SRC_URI += "file://usr/lib/firmware/brcm/brcmfmac4329-sdio.txt"
#SRC_URI += "file://usr/lib/firmware/brcm/brcmfmac4330-sdio.txt"
SRC_URI += "file://usr/lib/systemd/scripts/brcm43xx-firmware-update"
SRC_URI += "file://usr/lib/systemd/system/brcm43xx-firmware.service"
SRC_URI += "file://usr/lib/systemd/system/brcm43xx.service"


do_install() {
    install -m 0755 -d ${D}/etc/conf.d/
    install -m 0644 ${WORKDIR}/etc/conf.d/bcm43xx ${D}/etc/conf.d/

    install -m 0755 -d ${D}/usr/bin
    install -m 0755 ${WORKDIR}/usr/bin/brcm_patchram_plus ${D}/usr/bin

    install -m 0755 -d ${D}/lib/firmware/brcm/
    install -m 0644 ${WORKDIR}/usr/lib/firmware/brcm/bcm4329.hcd ${D}/lib/firmware/brcm/
    install -m 0644 ${WORKDIR}/usr/lib/firmware/brcm/bcm4330.hcd ${D}/lib/firmware/brcm/
    #install -m 0644 ${WORKDIR}/usr/lib/firmware/brcm/brcmfmac4329-sdio.txt ${D}/lib/firmware/brcm/
    #install -m 0644 ${WORKDIR}/usr/lib/firmware/brcm/brcmfmac4330-sdio.txt ${D}/lib/firmware/brcm/

    install -m 0755 -d ${D}/usr/lib/systemd/scripts
    install -m 0755 ${WORKDIR}/usr/lib/systemd/scripts/brcm43xx-firmware-update ${D}/usr/lib/systemd/scripts

    install -m 0755 -d ${D}/lib/systemd/system/
    install -m 0644 ${WORKDIR}/usr/lib/systemd/system/brcm43xx-firmware.service ${D}/lib/systemd/system
    install -m 0644 ${WORKDIR}/usr/lib/systemd/system/brcm43xx.service ${D}/lib/systemd/system
}

FILES_${PN} += "/etc/conf.d/bcm43xx"
FILES_${PN} += "/usr/bin/brcm_patchram_plus"
FILES_${PN} += "/lib/firmware/brcm/bcm4329.hcd"
FILES_${PN} += "/lib/firmware/brcm/bcm4330.hcd"
#FILES_${PN} += "/lib/firmware/brcm/brcmfmac4329-sdio.txt"
#FILES_${PN} += "/lib/firmware/brcm/brcmfmac4330-sdio.txt"
FILES_${PN} += "/usr/lib/systemd/scripts/brcm43xx-firmware-update"
FILES_${PN} += "/lib/systemd/system/brcm43xx-firmware.service"
FILES_${PN} += "/lib/systemd/system/brcm43xx.service"

SYSTEMD_SERVICE_${PN} = "brcm43xx-firmware.service"
SYSTEMD_SERVICE_${PN} = "brcm43xx.service"

INSANE_SKIP_${PN} = "ldflags"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INHIBIT_PACKAGE_STRIP = "1"