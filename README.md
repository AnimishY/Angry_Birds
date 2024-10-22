# Angry Birds Game - AP Edition
By Pranshu (2023385) & Animish Yadav (2023089)

This project is a clone of the classic Angry Birds game built using the [LibGDX](https://libgdx.com/) game development framework and Gradle for project management.

## Table of Contents
- [Getting Started](#getting-started)
- [Project Structure](#project-structure)
- [Building and Running](#building-and-running)
- [Controls](#controls)
- [Gameplay](#gameplay)

## Getting Started

### Prerequisites
Ensure you have the following installed on your system:
- **Java Development Kit (JDK)** (version 8 or higher)
- **Gradle** (optional, if you don't want to use the wrapper)

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/Pranshu-iiitd/Angry_Birds.git
   cd Angry_Birds
   ```

2. Sync the project dependencies:
   ```bash
   ./gradlew build
   ```

## Project Structure

- `core/` : Contains game logic, assets, and core mechanics of the game.
- `assets/` : Game assets (sprites, sounds, etc.).
- `gradle/` : Gradle wrapper and build configuration files.

## Building and Running

To build and run the game on your desktop:
```bash
./gradlew desktop:run
```

## Controls
- **Mouse Drag**: Pull the slingshot.
- **Release Mouse**: Launch the bird.

## Gameplay

Your objective is to use a slingshot to launch birds and destroy structures containing enemy pigs. You must destroy all pigs to complete the level.

### Features:
- Physics-based gameplay.
- Multiple bird types with different abilities.
- Multiple pig types with different strength.
- Support for 2 users.
- Multiple levels with increasing difficulty.
- Locked Levels are also available which unlocks are completing initial levels.
