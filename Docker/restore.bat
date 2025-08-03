@echo off
setlocal enabledelayedexpansion

REM Set backup folder to current directory
set BACKUP_DIR=%cd%/xroadVolumesBackup

REM Define list of volumes to restore
set VOLUMES=cs-db-data cs-conf-data ca-data ss1-db-data ss1-conf-data ss1-archive-data ss2-db-data ss2-conf-data ss2-archive-data ss3-db-data ss3-conf-data ss3-archive-data

echo Starting X-Road volume restores...
echo Backup folder: %BACKUP_DIR%
echo.

REM Loop through each volume
for %%V in (%VOLUMES%) do (
    set BACKUP_FILE=%%V-backup.tar.gz

    if exist "%BACKUP_DIR%\!BACKUP_FILE!" (
        echo Restoring volume: %%V from !BACKUP_FILE!

        docker run --rm -v %%V:/volume -v %BACKUP_DIR%:/backup alpine sh -c "cd /volume && tar xzf /backup/!BACKUP_FILE!"

        if %ERRORLEVEL% NEQ 0 (
            echo Error restoring volume %%V
        ) else (
            echo Restore completed for volume: %%V
        )
    ) else (
        echo Backup file not found for volume %%V: !BACKUP_FILE!
    )
    echo.
)

echo All restores completed.
pause
