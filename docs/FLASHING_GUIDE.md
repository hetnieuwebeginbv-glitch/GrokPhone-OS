# Flashing Guide – Stay4S GrokPhone (First OTA)

## Prerequisites
- Unlocked bootloader on Nothing Phone (asteroids)
- Fastboot and adb working
- Latest GrokPhone OTA zip from build

## Recommended Method (Fastboot + OTA)

### 1. Boot to Fastboot
```bash
adb reboot bootloader
```

### 2. Flash critical partitions (if needed for first time)
```bash
fastboot flash boot boot.img
fastboot flash dtbo dtbo.img
fastboot flash vendor_boot vendor_boot.img
```

### 3. Sideload OTA (recommended for updates)
```bash
adb reboot sideload
adb sideload stay4s_grok_edition_asteroids-ota-*.zip
```

### 4. After Flash
- First boot can take 5-10 minutes (new system + Grok agent initialization)
- Check logs:
  ```bash
  adb logcat -b all | grep -E 'Grok|stay4s.grok'
  ```

## Important Notes for First Build
- SELinux may be in permissive mode initially (check `adb shell getenforce`)
- Set Grok as default launcher manually the first time
- The `:agent` process should start early

Report any boot loops or major issues with full logs.
