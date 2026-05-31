# GrokPhone - Nothing Phone (asteroids)




# Device specific
PRODUCT_DEVICE := asteroids
PRODUCT_NAME := grokphone_asteroids
PRODUCT_BRAND := Nothing
PRODUCT_MODEL := Phone (3a)
PRODUCT_MANUFACTURER := Nothing

# Grok integratie
PRODUCT_PACKAGES += Grok
PRODUCT_COPY_FILES += \
    device/nothing/asteroids/grok/privapp-permissions-grok.xml:/etc/permissions/privapp-permissions-grok.xml
