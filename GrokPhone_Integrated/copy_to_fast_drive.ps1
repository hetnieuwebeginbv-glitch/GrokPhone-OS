# copy_to_fast_drive.ps1
# Run this on the main machine to copy the fully integrated 3-pillar GrokPhone tree
# to the fast build drive (GrokPhone_Build)

$source = "C:\Users\Gebruiker\GrokPhone_Integrated"
$destDrive = "GrokPhone_Build"   # Change if your fast drive has a different letter or path

# Try common locations for the fast drive
$possibleDests = @(
    "D:\GrokPhone_Build\grokphone-integrated-$(Get-Date -Format yyyyMMdd)",
    "E:\GrokPhone_Build\grokphone-integrated-$(Get-Date -Format yyyyMMdd)",
    "F:\GrokPhone_Build\grokphone-integrated-$(Get-Date -Format yyyyMMdd)",
    "C:\GrokPhone_Build\grokphone-integrated-$(Get-Date -Format yyyyMMdd)"
)

$chosenDest = $null
foreach ($d in $possibleDests) {
    $driveRoot = Split-Path $d -Parent
    if (Test-Path $driveRoot) {
        $chosenDest = $d
        break
    }
}

if (-not $chosenDest) {
    Write-Error "Could not find a fast drive. Please edit this script and set the correct destination path."
    exit 1
}

Write-Host "Copying integrated 3-pillar tree to fast drive..."
Write-Host "Source: $source"
Write-Host "Dest:   $chosenDest"

# Use robocopy for speed and reliability
robocopy $source $chosenDest /MIR /MT:8 /R:2 /W:2 /NFL /NDL

Write-Host ""
Write-Host "=== Copy complete ==="
Write-Host "On the build machine (fast drive), run:"
Write-Host "  cd <path-to-grokphone-integrated>"
Write-Host "  source build/envsetup.sh"
Write-Host "  lunch stay4s_grok_edition_asteroids-userdebug"
Write-Host "  m -j4 otapackage 2>&1 | tee build.log"
Write-Host ""
Write-Host "Then flash the resulting OTA and validate all 3 pillars."