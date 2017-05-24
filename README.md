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
The project uses maven for build tool.

### Test project
Use maven to run the tests of the project:
```shell
$ mvn test
```

### Build project
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

### Parsing
First, the program parses both the map definition and the queries as given above.
A common `Parser` interface is used for parsing both. 

More specifically, an abstract `GraphParser` defines the correct types for paring a graph, 
while a `CharGraphParser` implements the logic of building a graph as the ones given in the example (with *Character*s as vertices).

Furthermore, an abstract `QueriesParser` defines the correct types for parsing a query, 
while the abstract `RailwayQueryParser` implements the logic of building a set of queries such as the ones in the example.
Additionally, the concrete `CharRailwayQueryParser` implementation if defined for Character based graphs.

### Graph
The basic functionalities of a graph are described by the `Graph` interface. 
The generic type on its definition declares the type of the vertices that are included on the Graph, e.g. on our case is *Character*.

The `AdjacencyMapGraph` provides an implementation of the `Graph` interface using an **adjacency map** data structure.
However, this implementation is a *undirected* graph implementation and we need for this problem a *directed* graph.
The definition of a directed graph is given in `AdjacencyMapDirectedGraph` which is just an extension of the previous.

### Graph Algorithms
There are 3 different algorithms that are needed to answers the questions, one to count paths,
one to count the distance of a route and one to find the shortest distance between two vertices.

The algorithms are plug-able in the code. First, there is a very abstract interface `GraphAlgorithm`, 
which is more as a *marking* interface. 
Then, there are 3 different interfaces for each algorithmic problem that is needed: 
`CountPathsAlgorithm`, `RouteDistanceAlgorithm` and `ShortestPathAlgorithm` respectively.

By using the `GraphAlgorithmProvider` class a client can request an algorithm for each problem by asking with its name.
There is one algorithm implementation for each interface above: 
`DFSCountPathsAlgorithm`, `GreedyRouteDistanceAlgorithm` and `DijkstraShortestPathAlgorithm`.

### Queries
The queries are constructed as a simple **command** pattern.
The `Query` interface defines an executable query, then there is a query for each question that can be asked:
`NumOfRoutesQuery`, `RouteDistanceQuery` and `ShortestRouteQuery`.

Each one of these queries accepts an algorithm implementation on their creation, this way the client 
can choose which algorithm each query can use like a **strategy**. 
The `QueryParser` os responsible of creating each of these queries depending on the instructions given like a **factory**.

Moreover, the `QueryExecutor` interface defines a mechanism to execute queries.
On this problem, `RailwayQueryExecutor` has been used in order to load the queries instructions, 
then use the `RailwayQueryParser` to create the queries and then executing then one by one and provide the 
result to a *Consumer* on the client.

### Overall
The `RailwayMap` object contains a name of the map (e.g. Kiwiland) and a graph that represents the map structure,
where vertices are towns and edges are routes from town to town with their weight representing the distance.

A simple usage of the above can be seen bellow:
```java
// Read the input from files, map and queries
final String mapDescription = FileUtils.readFileContents(mapFilename);
final List<String> queryInstructions = FileUtils.readFileContentsLineByLine(queriesFilename);

// Create and parse the graph
final Graph<Character> graph = new AdjacencyMapDirectedGraph<>();
(new CharGraphParser(graph)).parse(mapDescription);

// Create a map with Character type for the vertices
final RailwayMap<Character> railwayMap = new RailwayMap<>(landName, graph);

// Parse the queries
final RailwayQueryParser<Character> queryParser = new CharRailwayQueryParser(railwayMap);
final QueryExecutor<Integer> queryExecutor = new RailwayQueryExecutor<>(queryParser);
queryExecutor.loadQueries(queryInstructions);

// Execute the queries
queryExecutor.executeAll(result -> {
    if (result.isPresent()) {
        System.out.println("Output: " + result.get());
    } else {
        System.out.println("NO SUCH ROUTE.");
    }
});
```

## Further Improvements
Some improvement can be:

  - Get error messages from files, in order to have different languages.
  - More advanced query parser (use an existing parser solution).
  - Full Doxygen documentation.

## About
Author: **Alex Theologos Zacharopoulos**

E-mail: **theol.zacharopoulos@gmail.com**