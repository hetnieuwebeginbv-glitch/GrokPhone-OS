# Release Signing

## Huidige Stand

De laptop kan nu alle Manus Ai Phone APKs bouwen en lokaal installeren. De huidige release build gebruikt een lokale debug signing key als installbare ontwikkel-release. Dat is bewust gedaan zodat de Nothing Phone 3a vandaag omgebouwd kan worden.

## Productievereiste

Voor echte productie moet Manus Ai Phone een offline bewaarde productie-keystore gebruiken:

- niet committen
- niet delen in chat
- niet opslaan in CI logs
- backup offline bewaren
- key rotation en incidentprocedure vastleggen

## Aanbevolen Env Vars

De Gradle modules moeten in de productiefase deze waarden krijgen:

```bash
export MANUS_RELEASE_STORE_FILE=/secure/path/manus-release.keystore
export MANUS_RELEASE_STORE_PASSWORD=...
export MANUS_RELEASE_KEY_ALIAS=manus
export MANUS_RELEASE_KEY_PASSWORD=...
```

## Lokale Build

```bash
./scripts/build-release.sh
```

Artifacts komen in:

```text
releases/
```

## Productie Build Stap

De volgende stap is de Gradle signing config omzetten van lokale debug signing naar env-gebaseerde release signing. Doe dat pas zodra de echte offline keystore is aangemaakt en veilig opgeslagen.
