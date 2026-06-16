@echo off
cd /d "%~dp0"
pip install -r requirements.txt -q 2>nul
echo Starting Y-zi Steps (Flask Local)...
start "Y-zi Steps" cmd /k "python app.py"
timeout /t 2 >nul
echo Done! Open http://127.0.0.1:5800 in your browser
pause
