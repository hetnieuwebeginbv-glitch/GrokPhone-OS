#
# stay4s_grok_edition.mk
# Stay4S GrokPhone - Full Custom ROM Product Definition
# LineageOS 22.1 (Android 15) for Nothing Phone (asteroids)
#

# Inherit from the base device
$(call inherit-product, device/nothing/asteroids/device.mk)

# Inherit from LineageOS common configuration
$(call inherit-product, vendor/lineage/config/common_full_phone.mk)

PRODUCT_NAME := stay4s_grok_edition_asteroids
PRODUCT_DEVICE := asteroids
PRODUCT_BRAND := Stay4S
PRODUCT_MODEL := GrokPhone
PRODUCT_MANUFACTURER := Nothing

# === Grok as Privileged System App ===
PRODUCT_PACKAGES += \
    Grok

# === Full Grok Properties ===
PRODUCT_PRODUCT_PROPERTIES += \
    ro.stay4s.grok.edition=true \
    ro.stay4s.grok.version=1.0 \
    ro.grok.assistant=system \
    ro.grok.telecom_expert=true \
    ro.grok.agent_process=:agent \
    ro.stay4s.grok.default_launcher=true

# === Default Launcher Configuration ===
PRODUCT_PACKAGES += \
    Grok

# Set Grok as default home
PRODUCT_DEFAULT_PROPERTY_OVERRIDES += \
    ro.setupwizard.mode=DISABLED

# Attempt to set Grok Launcher as default (best effort at build time)
PRODUCT_PROPERTY_OVERRIDES += \
    persist.sys.default_launcher=com.xai.grok/.launcher.GrokLauncherActivity

# Note: Full default launcher enforcement often requires a post-boot script or overlay in real builds.

# === Privileged Permissions ===
PRODUCT_COPY_FILES += \
    device/nothing/asteroids/grok/privapp-permissions-grok.xml:$(TARGET_COPY_OUT_PRODUCT)/etc/permissions/privapp-permissions-grok.xml

# === Boot Animation & Branding ===
TARGET_BOOT_ANIMATION_RES := 1080

# === Grok Edition Build Fingerprint (to be updated) ===
PRODUCT_BUILD_PROP_OVERRIDES += \
    BuildDesc="grokphone_asteroids-user 15 AP2A.240905.003 12345678 release-keys" \
    BuildFingerprint=Stay4S/GrokPhone/asteroids:15/AP2A.240905.003/12345678:user/release-keys
