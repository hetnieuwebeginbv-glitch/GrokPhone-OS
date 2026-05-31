# GitHub Sync Instructions – Stay4S GrokPhone

**Correct Repository**: https://github.com/miesdevries/Stay4s-grokrom

## Important
All future work for the custom ROM lives in this repo under `device/nothing/asteroids/`.

## Recommended Workflow (from your local machine)

### 1. Add the correct remote (if not already done)
```bash
cd C:\Users\Gebruiker\GrokPhone

# Add the correct remote (if it doesn't exist yet)
git remote add grokrom https://github.com/miesdevries/Stay4s-grokrom.git

# Or set it as origin if you want to work directly from this folder
git remote set-url origin https://github.com/miesdevries/Stay4s-grokrom.git
```

### 2. Pull latest from the repo first
```bash
git fetch grokrom
git checkout -b grokphone-rom-update
```

### 3. Copy the important files from this local tree into your clone of the repo

The key files that should go into the GitHub repo are:

**Device Tree:**
- device/nothing/asteroids/AndroidProducts.mk
- device/nothing/asteroids/stay4s_grok_edition.mk
- device/nothing/asteroids/device.mk (updated)
- device/nothing/asteroids/init/init.grok.rc
- device/nothing/asteroids/sepolicy/ (all files)
- device/nothing/asteroids/grok/privapp-permissions-grok.xml

**Grok App:**
- packages/apps/Grok/AndroidManifest.xml
- packages/apps/Grok/Android.bp

**Scripts & Docs:**
- scripts/
- docs/ (especially CUSTOM_ROM_MASTER_PLAN.md, PRE_BUILD_CHECKLIST.md, etc.)
- .gitignore (the improved one)

### 4. Commit & Push
```bash
git add .
git commit -m "GrokPhone ROM: Full custom ROM foundation - device tree, SELinux, init, build scripts and documentation"
git push grokrom grokphone-rom-update:main   # or create a PR
```

## Alternative: Direct Push from this folder
If you want this entire `GrokPhone` folder to act as your working copy for the repo:

```bash
cd C:\Users\Gebruiker\GrokPhone
git remote remove origin 2>nul
git remote add origin https://github.com/miesdevries/Stay4s-grokrom.git
git branch -M main
git add .
git commit -m "Initial full GrokPhone custom ROM configuration"
git push -u origin main
```

**Note**: Be careful with large files (kernel, vendor) — they are usually handled separately in LineageOS trees.

## Current Status
All core custom ROM files have been prepared locally in this folder.  
The previous work was accidentally pushed to the old repo (hetnieuwebeginbv-glitch/stay4s-grok-edition). We are now exclusively using miesdevries/Stay4s-grokrom.

Use the commands above to sync everything to the correct repository.
