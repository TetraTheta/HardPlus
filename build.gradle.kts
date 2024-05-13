@file:Suppress("SpellCheckingInspection")

import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
  `java-library`
  id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
  id("co.uzzu.dotenv.gradle") version "4.0.0"
  id("com.modrinth.minotaur") version "2.+"
}

group = "io.github.tetratheta"
version = "2.5.2"

repositories {
  mavenCentral()
  maven("https://repo.papermc.io/repository/maven-public/")
  maven("https://oss.sonatype.org/content/groups/public/")
}

val verMC = "1.20.6" // Minecraft (Modrinth)
val verAPI = "1.20" // API (plugin-yml)
val verPaper = "1.20.6-R0.1-SNAPSHOT" // Paper (Paper)

dependencies {
  compileOnly("io.papermc.paper:paper-api:$verPaper")
}

bukkit {
  main = "io.github.tetratheta.hardplus.Hardplus"
  apiVersion = verAPI
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
          "hardplus.dangerous-nether-chest",
          "hardplus.fast-air",
          "hardplus.fire-forever",
          "hardplus.hunger-debuff",
          "hardplus.lava-death",
          "hardplus.mob-target",
          "hardplus.no-fire-resistance",
          "hardplus.no-sweep",
          "hardplus.no-water",
          "hardplus.sharp-cut",
          "hardplus.uncooked-poison",
          "hardplus.wither-skeleton-bow")
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
    register("hardplus.dangerous-nether-chest") {
      description = "Enable Dangerous Nether Chest module of HardPlus"
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
    register("hardplus.wither-skeleton-bow") {
      description = "Enable Wither Skeleton Bow module of HardPlus"
      default = BukkitPluginDescription.Permission.Default.FALSE
    }
  }
}

modrinth {
  token.set(env.MODRINTH_TOKEN.value)
  projectId.set("hardplus")
  versionType.set("release")
  uploadFile.set(tasks.jar)
  gameVersions.add(verMC)
  loaders.add("paper")
  syncBodyFrom.set(rootProject.file("README.md").readText(Charsets.UTF_8))
}

tasks {
  compileJava {
    options.encoding = Charsets.UTF_8.name()
    options.release.set(21)
  }

  java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
  }

  withType<JavaCompile> {
    options.encoding = Charsets.UTF_8.name()
  }
}
