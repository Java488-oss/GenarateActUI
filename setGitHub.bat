@echo on
git init
git config --global user.name "Java488-oss"
git config --global user.email "job.mail1358@gmail.com"
git add .
set /p "id=Input commit: "
git commit -m "%id%"
git remote set-url origin https://Java488-oss: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@github.com/Java488-oss/GenarateActUI.git
git push origin main
pause