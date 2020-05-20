#!/bin/bash

DIRS="meta-openembedded meta-wandboard meta-qt5 meta-security poky meta-swupdate meta-jumpnow"

for dir in $DIRS; do
    echo " ==> Updating  $dir"
    pushd $dir
    git checkout dunfell
    git pull
    popd
done
