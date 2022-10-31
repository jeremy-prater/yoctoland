cmake_minimum_required(VERSION 3.2)
project (usb-parse-devpath)
set (CMAKE_CXX_STANDARD 14)

add_executable(usb-parse-devpath usb-parse-devpath.cpp )
install(TARGETS usb-parse-devpath DESTINATION bin)
