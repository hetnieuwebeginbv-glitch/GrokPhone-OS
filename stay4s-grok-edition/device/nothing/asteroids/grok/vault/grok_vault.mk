#
# grok_vault.mk
# Makes Grok Vault a first-class privileged system component
# (not just a normal app inside the Grok package)
#

PRODUCT_PACKAGES += \
    GrokVault

# Grok Vault runs with high privileges and is protected/monitored by the Guardian
PRODUCT_PRODUCT_PROPERTIES += \
    ro.stay4s.grok.vault.privileged=true \
    ro.stay4s.grok.vault.guardian_protected=true \
    ro.stay4s.grok.vault.meshmatic_enabled=true

# Future: Grok Vault will have its own SELinux domain and direct access to keystore
