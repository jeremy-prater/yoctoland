#!/bin/bash

ROOTDIR=$(realpath `git rev-parse --show-cdup`)

cd $ROOTDIR

DIRS="meta-openembedded meta-wandboard meta-rpi64 meta-qt5 meta-security poky meta-raspberrypi meta-swupdate meta-jumpnow"

for dir in $DIRS; do
    echo " ==> Updating  $dir"
    pushd $dir
    git checkout dunfell
    git pull
    popd
done

APPDIRS="meta-evoarm"

for dir in $APPDIRS; do
    echo " ==> Updating  $dir"
    pushd $dir
    git checkout master
    git pull
    popd
done
