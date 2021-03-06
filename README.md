# Game Of Thrones - Command Line Role playing game

Test Coverage (94%)
-----------------------------------------------------------------------
command
```
mvn clean test
```
jacoco report :
```
./target/site/jacoco/index.html
```
Note: Excluded classes are domain classes and main methods.

Building packages
-----------------------------------------------------------------------
command
```
1. mvn clean package
2. java -jar target/game_of_thrones.jar
```
Note: Best recommended with IntelliJ IDE.

UML Diagrams are present under the "uml" folder of source

## Game Overview

This is a game of seven kingdom to cliam the throne of Seven Kingdoms. There are in total 8 houses in the game and are set to fight with each other. Through the course of game your computer assisted 'commander' will help you with hints to play the game. it can also recommend the game moves for you.

Game asks you to register if you are new to game and identifies if you are existing user. to register the game.
``` 
register <Name> 
```
if you wish to de-register your self.
```
remove <Name which you entered already>
```
Before you choose your house, if you wish to understand houses available. Explore available houses using below command.
```
explore house all
```
Given the above command gives breif information about the house, if you wish to know the characters available within the house. you need to explore a specific house.
```
explore house <full or partial name>
```
once you are registered, you should select the house with which you want to battle the enemy.
```
PLAYHOUSE <full/or partial name of house>
Note: to know available houses you can use explore house all, explore house <full/partial name of house>
```

once your house is selected its time you choose a character.
```
selectmember <partial/full name>
Note: if you give a partial name which is not unique, it would random charcter within the house.
```

once you have choosen your fighter, its time you choose your enemy's house
```
playenemyhouse <full/or partial name of house>
note: if you try to select same house as your's it won't allow you.
```

once your have choose enemy house, you need to choose enemy character.
```
selectenemy <partial/full name>
```

now, if you want to understand whom you have choosen to fight
```
getinfo
(or)
myinfo
```

if you wish to know what is your current strength and weapons at your disposal
```
myweapons
```

if you wish to fight the enemy, you can choose your weapon. by design, you cannot see enemy strenth or weapons and what enemy can fight with, but computer will choose enemy with a fair weapon use.
```
fight <weapon name(from the list of myweapons)
```

at anypoint of game you can run below commands
to start new game
```
newgame
```
to quit/exit the game.
```
quit (or) exit
```
to understand your profile
```
myprofile
```
to explore houses available.
```
explore house all
```
to explore single house in details
```
explore house <full or partial name>
```

Note: if the command is not understood, then application will ask to train the application to recognize the command


Assumptions:
1. No Frameworks to be used.
2. No database support.
3. command line based input.


