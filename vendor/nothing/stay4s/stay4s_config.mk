# ================================================
# Stay4S Config - Nothing Phone (3a) AO59
# ================================================

# Basis Stay4S eigenschappen
PRODUCT_PROPERTY_OVERRIDES += \
    ro.stay4s.version=0.6-demo \
    ro.stay4s.ai=parallel \
    ro.stay4s.privacy=enabled \
    ro.stay4s.partner=grok

# Stay4S specifieke packages
PRODUCT_PACKAGES += \
    GrokAgentCore \
    GrokSecureMsg \
    GrokAdminSOS \
    GrokHyper

# Maak GrokAgentCore een privileged system app
PRODUCT_SYSTEM_PRIV_APP := GrokAgentCore

# Extra Stay4S instellingen
PRODUCT_CHARACTERISTICS := nosdcard

# Log niveau voor Stay4S
persist.logd.logsize=262144
