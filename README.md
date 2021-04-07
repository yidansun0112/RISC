# ECE 651 RISC Game

[![pipeline status](https://gitlab.oit.duke.edu/xl340/ece651-risc/badges/master/pipeline.svg)]
![coverage](https://gitlab.oit.duke.edu/xl340/ece651-risc/badges/master/coverage.svg?job=test)
## Coverage
[Test Coverage : 94 %](https://gitlab.oit.duke.edu/xl340/ece651-risc/-/pipelines/209485/builds)

You can see the coverage data on the `Jobs` tag in the link.

Note that the following commits on branch `master-evo2` are the editing on README, which does not change the coverage)

# Step

## Initial:

First please `gradle run-server`, then `gradle run-client` to launch multiple GUI windows. You need first to register and login before you play the game.

We will check whether the user name is already exist, or you provide a wrong password when you log in.

## Start:

The first connected player need to type the total amount of players he want after connecting to the server.
Then all other players can also get connect. Then he will be able to choose the map and the number of players. Currently for a specific number of players game, we have one map. So The first player just need to type any non-negative numebr to choose a map.
Then all players will choose which group of territory they would like to choose. For this version, one group of territory will have 3 territories and the total deployable amount of units is 15. 

## Deploy:

The players will be able to deploy the units in their own territory using the GUI. Players will use the drop down menu to deploy their units.

## Orders:

We have move order, attack order, upgrade max tech level order and upgrade units order in this evolution. And units have a new attribute called level.

You can only issue one upgrade max tech level order in a turn, if you have enough tech resources.

You cannot move from and to the same territory.

## Battle Reporting
Each time when a turn is finished, you will see the battle results in this turn, like who attacak who, win or lose. If there battle reporting box is emtpy, this means on one attacked other players in this turn.

## Leave the Room

Now we support numtiple games at a time. You are allowed to leave room before you issue the done order. From the user experience consideration, you are not allowed to leave the room before all players are here -- image you quite the game when matching with others in League of Legends, you may even be reported by others.

You cannot return to your room which you lost the game.

# GUI

We includes multiple user experience princples in our GUI design, and the explanation is included in file `UI_Prototype_with_UX_experience_explanation`. It would be convenient to start the slide show, the location where we include an UX principle is clearly shown with a animation effect.

**If you run our code through the connection between your local machine and the DUKE VM or between your local machine and virtual box, the delay of the content in GUI window is expected, due to the network speed. It may behave as the delay in clicking / dropping, none content in out pop-up window, etc. We point this out, to clarify that this is not due to the imperfect of out feature implementation. It is recommended to run the GUI App directly in your local environment (e.g., on a Mac)**