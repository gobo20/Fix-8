# EventTeamPlugin - Paper 26.2

## Build

Requirements:
- Java 25
- Maven 3.9+
- Internet access to repo.papermc.io

Run:

    mvn clean package

The plugin JAR will be created in `target/`.

## Commands
- `/event <Spieler>` - Spieler zum Event einladen
- `/event set red` - roten Spawn setzen
- `/event set blue` - blauen Spawn setzen
- `/rot` or `/red` - rotes Team
- `/blau` or `/blue` - blaues Team

The plugin uses Paper 26.2's `BasicCommand` API and does not bundle Bukkit/Paper classes.
