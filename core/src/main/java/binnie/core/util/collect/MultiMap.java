package binnie.core.util.collect;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

public class MultiMap<K, V, T extends Collection<V>> {
	protected final Map<K, T> map;
	private final Function<K, T> collectionMappingFunction;

	public MultiMap(Supplier<T> collectionSupplier) {
		this(new HashMap<>(), collectionSupplier);
	}

	public MultiMap(Map<K, T> map, Supplier<T> collectionSupplier) {
		this.map = map;
		this.collectionMappingFunction = (k -> collectionSupplier.get());
	}

	public T get(K key) {
		return map.computeIfAbsent(key, collectionMappingFunction);
	}

	public boolean put(K key, V value) {
		return get(key).add(value);
	}

	public boolean contains(K key, V value) {
		return get(key).contains(value);
	}

	public void clear() {
		map.clear();
	}

	public boolean isEmpty() {
		for (Collection<V> values : map.values()) {
			if (!values.isEmpty()) {
				return false;
			}
		}
		return true;
	}
}
