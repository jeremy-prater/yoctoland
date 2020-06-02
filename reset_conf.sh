#!/bin/bash

if [ -z "$BUILD_DIR" ]
then
    echo "No BUILD_DIR specified!"
    echo "Go Home!!!!"
    echo "source ./env_init <build_dir>"
    return 1
fi


ROOTDIR=$(realpath `git rev-parse --show-cdup`)
mkdir -p $ROOTDIR/$BUILD_DIR/conf

cp -v $ROOTDIR/conf/local.conf $ROOTDIR/$BUILD_DIR/conf/local.conf
cp -v $ROOTDIR/conf/bblayers.conf $ROOTDIR/$BUILD_DIR/conf/bblayers.conf
