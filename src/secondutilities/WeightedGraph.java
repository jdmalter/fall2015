package secondutilities;

public interface WeightedGraph<V> extends Graph<V> {

	void addEdge(V v, V u, int weight);

	int getEdgeWeight(V v, V u);

	int setEdgeWeight(V v, V u, int weight);

}