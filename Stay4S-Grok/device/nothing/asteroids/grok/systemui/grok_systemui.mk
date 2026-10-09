#
# grok_systemui.mk
# Removes stock SystemUI and prepares for a Grok-native system interface
#

# Remove stock SystemUI and related packages
PRODUCT_PACKAGES := $(filter-out SystemUI,$(PRODUCT_PACKAGES))
PRODUCT_PACKAGES := $(filter-out LineageSystemUI,$(PRODUCT_PACKAGES))
PRODUCT_PACKAGES := $(filter-out com.android.systemui,$(PRODUCT_PACKAGES))

# Placeholder for our own Grok SystemUI
# In later phases this will be a real forked/custom SystemUI
PRODUCT_PACKAGES += \
    GrokSystemUI

# For now we heavily overlay the stock one to make it feel Grok-native
PRODUCT_PACKAGE_OVERLAYS += device/nothing/asteroids/grok/systemui/overlay
