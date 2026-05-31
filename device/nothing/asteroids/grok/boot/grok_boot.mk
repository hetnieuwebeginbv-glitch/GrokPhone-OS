#
# grok_boot.mk
# Own boot experience: animation, sounds, early Grok presence
#

# Custom boot animation (replace the zip with a real one later)
# PRODUCT_COPY_FILES += \
#     device/nothing/asteroids/grok/boot/bootanimation.zip:$(TARGET_COPY_OUT_PRODUCT)/media/bootanimation.zip

# Early Grok presence
PRODUCT_PRODUCT_PROPERTIES += \
    ro.stay4s.grok.boot_animation=true \
    ro.stay4s.grok.early_intelligence=true

# Placeholder for custom sounds (UI, notifications, etc.)
# PRODUCT_COPY_FILES += \
#     device/nothing/asteroids/grok/sounds/*:$(TARGET_COPY_OUT_PRODUCT)/media/audio/ui/
