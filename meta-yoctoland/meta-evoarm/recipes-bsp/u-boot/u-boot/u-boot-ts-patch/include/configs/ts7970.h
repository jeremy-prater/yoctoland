/*
 * Copyright (C) 2010-2011 Freescale Semiconductor, Inc.
 *
 * Configuration settings for the Technologic Systems TS-7970
 *
 * SPDX-License-Identifier:    GPL-2.0+
 */

#ifndef __TS7990_CONFIG_H
#define __TS7990_CONFIG_H

#include "mx6_common.h"

#include <asm/arch/imx-regs.h>
#include <linux/sizes.h>

/* FPGA Config */

#define CONFIG_FPGA_TDI                 IMX_GPIO_NR(5, 16)
#define CONFIG_FPGA_TMS                 IMX_GPIO_NR(5, 8)
#define CONFIG_FPGA_TCK                 IMX_GPIO_NR(5, 11)
#define CONFIG_FPGA_TDO                 IMX_GPIO_NR(5, 12)

/* LED config */
#define CONFIG_STATUS_LED
#define CONFIG_RED_LED                  IMX_GPIO_NR(1, 2)
#define CONFIG_GREEN_LED                IMX_GPIO_NR(3, 27)
#define CONFIG_YEL_LED                  IMX_GPIO_NR(1, 9)
#define CONFIG_BLUE_LED                 IMX_GPIO_NR(4, 25)


/* Create build specific env vars */

#define CONFIG_IMX_TYPE "ts7970-q-2g-1000mhz-c\0"

#define ENV_IMX_TYPE "imx_type="CONFIG_IMX_TYPE"\0"
#ifdef CONFIG_MX6Q
#define ENV_CPU_TYPE "cpu=q\0"
#else
#define ENV_CPU_TYPE "cpu=dl\0"
#endif

/* USB Configuration */

#define CONFIG_MXC_USB_PORTSC   (PORT_PTS_UTMI | PORT_PTS_PTW)

/* UART Configuration */

#define CONFIG_MXC_UART_BASE            UART1_BASE
#define CFG_MXC_UART_BASE               UART1_BASE


/* FSL USDHC Configuration */

#define CONFIG_SYS_FSL_ESDHC_ADDR      0
#define CONFIG_SYS_FSL_USDHC_NUM       2

/* I2C Configuration */

#define CONFIG_SYS_I2C

/* SATA Configuration */

#define CONFIG_SYS_SATA_MAX_DEVICE    1

/* FEC Configuration */

#define IMX_FEC_BASE                  ENET_BASE_ADDR
#define CONFIG_FEC_XCV_TYPE           RGMII
#define CONFIG_ETHPRIME               "FEC"
#define CONFIG_FEC_MXC_PHYADDR        7


/* Memory Configuration */
#define CONFIG_SYS_MAX_FLASH_BANKS 1

/* Physical Memory Map */

#define CONFIG_DDR_MB 2048

#define PHYS_SDRAM                      MMDC0_ARB_BASE_ADDR
#define CONFIG_SYS_SDRAM_BASE           PHYS_SDRAM

#define CONFIG_SYS_INIT_RAM_ADDR       IRAM_BASE_ADDR
#define CONFIG_SYS_INIT_RAM_SIZE       IRAM_SIZE

#define CFG_SYS_INIT_RAM_ADDR          IRAM_BASE_ADDR
#define CFG_SYS_INIT_RAM_SIZE          IRAM_SIZE


#define CONFIG_SYS_INIT_SP_OFFSET \
    (CONFIG_SYS_INIT_RAM_SIZE - GENERATED_GBL_DATA_SIZE)
#define CONFIG_SYS_INIT_SP_ADDR \
    (CONFIG_SYS_INIT_RAM_ADDR + CONFIG_SYS_INIT_SP_OFFSET)

#define CFG_SYS_INIT_SP_OFFSET \
    (CONFIG_SYS_INIT_RAM_SIZE - GENERATED_GBL_DATA_SIZE)
#define CFG_SYS_INIT_SP_ADDR \
    (CONFIG_SYS_INIT_RAM_ADDR + CONFIG_SYS_INIT_SP_OFFSET)

/* I2C speed */

#define CONFIG_SYS_I2C_SPEED 100000

/* Default Env */

