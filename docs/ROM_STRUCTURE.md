# ROM Structure in the Repository

Om conflicten met root bestanden van de repo (zoals de hoofd README) te voorkomen, leeft alle GrokPhone ROM-gerelateerde content onder de map:

**grokphone-rom/**

## Huidige aanbevolen structuur

grokphone-rom/
├── device/
│   └── nothing/
│       └── asteroids/          ← Alle device tree aanpassingen
├── packages/
│   └── apps/
│       └── Grok/               ← De privileged com.xai.grok app
├── scripts/                    ← Build scripts + sync tools
├── docs/                       ← Alle documentatie (inclusief launcher specs)
├── .gitignore
├── .gitattributes
├── roomservice.xml
└── README.md                   ← Specifieke README voor alleen de ROM

De root van de repo (https://github.com/miesdevries/Stay4s-grokrom) blijft voor de bredere Stay4S Grok Edition visie.

## Synchroniseren

Gebruik het script:
```powershell
.\scripts\sync_rom_to_repo.ps1
```

Dit script pusht alles netjes onder `grokphone-rom/` zonder de root README of andere root bestanden aan te raken.