# Prepare_FastDrive_Build.ps1
# Comprehensive preparation script for the first real Stay4S Grok Edition build
# (All 3 Pillars deeply integrated)

param(
    [string]$SourceTree = "C:\Users\Gebruiker\GrokPhone_Integrated",
    [string]$FastDriveRoot = "GrokPhone_Build"
)

Write-Host "=== Stay4S Grok Edition — Full 3-Pillar Build Preparation ===" -ForegroundColor Cyan
Write-Host "Source: $SourceTree"
Write-Host ""

# 1. Find fast drive
$fastDrive = $null
$possible = @("D:", "E:", "F:", "G:")
foreach ($d in $possible) {
    if (Test-Path "$d\$FastDriveRoot") { $fastDrive = "$d\$FastDriveRoot"; break }
    if (Test-Path $d) { $fastDrive = "$d\$FastDriveRoot"; break }
}

if (-not $fastDrive) {
    Write-Error "Fast drive not found. Please create D:\GrokPhone_Build or edit this script."
    exit 1
}

$dest = "$fastDrive\GrokPhone_3Pillar_$(Get-Date -Format yyyyMMdd_HHmm)"
Write-Host "Destination: $dest" -ForegroundColor Green

# 2. Clean copy
Write-Host "Performing clean mirror copy to fast drive..."
robocopy $SourceTree $dest /MIR /MT:8 /R:2 /W:2 /NFL /NDL /XD .git out

# 3. Create build environment helper
$envScript = @"
# Run this on the fast drive after copy
source build/envsetup.sh
lunch stay4s_grok_edition_asteroids-userdebug

echo "=== Grok Edition 3-Pillar Build Ready ==="
echo "Run: m -j4 otapackage 2>&1 | tee build_3pillar.log"
"@
$envScript | Out-File -FilePath "$dest\START_BUILD.sh" -Encoding UTF8

Write-Host ""
Write-Host "=== Preparation Complete ===" -ForegroundColor Green
Write-Host "Tree ready at: $dest"
Write-Host "On the build machine run: bash START_BUILD.sh"
Write-Host ""
Write-Host "Remember: This build contains deep integration of all 3 pillars." -ForegroundColor Yellow
