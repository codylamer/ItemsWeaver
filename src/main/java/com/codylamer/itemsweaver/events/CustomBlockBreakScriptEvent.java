package com.codylamer.itemsweaver.events;

import com.denizenscript.denizen.events.BukkitScriptEvent;
import com.denizenscript.denizen.objects.PlayerTag;
import com.denizenscript.denizen.objects.*;
import com.denizenscript.denizen.utilities.implementation.BukkitScriptEntryData;
import com.denizenscript.denizencore.objects.ObjectTag;
import com.denizenscript.denizencore.objects.core.ElementTag;
import com.denizenscript.denizencore.scripts.ScriptEntryData;
import dev.lone.itemsadder.api.Events.CustomBlockBreakEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CustomBlockBreakScriptEvent extends BukkitScriptEvent implements Listener {

    public CustomBlockBreakScriptEvent() {
        registerCouldMatcher("ia player breaks block");
        registerSwitches("namespaced");
        registerSwitches("id");
    }

    CustomBlockBreakEvent event;
    LocationTag location;
    PlayerTag player;
    ElementTag namespaced;
    ElementTag id;

    @Override
    public boolean matches(ScriptPath path) {
        if (!runInCheck(path, player.getLocation())) {
            return false;
        }
        if (!runGenericSwitchCheck(path, "namespaced", namespaced.asString())) {
            return false;
        }
        if (!runGenericSwitchCheck(path, "id", id.asString())) {
            return false;
        }
        return super.matches(path);
    }

    @Override
    public ObjectTag getContext(String name) {
        return switch (name) {
            case "player" -> player;
            case "location" -> location;
            case "namespaced" -> namespaced;
            case "id" -> id;
            case "material" -> new ItemTag(event.getCustomBlockItem());
            default -> super.getContext(name);
        };
    }

    @Override
    public ScriptEntryData getScriptEntryData() {
        return new BukkitScriptEntryData(player, null);
    }

    @EventHandler
    public void onPlayerBreakCustomBlock(CustomBlockBreakEvent event) {
        this.event = event;
        namespaced = new ElementTag(event.getNamespacedID().split(":")[0]);
        id = new ElementTag(event.getNamespacedID().split(":")[1]);
        player = new PlayerTag(event.getPlayer());
        location = new LocationTag(event.getBlock().getLocation());
        fire(event);
    }
}
