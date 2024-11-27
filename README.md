
# **Angry Birds Game - AP Edition**  
**By Pranshu (2023385) & Animish Yadav (2023089)**  

This project is a clone of the classic Angry Birds game, built using the [LibGDX](https://libgdx.com/) framework as part of the CSE201 (Advanced Programming) course. The game demonstrates object-oriented programming (OOP) principles, design patterns, and serialization concepts.

---

## **Table of Contents**
- [Overview](#overview)
- [Features](#features)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
- [Controls](#controls)
- [Gameplay](#gameplay)
- [Design Highlights](#design-highlights)
- [Testing](#testing)
- [Credits](#credits)

---

## **Overview**

Angry Birds Game - AP Edition challenges players to use a slingshot to defeat pigs hidden within structures. The game incorporates physics-based gameplay, OOP principles, and clean design practices. It also supports saving and restoring progress through serialization.

---

## **Features**
### **Core Gameplay**
- Slingshot mechanics: drag to aim and launch birds.
- Birds with different speeds and impacts.
- Pigs with varying durability and size.
- Structures with blocks made of wood, glass, and steel.

### **Levels**
- Three unique levels, each with varying difficulties and setups.

### **Serialization**
- Save and restore game progress, including:
  - Remaining birds.
  - Hits dealt to pigs and structures.
  - Collapsed structures.

### **Bonus Features** *(if implemented)*:
- Multiple user profiles.
- Sound effects and background music.

---

## **Getting Started**

### **Prerequisites**
Ensure the following are installed:
- **Java Development Kit (JDK)** 8 or higher.
- **Gradle** (optional, if you don't use the Gradle wrapper).

### **Installation**
1. Clone this repository:
   ```bash
   git clone https://github.com/Pranshu-iiitd/Angry_Birds.git
   cd Angry_Birds
   ```
2. Build the project using Gradle:
   ```bash
   ./gradlew build
   ```
3. Run the game:
   ```bash
   ./gradlew run
   ```

---

## **Controls**
- **Drag**: Aim the slingshot.
- **Release**: Launch the bird.
- **Menu Options**:
    - Save: Save current game state.
    - Load: Restore a saved game state.

---

## **Gameplay**
- Launch birds at structures to destroy pigs.
- Win by defeating all pigs within the available birds.
- Lose if all birds are used without defeating all pigs.
- Each bird, pig, and block type has unique properties:
    - Birds vary in speed and damage.
    - Pigs and blocks vary in durability.

---

## **Design Highlights**
1. **OOP Principles**:
    - Inheritance: Birds and pigs are derived from base classes.
    - Polymorphism: Special abilities for different birds.
    - Interfaces: Game mechanics and event handling.
2. **Design Patterns**:
    - Composite: Used in materials and it's derived classes.
    - State: Used in switching between different menus and screens.
    - Strategy: Used in bird movement and collision detection.
    - Observer: Used in event handling and notifications.
3. **Serialization**:
    - Saves and restores gameplay progress.

---

## **Testing**
JUnit tests are implemented to ensure game components work as expected:
- Physics calculations.
- Serialization and deserialization.
- Level completion checks.
- A total of 23 tests are implemented.

---

## **Credits**
- Game framework: [LibGDX](https://libgdx.com/)
- Original concept: [Angry Birds](https://www.angrybirds.com/)
- Course: CSE201 - Advanced Programming
- Developers:
    - Pranshu (2023385)
    - Animish Yadav (2023089)
