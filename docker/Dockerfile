############################################################
# Dockerfile to build yocto build environment image
# Based on Ubuntu 18.04
############################################################

FROM ubuntu:18.04

MAINTAINER Jeremy Prater

# RUN dpkg --add-architecture i386
RUN apt-get dist-upgrade
RUN apt-get update
RUN apt-get -y upgrade
RUN apt-get install -y gawk wget git-core diffstat unzip texinfo gcc-multilib \
     build-essential chrpath socat libsdl1.2-dev xterm cpio g++-multilib tmux \
     python3 iputils-ping locales debconf language-pack-en-base language-pack-en \
     vim 
ENV LANG=en_US.UTF-8
RUN apt-get install -y locales && \
   sed -i -e "s/# $LANG.*/$LANG.UTF-8 UTF-8/" /etc/locale.gen && \
   dpkg-reconfigure --frontend=noninteractive locales && \
   update-locale LANG=$LANG

RUN apt-get install -y gawk wget git-core diffstat unzip texinfo gcc-multilib gobjc \
    build-essential chrpath libsdl1.2-dev xterm autoconf automake libtool libglib2.0-dev git-annex \
    flex byacc

# RUN apt-get install -y python3-venv python3-pip

#Get the add-apt-repository command
# RUN apt-get install -y software-properties-common

# RUN apt-get install -y python2.7
# RUN apt-get install -y repo
# RUN add-apt-repository ppa:longsleep/golang-backports
# RUN apt-get update
# RUN apt-get install -y golang-go

# RUN apt-get install -y libtool

# RUN apt-get install -y bzr protobuf-compiler

# RUN curl -sS https://dl.yarnpkg.com/debian/pubkey.gpg | apt-key add -
# RUN echo "deb https://dl.yarnpkg.com/debian/ stable main" | tee /etc/apt/sources.list.d/yarn.list
# RUN apt update
# RUN apt install -y yarn
# RUN apt install -y iproute2

# RUN apt install -y llvm clang
# RUN apt install -y lld
# RUN apt install -y strace

#RUN dpkg-reconfigure locales

#Include valhalla
#RUN add-apt-repository -y ppa:valhalla-core/valhalla
#RUN apt-get update
#RUN apt-get install -y valhalla-bin

WORKDIR "/work/yoctoland"
