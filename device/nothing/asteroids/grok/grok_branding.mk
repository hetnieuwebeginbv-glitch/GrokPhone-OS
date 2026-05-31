#
# grok_branding.mk
# Aggressive rebranding to make GrokPhone feel like a completely separate OS
#

# Product identity
PRODUCT_BRAND := Stay4S
PRODUCT_NAME := stay4s_grok_edition_asteroids
PRODUCT_MODEL := GrokPhone
PRODUCT_MANUFACTURER := Stay4S

# Remove Android/Lineage references where possible
PRODUCT_BUILD_PROP_OVERRIDES += \
    PRODUCT_NAME=GrokPhone \
    PRODUCT_BRAND=Stay4S \
    PRODUCT_MODEL=GrokPhone \
    PRODUCT_MANUFACTURER=Stay4S \
    BuildDesc="GrokPhone-user 15 AP2A.240905.003 20260601 release-keys" \
    BuildFingerprint=Stay4S/GrokPhone/asteroids:15/AP2A.240905.003/20260601:user/release-keys

# Custom boot animation (placeholder - replace with real zip later)
# PRODUCT_COPY_FILES += \
#     device/nothing/asteroids/grok/bootanimation.zip:$(TARGET_COPY_OUT_PRODUCT)/media/bootanimation.zip

# Future: Custom sounds
# PRODUCT_COPY_FILES += \
#     device/nothing/asteroids/grok/sounds/*:$(TARGET_COPY_OUT_PRODUCT)/media/audio/ui/
