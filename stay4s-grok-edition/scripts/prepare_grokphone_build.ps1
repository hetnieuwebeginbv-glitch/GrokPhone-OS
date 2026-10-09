# prepare_grokphone_build.ps1
# Prepares the GrokPhone custom ROM tree for building on the fast drive (GrokPhone_Build)

param(
    [string]$Source = "C:\Users\Gebruiker\GrokPhone",
    [string]$FastDriveBase = "GrokPhone_Build"
)

Write-Host "=== Stay4S GrokPhone Custom ROM - Build Preparation ===" -ForegroundColor Cyan

# Find fast drive
$fastDrive = $null
foreach ($drive in "D:", "E:", "F:", "G:") {
    if (Test-Path $drive) {
        $fastDrive = "$drive\$FastDriveBase"
        break
    }
}

if (-not $fastDrive) {
    Write-Error "No fast drive found. Create D:\GrokPhone_Build or similar."
    exit 1
}

$dest = "$fastDrive\GrokPhone_$(Get-Date -Format yyyyMMdd_HHmm)"
Write-Host "Copying to: $dest"

robocopy $Source $dest /MIR /MT:8 /R:2 /W:2 /NFL /NDL /XD ".git" "out" "kernel" "vendor"

Write-Host "`n=== Preparation Complete ===" -ForegroundColor Green
Write-Host "Recommended environment setup on build machine:"
Write-Host ""
Write-Host "export USE_CCACHE=1"
Write-Host "export CCACHE_DIR=/ccache"
Write-Host "ccache -M 50G"
Write-Host ""
Write-Host "source build/envsetup.sh"
Write-Host "lunch stay4s_grok_edition_asteroids-userdebug"
Write-Host ""
Write-Host "For low-RAM machines use:"
Write-Host "m -j4 otapackage 2>&1 | tee build_grokphone.log"
Write-Host ""
Write-Host "After build: check out/target/product/asteroids/ for the OTA zip."
