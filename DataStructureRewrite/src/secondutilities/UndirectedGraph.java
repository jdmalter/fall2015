package secondutilities;

public interface UndirectedGraph<V> {

	void addVertex(V v);

	void addEdge(V v, V u);

	boolean containsVertex(V v);

	boolean containsEdge(V v, V u);

	Iterator<V> vertices();

	Iterator<V> adjacentVertices(V v);

	Iterator<V> sharedVerticies(V v, V u);

	UndirectedGraph<V> shortestPathTree(V v);

	UndirectedGraph<V> connectedComponent(V v);

	void removeVertex(V v);

	void removeEdge(V v, V u);

	int sizeVertices();

	int sizeEdges();

	int degree(V v);

}