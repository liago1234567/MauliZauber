package com.mauliwinterevent;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class MauliWinterEventPlugin extends JavaPlugin {

    private Storage storage;
    private AdventManager adventManager;
    private Settings settings;
    private CosmeticsManager cosmetics;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        this.settings = new Settings(this);
        this.storage = new Storage(this);
        this.storage.init();
        this.adventManager = new AdventManager(this, storage, settings);
        this.cosmetics = new CosmeticsManager(this, storage, settings);

        // Events registrieren
        getServer().getPluginManager().registerEvents(adventManager, this);
        getServer().getPluginManager().registerEvents(cosmetics, this);

        // Commands registrieren
        getCommand("winter").setExecutor(adventManager);
        getCommand("advent").setExecutor(adventManager);
        getCommand("cosmetics").setExecutor(cosmetics);

        getLogger().info("MauliWinterEvent aktiviert.");
    }

    @Override
    public void onDisable() {
        try { storage.close(); } catch (Exception ignored) {}
        getLogger().info("MauliWinterEvent deaktiviert.");
    }
}
