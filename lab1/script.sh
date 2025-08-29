#!/bin/bash

# Define repository URLs
MAIN_REPO="git@gitlab.liu.se:Large-scale-dev/git-web-server.git"
OUR_REPO="https://gitlab.liu.se/ellno907/tdde51-labs.git"

# Clone the main repository
git clone --bare $MAIN_REPO
cd git-web-server
git push --mirror $OUR_REPO
cd ..
git clone $OUR_REPO
cd $OUR_REPO

git switch --track origin/develop
git switch --track origin/implement-api
git switch --track origin/add-tests

git checkout develop
git checkout -b new-feature

code .

git add -A
git commit -m "New feature"
git checkout develop
git merge new-feature
git push origin develop
git merge add-tests

code

git add -A 
git commit -m "Add tests merged into develop"
git push origin develop

git checkout implement-api
git rebase develop
git checkout develop
git merge implement-api
git push origin develop

git reset --hard HEAD~1 
git push --force origin develop

#For higher grades
#Part 1
git reset --soft HEAD~2
git commit -m "Combined two commits into one"
git push origin develop --force

#Part 2
git remote add new-remote https://gitlab.liu.se/large-scale-dev/git-lab-remote2.git
git fetch new-remote
git log new-remote/implement-api --oneline #Look for Refactor Database Configuration
git checkout develop
git cherry-pick #commit hash
git push origin develop
