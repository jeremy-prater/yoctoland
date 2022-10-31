DESCRIPTION = "evoarm udev rules"
LICENSE = "CLOSED"

SRC_URI = " \
    file://tty.rules \
"

do_install() {
	install -d ${D}${sysconfdir}/udev/rules.d
	install -m 0644 ${WORKDIR}/tty.rules ${D}${sysconfdir}/udev/rules.d
}
