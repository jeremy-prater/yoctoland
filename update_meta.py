import os
import subprocess
import time

curdir = os.getcwd()

repoList = [
    ("meta-clang", "kirkstone", "HEAD"),
    ("meta-openembedded", "kirkstone", "HEAD"),
    ("meta-qt5", "kirkstone", "HEAD"),
    ("meta-raspberrypi", "kirkstone", "HEAD"),
    ("meta-security", "kirkstone", "HEAD"),
    ("meta-swupdate", "kirkstone", "HEAD"),
    ("meta-wandboard", "kirkstone", "HEAD"),
    ("poky", "kirkstone", "HEAD"),
]


def git(dir, args):
    args = ["git"] + args
    subprocess.run(args=args, cwd=dir, check=True)


git(".", ["submodule", "init"])
git(".", ["submodule", "update"])
git(".", ["submodule", "sync"])

for repo in repoList:
    print("Updating Repo ==> [{}] to [{}]".format(repo[0], repo[1]))
    repoDir = curdir + "/" + repo[0]
    # git(repoDir, ["reset", "--hard", "origin/{}".format(repo[2])])
    git(repoDir, ["fetch", "origin"])
    git(repoDir, ["checkout", repo[1]])
    git(repoDir, ["pull"])
