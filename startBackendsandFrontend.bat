@echo off
cd backend

REM Start backend in akafoewg
start "akafoewg" cmd /k "cd akafoewg && call gradlew bootRun"

REM Start backend in hsbobackend
start "hsbobackend" cmd /k "cd hsbobackend && call gradlew bootRun"

REM Start backend in zfarub
start "zfarub" cmd /k "cd zfarub && call gradlew bootRun"

cd ..
cd frontend
start "hsboportal" cmd /k "cd hsbo-frontend && call ng serve"


