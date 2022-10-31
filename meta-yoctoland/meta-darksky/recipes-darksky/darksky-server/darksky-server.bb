DESCRIPTION = "DarkSky python motor socket server"
LICENSE = "CLOSED"

SRCBRANCH = "main"
SRCREV = "${AUTOREV}"

SRC_URI = " \
    git://git@github.com/jeremy-prater/DarkSky.git;protocol=ssh;branch=${SRCBRANCH} \
"

SRC_URI += "file://darksky-server.service"

inherit systemd

S = "${WORKDIR}/git/socket-server"

DEPENDS = " \
"

RDEPENDS:${PN} = " \
    python3 \
"

do_install() {
    # Install python sources
    install -m 0755 -d ${D}${bindir}/server/
    cp -v *.py ${D}${bindir}/server

    # Install service file
    install -m 0755 -d ${D}${systemd_unitdir}/system/
    install -m 0644 ${WORKDIR}/darksky-server.service ${D}${systemd_unitdir}/system
}

FILES:${PN} += "${bindir}/server/*"
FILES:${PN} += "${systemd_system_unitdir}/darksky-server.service"

SYSTEMD_SERVICE:${PN} += "darksky-server.service"

# inherit externalsrc
# EXTERNALSRC = "/home/prater/src/DarkSky/server/"
# EXTERNALSRC_BUILD = "/home/prater/src/DarkSky/server/"