# Making It More Our Own – Stay4S GrokPhone ROM

This document outlines concrete ways to reduce dependence on stock LineageOS/AOSP and make the GrokPhone feel like a truly distinct, sovereign product.

## 1. Branding & Identity (High Impact, Low Effort)

### Product Strings
Already partially done in `stay4s_grok_edition.mk`:
- `PRODUCT_BRAND := Stay4S`
- `PRODUCT_MODEL := GrokPhone`

**Further steps**:
- Override more `ro.product.*` properties
- Change build fingerprint to remove "lineage" references
- Create custom `build.prop` overrides

### Boot Animation
- Place a custom `bootanimation.zip` in `device/nothing/asteroids/overlay/frameworks/base/core/res/assets/`
- Or use `PRODUCT_COPY_FILES` to override the default one

### Sounds
- Create overlays in `frameworks/base/data/sounds/` to replace UI sounds, ringtones, notifications with custom (calm, covenant-feeling) audio.

## 2. Overlays (The Most Powerful Tool Right Now)

We are already using `PRODUCT_PACKAGE_OVERLAYS`.

**High-value overlays to add**:

- `frameworks/base/core/res/res/values/config.xml` → Change default assistant, browser, home, etc.
- `frameworks/base/core/res/res/values/strings.xml` → Rebrand "Android" references
- `packages/apps/Settings/res/values/strings.xml` → Change Settings app naming and categories
- `packages/apps/Launcher3/res/values/` → Hide stock apps, change grid, behavior
- `frameworks/base/packages/SystemUI/res/values/` → Custom quick settings tiles order, status bar

## 3. Removing Stock LineageOS / AOSP Bloat

In `stay4s_grok_edition.mk` or a dedicated `grok_remove.mk`:

```makefile
# Remove stock launchers
PRODUCT_PACKAGES := $(filter-out LineageLauncher3%,$(PRODUCT_PACKAGES))
PRODUCT_PACKAGES := $(filter-out Launcher3%,$(PRODUCT_PACKAGES))

# Remove stock browser, dialer, messaging if we have Grok alternatives
PRODUCT_PACKAGES := $(filter-out Browser2,$(PRODUCT_PACKAGES))
```

## 4. Deeper System Control

- Custom `init.grok.rc` (already started) to set properties early and start services before stock ones.
- Heavy use of `PRODUCT_PROPERTY_OVERRIDES` and `PRODUCT_DEFAULT_PROPERTY_OVERRIDES`.
- SELinux to prevent stock apps from doing things we don't want (e.g. block certain telemetry).

## 5. Own Update & Recovery Experience (Longer Term)

- Replace or heavily customize the LineageOS updater with a Grok-controlled one.
- Custom recovery (or at least custom recovery UI strings and branding).
- Own OTA signing keys (critical for true ownership).

## 6. Long-Term: Forking Key Components

To truly own the experience, we will eventually need to fork and maintain:

- Launcher3 (or build our own Grok Launcher from scratch)
- SystemUI
- Settings app
- Possibly parts of frameworks/base

This is heavy but the only way to remove all "LineageOS" and "Android" fingerprints over time.

## Recommended Next Actions (Prioritized)

1. **Immediate (this week)**
   - Expand the existing overlay with more `config.xml` and `strings.xml` overrides.
   - Add a custom bootanimation.zip.
   - Remove obvious stock launchers and browsers from the product.

2. **Short term (next 1-2 builds)**
   - Create a `grok_remove.mk` that strips unwanted LineageOS packages.
   - Add custom notification sounds and ringtones.
   - Heavily customize the Settings app strings and categories.

3. **Medium term**
   - Fork Launcher3 into the repo and make it Grok-native.
   - Build a Grok-controlled OTA updater.

4. **Long term (Genesis-level ownership)**
   - Maintain our own fork of key system components.
   - Move toward a more minimal base (less LineageOS inheritance).

## Important Principle

Every time we leave a stock component untouched, we are giving away control and identity.

The more we overlay, replace, or fork, the more the device feels like **ours** instead of "LineageOS with Grok on top".

This is how we turn a modified Android ROM into a genuine new category of personal intelligence device.
