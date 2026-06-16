@echo off
cd /d "%~dp0"
echo Stopping old process if any...
for /f "tokens=5" %%a in ('netstat -ano ^| find ":5800" ^| find "LISTEN"') do taskkill /f /pid %%a >nul 2>&1
timeout /t 1 >nul
pip install -r requirements.txt -q 2>nul
start /min "Y-zi Steps" cmd /c "python app.py"
timeout /t 2 >nul
echo Done! Open http://127.0.0.1:5800 in your browser
