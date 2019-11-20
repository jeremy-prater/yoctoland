LICENSE="CLOSED"

SRC_URI += "file://20-wired.network"

do_install() {
    install -d ${D}/etc/systemd/network
    install -m 0644 ${WORKDIR}/20-wired.network ${D}/etc/systemd/network
}

FILES_${PN} += "/etc/systemd/network/20-wired.network"
