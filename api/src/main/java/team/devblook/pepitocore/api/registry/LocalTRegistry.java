package team.devblook.pepitocore.api.registry;

import java.util.Iterator;
import java.util.Map;

public class LocalTRegistry<K, V> implements TRegistry<K, V> {

    private final Map<K, V> cached;

    public LocalTRegistry(Map<K, V> backing) {
        this.cached = backing;
    }

    @Override
    public V find(K key) {
        return cached.get(key);
    }

    @Override
    public V insert(K key, V model) {
        return cached.put(key, model);
    }

    @Override
    public V delete(K key) {
        return cached.remove(key);
    }

    @Override
    public long clear() {
        int size = cached.size();
        cached.clear();
        return size;
    }

    public int size() {
        return cached.size();
    }

    @Override
    public Iterator<V> iterator() {
        return cached.values().iterator();
    }
}