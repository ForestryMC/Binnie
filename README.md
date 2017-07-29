[Binnie mods](https://minecraft.curseforge.com/projects/binnies-mods/files)
====================================

### What is it?
Binnie’s Mods is a suite of mods designed to expand the [Forestry](https://github.com/ForestryMC/ForestryMC) mod. 
It both extends the bee and tree breeding aspect by adding many new species with varied products,
along with new features such as fully breedable flowers and genetic manipulation.

### Homepage

[Wiki](https://binnie.mods.wiki/wiki/Main_Page)

[Latest Builds for 1.11 / 1.11.2](https://ci.bymarcin.com/job/Binnie-Mods-MC1.11.2/)

### Building

The master branch will always contain the latest released version with possibly some bug fixes for that version that will eventually be released.
New versions will be created in branched named like {mc_version}-{mod_version}

#### IntelliJ IDEA

1. Execute the following gradle command to setup your workspace and create the necessary idea project files:

  ```
  ./gradlew setupDecompWorkspace idea genIntellijRuns
  ```
  
2. Open the `Binnie*.ipr` project file using IntelliJ IDEA

3. Select `Minecraft Client` from _Run configurations_ and press the green play button.
Minecraft should now launch with Binnie and all of its dependencies loaded :)
