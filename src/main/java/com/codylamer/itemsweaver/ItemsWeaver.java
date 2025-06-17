package com.codylamer.itemsweaver;

import com.denizenscript.denizen.Denizen;
import com.denizenscript.denizencore.events.ScriptEvent;
import com.denizenscript.denizencore.utilities.debugging.Debug;

import com.codylamer.itemsweaver.events.*;

import org.bukkit.plugin.java.JavaPlugin;

public class ItemsWeaver extends JavaPlugin {

    public static ItemsWeaver instance;

    @Override
    public void onEnable() {
        Debug.log("ItemsWeaver loading...");
        instance = this;

        ScriptEvent.registerScriptEvent(PackCompressedScriptEvent.class);
        ScriptEvent.registerScriptEvent(ResourcePackSendScriptEvent.class);
        ScriptEvent.registerScriptEvent(LoadDataScriptEvent.class);
        ScriptEvent.registerScriptEvent(PlayerEmotePlayScriptEvent.class);
        ScriptEvent.registerScriptEvent(PlayerEmoteEndScriptEvent.class);

        Debug.log("ItemsWeaver loaded!");
    }

    @Override
    public void onDisable() {
        Denizen.getInstance().onDisable();
    }
}
