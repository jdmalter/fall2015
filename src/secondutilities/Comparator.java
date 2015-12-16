package secondutilities;

public interface Comparator<E extends Comparable<E>> {

	int compare(E left, E right);

}