# Manus Managed OS Device Owner Provisioning

## Doel

Device-owner provisioning geeft Manus Managed OS meer controle op een Nothing Phone 3a zonder custom ROM. Dit is de route voor managed installs, policies, fleet/family beheer en gecontroleerde updates.

## Belangrijke Voorwaarde

`dpm set-device-owner` werkt alleen betrouwbaar op een schoon/fresh toestel zonder Google/accounts en zonder bestaande device owner. Voor een toestel dat al ingericht is, moet je meestal factory resetten.

## Wat Nu Is Toegevoegd

- `manus-suite` bevat `ManusDeviceAdminReceiver`.
- `scripts/install.sh` installeert Manus Suite mee.
- `scripts/install.sh` kan optioneel device-owner proberen met:

```bash
MANUS_ENABLE_DEVICE_OWNER=1 ./scripts/install.sh
```

- Los provisioning script:

```bash
./scripts/provision-device-owner.sh
```

## Stappen Op Een Fresh Nothing Phone 3a

1. Factory reset of start met een nieuw toestel.
2. Sla account-login over waar mogelijk.
3. Zet Developer Options en USB debugging aan.
4. Sluit USB aan en accepteer ADB prompt.
5. Installeer Manus Ai Phone:

```bash
cd /Users/mitfcg-/M-Ai-Phone
./scripts/install.sh
```

6. Zet device owner:

```bash
./scripts/provision-device-owner.sh
```

Of in een stap:

```bash
MANUS_ENABLE_DEVICE_OWNER=1 ./scripts/install.sh
```

## Wat Device Owner Later Mogelijk Maakt

- managed app installs
- app allow/block lists
- lock task/kiosk policies
- camera/policy restrictions
- certificates/VPN/DNS policies
- update rings voor fleet devices
- compliance status in Manus Fleet

## Wat Nog Niet Automatisch Is

- silent install policy logic is nog niet gebouwd
- admin console is nog niet gebouwd
- QR provisioning is nog niet gebouwd
- fleet backend is nog niet gebouwd

Deze onderdelen horen bij Manus Managed OS fase 2.
