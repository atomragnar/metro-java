# metro-java

My implementation and completion of the HyperMetro project in Java, link to project -> https://hyperskill.org/projects/120

---------------------------------------------------

For the project I created my own Graph data structure that reads Metro input from a Json file.

Possible to supply Json through args with matchning structure to london.json. If no args is supplied it will load up london.json by default.

london.json is a json file of the London Underground network. The Graph is therefore able to handle MetroLines (or Graphs) with multiple starting points and cycles.

Graph Traversal algorithms are implemented through an Enum/Strategy pattern. 

The implemented algorithms are Djikstra, BFS and DFS.

![](https://github.com/atomragnar/metro-java/blob/master/metro.gif)
