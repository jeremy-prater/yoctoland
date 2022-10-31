DESCRIPTION = "Update recipe for DarkSky"
SECTION = ""

LICENSE = "CLOSED"

# Add all local files to be added to the SWU
# sw-description must always be in the list.
# You can extend with scripts or wahtever you need
SRC_URI = " \
    file://sw-description \
    file://update.sh \
    "

# images to build before building swupdate image
IMAGE_DEPENDS = "darksky virtual/kernel"

# images and files that will be included in the .swu image
SWUPDATE_IMAGES = "darksky rpi4-boot.tar.gz"

SWUPDATE_IMAGES_FSTYPES[darksky] = ".ext4.gz"

# a deployable image can have multiple format, choose one
#SWUPDATE_IMAGES_FSTYPES[core-image-full-cmdline] = ".ubifs"
#SWUPDATE_IMAGES_FSTYPES[uImage] = ".bin"

inherit swupdate