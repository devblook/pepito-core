package team.devblook.pepitocore.api.datastore;

import team.devblook.pepitocore.api.Identity;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public interface TDatastore<T extends Identity> {

    T findSync(String id, String name);

    CompletableFuture<T> find(String id, String name);

    CompletableFuture<Void> save(String owner, T value);

    CompletableFuture<Collection<T>> values(String owner);
}