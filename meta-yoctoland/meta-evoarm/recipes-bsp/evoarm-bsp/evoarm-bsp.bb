LICENSE="CLOSED"

SRC_URI += "file://sw-versions"

do_install() {
    install -d ${D}/etc
    install -m 0644 ${WORKDIR}/sw-versions ${D}/etc
}

FILES:${PN} += "/etc/sw-versions"