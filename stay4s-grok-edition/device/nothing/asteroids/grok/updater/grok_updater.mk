#
# grok_updater.mk
# Grok-controlled OTA and system updates (replaces stock Lineage updater)
#

# Remove stock updater
PRODUCT_PACKAGES := $(filter-out Updater,$(PRODUCT_PACKAGES))
PRODUCT_PACKAGES := $(filter-out LineageUpdater,$(PRODUCT_PACKAGES))

# Our own Grok Updater (will be built as a privileged component)
PRODUCT_PACKAGES += \
    GrokUpdater

PRODUCT_PRODUCT_PROPERTIES += \
    ro.stay4s.grok.controls_updates=true \
    ro.stay4s.grok.updater.guardian_approved=true
