@file:Suppress("SpellCheckingInspection")

import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
  `java-library`
  id("io.papermc.paperweight.userdev") version "1.3.8"
  id("net.minecrell.plugin-yml.bukkit") version "0.5.2"
  id("xyz.jpenilla.run-paper") version "1.0.6"
  id("co.uzzu.dotenv.gradle") version "2.0.0"
  id("com.modrinth.minotaur") version "2.+"
}

group = "xyz.tetratheta"
version = "2.0.0-beta1"

repositories {
  mavenCentral()
  maven("https://repo.papermc.io/repository/maven-public/")
  maven("https://oss.sonatype.org/content/groups/public/")
}

val versionMinecraft = "1.19.2" // Minecraft (run-paper)
val versionAPI = "1.19" // API (plugin-yml)
val versionPaper = "1.19.2-R0.1-SNAPSHOT" // Paper (Paper)

dependencies {
  paperDevBundle(versionPaper) // Paper API + userdev
}

bukkit {
  main = "xyz.tetratheta.hardplus.Hardplus"
  apiVersion = versionAPI
  load = BukkitPluginDescription.PluginLoadOrder.POSTWORLD
  prefix = "HardPlus"
  author = "TetraTheta"
  permissions {
    register("hardplus.*") {
      description = "Enable All modules of HardPlus"
      default = BukkitPluginDescription.Permission.Default.FALSE
      children = listOf(
          "hardplus.cold-damage",
          "hardplus.creeper-charge",
          "hardplus.creeper-explode",
          "hardplus.damage-critical",
          "hardplus.damage-give",
          "hardplus.damage-take",
          "hardplus.fast-air",
          "hardplus.fire-forever",
          "hardplus.hunger-debuff",
          "hardplus.lava-death",
          "hardplus.mob-target",
          "hardplus.no-fire-resistance",
          "hardplus.no-sweep",
          "hardplus.no-water",
          "hardplus.sharp-cut",
          "hardplus.uncooked-poison")
      childrenMap = children!!.associateWith { true }
    }
    register("hardplus.cold-damage") {
      description = "Enable Cold Damage module of HardPlus"
      default = BukkitPluginDescription.Permission.Default.FALSE
    }
    register("hardplus.creeper-charge") {
      description = "Enable Creeper Charge module of HardPlus"
      default = BukkitPluginDescription.Permission.Default.FALSE
    }
    register("hardplus.creeper-explode") {
      description = "Enable Creeper Explode module of HardPlus"
      default = BukkitPluginDescription.Permission.Default.FALSE
    }
    register("hardplus.damage-critical") {
      description = "Enable Damage Critical module of HardPlus"
      default = BukkitPluginDescription.Permission.Default.FALSE
    }
    register("hardplus.damage-give") {
      description = "Enable Damage Give module of HardPlus"
      default = BukkitPluginDescription.Permission.Default.FALSE
    }
    register("hardplus.damage-take") {
      description = "Enable Damage Take module of HardPlus"
      default = BukkitPluginDescription.Permission.Default.FALSE
    }
    register("hardplus.fast-air") {
      description = "Enable Fast Air module of HardPlus"
      default = BukkitPluginDescription.Permission.Default.FALSE
    }
    register("hardplus.fire-forever") {
      description = "Enable Fire Forever module of HardPlus"
      default = BukkitPluginDescription.Permission.Default.FALSE
    }
    register("hardplus.hunger-debuff") {
      description = "Enable Hunger Debuff module of HardPlus"
      default = BukkitPluginDescription.Permission.Default.FALSE
    }
    register("hardplus.lava-death") {
      description = "Enable Lava Death module of HardPlus"
      default = BukkitPluginDescription.Permission.Default.FALSE
    }
    register("hardplus.mob-target") {
      description = "Enable Mob Target module of HardPlus"
      default = BukkitPluginDescription.Permission.Default.FALSE
    }
    register("hardplus.no-fire-resistance") {
      description = "Enable No Fire Resistance module of HardPlus"
      default = BukkitPluginDescription.Permission.Default.FALSE
    }
    register("hardplus.no-sweep") {
      description = "Enable No Sweep module of HardPlus"
      default = BukkitPluginDescription.Permission.Default.FALSE
    }
    register("hardplus.no-water") {
      description = "Enable No Water module of HardPlus"
      default = BukkitPluginDescription.Permission.Default.FALSE
    }
    register("hardplus.sharp-cut") {
      description = "Enable Shar Cut module of HardPlus"
      default = BukkitPluginDescription.Permission.Default.FALSE
    }
    register("hardplus.uncooked-poison") {
      description = "Enable Uncooked Poison module of HardPlus"
      default = BukkitPluginDescription.Permission.Default.FALSE
    }
  }
}

tasks {
  assemble {
    dependsOn(reobfJar)
  }

  compileJava {
    options.encoding = Charsets.UTF_8.name()
    options.release.set(17)
  }

  java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }

  withType<JavaCompile> {
    options.encoding = Charsets.UTF_8.name()
  }

  runServer {
    minecraftVersion(versionMinecraft)
  }
}
