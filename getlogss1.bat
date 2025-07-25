@echo off
REM ======================================
REM Script: extract_messagelog_tables.bat
REM Purpose: Enter Docker container, run psql command, extract table list to txt
REM ======================================

REM Change directory if needed or ensure docker is in PATH

REM Execute the command in Docker container 'ss1'
docker exec -i ss1 bash -c "PGPASSWORD='O1JgsSNqlBCBm2nEQ1iTZQGCdR-8Mhpp' psql -h 127.0.0.1 -U messagelog -d messagelog -c \"\\dt messagelog.*;\"" > messagelog_tables.txt

echo Extraction finished. Output saved to messagelog_tables.txt
pause
