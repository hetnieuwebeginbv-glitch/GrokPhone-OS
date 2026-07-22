#!/usr/bin/env pwsh
# build-stay4os.ps1 — Stay4OS GrokPhone OS build script
# Usage: .\scripts\build-stay4os.ps1 [-Clean] [-Jobs 8]

param(
    [switch]$Clean = $false,
    [int]$Jobs = (Get-CimInstance Win32_ComputerSystem).NumberOfLogicalProcessors,
    [string]$BuildDir = "~/stay4os"
)

$ErrorActionPreference = "Stop"
$BuildDir = $ExecutionContext.SessionState.Path.GetUnresolvedProviderPathFromPSPath($BuildDir)

Write-Host "=== Stay4OS Build Script ===" -ForegroundColor Cyan
Write-Host "Build dir: $BuildDir"
Write-Host "Jobs: $Jobs"
Write-Host "Clean: $Clean"
Write-Host ""

# Check prerequisites
$prereqs = @("git", "python3", "java")
foreach ($prereq in $prereqs) {
    if (!(Get-Command $prereq -ErrorAction SilentlyContinue)) {
        Write-Host "ERROR: Prerequisite '$prereq' not found" -ForegroundColor Red
        exit 1
    }
}

# Verify Java 17+
$javaVer = java -version 2>&1 | ForEach-Object { "$_" } | Select-String "version"
if ($javaVer -notmatch '"(\d+)') {
    Write-Host "ERROR: Could not detect Java version" -ForegroundColor Red
    exit 1
}

# Source build environment
$env:USE_CCACHE = "1"
$env:CCACHE_DIR = "/mnt/ccache"
$env:CCACHE_COMPRESS = "1"
$env:CCACHE_MAXSIZE = "50G"

Set-Location -LiteralPath $BuildDir

if ($Clean) {
    Write-Host "Cleaning build..." -ForegroundColor Yellow
    & make clean 2>&1 | Out-Null
}

Write-Host "Setting up environment..." -ForegroundColor Green
$env:TARGET_KERNEL_SOURCE = "kernel/nothing/sm7635"
$env:TARGET_KERNEL_CONFIG = "gki_defconfig vendor/pineapple_perf.config vendor/asteroids_perf.config"
$env:TARGET_KERNEL_VERSION = "6.1"

Write-Host "Lunch target: lineage_asteroids-userdebug" -ForegroundColor Green
$lunchOutput = & bash -c "source build/envsetup.sh && lunch lineage_asteroids-userdebug" 2>&1
Write-Host $lunchOutput

Write-Host "Starting build with $Jobs jobs..." -ForegroundColor Green
$buildOutput = & bash -c "source build/envsetup.sh && mka bacon -j$Jobs" 2>&1
Write-Host $buildOutput

# Check build result
$outDir = "$BuildDir/out/target/product/asteroids"
if (Test-Path "$outDir/lineage_asteroids-ota-*.zip") {
    $otaZip = Get-ChildItem "$outDir/lineage_asteroids-ota-*.zip" | Sort-Object LastWriteTime -Descending | Select-Object -First 1
    Write-Host ""
    Write-Host "=== BUILD SUCCESS ===" -ForegroundColor Green
    Write-Host "OTA package: $($otaZip.FullName)"
    Write-Host "Size: $('{0:N2}' -f ($otaZip.Length / 1MB)) MB"
} elseif (Test-Path "$outDir/boot.img") {
    Write-Host ""
    Write-Host "=== BUILD SUCCESS (boot.img only) ===" -ForegroundColor Green
    Write-Host "Boot: $outDir/boot.img"
} else {
    Write-Host ""
    Write-Host "=== BUILD FAILED ===" -ForegroundColor Red
    Write-Host "Check build.log for details"
    exit 1
}
