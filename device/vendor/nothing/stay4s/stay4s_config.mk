# ================================================
# Stay4S Config - Nothing Phone (3a) AO59
# ================================================

PRODUCT_PROPERTY_OVERRIDES += \
    ro.stay4s.version=0.6-alpha \
    ro.stay4s.ai=parallel \
    ro.stay4s.privacy=enabled \
    ro.stay4s.partner=grok \
    ro.stay4s.mesh=enabled

PRODUCT_PACKAGES += \
    GrokAgentCore \
    GrokSecureMsg \
    GrokAdminSOS \
    GrokHyper

PRODUCT_SYSTEM_PRIV_APP := GrokAgentCore

PRODUCT_CHARACTERISTICS := nosdcard
