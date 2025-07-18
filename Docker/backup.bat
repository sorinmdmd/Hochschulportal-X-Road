@echo off
setlocal enabledelayedexpansion

REM ---------------------------------
REM Backup X-Road Docker volumes
REM Author: ChatGPT
REM ---------------------------------

REM Set backup folder to current directory
set BACKUP_DIR=%cd%

REM Define list of volumes to backup
set VOLUMES=cs-db-data cs-conf-data ca-data ss1-db-data ss1-conf-data ss1-archive-data ss2-db-data ss2-conf-data ss2-archive-data ss3-db-data ss3-conf-data ss3-archive-data

echo Starting X-Road volume backups...
echo Backup folder: %BACKUP_DIR%
echo.

REM Loop through each volume
for %%V in (%VOLUMES%) do (
    echo Backing up volume: %%V

    docker run --rm -v %%V:/volume -v %BACKUP_DIR%:/backup alpine tar czf /backup/%%V-backup.tar.gz -C /volume .

    if %ERRORLEVEL% NEQ 0 (
        echo Error backing up volume %%V
    ) else (
        echo Backup completed: %%V-backup.tar.gz
    )
    echo.
)

echo All backups completed.
pause
