package secondutilities;

public interface UndirectedWeightedGraph<V> extends WeightedGraph<V> {

	UndirectedWeightedGraph<V> boruvka();

	UndirectedWeightedGraph<V> dijkstra(V v);

}