# GrokPhone - Nothing Phone (asteroids)
# Full integrated custom ROM - Grok is the OS

# Device specific
PRODUCT_DEVICE := asteroids
PRODUCT_NAME := grokphone_asteroids
PRODUCT_BRAND := Nothing
PRODUCT_MODEL := GrokPhone
PRODUCT_MANUFACTURER := Nothing

# === Core Grok Integration ===
PRODUCT_PACKAGES += \
    Grok

# Privileged app permissions
PRODUCT_COPY_FILES += \
    device/nothing/asteroids/grok/privapp-permissions-grok.xml:$(TARGET_COPY_OUT_PRODUCT)/etc/permissions/privapp-permissions-grok.xml

# Early boot init for Grok Agent
PRODUCT_COPY_FILES += \
    device/nothing/asteroids/init/init.grok.rc:$(TARGET_COPY_OUT_VENDOR)/etc/init/init.grok.rc

# === Grok System Properties (deep integration) ===
PRODUCT_PRODUCT_PROPERTIES += \
    ro.stay4s.grok.edition=true \
    ro.stay4s.grok.version=1.0 \
    ro.grok.assistant=system \
    ro.grok.telecom_expert=true \
    ro.grok.agent_process=:agent \
    ro.stay4s.grok.default_launcher=true \
    ro.stay4s.grok.privileged=true

# === Force Grok as default launcher ===
PRODUCT_DEFAULT_PROPERTY_OVERRIDES += \
    ro.setupwizard.mode=DISABLED

PRODUCT_PROPERTY_OVERRIDES += \
    persist.sys.default_launcher=com.xai.grok/.launcher.GrokLauncherActivity

# === Telecom & System Rights ===
PRODUCT_PACKAGES += \
    com.android.ims.rcsmanager \
    com.android.ims.rcsservice \
    com.android.telephony.ims

# === Future Grok Overlays (add when ready) ===
# PRODUCT_PACKAGE_OVERLAYS += device/nothing/asteroids/overlay-grok

# === Grok Boot Animation ===
TARGET_BOOT_ANIMATION_RES := 1080

# Extra system services die Grok mag gebruiken
PRODUCT_COPY_FILES += \
    frameworks/native/data/etc/android.hardware.telephony.gsm.xml:$(TARGET_COPY_OUT_VENDOR)/etc/permissions/android.hardware.telephony.gsm.xml
