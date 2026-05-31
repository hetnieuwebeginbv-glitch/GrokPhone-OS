#
# grok_ai_apps.mk
# Defines the Grok-native experiences that replace traditional apps
# These are not normal apps — they are part of the Grok OS layer
#

# Grok Vault / Grok Pay (highest priority own AI app)
# Will become a privileged component with direct Guardian protection
PRODUCT_PACKAGES += \
    GrokVault

# Future own AI experiences (to be built as privileged or system components):
# - GrokBrowser
# - GrokMessages
# - GrokCalls
# - GrokSoftwareCenter

# For now we declare the intent so the architecture is clear
PRODUCT_PRODUCT_PROPERTIES += \
    ro.stay4s.grok.has_own_vault=true \
    ro.stay4s.grok.has_own_payment_center=true
