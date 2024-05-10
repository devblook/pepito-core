package team.devblook.pepitocore.api.module;

import team.devblook.pepitocore.api.Identity;

public interface CoreModule extends Identity {

    @Override
    default String id() {
        return getClass().getSimpleName().toLowerCase();
    }

    default void load() {

    }

    void enable();

    void disable();
}