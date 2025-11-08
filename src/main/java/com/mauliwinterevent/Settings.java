package com.mauliwinterevent;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class Settings {

    private MonthDay start;
    private MonthDay end;
    private boolean catchup;
    private boolean dailyOneClaim;
    private String title;
    private String bossbarTitle;
    private String claimedPrefix;
    private String availablePrefix;
    private String lockedPrefix;
    private String euroSymbol;
    private String prefix;
    private String msgNotInEvent;
    private String msgAlreadyClaimed;
    private String msgDailyLimit;
    private String msgClaimedSuccess;
    private String msgReload;

    private Map<String, List<String>> specialDayCommands;
    private Map<String, List<Cosmetic>> specialDayCosmetics;
    private List<String> defaultCommands;
    private List<Cosmetic> defaultCosmetics;

    private final DateTimeFormatter mmdd = DateTimeFormatter.ofPattern("MM-dd");

    public MonthDay start() { return start; }
    public MonthDay end() { return end; }
    public boolean catchup() { return catchup; }
    public boolean dailyOneClaim() { return dailyOneClaim; }
    public String title() { return title; }
    public String bossbarTitle() { return bossbarTitle; }
    public String claimedPrefix() { return claimedPrefix; }
    public String availablePrefix() { return availablePrefix; }
    public String lockedPrefix() { return lockedPrefix; }
    public String euroSymbol() { return euroSymbol; }
    public String prefix() { return prefix; }
    public String msgNotInEvent() { return msgNotInEvent; }
    public String msgAlreadyClaimed() { return msgAlreadyClaimed; }
    public String msgDailyLimit() { return msgDailyLimit; }
    public String msgClaimedSuccess() { return msgClaimedSuccess; }
    public String msgReload() { return msgReload; }

    // Rewards
    public List<String> commandsFor(LocalDate date) {
        String key = mmdd.format(date);
        return specialDayCommands.getOrDefault(key, defaultCommands);
    }

    // Cosmetics
    public List<Cosmetic> cosmeticsFor(LocalDate date) {
        String key = mmdd.format(date);
        return specialDayCosmetics.getOrDefault(key, defaultCosmetics);
    }
}
