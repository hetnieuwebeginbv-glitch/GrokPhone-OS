# ================================================
# Stay4S / GrokPhone Product Definition
# ================================================

PRODUCT_NAME := stay4s_asteroids
PRODUCT_DEVICE := asteroids
PRODUCT_BRAND := Nothing
PRODUCT_MODEL := Stay4S GrokPhone
PRODUCT_MANUFACTURER := Nothing

PRODUCT_GMS_CLIENTID_BASE := android-nothing

# Stay4S packages (privileged)
PRODUCT_PACKAGES += \
    GrokAgentCore \
    GrokSecureMsg \
    GrokAdminSOS \
    GrokHyper

PRODUCT_SYSTEM_PRIV_APP := GrokAgentCore

# Stay4S properties
PRODUCT_PROPERTY_OVERRIDES += \
    ro.stay4s.version=0.6-alpha \
    ro.stay4s.ai=parallel \
    ro.stay4s.privacy=enabled \
    ro.stay4s.partner=grok

$(call inherit-product, vendor/nothing/stay4s/stay4s_config.mk)
