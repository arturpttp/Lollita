#!/bin/sh
###########################
git checkout master
git add .
read commitMessage
git commit -m "$commitMessage"
git push
###########################
echo Press Enter...
read