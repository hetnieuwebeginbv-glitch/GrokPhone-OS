#
# grok_native_apis.mk
# Defines the path toward native (non-Accessibility) APIs for the Grok Brain
#

# Long-term goal: The Parallel Grok Brain gets safe, direct, auditable APIs
# instead of relying on AccessibilityService hacks.

PRODUCT_PRODUCT_PROPERTIES += \
    ro.stay4s.grok.native_apis.planned=true \
    ro.stay4s.grok.tooluse.native_future=true

# For now we document the intent and keep using privileged + Accessibility
# In Phase 2 we will introduce real system services for the Brain.
