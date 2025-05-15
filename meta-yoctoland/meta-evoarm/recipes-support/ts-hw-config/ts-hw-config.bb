# Copyright (C) 2020 Racepoint Energy
DESCRIPTION = "TS-7970 hw configuration files"
LICENSE = "CLOSED"

inherit systemd

RDEPENDS:${PN} = "ts4900-utils-tshwctl"

SRC_URI = " \
    file://ts7970-fpga-crossbar-setup.service \
	file://winstar.rules \
"

do_install() {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/ts7970-fpga-crossbar-setup.service ${D}${systemd_system_unitdir}
	
    install -d ${D}/etc/udev/rules.d
    install -m 644 ${WORKDIR}/winstar.rules ${D}/etc/udev/rules.d/winstar.rules 
}

SYSTEMD_SERVICE:${PN} = "ts7970-fpga-crossbar-setup.service"
