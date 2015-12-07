package secondutilities;

public abstract class AbstractMap<K, V> implements Map<K, V> {

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

}