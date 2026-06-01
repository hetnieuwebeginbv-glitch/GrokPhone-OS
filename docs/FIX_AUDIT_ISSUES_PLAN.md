# FIX_AUDIT_ISSUES_PLAN.md
## Hoe fix je de grootste problemen uit de audit

### 1. Remote is al gefixt
Je lokale repo wijst nu naar de juiste repo:
`https://github.com/miesdevries/Stay4s-grokrom.git`

**Backup van je oude config staat hier:**
`C:\Users\Gebruiker\GrokPhone\.git\config.backup_20260531_235759`

### 2. Sync je lokale werk naar de juiste repo (belangrijkste stap nu)

Gebruik dit script (het zet alles netjes onder `grokphone-rom/` zodat je geen conflicten krijgt met de root README):

```powershell
cd C:\Users\Gebruiker\GrokPhone
.\scripts\sync_rom_to_repo.ps1
```

Daarna handmatig pushen (vanuit de clone map):

```bash
cd C:\Users\Gebruiker\Stay4s-grokrom-work
git checkout -b grokphone-rom-update
git add grokphone-rom/
git commit -m "GrokPhone ROM: Grote update - device tree, overlays, scripts, docs en ownership"
git push -u origin grokphone-rom-update
```

Maak daarna een Pull Request van `grokphone-rom-update` naar `main` op GitHub.

### 3. Versterk Branding & Verwijder stock spul (snel hoog effect)

Run dit om de huidige branding en removal te updaten:

```powershell
cd C:\Users\Gebruiker\GrokPhone

# Update branding en removal
git add device/nothing/asteroids/grok/grok_branding.mk
git add device/nothing/asteroids/grok/grok_remove.mk
git add device/nothing/asteroids/stay4s_grok_edition.mk

git commit -m "Improve branding and stock component removal"
```

### 4. Maak overlays sterker (dit is momenteel je grootste hefboom)

Focus eerst op deze twee:

- SystemUI overlays (nog bijna niets)
- Meer agressieve strings verwijderen ("Android", "LineageOS", etc.)

De map `device/nothing/asteroids/overlay/` bestaat al. Vul hem verder aan.

### 5. Volgende prioriteit na sync

1. Push alles wat je lokaal hebt naar de repo (via het sync script + PR).
2. Bouw een minimale maar werkende eerste versie (zelfs als veel dingen nog stubs zijn).
3. Breid overlays uit (SystemUI + Settings).
4. Maak een echte bootanimation + geluiden.

---

**Kort samengevat wat je nu moet doen:**

1. Run `.\scripts\sync_rom_to_repo.ps1`
2. Push via de clone map zoals hierboven.
3. Maak een Pull Request.
4. Daarna gaan we verder met overlays en branding versterken.

Laat maar weten als je de volgende concrete stap wilt (bijv. "maak nu SystemUI overlays" of "maak een minimale buildbare versie").
