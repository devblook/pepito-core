package team.devblook.pepitocore.plugin.module.back.binding;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import net.jodah.expiringmap.ExpiringMap;
import team.devblook.pepitocore.api.registry.LocalTRegistry;
import team.devblook.pepitocore.api.registry.TRegistry;
import team.devblook.pepitocore.plugin.module.back.model.LockedLocation;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class GoBackBindingModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(new TypeLiteral<TRegistry<UUID, LockedLocation>>() {
        }).toInstance(new LocalTRegistry<>(ExpiringMap.builder()
                .expiration(1, TimeUnit.HOURS)
                .maxSize(50)
                .build()));
    }
}