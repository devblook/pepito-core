package team.devblook.pepitocore.plugin.module.tpa.binding;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;
import team.devblook.pepitocore.plugin.module.tpa.model.TPARequest;

import java.util.UUID;

public class TPABindingModule extends AbstractModule implements Module {

    @Override
    protected void configure() {
        bind(new TypeLiteral<Multimap<UUID, TPARequest>>() {}).toInstance(MultimapBuilder.hashKeys().hashSetValues().build());
    }
}