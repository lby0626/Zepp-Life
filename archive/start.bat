@echo off
cd /d "%~dp0"
echo Installing dependencies if needed...
pip install -r requirements.txt -q 2>nul
echo Starting Y子步数 (Flask 本地版)...
start "Y子步数" cmd /k "python app.py"
timeout /t 2 >nul
echo Done! Open http://127.0.0.1:5800 in your browser
pause
