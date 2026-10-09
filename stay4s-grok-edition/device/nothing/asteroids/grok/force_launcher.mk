# force_launcher.mk
# Include dit in stay4s_grok_edition.mk als je de post-boot launcher force script wilt bundelen

PRODUCT_COPY_FILES += \
    device/nothing/asteroids/scripts/force_grok_launcher.sh:$(TARGET_COPY_OUT_SYSTEM)/bin/force_grok_launcher.sh

# Optioneel: start via init (vereist extra SELinux regels)
# PRODUCT_COPY_FILES += \
#     device/nothing/asteroids/init/force_launcher.rc:$(TARGET_COPY_OUT_VENDOR)/etc/init/force_launcher.rc
