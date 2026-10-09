# ================================================
# Stay4S / GrokPhone Overlay voor Nothing Phone (3a)
# ================================================

# Product definitie
PRODUCT_NAME := stay4s_asteroids
PRODUCT_DEVICE := asteroids
PRODUCT_BRAND := Nothing
PRODUCT_MODEL := Nothing Phone (3a)
PRODUCT_MANUFACTURER := Nothing

# Basis Stay4S eigenschappen
PRODUCT_GMS_CLIENTID_BASE := android-nothing

# Voeg onze Stay4S packages toe
PRODUCT_PACKAGES += \
    GrokAgentCore \
    GrokSecureMsg \
    GrokAdminSOS \
    GrokHyper

# Maak GrokAgentCore een privileged system app
PRODUCT_SYSTEM_PRIV_APP := GrokAgentCore

# Stay4S specifieke eigenschappen
PRODUCT_PROPERTY_OVERRIDES += \
    ro.stay4s.version=0.6-demo \
    ro.stay4s.ai=parallel \
    ro.stay4s.privacy=enabled

# Inclusie van Stay4S specifieke configuraties
$(call inherit-product, vendor/nothing/stay4s/stay4s_config.mk)
