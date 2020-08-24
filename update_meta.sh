#!/bin/bash

ROOTDIR=$(realpath `git rev-parse --show-cdup`)

cd $ROOTDIR

DIRS=" \
    meta-cloud-services \
    meta-jumpnow \
    meta-openembedded \
    meta-qt5 \
    meta-raspberrypi \
    meta-security \
    meta-swupdate \
    meta-virtualization \
    meta-wandboard \
    poky \
"

for dir in $DIRS; do
    echo " ==> Updating  $dir"
    pushd $dir
    git checkout dunfell
    git pull
    popd
done

APPDIRS="
    meta-evoarm \
    meta-darksky \
"

for dir in $APPDIRS; do
    echo " ==> Updating  $dir"
    pushd $dir
    git checkout trunk
    git pull
    popd
done
