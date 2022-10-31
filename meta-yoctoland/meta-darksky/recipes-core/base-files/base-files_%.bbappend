FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

do_install:append() {
    echo "Creating /data mount point"
    install -m 0755 -d ${D}/data/
}

FILES:${PN} += "/data"