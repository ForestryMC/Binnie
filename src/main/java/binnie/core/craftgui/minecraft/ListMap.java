package binnie.core.craftgui.minecraft;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

class ListMap<T> implements List<T> {
    private LinkedHashMap<Integer, T> map;

    ListMap() {
        map = new LinkedHashMap<>();
    }

    @Override
    public int size() {
        int i = -1;
        for (int k : map.keySet()) {
            if (k > i) {
                i = k;
            }
        }
        return i + 1;
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return map.containsValue(o);
    }

    @Override
    public Iterator<T> iterator() {
        return map.values().iterator();
    }

    @Override
    public Object[] toArray() {
        return map.values().toArray();
    }

    @Override
    public <P> P[] toArray(P[] a) {
        return map.values().toArray(a);
    }

    @Override
    public boolean add(T e) {
        if (get(size()) == null) {
            add(size(), e);
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return map.values().containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public T get(int index) {
        return map.get(index);
    }

    @Override
    public T set(int index, T element) {
        map.put(index, element);
        return element;
    }

    @Override
    public void add(int index, T element) {
        map.put(index, element);
    }

    @Override
    public T remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        for (Map.Entry<Integer, T> entry : map.entrySet()) {
            if (entry.getValue() == o) {
                return entry.getKey();
            }
        }
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return indexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        return null;
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return null;
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return null;
    }
}
