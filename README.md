# Trains

## Problem Definition
The local commuter railroad services a number of towns in Kiwiland.
Because of monetary concerns, all of the tracks are 'one-way.'
That is, a route from Kaitaia to Invercargill does not imply the existence of a route from Invercargill to Kaitaia.
In fact, even if both of these routes do happen to exist, they are distinct and are not necessarily the same totalDistance!
 
The purpose of this problem is to help the railroad provide its customers with information about the routes.
In particular, you will compute the totalDistance along a certain route, 
the number of different routes between two towns, and the shortest route between two towns.

### Input 
A directed graph where a node represents a town and 
an edge represents a route between two towns. 
The weighting of the edge represents the distance between the two towns. 
A given route will never appear more than once, and for a given route, 
the starting and ending town will not be the same town

### Output
For test input 1 through 5, if no such route exists, output 'NO SUCH ROUTE'.
Otherwise, follow the route as given; do not make any extra stops!  
For example, the first problem means to start at city A, 
then travel directly to city B (a distance of 5), 
then directly to city C (a distance of 4).


## Run Information

### Build project
The project uses maven for build tool.


In order to build it you can just got to the tw-trains directory and:
```shell
$ mvn clean install
```
 
### Run project
During the packaging phase maven create a jar file which can be used as an executable.

In order to run the application just: 

```shell
$ java -jar trains.jar <land> <railway map filename> <queries filename>
```

The program arguments:

  - **land**: the name of the land that is represented on the map.
  - **railway map filename**: the file name which describes the trains map.
  - **queries filename**: the file name which describes the queries on the map.
  
#### Sample Input
Railway Map:
```text
AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7
```

Queries:
```text
The distance of the route A-B-C.
The distance of the route A-D.
The distance of the route A-D-C.
The distance of the route A-E-B-C-D.
The distance of the route A-E-D.
The number of trips starting at C and ending at C with a maximum of 3 stops.  In the sample data below, there are two such trips: C-D-C (2 stops). and C-E-B-C (3 stops).
The number of trips starting at A and ending at C with exactly 4 stops.  In the sample data below, there are three such trips: A to C (via B,C,D); A to C (via D,C,D); and A to C (via D,E,B).
The length of the shortest route (in terms of distance to travel) from A to C.
The length of the shortest route (in terms of distance to travel) from B to B.
The number of different routes from C to C with a distance of less than 30.  In the sample data, the trips are: CDC, CEBC, CEBCDC, CDCEBC, CDEBC, CEBCEBC, CEBCEBCEBC.
```

#### Expected Output
```text
Output #1: 9
Output #2: 5
Output #3: 13
Output #4: 22
Output #5: NO SUCH ROUTE
Output #6: 2
Output #7: 3
Output #8: 9
Output #9: 9
Output #10: 7
```

## Implementation Details
The project has been implemented in **Java 8** and it is being used a completely Test Driven Development approach.
Both code and design are a result of TDD while the testing framework have been used is **JUnit 4**.

### Further Improvements
Some improvement can be:

  - Get error messages from files, in order to have different languages.
  - Better query parser (language using something like antlr).
  - More algorithms in order to show the power of changing between implementations.
  - Full Doxygen documentation.

## About
Author: **Alex Theologos Zacharopoulos**

E-mail: **theol.zacharopoulos@gmail.com**