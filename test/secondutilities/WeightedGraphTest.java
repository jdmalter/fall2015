package secondutilities;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class WeightedGraphTest {

	private static final int SIZE = 50;
	private static final int SMALL_SIZE = 10;

	private WeightedGraph<Integer>[] graphs;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		graphs = (WeightedGraph<Integer>[]) new WeightedGraph[] { new AdjacencyMapGraph<Integer>() };
	}

	@Test
	public void testAddVertex() {
		for (int i = 0; i < graphs.length; i++) {
			for (int j = 0; j < SIZE; j++) {
				int size = graphs[i].sizeVertices();
				graphs[i].addVertex(j);
				assertEquals(graphs[i].sizeVertices(), size + 1);
			}
			for (int j = 0; j < SIZE; j++) {
				assertEquals(graphs[i].sizeVertices(), SIZE);
				graphs[i].addVertex(j);
				assertEquals(graphs[i].sizeVertices(), SIZE);
			}
		}
	}

	@Test
	public void testAddEdge() {
		testAddVertex();
		for (int i = 0; i < graphs.length; i++) {
			for (int j = 0; j < SIZE / SMALL_SIZE; j++)
				for (int k = 0; k < SMALL_SIZE - 1; k++) {
					int vertex = (j * SMALL_SIZE) + k;
					int size = graphs[i].sizeEdges();
					graphs[i].addEdge(vertex, vertex + 1);
					assertEquals(graphs[i].sizeEdges(), size + 1);
				}
			int size = graphs[i].sizeEdges();
			graphs[i].addEdge(-1, 0);
			assertEquals(graphs[i].sizeEdges(), size);
			size = graphs[i].sizeEdges();
			graphs[i].addEdge(0, -1);
			assertEquals(graphs[i].sizeEdges(), size);
			size = graphs[i].sizeEdges();
			graphs[i].addEdge(0, 1);
			assertEquals(graphs[i].sizeEdges(), size);
		}
	}

	@Test
	public void testContainsVertex() {
		testAddVertex();
		for (int i = 0; i < graphs.length; i++) {
			assertEquals(graphs[i].containsVertex(-1), false);
			assertEquals(graphs[i].containsVertex(0), true);
		}
	}

	@Test
	public void testContainsEdge() {
		testAddEdge();
		for (int i = 0; i < graphs.length; i++) {
			assertEquals(graphs[i].containsEdge(-1, 0), false);
			assertEquals(graphs[i].containsEdge(0, -1), false);
			assertEquals(graphs[i].containsEdge(0, 0), false);
			assertEquals(graphs[i].containsEdge(0, 1), true);
		}
	}

	@Test
	public void testVertices() {
		testAddVertex();
		for (int i = 0; i < graphs.length; i++) {
			Iterator<Integer> it = graphs[i].vertices();
			assertEquals(it.hasNext(), true);
			for (int j = 0; j < SIZE; j++)
				assertEquals(it.next().intValue(), j);
			assertEquals(it.hasNext(), false);
		}
	}

	@Test
	public void testAdjacentVertices() {
		testAddEdge();
		for (int i = 0; i < graphs.length; i++) {
			for (int j = 0; j < SIZE / SMALL_SIZE; j++)
				for (int k = 1; k < SMALL_SIZE - 1; k++) {
					int vertex = (j * SMALL_SIZE) + k;
					Iterator<Integer> it = graphs[i].adjacentVertices(vertex);
					assertEquals(it.hasNext(), true);
					assertEquals(it.next().intValue(), vertex - 1);
					assertEquals(it.hasNext(), true);
					assertEquals(it.next().intValue(), vertex + 1);
					assertEquals(it.hasNext(), false);
				}
			assertEquals(graphs[i].adjacentVertices(-1).hasNext(), false);
		}
	}

	@Test
	public void testSharedVerticies() {
		testAddEdge();
		for (int i = 0; i < graphs.length; i++) {
			for (int j = 0; j < SIZE / SMALL_SIZE; j++)
				for (int k = 0; k < SMALL_SIZE - 2; k++) {
					int vertex = (j * SMALL_SIZE) + k;
					Iterator<Integer> it = graphs[i].sharedVerticies(vertex,
							vertex + 2);
					assertEquals(it.hasNext(), true);
					assertEquals(it.next().intValue(), vertex + 1);
					assertEquals(it.hasNext(), false);
				}
			assertEquals(graphs[i].sharedVerticies(-1, 0).hasNext(), false);
			assertEquals(graphs[i].sharedVerticies(0, -1).hasNext(), false);
		}
	}

	@Test
	public void testShortestPathTree() {
		testAddVertex();
		for (int i = 0; i < graphs.length; i++) {
			for (int j = 0; 2 * j + 1 < SIZE; j++) {
				graphs[i].addEdge(j, 2 * j + 1);
				graphs[i].addEdge(j, 2 * j + 2);
			}
			Graph<Integer> tree = graphs[i].shortestPathTree(0);
			assertEquals(tree.sizeVertices(), SIZE);
			assertEquals(tree.sizeEdges(), SIZE - 1);
			assertEquals(tree.degree(0), 2);
		}
	}

	@Test
	public void testConnectedComponent() {
		testAddEdge();
		for (int i = 0; i < graphs.length; i++) {
			for (int j = 0; j < SIZE / SMALL_SIZE; j++) {
				Graph<Integer> component = graphs[i].connectedComponent(j
						* SMALL_SIZE);
				assertEquals(component.sizeVertices(), SMALL_SIZE);
			}
			assertEquals(graphs[i].connectedComponent(-1).sizeVertices(), 0);
		}
	}

	@Test
	public void testRemoveVertex() {
		testAddVertex();
		for (int i = 0; i < graphs.length; i++) {
			for (int j = 0; j < SIZE - 1; j++)
				graphs[i].addEdge(j, j + 1);
			for (int j = 0; j < SIZE - 1; j++) {
				int size = graphs[i].sizeVertices();
				int edgeSize = graphs[i].sizeEdges();
				graphs[i].removeVertex(j);
				assertEquals(graphs[i].sizeVertices(), size - 1);
				assertEquals(graphs[i].sizeEdges(), edgeSize - 1);
			}
			int size = graphs[i].sizeVertices();
			int edgeSize = graphs[i].sizeEdges();
			graphs[i].removeVertex(SIZE - 1);
			assertEquals(graphs[i].sizeVertices(), size - 1);
			assertEquals(graphs[i].sizeEdges(), edgeSize);
			size = graphs[i].sizeVertices();
			graphs[i].removeVertex(-1);
			assertEquals(graphs[i].sizeVertices(), size);
		}
	}

	@Test
	public void testRemoveEdge() {
		testAddVertex();
		for (int i = 0; i < graphs.length; i++) {
			for (int j = 0; j < SIZE - 1; j++)
				graphs[i].addEdge(j, j + 1);
			for (int j = 0; j < SIZE - 1; j++) {
				int edgeSize = graphs[i].sizeEdges();
				graphs[i].removeEdge(j, j + 1);
				assertEquals(graphs[i].sizeEdges(), edgeSize - 1);
			}
			int size = graphs[i].sizeEdges();
			graphs[i].removeEdge(-1, 0);
			assertEquals(graphs[i].sizeEdges(), size);
			size = graphs[i].sizeEdges();
			graphs[i].removeEdge(0, -1);
			assertEquals(graphs[i].sizeEdges(), size);
			size = graphs[i].sizeEdges();
			graphs[i].removeEdge(0, 0);
			assertEquals(graphs[i].sizeEdges(), size);
		}
	}

	@Test
	public void testAddEdgeVVInt() {
		testAddVertex();
		for (int i = 0; i < graphs.length; i++) {
			for (int j = 0; j < SIZE / SMALL_SIZE; j++)
				for (int k = 0; k < SMALL_SIZE - 1; k++) {
					int vertex = (j * SMALL_SIZE) + k;
					int size = graphs[i].sizeEdges();
					graphs[i].addEdge(vertex, vertex + 1, vertex);
					assertEquals(graphs[i].sizeEdges(), size + 1);
				}
		}
	}

	@Test
	public void testGetEdgeWeight() {
		testAddEdgeVVInt();
		for (int i = 0; i < graphs.length; i++) {
			for (int j = 0; j < SIZE / SMALL_SIZE; j++)
				for (int k = 0; k < SMALL_SIZE - 1; k++) {
					int vertex = (j * SMALL_SIZE) + k;
					int weight = graphs[i].getEdgeWeight(vertex, vertex + 1);
					assertEquals(weight, vertex);
				}
			assertEquals(graphs[i].getEdgeWeight(-1, -1), 0);
		}
	}

	@Test
	public void testSetEdgeWeight() {
		testAddEdgeVVInt();
		for (int i = 0; i < graphs.length; i++) {
			for (int j = 0; j < SIZE / SMALL_SIZE; j++)
				for (int k = 0; k < SMALL_SIZE - 1; k++) {
					int vertex = (j * SMALL_SIZE) + k;
					int weight = graphs[i].setEdgeWeight(vertex, vertex + 1,
							2 * vertex);
					assertEquals(weight, vertex);
					weight = graphs[i].getEdgeWeight(vertex, vertex + 1);
					assertEquals(weight, 2 * vertex);
				}
			assertEquals(graphs[i].setEdgeWeight(-1, -1, -1), 0);
		}
	}
}