#define CONFIG_EXTRA_ENV_SETTINGS \
    "fitimage=/boot/fitImage\0" \
    "uimage=/boot/uImage\0" \
    "initrd_high=0xffffffff\0" \
    "fdtaddr=0x18000000\0" \
    "fdt_high=0xffffffff\0" \
    "initrd_addr=0x10800000\0" \
    ENV_IMX_TYPE \
    ENV_CPU_TYPE \
    "model=7970\0" \
    "autoload=no\0" \
    "disable_giga=1\0" \
    "emmcbootlabel=rootfs1\0" \
    "cmdline_append=console=ttymxc0,115200 cma=256M@2G rootwait ro init=/sbin/init\0" \
    "clearenv=if sf probe; then " \
        "sf erase 0x100000 0x100000;" \
        "sf erase 0x200000 0x100000;" \
        "echo restored environment to factory default; fi\0" \
    "findfdt=" \
        "if test $rev > 'E'; then " \
            "if load ${bootdev} ${bootpart} ${fdtaddr} /boot/imx6${cpu}-ts7970-revf.dtb; then " \
                "echo Loaded TS-7970 REV F dtb;" \
            "elif load ${bootdev} ${bootpart} ${fdtaddr} /boot/imx6${cpu}-ts7970.dtb; then " \
                "echo Booting TS-7970 dtb;" \
            "fi;" \
        "else " \
            "if load ${bootdev} ${bootpart} ${fdtaddr} /boot/imx6${cpu}-ts7970.dtb; then " \
                "echo Booting TS-7970 dtb;" \
            "fi;" \
        "fi;\0" \
    "bootlinux=if load ${bootdev} ${bootpart} ${loadaddr} /boot/boot.ub; " \
            "then echo Booting from custom /boot/boot.ub; " \
            "source ${loadaddr}; " \
        "fi; " \
        "run findfdt; " \
        "if load ${bootdev} ${bootpart} ${loadaddr} /boot/ts7970-fpga.vme; " \
            "then fpga load 0 ${loadaddr} ${filesize};" \
        "else " \
            "echo Using default built in FPGA;" \
        "fi;" \
        "load ${bootdev} ${bootpart} ${loadaddr} ${uimage}; " \
        "setenv bootargs root=${rootdev} ${cmdline_append}; " \
        "bootm ${loadaddr} - ${fdtaddr};\0" \
    "bootfitlinux=if load ${bootdev} ${bootpart} ${loadaddr} /boot/boot.ub; " \
            "then echo Booting from custom /boot/boot.ub; " \
            "source ${loadaddr}; " \
        "fi; " \
        "load ${bootdev} ${bootpart} ${loadaddr} ${fitimage};" \
        "setenv bootargs root=${rootdev} ${cmdline_append}; " \
        "bootm ${loadaddr};\0" \
    "sdboot=echo Booting from the SD card ...; " \
        "env set bootdev mmc; " \
        "env set bootpart 0:1; " \
        "env set rootdev '/dev/mmcblk1p1'; " \
        "run bootlinux;\0" \
    "emmcboot=echo Booting from the eMMC ...; " \
        "gpt part-num mmc 1 ${emmcbootlabel} emmcbootpart; " \
        "env set bootdev mmc; " \
        "env set bootpart 1:${emmcbootpart}; " \
        "env set rootdev PARTLABEL=${emmcbootlabel}; " \
        "run bootfitlinux;\0" \
    "sataboot=echo Booting from SATA ...; " \
        "env set bootdev sata; " \
        "env set bootpart 0:1; " \
        "env set rootdev '/dev/sda1'; " \
        "sata init; " \
        "run bootlinux;\0" \
    "usbboot=echo Booting from USB ...; " \
        "env set bootdev usb; " \
        "env set bootpart 0:1; " \
        "env set rootdev '/dev/sda1'; " \
        "usb start; " \
        "run bootlinux;\0" \
    "usbprod=usb start;" \
        "if usb storage;" \
            "then echo Checking USB storage for updates;" \
            "if load usb 0:1 ${loadaddr} /tsinit.ub;" \
                "then led green on;" \
                "source ${loadaddr};" \
                "led red off;" \
                "exit;" \
            "fi;" \
        "fi;\0" \
    "nfsboot=echo Booting from NFS ...;" \
        "dhcp;" \
        "nfs ${fdtaddr} ${nfsroot}/boot/imx6${cpu}-ts7970-revf.dtb;" \
        "nfs ${loadaddr} ${nfsroot}/boot/uImage;" \
        "setenv bootargs root=/dev/nfs ip=dhcp nfsroot=${nfsroot} ${cmdline_append};" \
        "bootm ${loadaddr} - ${fdtaddr};\0"

#endif /*__TS7990_CONFIG_H */
