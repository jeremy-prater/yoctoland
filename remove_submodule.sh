MODULE=$1

git submodule deinit -f $MODULE
rm -rf .git/modules/$MODULE
git rm -f $MODULE
git commit -m "Removed submodule : $MODULE"