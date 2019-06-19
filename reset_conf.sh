#!/bin/bash

ROOTDIR=$(realpath `git rev-parse --show-cdup`)
mkdir -p $ROOTDIR/build/conf

cp -v $ROOTDIR/meta-evoarm/conf/local.conf $ROOTDIR/build/conf/local.conf
cp -v $ROOTDIR/meta-evoarm/conf/bblayers.conf $ROOTDIR/build/conf/bblayers.conf
