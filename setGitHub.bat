@echo on
git init
git add .
set /p "id=Input commit: "
git commit -m "%id%"
git remote set-url origin https://Java488-oss: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@github.com/Java488-oss/GenarateActUI.git
git push origin master
