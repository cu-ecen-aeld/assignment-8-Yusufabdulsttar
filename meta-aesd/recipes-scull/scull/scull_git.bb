# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
LICENSE = "Unknown"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f098732a73b5f6f3430472f5b094ffdb"

SRC_URI = "git://github.com/cu-ecen-aeld/assignment-7-Yusufabdulsttar;protocol=https;branch=master \
           file://0001-Modify-the-ldd3-makefile.patch \
           file://files/scull-start-stop \
           "

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "2ddf5578e7f39085c37dd519ea7bb37d9c7a5b50"

S = "${WORKDIR}/git"

inherit module

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}/scull"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

# for start
inherit update-rc.d
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "scull-start-stop"
KERNEL_MODULE_AUTOLOAD += "scull"

FILES:${PN} += "${sysconfdir}/init.d/scull-start-stop"


do_install () {
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/files/scull-start-stop ${D}${sysconfdir}/init.d
	
	install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/
	install -m 0755 ${S}/scull/scull.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/
}
