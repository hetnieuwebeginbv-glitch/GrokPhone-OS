# Stay4S Grok - Security Properties (add to device.mk or stay4s_*.mk)
#
# Privacy & Cybersecurity hardened defaults for the Grok edition

PRODUCT_PRODUCT_PROPERTIES += \
    ro.grok.agent.enabled=true \
    ro.grok.agent.sandboxed=true \
    ro.grok.agent.process=:agent \
    ro.grok.agent.version=0.7.0-parallel \
    ro.grok.partnership.enforced=true \
    ro.grok.explicit_consent.required=true \
    ro.grok.audit.log_to_admin=true

# Reduce attack surface for privileged AI component
PRODUCT_PRODUCT_PROPERTIES += \
    ro.adb.secure=1 \
    ro.secure=1 \
    ro.debuggable=0

# Future: hardware-backed features
# ro.grok.killswitch.gpio.enabled=true
