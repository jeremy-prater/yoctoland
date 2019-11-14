#!/bin/bash

DIRS="meta-openembedded meta-wandboard meta-qt5 meta-security poky meta-swupdate"

for dir in $DIRS; do
    echo " ==> Updating  $dir"
    pushd $dir
    git checkout warrior
    git pull
    popd
done