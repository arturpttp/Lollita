#!/bin/sh
###########################
git checkout master
# add all added/modified files
git add .
# commit changes
read commitMessage
git commit -am "$commitMessage"
# push to git remote repository
git push
###########################
echo Press Enter...
read