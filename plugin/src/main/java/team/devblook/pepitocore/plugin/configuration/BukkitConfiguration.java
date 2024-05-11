package team.devblook.pepitocore.plugin.configuration;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;

public class BukkitConfiguration {

    private MiniMessage miniMessage;
    private final File file;
    private FileConfiguration config;

    public BukkitConfiguration(final File folder, final String fileName) {
        this.miniMessage = MiniMessage.miniMessage();
        if (!folder.exists() && !folder.mkdirs()) {
            throw new IllegalStateException("Plugin folder" + folder.getName() + "cannot be created");
        }

        this.file = new File(folder, fileName + ".yml");

        if (!file.exists()) {
            try (InputStream stream = getClass().getClassLoader().getResourceAsStream(file.getName())) {
                if (stream != null) {
                    Files.copy(stream, file.toPath());
                }
            } catch (IOException e) {
                throw new UncheckedIOException("An error occurred while loading file '" + fileName + "'.", e);
            }
        }
        reload();
    }

    public BukkitConfiguration(final Plugin plugin, final String fileName) {
        this(plugin.getDataFolder(), fileName);
    }

    public FileConfiguration get() {
        return config;
    }

    public void reload() {
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            throw new UncheckedIOException("An error occurred while saving file '" + file.getName() + "'.", e);
        }
    }

    /**
     * Get a component from the configuration file
     *
     * @param path The path to the component
     * @return Adventure Component with text styles and color from minimessage deserialization
     */
    public Component getComponent(final @NotNull String path) {
        return miniMessage.deserialize(config.getString(path, path));
    }

    /**
     * Get a legacy string from the configuration file
     *
     * @param path The path to the string
     * @return Legacy string with color codes
     */
    public String getLegacyString(final @NotNull String path) {
        return LegacyComponentSerializer.legacyAmpersand().serialize(getComponent(path));
    }


}
