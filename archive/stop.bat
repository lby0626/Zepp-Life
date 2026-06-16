@echo off
cd /d "%~dp0"
echo Stopping Y子步数...
for /f "tokens=5" %%a in ('netstat -ano ^| find ":5800" ^| find "LISTEN"') do taskkill /f /pid %%a >nul 2>&1
echo Done.
pause