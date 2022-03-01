**I implemented "A Star", "BFS" and "IDA Star" algorithms.**


# Sokoban4J

## Building the game

This version of Sokoban4J works with Java 11, 12, or 13, and probably older Java versions as well.  It includes Maven project files, and you should easily be able to open it in any Java IDE such as Eclipse, IntelliJ, or Visual Studio Code.

## Playing the game

To play the game from the keyboard, run the Main class in the Sokoban4J/src/main/java/cz/sokoban4j subdirectory.  Edit the code in the main() method to specify the level(s) that you wish to play.  You can choose any of the levels in the Sokoban4J/levels subdirectory.

Use the arrow keys or the W/S/A/D keys to move.

## Testing the agents

To test the agents, the class RunMyAgent should be run. Then, from the keyboard:
Press '0' to exit or select a level by typing a number between '1' - '16' followed by the character 'i' for IDA* agent, 'a' for A* agent and 'b' for BFS agent.


