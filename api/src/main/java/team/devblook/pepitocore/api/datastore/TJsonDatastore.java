package team.devblook.pepitocore.api.datastore;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import team.devblook.pepitocore.api.Identity;

import java.io.*;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TJsonDatastore<T extends Identity> implements TDatastore<T> {

    private static final String EXTENSION = ".json";
    private static final Gson GSON
            = new GsonBuilder().setPrettyPrinting().create();
    private static final ExecutorService EXECUTOR_SERVICE
            = Executors.newCachedThreadPool();

    private final Logger LOGGER
            = Logger.getLogger(getClass().getName());

    private final File folder;
    private Class<T> type;

    public TJsonDatastore(File parent, Class<T> type) {
        this.folder = new File(parent, type.getSimpleName().toLowerCase());
        if (!folder.exists() && !folder.mkdirs()) {
            LOGGER.severe("An error occurred while creating '" + type.getSimpleName().toLowerCase() + "' folder.");
            return;
        }

        this.type = type;
    }

    @Override
    public T findSync(String owner, String name) {
        File file = new File(new File(folder, owner), name + EXTENSION);
        if (!file.exists()) {
            return null;
        }

        try (Reader reader = new BufferedReader(new FileReader(file))) {
            return GSON.fromJson(reader, type);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "An error occurred while deserializing file with owner '" + owner + "'.", e);
            return null;
        }
    }

    @Override
    public CompletableFuture<T> find(String owner, String name) {
        return CompletableFuture.supplyAsync(() -> findSync(owner, name), EXECUTOR_SERVICE);
    }

    @Override
    public CompletableFuture<Void> save(String owner, T value) {
        return CompletableFuture.runAsync(() -> {
            File realFolder = new File(folder, owner);
            if (!realFolder.exists() && !realFolder.mkdirs()) {
                return;
            }
            File file = new File(realFolder, value.id() + EXTENSION);
            try {
                if (!file.exists() && !file.createNewFile()) {
                    LOGGER.severe("An error occurred while creating file identified as '" + value.id() + "'.");
                    return;
                }
                try (Writer writer = new BufferedWriter(new FileWriter(file))) {
                    GSON.toJson(value, writer);
                }
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "An I/O exception was thrown while creating file identified as '" + value.id() + "'.");
            }
        }, EXECUTOR_SERVICE);
    }

    @Override
    public CompletableFuture<Collection<T>> values(String owner) {
        return CompletableFuture.supplyAsync(() -> {
            File[] files = folder.listFiles();
            if (files == null || files.length == 0) {
                return Collections.emptyList();
            }

            Collection<T> values = new HashSet<>();
            for (File file : files) {
                values.add(findSync(owner, file.getName().replace(EXTENSION, "")));
            }

            return values;
        }, EXECUTOR_SERVICE);
    }
}