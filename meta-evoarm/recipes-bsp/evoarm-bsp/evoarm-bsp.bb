LICENSE="CLOSED"

SRC_URI += "file://hwrevision"
SRC_URI += "file://sw-versions"
SRC_URI += "file://fw_env.config"

do_install() {
    install -d ${D}/etc
    install -m 0644 ${WORKDIR}/hwrevision ${D}/etc
    install -m 0644 ${WORKDIR}/sw-versions ${D}/etc
    install -m 0644 ${WORKDIR}/fw_env.config ${D}/etc
}

FILES_${PN} += "/etc/hwrevision"
FILES_${PN} += "/etc/sw-versions"
FILES_${PN} += "/etc/fw_env.config"
