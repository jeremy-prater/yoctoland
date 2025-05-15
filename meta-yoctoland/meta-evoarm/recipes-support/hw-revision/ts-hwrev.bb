# Copyright (C) 2020 Racepoint Energy
DESCRIPTION = "IMX6 thermostat hw revision file"
LICENSE = "CLOSED"

SRC_URI = "file://hwrevision"

do_install() {
	install -v -d ${D}/etc
	install -v -m 644 ${WORKDIR}/hwrevision ${D}/etc/hwrevision
}

