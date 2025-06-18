package com.codylamer.itemsweaver;

import com.denizenscript.denizen.Denizen;
import com.denizenscript.denizencore.events.ScriptEvent;

import com.codylamer.itemsweaver.events.*;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class ItemsWeaver extends JavaPlugin {

    public static ItemsWeaver instance;


    @Override
    public void onEnable() {
        instance = this;

        getLogger().info("║ ■=■=■=■=■=■=■=■=■=■=■=■=■=■=■=■=■=■=■=■");
        getLogger().info("║ ItemsWeaver ");

        if (isPluginAvailable("Denizen")) {
            getLogger().severe("║ Denizen is not installed! ║");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        if (isPluginAvailable("ItemsAdder")) {
            getLogger().severe("║ ItemsAdder is not installed! ║");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        registerDenizen();

        getLogger().info("╔══════════════════════════════════════════════════════╗");
        getLogger().info("║              ✨ Created by codylamer ✨             ║");
        getLogger().info("║      🌐 Visit: https://funpay.com/users/5384490/     ║");
        getLogger().info("║             Thank you for using this plugin!         ║");
        getLogger().info("╚══════════════════════════════════════════════════════╝");
        getLogger().info("║ ■=■=■=■=■=■=■=■=■=■=■=■=■=■=■=■=■=■=■=■");
    }

    @Override
    public void onDisable() {
        Denizen.getInstance().onDisable();
    }

    private void registerDenizen() {
        getLogger().info("║ Successfully started Denizen hook ║");

        getLogger().info("║ Initializing Denizen Events ║");

        ScriptEvent.registerScriptEvent(PackCompressedScriptEvent.class);
        ScriptEvent.registerScriptEvent(ResourcePackSendScriptEvent.class);
        ScriptEvent.registerScriptEvent(LoadDataScriptEvent.class);

        ScriptEvent.registerScriptEvent(PlayerEmotePlayScriptEvent.class);
        ScriptEvent.registerScriptEvent(PlayerEmoteEndScriptEvent.class);

        ScriptEvent.registerScriptEvent(CustomBlockBreakScriptEvent.class);
        ScriptEvent.registerScriptEvent(CustomBlockPlaceScriptEvent.class);
    }

    private boolean isPluginAvailable(String pluginName) {
        return !Bukkit.getPluginManager().isPluginEnabled(pluginName);
    }
}
