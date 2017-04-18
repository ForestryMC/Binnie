#### IntelliJ IDEA

1. Execute the following gradle command to setup your workspace and create the necessary idea project files:

  ```
  ./gradlew setupDecompWorkspace idea genIntellijRuns
  ```
  
2. Open the `Binnie*.ipr` project file using IntelliJ IDEA

3. Select `Minecraft Client` from _Run configurations_ and press the green play button.
Minecraft should now launch with Binnie and all of its dependencies loaded :)
