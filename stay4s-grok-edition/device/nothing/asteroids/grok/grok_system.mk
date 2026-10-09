#
# grok_system.mk
# This file ties the Grok AI layer (Brain + Guardian) into the operating system
# as first-class citizens instead of normal apps.
#

# Core Grok privileged components
PRODUCT_PACKAGES += \
    Grok

# Grok is allowed to act as the system assistant and more
PRODUCT_PRODUCT_PROPERTIES += \
    ro.stay4s.grok.is_system_intelligence=true \
    ro.stay4s.grok.controls_default_experience=true

# In the future this will include:
# - Grok System Services (native privileged services)
# - Grok Framework extensions
# - Direct Brain APIs for safe system control

# For now we heavily rely on the privileged app + overlays + Guardian control
