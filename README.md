**ECE 651 RISC Game**

[![pipeline status](https://gitlab.oit.duke.edu/xl340/ece651-risc/badges/master/pipeline.svg)]
![coverage](https://gitlab.oit.duke.edu/xl340/ece651-risc/badges/master/coverage.svg?job=test)
## Coverage
[Detailed coverage](https://xl340.pages.oit.duke.edu/ece651-risc/dashboard.html)

# UML
https://www.processon.com/diagraming/60440005e0b34d07b85fe45c

# Step

## Initial:

The Player will be able to choose the server to connect. For example: ./client localhost

## Start:

The first connected player need to type the total amount of players he want after connecting to the server.
Then all other players can also get connect. Then he will be able to choose the map and the number of players. Currently for a specific number of players game, we have one map. So The first player just need to type any non-negative numebr to choose a map.
Then all players will choose which group of territory they would like to choose. For this version, one group of territory will have 3 territories and the total deployable amount of units is 15. 

## Deploy:

The players will be able to deploy the units in their own territory. A player needs to type as the following format
```
<territory id>,<# of units>
```
for example,
```
0,5
```
means he will deploy 5 units on territory 0.

All players must deploy all their units to continue.

## Orders:

The players will be able to Move and Attack. And after issuing all the orders, a player can declare he/she is done. To choose which type of order he/she wants to issue, the player just need to type the first letter of the order type. For example. type `a` or `A` will issue an attack order, type 'm' or `M` will issue a move order. And `d` or `D` for declaring "I'm done".

The format of a order is the following: 
```
<source territory id> <destination territory id> <# of units>
```

each number is seperated with one white space.

For example, a move order with following content:
```
0 1 4
```

