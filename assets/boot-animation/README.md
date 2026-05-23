# Boot Animatie — Ai Manus Phone

## Beschrijving

De custom boot animatie vervangt de standaard Nothing OS boot animatie met een Stay4Safe Ai branded versie.

## Animatie Concept

```
Frame 1-30:   Zwart scherm — langzame teal glow verschijnt vanuit het midden
Frame 31-60:  "S4S" logo materialiseert — letter voor letter
Frame 61-90:  Guardian Ring verschijnt — roterend teal circuit-patroon
Frame 91-120: "Stay4Safe Ai" tekst fade-in — Space Grotesk font
Frame 121-150: Volledige logo — pulserende glow, klaar voor OS
```

## Technische Specs

| Parameter | Waarde |
|---|---|
| Formaat | Android `bootanimation.zip` |
| Resolutie | 1080 × 2400 px (Nothing Phone 3a) |
| FPS | 30 fps |
| Duur | 5 seconden |
| Formaat frames | PNG (verliesvrij) |

## Installatie (vereist root)

```bash
# Root vereist voor boot animatie vervanging
adb push bootanimation.zip /system/media/bootanimation.zip
adb shell chmod 644 /system/media/bootanimation.zip
adb reboot
```

## Alternatief (zonder root)

Zonder root is de boot animatie niet vervangbaar. De Guardian AI app toont wel een branded splash screen bij het opstarten van de app.

## Boot Animatie Bouwen

```bash
# Maak frames aan (vereist Python + Pillow)
python3 scripts/generate_boot_frames.py

# Pak in als bootanimation.zip
cd boot-frames && zip -0 ../bootanimation.zip part0/*.png
```

## desc.txt (animatie configuratie)

```
1080 2400 30
p 1 0 part0
```
