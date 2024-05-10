package team.devblook.pepitocore.api.registry;

import java.util.Iterator;

public interface TRegistry<K, V> extends Iterable<V> {

    V find(K key);

    V insert(K key, V model);

    V delete(K key);

    long clear();

    @Override
    Iterator<V> iterator();
}