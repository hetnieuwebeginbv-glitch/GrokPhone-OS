#
# grok_remove.mk
# Explicitly remove stock LineageOS and AOSP packages we do not want in the GrokPhone experience.
# Include this from stay4s_grok_edition.mk to make the ROM feel more our own.
#

# Remove stock launchers (we only want Grok)
PRODUCT_PACKAGES := $(filter-out LineageLauncher3%,$(PRODUCT_PACKAGES))
PRODUCT_PACKAGES := $(filter-out Launcher3%,$(PRODUCT_PACKAGES))
PRODUCT_PACKAGES := $(filter-out Trebuchet%,$(PRODUCT_PACKAGES))

# Remove stock browser (we will control web experience via Grok)
PRODUCT_PACKAGES := $(filter-out Browser2,$(PRODUCT_PACKAGES))
PRODUCT_PACKAGES := $(filter-out Jelly,$(PRODUCT_PACKAGES))

# Remove stock dialer / messaging if we want full control later
# PRODUCT_PACKAGES := $(filter-out Dialer,$(PRODUCT_PACKAGES))
# PRODUCT_PACKAGES := $(filter-out messaging,$(PRODUCT_PACKAGES))

# Remove some LineageOS specific apps we don't need
PRODUCT_PACKAGES := $(filter-out LineageParts,$(PRODUCT_PACKAGES))
PRODUCT_PACKAGES := $(filter-out LineageSettingsProvider,$(PRODUCT_PACKAGES))
PRODUCT_PACKAGES := $(filter-out LineageSetupWizard,$(PRODUCT_PACKAGES))

# Remove some AOSP bloat
PRODUCT_PACKAGES := $(filter-out Calendar,$(PRODUCT_PACKAGES))
PRODUCT_PACKAGES := $(filter-out DeskClock,$(PRODUCT_PACKAGES))
PRODUCT_PACKAGES := $(filter-out Email,$(PRODUCT_PACKAGES))
PRODUCT_PACKAGES := $(filter-out ExactCalculator,$(PRODUCT_PACKAGES))
