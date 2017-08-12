[Binnie mods](https://minecraft.curseforge.com/projects/binnies-mods/files)
====================================

### What is it?
Binnie’s Mods is a suite of mods designed to expand the [Forestry](https://github.com/ForestryMC/ForestryMC) mod. 
It both extends the bee and tree breeding aspect by adding many new species with varied products,
along with new features such as fully breedable flowers and genetic manipulation.

### Homepage

[Wiki](https://binnie.mods.wiki/wiki/Main_Page)

[Latest Builds for 1.12 ](http://jenkins.ic2.player.to/job/Binnie's%201.12/)

### Mods

[![](https://binnie.mods.wiki/w/images/binnie/9/91/Extra_Bees_Header.png)](https://binnie.mods.wiki/wiki/Extra_Bees)

Extra Bees expands upon the bee breeding of Forestry by adding 107 new bee species to forestry. These bees cover a large spectrum of different effects and products, giving you the ability to produce everything from dyes to rare metal dusts purely from bees. It also adds the Apiarist Database, a tool to keep track of discovered species, mutations and traits. There are also new alveary components which help improve and streamline bee production. 


[![](https://binnie.mods.wiki/w/images/binnie/5/59/Extra_Trees_Header.png)](https://binnie.mods.wiki/wiki/Extra_Trees)

Extra Trees both increases the number of tree species and adds many new aesthetic uses. There are 96 new tree species, such as Banana, Brazil nut and Douglas-fir. This comes with many new fruits as well as 35 new wooden planks. Extra Trees allows these, as well as Forestry and vanilla planks, to be combined to give doors, gates and various designs of fences. The woodworker allows planks to be combined to give patterned tiles, allowing you to build beautiful wooden floors, and the same can be done with stained glass. A group of machines such as the brewery and distillery allow you to use the fruits you grow to make a variety of wines, ciders and spirits.


[![](https://binnie.mods.wiki/w/images/binnie/3/34/Botany_Header.png)](https://binnie.mods.wiki/wiki/Botany)

Botany adds an entirely new breeding system to Forestry: flowers. There are 46 different species that can be bred, including both vanilla flowers and new ones such as Fuschia and Lilies. The colour of the flowers can be transferred and selectively bred, meaning that each flower can be one of a possible 80 different colours. These new flowers can be bred manually in small gardens using a trowel and fertiliser, with careful care taken to maintain the desired pH or moisture, or grown on the large scale in Forestry farms. 

[![](https://binnie.mods.wiki/w/images/binnie/4/4e/Genetics_Header.png)](https://binnie.mods.wiki/wiki/Genetics)

Genetics is for those who want to eschew conventional breeding, and want to enter the realm of genetic manipulation. Here, genes can be isolated, sequenced and then inoculated back into different species, making it easier to obtain the perfect genome. Bees, trees, butterflies and flowers can all be manipulated in this way. They can also be acclimatised to different environments to build tolerance to hostile climates.

[![](https://binnie.mods.wiki/w/images/binnie/d/d7/Binnie_Core_Header.png)](https://binnie.mods.wiki/wiki/Binnie_Core)

Binnie Core is a mod which contains a lot of the core functionality such as networking and GUI's for the other mods. The only in game features it currently contains are the Compartments


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
