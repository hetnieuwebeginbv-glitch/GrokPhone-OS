# GrokPhone Product Makefile
PRODUCT_NAME := grokphone_asteroids
PRODUCT_DEVICE := asteroids
PRODUCT_BRAND := Nothing
PRODUCT_MODEL := GrokPhone
PRODUCT_MANUFACTURER := Nothing

# Grok integratie
PRODUCT_PACKAGES += Grok
PRODUCT_PROPERTY_OVERRIDES += \
    ro.grok.telecom_expert=true \
    ro.grok.assistant=system

# Grok AI Telecom Expert
PRODUCT_PACKAGES += Grok
PRODUCT_COPY_FILES += \
    device/nothing/asteroids/grok/privapp-permissions-grok.xml:/etc/permissions/privapp-permissions-grok.xml

PRODUCT_PROPERTY_OVERRIDES += \
    ro.grok.telecom_expert=true \
    ro.grok.assistant=system \
    ro.grok.agent_process=:agent
