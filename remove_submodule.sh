MODULE=$1

git submodule deinit $MODULE
git rm $MODULE
git commit -m "Removed submodule : $MODULE"
rm -rf .git/modules/$MODULE