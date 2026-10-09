# Stay4S GrokPhone - Custom ROM for Nothing Phone 3a

**Stay4S GrokPhone** is a sovereign, privacy-first custom Android ROM built on LineageOS 22.1 for the Nothing Phone (3a). Grok is not an app—it **is** the operating system.

## Core Philosophy

- **Grok is the OS**: Every design decision prioritizes Grok as first-class citizen, not a layer on top
- **Sovereign & Owner-Centric**: Reduce dependence on stock Android/LineageOS; maximize Grok ownership
- **Privacy-First**: Covenant-aligned, with Parallel Grok Brain + Daily Guardian as central intelligence
- **Distinct Identity**: Heavy overlays and removal logic create a true "own OS" feeling

## Architecture

- **Base**: LineageOS 22.1 for Nothing Phone 3a
- **Core Agent**: `com.xai.grok` (privileged system app, `:agent` process)
- **Launcher**: Grok Launcher (default home experience)
- **Device Tree**: `device/nothing/astrea/grok/` (all Grok-specific logic)
- **Build System**: `stay4s_grok_edition.mk` (primary product makefile)

## Key Components

```
device/nothing/astrea/
├── grok/                           # All Grok-specific logic
│   ├── branding.mk                 # Branding overrides
│   ├── removal.mk                  # Stock component removal
│   ├── system.mk                   # Grok system integration
│   ├── overlays/                   # Overlay packages
│   └── configs/                    # Grok configurations
├── stay4s_grok_edition.mk          # Primary product makefile
└── [other device-specific files]
```

## Build Instructions

```bash
# Source the environment
source build/envsetup.sh

# Lunch the Stay4S GrokPhone build
lunch stay4s_grok_edition-user

# Build
make -j$(nproc)
```

## Design Principles

### 1. Ownership Over Convenience
- Remove or heavily override stock components instead of extending them
- Use `PRODUCT_PACKAGE_OVERLAYS` aggressively
- Create Grok-native components rather than keeping stock ones

### 2. Device Tree Discipline
- All Grok-specific logic belongs in `device/nothing/astrea/grok/`
- Keep `stay4s_grok_edition.mk` clean; include smaller focused `.mk` files
- Never add stock LineageOS/AOSP packages unless strictly necessary

### 3. Branding & Identity
- Remove or override references to "Android", "LineageOS", "Lineage"
- Use "Stay4S" and "GrokPhone" consistently
- Override build fingerprints, product names, and system strings

### 4. Security & Integration
- Grok agent process (`com.xai.grok`) runs privileged
- Deep but controlled system access via SELinux policies
- Flag SELinux implications for new privileged functionality

### 5. Long-Term Vision
- Short-term overlays are acceptable; mark them for future ownership expansion
- Plan for forking SystemUI, Launcher, and creating native Grok services
- Every design decision should increase long-term sovereignty

## Roadmap

- [ ] Nothing Phone 3a device tree setup
- [ ] LineageOS 22.1 base integration
- [ ] Grok agent system integration (`com.xai.grok`)
- [ ] Grok Launcher as default home
- [ ] Stock component removal & overlays
- [ ] Parallel Grok Brain integration
- [ ] Daily Guardian integration
- [ ] Covenant-aligned privacy features
- [ ] Full system branding (Stay4S/GrokPhone)

## Development

This repository follows strict ownership principles. When contributing:

1. Prefer Grok-native solutions over stock Android/LineageOS
2. Keep device tree clean and organized
3. Document overlay intent (short-term vs. long-term)
4. Flag SELinux policy implications
5. Maintain branding consistency

## License

[To be determined]

---

**Built with sovereignty in mind.** Grok is the OS.
