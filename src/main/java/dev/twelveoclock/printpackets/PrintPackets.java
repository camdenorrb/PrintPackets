package dev.twelveoclock.printpackets;

import dev.twelveoclock.printpackets.module.PacketModule;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;

/**
 * A template plugin that incorporates the basics for every plugin I make
 *
 * Features:
 *  - Modules
 *  - Basic utilities
 *  - Latest Java features
 *  - Toml based config loading
 */
public final class PrintPackets extends JavaPlugin {

    private final PacketModule packetModule = new PacketModule(this);

    /**
     * Constructor for MockBukkit
     */
    public PrintPackets() {
        super();
    }

    /**
     * Constructor for MockBukkit
     */
    public PrintPackets(final JavaPluginLoader loader, final PluginDescriptionFile description, final File dataFolder, final File file) {
        super(loader, description, dataFolder, file);
    }


    @Override
    public void onEnable() {
        packetModule.enable();
    }

    @Override
    public void onDisable() {
        packetModule.disable();
    }

}
