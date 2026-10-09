# sync_rom_to_repo.ps1
# Synchroniseert de GrokPhone custom ROM bestanden naar de repo
# onder de map 'grokphone-rom/' om conflicten met root bestanden (zoals README) te voorkomen.

param(
    [string]$LocalGrokPhone = "C:\Users\Gebruiker\GrokPhone",
    [string]$TargetClone   = "C:\Users\Gebruiker\Stay4s-grokrom-work"
)

Write-Host "=== Stay4S GrokPhone ROM → grokphone-rom/ in repo ===" -ForegroundColor Cyan

if (-not (Test-Path $TargetClone)) {
    Write-Host "Cloning target repo..."
    git clone https://github.com/miesdevries/Stay4s-grokrom.git $TargetClone
}

$TargetClone = Resolve-Path $TargetClone
$RomTarget   = Join-Path $TargetClone "grokphone-rom"

# Maak de doelmap aan als die nog niet bestaat
New-Item -ItemType Directory -Force -Path $RomTarget | Out-Null

# Belangrijke mappen die onder grokphone-rom/ komen
$romDirs = @(
    "device",
    "packages",
    "scripts",
    "docs"
)

foreach ($dir in $romDirs) {
    $src = Join-Path $LocalGrokPhone $dir
    $dst = Join-Path $RomTarget $dir

    if (Test-Path $src) {
        Write-Host "Syncing $dir -> grokphone-rom/$dir" -ForegroundColor Green
        robocopy $src $dst /MIR /NFL /NDL /NJH /NJS /XD ".git" "out"
    }
}

# Root bestanden die veilig onder grokphone-rom/ kunnen
$rootFiles = @(".gitignore", ".gitattributes", "roomservice.xml")
foreach ($file in $rootFiles) {
    $src = Join-Path $LocalGrokPhone $file
    if (Test-Path $src) {
        Copy-Item $src (Join-Path $RomTarget $file) -Force
    }
}

Write-Host ""
Write-Host "ROM bestanden klaar in: $RomTarget" -ForegroundColor Green
Write-Host ""
Write-Host "Nu handmatig committeren en pushen:" -ForegroundColor Yellow
Write-Host "cd `"$TargetClone`""
Write-Host "git add grokphone-rom/"
Write-Host 'git commit -m "GrokPhone ROM: Update custom ROM files (device tree, SELinux, scripts, docs)"'
Write-Host "git push origin main"
Write-Host ""
Write-Host "Tip: Gebruik een branch als je eerst wilt reviewen." -ForegroundColor DarkCyan