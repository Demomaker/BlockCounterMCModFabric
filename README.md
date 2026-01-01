# Block Counter (Fabric)

[![Platform](https://img.shields.io/badge/Platform-Fabric-lightgrey.svg)](https://fabricmc.net/)
[![Minecraft Version](https://img.shields.io/badge/Minecraft-1.21.11-blue.svg)](https://www.minecraft.net/)

**Block Counter** is a utility mod for Minecraft (Fabric) designed to help builders and technical players. It allows you to select a region and get a precise breakdown of every block type within those boundaries.

---

## Features

* **Regional Scanning:** Define two points to create a selection area.
* **Total Tallies:** Outputs a list of blocks and their quantities directly to your chat or logs.
* **Material Lists:** Ideal for planning survival builds by knowing exactly what resources you need to gather.
* **Clean Integration:** Built specifically for the Fabric modding toolchain.

## Getting Started

### Prerequisites
* [Fabric Loader](https://fabricmc.net/use/installer/)
* [Fabric API](https://www.curseforge.com/minecraft/mc-mods/fabric-api) (Required for most Fabric mods)

### Installation
1.  Download the latest release from the [GitHub Releases](https://github.com/Demomaker/BlockCounterMCModFabric/releases) page.
2.  Move the `.jar` file into your Minecraft `mods` folder.
3.  Launch Minecraft using the Fabric profile.

## How to Use

- **Set Positions:** Use a book and quill or `/setposition` command to set Point A and Point B.
- **Count manually from existing coordinates:** Execute the count command (usually `/countblocks` or similar, depending on the version).

**View Results:** Check your chat window for the categorized list of blocks found in the selection.

## Development & Porting Process

This project maintains support for multiple Minecraft versions using a branch-per-version strategy.

### Repository Structure
* **`main` Branch:** Contains the source code for the most recent stable release of the mod.
* **Version Branches:** Each supported Minecraft version (e.g., `1.19`, `1.20.1`) has its own dedicated branch.

### How to Port to a New Minecraft Version
When a new Minecraft version is released, follow these steps to update the mod:

1.  **Initialize New Environment:**
    * Refer to the [Fabric Develop](https://fabricmc.net/develop/) setup tool to get the updated dependencies and buildscript configurations for the target version.
    * Update the `gradle.properties` and `fabric.mod.json` with the new version requirements.

2.  **Code Migration:**
    * Copy the core logic from the previous version.
    * Audit the code for breaking API changes in the Fabric API or Minecraft internals.
    * Test functionality in a development environment (`./gradlew runClient`).

3.  **Branching & Building:**
    * Create a new branch for the version: `git checkout -b <version_number>`.
    * Run the build command: `./gradlew build`.
    * The compiled `.jar` will be located in `build/libs`.

### Release Workflow
Releases are handled in batches once all targeted versions are verified:
1.  Verify builds for all active branches.
2.  Tag the releases.
3.  Simultaneously publish to **GitHub Releases**, **Modrinth**, and **CurseForge**.

## License

This project is licensed under the **CC0 1.0 Universal License**. See the [LICENSE](LICENSE) file for more details.

---
*Maintained by [Demomaker](https://github.com/Demomaker)*
