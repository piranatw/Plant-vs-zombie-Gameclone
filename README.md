# Plants vs Zombies Clone

This is a JavaFX-based clone of the popular game \"Plants vs Zombies.\" The project implements a simplified version of the game where players can plant various plants to defend against waves of zombies.

## Features

- **Plants**: Includes various plants like Sunflower, Peashooter, Walnut, Cherry Bomb, Potato Mine, and Frosty Peashooter, each with unique abilities.
- **Zombies**: Includes Basic Zombies and Cap Zombies with different health and behaviors.
- **Sun Collection**: Players can collect suns to plant more plants.
- **Game Logic**: Handles waves of zombies, plant placement, and collisions between plants, zombies, and bullets.
- **Collision Detection**: Manages interactions between plants, zombies, and bullets.
- **Difficulty Levels**: Easy, Normal, and Hard modes available from the main menu.

## Project Structure

`````
src/
├── main/
│ ├── java/
│ │ ├── base/ # Core game entities like plants, zombies, and bullets
│ │ ├── deck/ # Plant cards for selecting plants
│ │ ├── gui/ # GUI components like the game grid and slots
│ │ ├── logic/ # Interfaces for shootable and explodable entities
│ │ └── main/ # Main game logic and entry point
├── test/
│ ├── java/ # Placeholder for unit tests
res/ # Resources like images and audio
pom.xml # Maven configuration file
`````

## How to Run

### Prerequisites

- Java 11 or higher
- Maven
- JavaFX SDK (configured via Maven)

### Steps

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd <repository-folder>
   ```

2. Build the project using Maven:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   mvn javafx:run
   ```

## Gameplay

1. **Main Menu**: Choose a difficulty level (Easy, Normal, or Hard) or quit the game.
2. **Planting**: Select a plant card from the slot and click on a tile to plant it.
3. **Defend**: Use plants to defend against waves of zombies.
4. **Victory**: Defeat all zombies to win the game.
5. **Defeat**: If a zombie reaches the leftmost side, the game is over.

## Key Classes

- **Main**: Entry point of the application.
- **MainMenu**: Handles the main menu UI.
- **GameLogic**: Manages the game loop, zombie spawning, and sun spawning.
- **PvzPane**: Represents the game grid and manages layers for plants, zombies, and bullets.
- **Slot**: Displays plant cards and manages plant selection.
- **CollisionManager**: Detects and handles collisions between game entities.

## Resources

- **Images**: Located in the \`res/\` folder, including plant and zombie sprites.
- **Audio**: Background music and sound effects (if applicable).

## Dependencies

The project uses the following dependencies:

- [JavaFX](https://openjfx.io/) for UI and graphics.

Dependencies are managed via Maven. See the [pom.xml](pom.xml) file for details.

## Contributing

Contributions are welcome! Feel free to open issues or submit pull requests.

## License

This project is for educational purposes and is not affiliated with or endorsed by the creators of \"Plants vs Zombies.\"

## Acknowledgments

- Inspired by the original \"Plants vs Zombies\" game.
- Thanks to the JavaFX community for providing resources and support." > README.md
