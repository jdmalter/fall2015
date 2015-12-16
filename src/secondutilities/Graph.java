package secondutilities;

public interface Graph<V> {

	void addVertex(V v);

	void addEdge(V v, V u);

	boolean containsVertex(V v);

	boolean containsEdge(V v, V u);

	Iterator<V> vertices();

	Iterator<V> adjacentVertices(V v);

	Iterator<V> sharedVerticies(V v, V u);

	Graph<V> shortestPathTree(V v);

	Graph<V> connectedComponent(V v);

	void removeVertex(V v);

	void removeEdge(V v, V u);

	int sizeVertices();

	int sizeEdges();

	int degree(V v);

}