package secondutilities;

public class AdjacencyMapGraph<V> implements UndirectedWeightedGraph<V> {

	private int edgeSize;
	private Map<V, Map<V, Integer>> vertices;

	public AdjacencyMapGraph() {
		vertices = new LinkedHashMap<V, Map<V, Integer>>();
	}

	@Override
	public void addVertex(V v) {
		if (!vertices.containsKey(v))
			vertices.put(v, new LinkedHashMap<V, Integer>());
	}

	@Override
	public void addEdge(V v, V u) {
		addEdge(v, u, 1);
	}

	@Override
	public boolean containsVertex(V v) {
		return vertices.containsKey(v);
	}

	@Override
	public boolean containsEdge(V v, V u) {
		if (!vertices.containsKey(v) || !vertices.containsKey(u))
			return false;
		return vertices.get(v).containsKey(u);
	}

	@Override
	public Iterator<V> vertices() {
		return vertices.keyIterator();
	}

	@Override
	public Iterator<V> adjacentVertices(V v) {
		if (!vertices.containsKey(v))
			return new LinkedList<V>().iterator();
		return vertices.get(v).keyIterator();
	}

	@Override
	public Iterator<V> sharedVerticies(V v, V u) {
		List<V> result = new LinkedList<V>();
		if (!vertices.containsKey(v) || !vertices.containsKey(u))
			return result.iterator();
		Iterator<V> adjacent = vertices.get(v).keyIterator();
		while (adjacent.hasNext()) {
			V current = adjacent.next();
			if (vertices.get(u).containsKey(current))
				result.add(result.size(), current);
		}
		return result.iterator();
	}

	@Override
	public Graph<V> shortestPathTree(V v) {
		Graph<V> spt = new AdjacencyMapGraph<V>();
		Deque<V> queue = new LinkedDeque<V>();
		queue.addLast(v);
		spt.addVertex(v);
		while (queue.size() > 0) {
			V vertex = queue.removeFirst();
			Iterator<V> edges = vertices.get(vertex).keyIterator();
			while (edges.hasNext()) {
				V otherVertex = edges.next();
				if (!spt.containsVertex(otherVertex)) {
					queue.addLast(otherVertex);
					spt.addVertex(otherVertex);
					spt.addEdge(vertex, otherVertex);
				}
			}
		}
		return spt;
	}

	@Override
	public Graph<V> connectedComponent(V v) {
		Graph<V> graph = new AdjacencyMapGraph<V>();
		if (!vertices.containsKey(v))
			return graph;
		Deque<V> stack = new LinkedDeque<V>();
		stack.addFirst(v);
		while (stack.size() > 0) {
			V current = stack.removeFirst();
			graph.addVertex(current);
			Iterator<V> edges = vertices.get(current).keyIterator();
			while (edges.hasNext()) {
				V next = edges.next();
				if (!graph.containsVertex(next))
					stack.addFirst(next);
				graph.addEdge(current, next);
			}
		}
		stack = null;
		return graph;
	}

	@Override
	public Iterator<Graph<V>> connectedComponents() {
		List<Graph<V>> result = new LinkedList<Graph<V>>();
		Map<V, V> found = new LinkedHashMap<V, V>();
		Iterator<V> it = vertices.keyIterator();
		while (it.hasNext()) {
			V current = it.next();
			if (!found.containsKey(current)) {
				Graph<V> component = connectedComponent(current);
				result.add(result.size(), component);
				Iterator<V> searched = component.vertices();
				while (searched.hasNext()) {
					V next = searched.next();
					found.put(next, next);
				}
			}
		}
		return result.iterator();
	}

	@Override
	public void removeVertex(V v) {
		if (!vertices.containsKey(v))
			return;
		Iterator<V> edges = vertices.get(v).keyIterator();
		while (edges.hasNext())
			removeEdge(v, edges.next());
		vertices.remove(v);
	}

	@Override
	public void removeEdge(V v, V u) {
		if (!vertices.containsKey(v) || !vertices.containsKey(u))
			return;
		if (vertices.get(v).containsKey(u)) {
			vertices.get(v).remove(u);
			vertices.get(u).remove(v);
			edgeSize--;
		}
	}

	@Override
	public int sizeVertices() {
		return vertices.size();
	}

	@Override
	public int sizeEdges() {
		return edgeSize;
	}

	@Override
	public int degree(V v) {
		if (!vertices.containsKey(v))
			return 0;
		return vertices.get(v).size();
	}

	@Override
	public void addEdge(V v, V u, int weight) {
		if (!vertices.containsKey(v) || !vertices.containsKey(u)
				|| containsEdge(v, u))
			return;
		vertices.get(v).put(u, weight);
		vertices.get(u).put(v, weight);
		edgeSize++;
	}

	@Override
	public int getEdgeWeight(V v, V u) {
		if (!containsEdge(v, u))
			return 0;
		return vertices.get(v).get(u).intValue();
	}

	@Override
	public int setEdgeWeight(V v, V u, int weight) {
		if (!containsEdge(v, u))
			return 0;
		return vertices.get(v).put(u, weight);
	}

	@Override
	public UndirectedWeightedGraph<V> boruvka() {
		UndirectedWeightedGraph<V> mst = new AdjacencyMapGraph<V>();
		Iterator<V> it = vertices.keyIterator();
		while (it.hasNext())
			mst.addVertex(it.next());
		while (mst.sizeEdges() < mst.sizeVertices() - 1) {
			Iterator<Graph<V>> components = mst.connectedComponents();
			while (components.hasNext())
				addMinimumEdge(components.next(), mst);
		}
		return mst;
	}

	@Override
	public UndirectedWeightedGraph<V> dijkstra(V v) {
		if (!vertices.containsKey(v))
			return new AdjacencyMapGraph<V>();
		UndirectedWeightedGraph<V> spt = new AdjacencyMapGraph<V>();
		return spt;
	}

	private void addMinimumEdge(Graph<V> component, WeightedGraph<V> mst) {
		class Edge {
			private V outgoing, ingoing;
			private int weight;

			private Edge(V outgoing, V ingoing, int weight) {
				this.outgoing = outgoing;
				this.ingoing = ingoing;
				this.weight = weight;
			}
		}
		Edge edge = null;
		Iterator<V> vertices = component.vertices();
		while (vertices.hasNext()) {
			V vertex = vertices.next();
			Iterator<V> edges = adjacentVertices(vertex);
			while (edges.hasNext()) {
				V opposite = edges.next();
				int weight = getEdgeWeight(vertex, opposite);
				if (!component.containsVertex(opposite)
						&& (edge == null || weight < edge.weight))
					edge = new Edge(vertex, opposite, weight);
			}
		}
		if (edge != null && !mst.containsEdge(edge.outgoing, edge.ingoing))
			mst.addEdge(edge.outgoing, edge.ingoing, edge.weight);
	}

}