package com.mauliwinterevent;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.MonthDay;
import java.time.format.DateTimeFormatter;

public class Settings {

    private final JavaPlugin plugin;
    private final DateTimeFormatter mmdd = DateTimeFormatter.ofPattern("MM-dd");

    // Event
    private MonthDay start;
    private MonthDay end;
    private boolean catchup;
    private boolean dailyOneClaim;

    // UI
    private String title;
    private String bossbarTitle;
    private String claimedPrefix;
    private String availablePrefix;
    private String lockedPrefix;
    private String euroSymbol;

    // Messages
    private String prefix, notInEvent, alreadyClaimed, dailyLimit, claimedSuccess, reload;

    // Rewards
    private java.util.Map<String, java.util.List<String>> specialDayCommands;
    private java.util.List<String> defaultCommands;

    // Cosmetics
    private final java.util.Map<String, java.util.List<Cosmetic>> specialDayCosmetics = new java.util.HashMap<>();
    private final java.util.List<Cosmetic> defaultCosmetics = new java.util.ArrayList<>();

    // >>> WICHTIG: Wir brauchen wieder den Plugin-Konstruktor!
    public Settings(JavaPlugin plugin) {
        this.plugin = plugin;
        load();
    }

    public void load() {
        var conf = plugin.getConfig();

        // Event
        this.start = MonthDay.parse(conf.getString("event.start", "12-01"), mmdd);
        this.end   = MonthDay.parse(conf.getString("event.end",   "12-31"), mmdd);
        this.catchup = conf.getBoolean("event.catchup", true);
        this.dailyOneClaim = conf.getBoolean("event.daily_one_claim", true);

        // UI
        this.title          = conf.getString("ui.title", "§b❄ Mauli Adventskalender ❄");
        this.bossbarTitle   = conf.getString("ui.bossbar_title", "§bFrohe Winterzeit auf MauliCraft!");
        this.claimedPrefix  = conf.getString("ui.claimed_name_prefix", "§7[✓] §fTag ");
        this.availablePrefix= conf.getString("ui.available_name_prefix","§aTag ");
        this.lockedPrefix   = conf.getString("ui.locked_name_prefix",  "§cTag ");
        this.euroSymbol     = conf.getString("ui.euro_symbol","€");

        // Messages
        this.prefix         = conf.getString("messages.prefix", "§b[Winter]§7 ");
        this.notInEvent     = conf.getString("messages.not_in_event", "Der Adventskalender ist nur vom 01.12. bis 31.12. verfügbar.");
        this.alreadyClaimed = conf.getString("messages.already_claimed_day", "Du hast diesen Tag bereits abgeholt.");
        this.dailyLimit     = conf.getString("messages.daily_limit_reached", "Du hast heute bereits ein Geschenk eingelöst. Komm morgen wieder!");
        this.claimedSuccess = conf.getString("messages.claimed_success", "Geschenk für den §b%s. Dezember §7erfolgreich abgeholt!");
        this.reload         = conf.getString("messages.reload", "Konfiguration neu geladen.");

        // Rewards – Commands
        this.specialDayCommands = new java.util.HashMap<>();
        ConfigurationSection sd = conf.getConfigurationSection("rewards.special_days");
        if (sd != null) {
            for (String k : sd.getKeys(false)) {
                var cmds = conf.getStringList("rewards.special_days." + k + ".commands");
                specialDayCommands.put(k, cmds);
            }
        }
        this.defaultCommands = conf.getStringList("rewards.default.commands");

        // Rewards – Cosmetics
        defaultCosmetics.clear();
        for (String c : conf.getStringList("rewards.default.cosmetics")) {
            Cosmetic cs = Cosmetic.from(c);
            if (cs != null) defaultCosmetics.add(cs);
        }
        specialDayCosmetics.clear();
        if (sd != null) {
            for (String k : sd.getKeys(false)) {
                var raw = conf.getStringList("rewards.special_days." + k + ".cosmetics");
                var list = new java.util.ArrayList<Cosmetic>();
                for (String c : raw) {
                    Cosmetic cs = Cosmetic.from(c);
                    if (cs != null) list.add(cs);
                }
                specialDayCosmetics.put(k, list);
            }
        }
    }

    // ----- Getter -----
    public MonthDay start()                { return start; }
    public MonthDay end()                  { return end; }
    public boolean catchup()               { return catchup; }
    public boolean dailyOneClaim()         { return dailyOneClaim; }

    public String title()                  { return title; }
    public String bossbarTitle()           { return bossbarTitle; }
    public String claimedPrefix()          { return claimedPrefix; }
    public String availablePrefix()        { return availablePrefix; }
    public String lockedPrefix()           { return lockedPrefix; }
    public String euroSymbol()             { return euroSymbol; }

    public String msgPrefix()              { return prefix; }
    public String msgNotInEvent()          { return notInEvent; }
    public String msgAlreadyClaimed()      { return alreadyClaimed; }
    public String msgDailyLimit()          { return dailyLimit; }
    public String msgClaimedSuccess()      { return claimedSuccess; }
    public String msgReload()              { return reload; }

    public java.util.List<String> commandsFor(java.time.LocalDate date) {
        String key = mmdd.format(date);
        return specialDayCommands.getOrDefault(key, defaultCommands);
    }

    public java.util.List<Cosmetic> cosmeticsFor(java.time.LocalDate date) {
        String key = mmdd.format(date);
        return specialDayCosmetics.getOrDefault(key, defaultCosmetics);
    }
}
