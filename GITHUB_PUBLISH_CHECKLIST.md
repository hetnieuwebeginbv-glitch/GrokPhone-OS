# Stay4S - GitHub Publish Checklist

**Status:** Lokaal klaar, GitHub tooling nodig

## Pre-Publish Verificatie ✅

- [x] Release tag gezet: `stay4s-v0.1.0`
- [x] Repo-bundle gemaakt: `sd_card/Stay4S/export/stay4s_nexus_v3.bundle`
- [x] GitHub-ready map klaar: `sd_card/Stay4S/github_ready/`
- [x] Tests groen: 32/32 passing
- [ ] GitHub CLI (`gh`) geïnstalleerd
- [ ] GitHub authenticatie ingesteld

---

## Stap 1: GitHub CLI Setup (PowerShell of Penguin)

```powershell
# PowerShell
winget install GitHub.cli

# OF Penguin/Linux
sudo apt install gh -y

# Verificatie
gh --version
```

### Stap 2: GitHub Authenticatie

```bash
gh auth login
# - Kies: github.com
# - Kies: HTTPS
# - Volg browser prompt

# Verificatie
gh auth status
```

---

## Stap 3: Repository Aanmaken op GitHub

```bash
# Via GitHub CLI
gh repo create stay4s \
  --public \
  --description "Stay4S - Nexus v3 Smart Home Automation System" \
  --homepage "https://github.com/hetnieuwebeginbv-glitch/stay4s" \
  --source=. \
  --remote=origin \
  --push
```

**OF handmatig via web:**
- Ga naar: https://github.com/new
- Repository name: `stay4s`
- Description: `Stay4S - Nexus v3 Smart Home Automation System`
- Public
- Klik "Create repository"

---

## Stap 4: Lokale Git Setup

```bash
# Als je de repo nog niet hebt gecloned
git clone https://github.com/hetnieuwebeginbv-glitch/stay4s.git
cd stay4s

# OF als je al in de repo directory bent
git remote add origin https://github.com/hetnieuwebeginbv-glitch/stay4s.git
git branch -M main
```

---

## Stap 5: Push naar GitHub

```bash
# Push alle commits
git push -u origin main

# Push alle tags (inclusief stay4s-v0.1.0)
git push --tags

# Verificatie
git remote -v
git branch -a
```

---

## Stap 6: Upload Bundle & Documentatie

### Optie A: Via GitHub CLI (aanbevolen)

```bash
# Maak een Release aan met het bundle bestand
gh release create stay4s-v0.1.0 \
  --title "Stay4S v0.1.0" \
  --notes "Initial release - Stay4S Smart Home System" \
  sd_card/Stay4S/export/stay4s_nexus_v3.bundle
```

### Optie B: Via Web UI

1. Ga naar: https://github.com/hetnieuwebeginbv-glitch/stay4s/releases
2. Klik "Draft a new release"
3. Tag version: `stay4s-v0.1.0`
4. Title: `Stay4S v0.1.0`
5. Upload bestand: `stay4s_nexus_v3.bundle`
6. Publish

---

## Stap 7: Voeg Documentatie Toe

```bash
# Copy documentatie naar repo
cp sd_card/Stay4S/github_ready/README.md .
cp sd_card/Stay4S/github_ready/github_publish.md docs/
cp sd_card/Stay4S/github_ready/github_publish.sh scripts/

# Commit en push
git add README.md docs/ scripts/
git commit -m "Add GitHub documentation and publish scripts"
git push
```

---

## Stap 8: Repository Configuratie

### Branch Protection (optioneel)

```bash
# Via GitHub CLI
gh repo edit --enable-discussions --enable-wiki=false
```

### Via Web UI

1. Ga naar: https://github.com/hetnieuwebeginbv-glitch/stay4s/settings
2. Tabs checken:
   - [x] Code and automation → Branches
   - [x] Add branch protection rule (voor `main`)
   - [x] Require pull request reviews
   - [x] Dismiss stale pull request approvals

---

## Stap 9: Verificatie Checklist

```bash
# Check alles is gepusht
git log --oneline | head -5
git tag -l

# Check remote
git remote -v

# Check GitHub Status
gh repo view

# Check release
gh release view stay4s-v0.1.0
```

---

## Post-Publish

- [ ] Repository URL: https://github.com/hetnieuwebeginbv-glitch/stay4s
- [ ] Release URL: https://github.com/hetnieuwebeginbv-glitch/stay4s/releases/tag/stay4s-v0.1.0
- [ ] Documentatie zichtbaar op GitHub
- [ ] Bundle downloadbaar uit Release
- [ ] Tests workflow ingesteld (optioneel)
- [ ] README zichtbaar op repo homepage

---

## Troubleshooting

| Probleem | Oplossing |
|---|---|
| `gh: command not found` | Installeer GitHub CLI |
| `fatal: not a git repository` | Zorg je in juiste directory bent |
| `Permission denied` | Authenticeer met `gh auth login` |
| `remote already exists` | Verwijder eerst: `git remote remove origin` |

---

**Klaar? Start met Stap 1!** 🚀
