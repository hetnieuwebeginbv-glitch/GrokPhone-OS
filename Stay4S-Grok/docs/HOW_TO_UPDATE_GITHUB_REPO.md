# How to Update the Official GitHub Repo

**Target repo**: https://github.com/miesdevries/Stay4s-grokrom

> **Important**: The README.md on GitHub is intentionally **not** overwritten by the sync process to avoid conflicts. Only update it manually when you specifically want to change the public description.

## Recommended Method (easiest)

1. Run the sync script from the root of this folder:

```powershell
cd C:\Users\Gebruiker\GrokPhone
.\scripts\sync_to_correct_repo.ps1
```

2. If the script asks for the clone path, point it to a fresh clone of the target repo.

## Manual Method (more control)

```bash
# 1. Clone the target repo (if you don't have it yet)
git clone https://github.com/miesdevries/Stay4s-grokrom.git C:\Users\Gebruiker\Stay4s-grokrom-work
cd C:\Users\Gebruiker\Stay4s-grokrom-work

# 2. Copy the important parts from your local GrokPhone work
# Core ROM files
robocopy "C:\Users\Gebruiker\GrokPhone\device" "device" /MIR
robocopy "C:\Users\Gebruiker\GrokPhone\packages" "packages" /MIR
robocopy "C:\Users\Gebruiker\GrokPhone\scripts" "scripts" /MIR
robocopy "C:\Users\Gebruiker\GrokPhone\docs" "docs" /MIR

# Copy root files
copy "C:\Users\Gebruiker\GrokPhone\.gitignore" . -Force

# 3. Commit and push
git add .
git commit -m "GrokPhone ROM: Major update - full device tree, Grok integration, SELinux, launcher specs, build tooling"
git push origin main
```

## What Should Be in the Repo

Priority order for this repo:

1. `device/nothing/asteroids/` (the complete Grok Edition configuration)
2. `packages/apps/Grok/` (the privileged system app)
3. `scripts/` (build + sync tools)
4. `docs/` (architecture, plans, launcher specifications)
5. Root files (.gitignore, README.md)

The large `agent/`, `software/`, `genesis-001-100/` etc. folders can stay in a separate vision repo if the ROM repo becomes too big.

## After Pushing

- Update the README on GitHub with the latest status.
- Create a `grokphone-rom` branch if you want to keep main clean.

Run the sync script regularly as you make progress on the ROM.
