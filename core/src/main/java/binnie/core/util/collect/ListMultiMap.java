package binnie.core.util.collect;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ListMultiMap<K, V> extends MultiMap<K, V, List<V>> {
	public ListMultiMap() {
		this(ArrayList::new);
	}

	public ListMultiMap(Supplier<List<V>> collectionSupplier) {
		super(collectionSupplier);
	}
}
