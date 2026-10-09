# Manus Ai Phone Flash Readiness

## Doel

Deze fase maakt de Nothing Phone 3a klaar voor veilige installatie, managed provisioning en later ROM/flash-onderzoek.

Belangrijk: deze repo flasht nu nog geen ROM. De veilige volgorde is eerst APK-laag, daarna managed mode, daarna pas ROM-track op een testtoestel.

## Wat nu klaar is

- Manus APK-laag via `scripts/install.sh`.
- Device-owner voorbereiding via `scripts/provision-device-owner.sh`.
- Flash-readiness controle via `scripts/flash-readiness.sh`.
- Admin Room in Manus Suite met Device & Flash scherm.
- Statusrapport in `reports/flash-readiness.txt` na het draaien van het readiness-script.

## Readiness uitvoeren

Verbind de Nothing Phone 3a met USB debugging aan en voer uit:

```bash
./scripts/flash-readiness.sh
```

Het script controleert:

- ADB device.
- Model, product, manufacturer en Android versie.
- Build fingerprint en security patch.
- Active slot.
- Verified boot state.
- Flash lock/device state properties.
- Device-owner status.
- Signatures van lokale release APK's.

Het script is non-destructive. Het unlockt niet, wist niets en flasht geen images.

## Veilige volgorde

1. Bouw release APK's:

```bash
./scripts/build-release.sh
```

2. Controleer readiness:

```bash
./scripts/flash-readiness.sh
```

3. Installeer APK-laag:

```bash
./scripts/install.sh
```

4. Alleen op een schoon/fresh testtoestel: device-owner provisioning.

```bash
MANUS_ENABLE_DEVICE_OWNER=1 ./scripts/install.sh
```

5. ROM/flash-track pas starten na:

- testtoestel beschikbaar;
- bootloader unlock-keuze expliciet gemaakt;
- originele firmware en herstelpad bekend;
- device tree en kernel sources beoordeeld;
- vendor blobs en firmware afhankelijkheden geinventariseerd;
- geteste boot/recovery/system/vendor images beschikbaar;
- rollbackplan gedocumenteerd;
- emergency calling, modem, camera, NFC, fingerprint, Wi-Fi, Bluetooth en sensors getest.

## Niet doen zonder ROM-track

- Geen willekeurige boot/recovery/vendor/system images flashen.
- Geen productie- of prive-toestel gebruiken voor eerste ROM-tests.
- Geen bootloader unlock zonder backup en herstelplan.
- Geen claim maken dat Nothing OS volledig is vervangen zolang alleen de APK-laag draait.

## Beslissing

Status nu:

- Klaar voor APK-installatie.
- Klaar voor device-owner test op schoon toestel.
- Klaar voor flash-readiness inventarisatie.
- Nog niet klaar voor echte ROM flash.
