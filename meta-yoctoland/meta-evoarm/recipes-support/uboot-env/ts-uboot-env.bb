# Copyright (C) 2020 Racepoint Energy
DESCRIPTION = "TS-7970 uboot environment settings files"
LICENSE = "CLOSED"

SRC_URI = "file://fw_env.config"

do_install() {
	install -v -d ${D}/etc
	install -v -m 644 ${WORKDIR}/fw_env.config ${D}/etc/fw_env.config
}

