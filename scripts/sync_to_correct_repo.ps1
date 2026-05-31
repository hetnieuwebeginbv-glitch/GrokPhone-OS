# sync_to_correct_repo.ps1
# Synchronizes the most important parts of the local GrokPhone work
# to https://github.com/miesdevries/Stay4s-grokrom

param(
    [string]$LocalGrokPhone = "C:\Users\Gebruiker\GrokPhone",
    [string]$TargetClone   = "C:\Users\Gebruiker\Stay4s-grokrom-work"
)

Write-Host "=== Stay4S GrokPhone → Official Repo Sync ===" -ForegroundColor Cyan

if (-not (Test-Path $TargetClone)) {
    Write-Host "Cloning target repo..."
    git clone https://github.com/miesdevries/Stay4s-grokrom.git $TargetClone
}

$TargetClone = Resolve-Path $TargetClone

# Directories we want to keep in sync for the ROM
$syncDirs = @(
    "device",
    "packages",
    "scripts",
    "docs"
)

foreach ($dir in $syncDirs) {
    $src = Join-Path $LocalGrokPhone $dir
    $dst = Join-Path $TargetClone $dir

    if (Test-Path $src) {
        Write-Host "Syncing $dir ..." -ForegroundColor Green
        robocopy $src $dst /MIR /NFL /NDL /NJH /NJS /XD ".git" "out"
    }
}

# Root files (README.md is intentionally excluded to avoid conflicts)
$rootFiles = @(".gitignore")
foreach ($file in $rootFiles) {
    $src = Join-Path $LocalGrokPhone $file
    if (Test-Path $src) {
        Copy-Item $src (Join-Path $TargetClone $file) -Force
    }
}

# Explicitly do NOT sync README.md to prevent conflicts on the target repo.

Write-Host ""
Write-Host "Files prepared in: $TargetClone" -ForegroundColor Green
Write-Host ""
Write-Host "Next steps (run these manually):" -ForegroundColor Yellow
Write-Host "cd `"$TargetClone`""
Write-Host "git add ."
Write-Host 'git commit -m "GrokPhone ROM: Major update - device tree, Grok integration, SELinux, launcher architecture, build scripts"'
Write-Host "git push origin main"
Write-Host ""
Write-Host "Tip: Create a branch first if you want to review changes before pushing to main." -ForegroundColor DarkCyan