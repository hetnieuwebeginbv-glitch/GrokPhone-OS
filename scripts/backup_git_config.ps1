# backup_git_config.ps1
# Maakt een timestamped backup van je .git/config

$repoPath = "C:\Users\Gebruiker\GrokPhone"
$gitConfig = Join-Path $repoPath ".git\config"

if (Test-Path $gitConfig) {
    $timestamp = Get-Date -Format "yyyyMMdd_HHmmss"
    $backupPath = Join-Path $repoPath ".git\config.backup_$timestamp"
    
    Copy-Item $gitConfig $backupPath -Force
    Write-Host "Backup gemaakt: $backupPath" -ForegroundColor Green
    
    Write-Host "`nHuidige remote configuratie:" -ForegroundColor Cyan
    git -C $repoPath remote -v
} else {
    Write-Error "Geen .git/config gevonden in $repoPath"
}