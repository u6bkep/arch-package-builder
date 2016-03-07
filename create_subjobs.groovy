def PackagesFile = new File('/var/lib/jenkins/jobs/Arch Package Builder/workspace/aur-packages')
PackagesFile.eachLine { line ->
  packageName = line.trim()
  println(packageName)
    job(packageName) {
      description("This Job builds the ${packageName} package for archlinux")
      concurrentBuild()
      label('master')
      scm {
        git("https://aur.archlinux.org/${line}.git")
      }
      triggers {
        scm('*/15 * * * *')
      }
      steps {
        shell("sudo arch-nspawn /mnt/aur/build_test/root pacman -Syyu; sudo /usr/bin/makechrootpkg -c -r /mnt/aur/build_test -l ${line}")
      }
    }
}