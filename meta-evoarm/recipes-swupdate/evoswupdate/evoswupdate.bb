DESCRIPTION = "EVO ARM SWUpdate image"
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
IMAGE_DEPENDS = "evo1 virtual/kernel"

# images and files that will be included in the .swu image
SWUPDATE_IMAGES = "evo1"

SWUPDATE_IMAGES_FSTYPES[evo1] = ".ext4.gz"

# a deployable image can have multiple format, choose one
#SWUPDATE_IMAGES_FSTYPES[core-image-full-cmdline] = ".ubifs"
#SWUPDATE_IMAGES_FSTYPES[uImage] = ".bin"

inherit swupdate