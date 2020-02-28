# Automount USB drive(s) to /media
DESCRIPTION = "udev automount rules"
LICENSE = "CLOSED"

inherit cmake

SRC_URI = " \
    file://automount.rules \
    file://usb-parse-devpath.cpp \
    file://CMakeLists.txt \
"

do_configure_prepend(){
    cp ${WORKDIR}/usb-parse-devpath.cpp ${S}
    cp ${WORKDIR}/CMakeLists.txt ${S}
}

do_install_append() {
	install -d ${D}${sysconfdir}/udev/rules.d
	install -m 0644 ${WORKDIR}/automount.rules ${D}${sysconfdir}/udev/rules.d
}
