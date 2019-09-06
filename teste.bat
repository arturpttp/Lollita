title Lollita Git
start "" "%SYSTEMDRIVE%\Program Files (x86)\Git\bin\sh.exe" --login -i
git add .
git commit -m %1
git push
exit