<p align="center">
<img src="https://raw.githubusercontent.com/TetraTheta/HardPlus/main/.etc/hardplus_info.png" style="width: 100%" alt="HardPlus Logo">
</p>

# HardPlus
**HardPlus adds extra difficulty to Paper server**

![Version](https://img.shields.io/modrinth/v/hardplus?style=for-the-badge&label=Plugin%20Version)
![Game Version](https://img.shields.io/modrinth/game-versions/hardplus?style=for-the-badge&label=Minecraft%20Version)<br>
[![Modrinth Downloads](https://img.shields.io/modrinth/dt/hardplus?style=for-the-badge&label=Modrinth%20Downloads)](https://modrinth.com/plugin/hardplus)

[![Discord](https://img.shields.io/discord/1514516278226845726?style=for-the-badge)](https://discord.gg/eS8tCEkecp)

## Introduction

HardPlus adds optional difficulty tweaks for Paper servers where vanilla Hard difficulty is no longer enough. Each module is controlled by configuration and by its own permission node, so server owners can apply extra difficulty only to selected players, groups, or WorldGuard regions.

Modules can change combat, survival pressure, mob targeting, etc. By default, modules are enabled in the configuration, but no player receives the effects unless they have the matching `hardplus.*` or module permission.

WorldGuard is optional. When WorldGuard is installed, HardPlus registers one state flag for each module, using the permission node with `.` replaced by `-`. For example, `hardplus.cold-damage` becomes `hardplus-cold-damage`.

Use the [wiki](https://github.com/TetraTheta/HardPlus/wiki) for detailed module behavior.

This plugin is highly motivated by [Fundy's video](https://youtu.be/Z5Y8uwpJrhs).

See [CHANGELOG.md](CHANGELOG.md) for release notes.

## Features

- Permission-gated difficulty modules.
- Optional WorldGuard flags for region-based difficulty.
- Configurable incoming and outgoing damage modifiers.
- Extra survival pressure from cold items, air loss, hunger, fire, lava, and water impacts.
- Combat restrictions such as no critical hits, no sweep attacks, and sharp-tool movement damage.
- Mob pressure through aggressive targeting, charged creepers, instant creeper explosions, Nether chest danger, and Wither Skeleton bows.
- Passive health regeneration blocking.
- Uncooked food poisoning.

## Configuration

```yml
module:
  # Damages players carrying cold blocks or items with 'FREEZE' damage.
  cold-damage:
    enable: true
    timer:
      delay: 10
      period: 10
  # Charges creepers near affected players when those creepers are targeting affected players.
  creeper-charge:
    enable: true
    radius: 16.0
    timer:
      delay: 10
      period: 10
  # Forces nearby creepers to explode immediately when they target affected players.
  creeper-explode:
    enable: true
    radius: 2.5
    timer:
      delay: 10
      period: 10
  # Prevents affected players from dealing critical damage with melee attacks or arrows.
  damage-critical:
    enable: true
  # Scales down damage dealt by affected players, including arrows shot by them.
  damage-give:
    enable: true
    modifier: 0.4
  # Multiplies incoming damage for affected players according to the damage source.
  damage-take:
    enable: true
    modifier:
      default: 2.5
      player: 2.5
      melee: 2.5
      fall: 2.5
      magic: 2.5
      environment: 2.5
      mob:
        passive: 2.5
        hostile:
          default: 2.5
          zombie: 2.5
          skeleton: 2.5
          enderman: 2.5
  # Makes Nether mobs target affected players who open chests or barrels in Nether biomes.
  dangerous-nether-chest:
    enable: true
    radius: 32.0
  # Accelerates air loss for affected players while their remaining air is decreasing.
  fast-air:
    enable: true
    modifier: 40
  # Keeps affected players burning until an external action, such as water, extinguishes them.
  fire-forever:
    enable: true
  # Treats high water impacts like fall damage for affected players.
  hard-water:
    enable: true
    minimum-fall-distance: 8
    modifier: 1
  # Applies movement, mining, and weakness debuffs when affected players fall below the configured food level.
  hunger-debuff:
    enable: true
    food-level: 6
  # Removes post-hit invulnerability after lava damage so affected players die almost immediately in lava.
  lava-death:
    enable: true
  # Makes nearby hostile mobs target affected players unless invisibility and no armor allow a bypass.
  mob-target:
    enable: true
    range: 16.0
    timer:
      delay: 10
      period: 10
  # Blocks newly added Fire Resistance effects from affected players.
  no-fire-resistance:
    enable: true
  # Cancels configured passive healing reasons for affected players.
  no-passive-health-regen:
    enable: true
    blocked-reason:
      - SATIATED
  # Cancels sweep attacks made by affected players so only the direct target can be damaged.
  no-sweep:
    enable: true
  # Removes player-placed water immediately in The End and drains Nether cauldrons quickly.
  no-water:
    enable: true
    timer:
      delay: 10
      period: 10
  # Damages and stops affected players who jump or sprint while holding configured sharp tools.
  sharp-cut:
    enable: true
    condition:
      jump: true
      run: true
    check:
      sword: true
      axe: true
      shear: true
  # Poisons affected players when they eat configured uncooked foods.
  uncooked-poison:
    enable: true
    duration: 30
  # Arms Wither Skeletons with bows for affected targets, then swaps back to stone swords for other targets.
  wither-skeleton-bow:
    enable: true
    spawn-chance: 50.0
    bow:
      damage-level: 3
      knockback-level: 2
    arrow:
      wither-level: 0
```

`module.<name>.enable` controls whether HardPlus enables that module at startup. If it is `false`, the module is not loaded, so its permission and WorldGuard flag have no effect.

Scheduled modules use `timer.delay` and `timer.period` in ticks. Damage and radius values are read from the module-specific settings shown above.

## Technical Notes

HardPlus applies module effects only when the player has the matching permission or is inside a WorldGuard region where the matching flag is allowed.

Permissions:

- `hardplus.*`: Enables all modules.
- `hardplus.cold-damage`: Enables Cold Damage.
- `hardplus.creeper-charge`: Enables Creeper Charge.
- `hardplus.creeper-explode`: Enables Creeper Explode.
- `hardplus.damage-critical`: Enables Damage Critical.
- `hardplus.damage-give`: Enables Damage Give.
- `hardplus.damage-take`: Enables Damage Take.
- `hardplus.dangerous-nether-chest`: Enables Dangerous Nether Chest.
- `hardplus.fast-air`: Enables Fast Air.
- `hardplus.fire-forever`: Enables Fire Forever.
- `hardplus.hard-water`: Enables Hard Water.
- `hardplus.hunger-debuff`: Enables Hunger Debuff.
- `hardplus.lava-death`: Enables Lava Death.
- `hardplus.mob-target`: Enables Mob Target.
- `hardplus.no-fire-resistance`: Enables No Fire Resistance.
- `hardplus.no-passive-health-regen`: Enables No Passive Health Regen.
- `hardplus.no-sweep`: Enables No Sweep.
- `hardplus.no-water`: Enables No Water.
- `hardplus.sharp-cut`: Enables Sharp Cut.
- `hardplus.uncooked-poison`: Enables Uncooked Poison.
- `hardplus.wither-skeleton-bow`: Enables Wither Skeleton Bow.

WorldGuard flag names are derived from permissions:

- `hardplus.cold-damage` -> `hardplus-cold-damage`
- `hardplus.hard-water` -> `hardplus-hard-water`
- `hardplus.wither-skeleton-bow` -> `hardplus-wither-skeleton-bow`

## Credits

- Fundy: Provided the motif of HardPlus with his video.
- Dkbay#1337: Provided the design idea of the HardPlus logo.
- People on PaperMC Discord: Helped a lot while developing HardPlus.
