#
# stay4s_grok_edition.mk
# Stay4S GrokPhone - Full Custom ROM Product Definition
# LineageOS 22.1 (Android 15) for Nothing Phone (asteroids)
#

# Inherit from the base device
$(call inherit-product, device/nothing/asteroids/device.mk)

# Inherit from LineageOS common configuration
$(call inherit-product, vendor/lineage/config/common_full_phone.mk)

# Grok Edition specific inheritance (when we have more overlays)
# $(call inherit-product, device/nothing/asteroids/grok/grok_common.mk)

PRODUCT_NAME := stay4s_grok_edition_asteroids
PRODUCT_DEVICE := asteroids
PRODUCT_BRAND := Stay4S
PRODUCT_MODEL := GrokPhone
PRODUCT_MANUFACTURER := Nothing

# === Grok als privileged system app (com.xai.grok) ===
PRODUCT_PACKAGES += \
    Grok

# === Diepe Grok integratie properties ===
PRODUCT_PRODUCT_PROPERTIES += \
    ro.stay4s.grok.edition=true \
    ro.stay4s.grok.version=1.0 \
    ro.grok.assistant=system \
    ro.grok.telecom_expert=true \
    ro.grok.agent_process=:agent \
    ro.stay4s.grok.default_launcher=true \
    ro.stay4s.grok.privileged=true \
    ro.stay4s.grok.has_telecom_rights=true

# === Grok als default home screen ===
PRODUCT_DEFAULT_PROPERTY_OVERRIDES += \
    ro.setupwizard.mode=DISABLED

PRODUCT_PROPERTY_OVERRIDES += \
    persist.sys.default_launcher=com.xai.grok/.launcher.GrokLauncherActivity

# Privileged permissions + early boot
PRODUCT_COPY_FILES += \
    device/nothing/asteroids/grok/privapp-permissions-grok.xml:$(TARGET_COPY_OUT_PRODUCT)/etc/permissions/privapp-permissions-grok.xml \
    device/nothing/asteroids/init/init.grok.rc:$(TARGET_COPY_OUT_VENDOR)/etc/init/init.grok.rc

TARGET_BOOT_ANIMATION_RES := 1080

# Build fingerprint (update bij echte releases)
PRODUCT_BUILD_PROP_OVERRIDES += \
    BuildDesc="stay4s_grok_edition_asteroids-user 15 AP2A.240905.003 20260601 release-keys" \
    BuildFingerprint=Stay4S/GrokPhone/asteroids:15/AP2A.240905.003/20260601:user/release-keys

# Extra telecom features voor Grok
PRODUCT_PACKAGES += \
    com.android.ims.rcsmanager \
    com.android.ims.rcsservice

# Default launcher overlay (Grok as home without script)
PRODUCT_PACKAGE_OVERLAYS += device/nothing/asteroids/overlay

# Remove stock packages to make the experience more our own
$(call inherit-product, device/nothing/asteroids/grok/grok_remove.mk)

# Aggressive rebranding so it feels like our own OS
$(call inherit-product, device/nothing/asteroids/grok/grok_branding.mk)

# Tie the Grok AI layer (Brain + Guardian) into the OS as first-class citizens
$(call inherit-product, device/nothing/asteroids/grok/grok_system.mk)

# Grok-native AI apps and experiences (Vault, future Browser, Messages, etc.)
$(call inherit-product, device/nothing/asteroids/grok/grok_ai_apps.mk)

# === All 6 Deep Ownership Initiatives ===
# 1. Remove stock SystemUI + start Grok-native system interface
$(call inherit-product, device/nothing/asteroids/grok/systemui/grok_systemui.mk)

# 2. Grok Vault as real privileged system component
$(call inherit-product, device/nothing/asteroids/grok/vault/grok_vault.mk)

# 3. Own boot experience + visuals
$(call inherit-product, device/nothing/asteroids/grok/boot/grok_boot.mk)

# 4. Path to native APIs for the Brain (instead of Accessibility)
$(call inherit-product, device/nothing/asteroids/grok/services/grok_native_apis.mk)

# 5. Grok-controlled OTA updater
$(call inherit-product, device/nothing/asteroids/grok/updater/grok_updater.mk)

# 6. Own shell (Grok Launcher / future minimal Grok Shell)
$(call inherit-product, device/nothing/asteroids/grok/shell/grok_shell.mk)

# === Future: Add custom overlays here ===
# PRODUCT_PACKAGE_OVERLAYS += device/nothing/asteroids/overlay-grok
