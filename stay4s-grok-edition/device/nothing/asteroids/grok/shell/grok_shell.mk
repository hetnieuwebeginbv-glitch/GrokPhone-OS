#
# grok_shell.mk
# Own interface layer: Grok Shell (either heavily customized Launcher3 or a minimal native shell)
#

# Remove stock launchers completely
PRODUCT_PACKAGES := $(filter-out LineageLauncher3%,$(PRODUCT_PACKAGES))
PRODUCT_PACKAGES := $(filter-out Launcher3%,$(PRODUCT_PACKAGES))
PRODUCT_PACKAGES := $(filter-out Trebuchet%,$(PRODUCT_PACKAGES))

# Grok Shell (our own interface)
PRODUCT_PACKAGES += \
    GrokShell

# For now we use our custom Grok Launcher as the shell.
# Long-term this can become a true minimal Grok Shell.
