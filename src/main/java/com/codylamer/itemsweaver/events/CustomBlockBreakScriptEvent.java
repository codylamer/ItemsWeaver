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
        registerSwitches("namespaced", "id", "with");
    }

    CustomBlockBreakEvent event;
    PlayerTag player;
    ItemTag item_in_hand;
    ElementTag namespaced;
    ElementTag id;

    @Override
    public boolean matches(ScriptPath path) {
        if (!runInCheck(path, player.getLocation())) {
            return false;
        }
        if (!runWithCheck(path, item_in_hand)) {
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
            case "item_in_hand" -> item_in_hand;
            case "namespaced" -> namespaced;
            case "id" -> id;
            case "location" -> new LocationTag(event.getBlock().getLocation());
            case "material" -> new MaterialTag(event.getBlock());
            case "ia_item" -> new ItemTag(event.getCustomBlockItem());
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
        player = new PlayerTag(event.getPlayer());
        item_in_hand = new ItemTag(event.getPlayer().getEquipment().getItemInMainHand());
        namespaced = new ElementTag(event.getNamespacedID().split(":")[0]);
        id = new ElementTag(event.getNamespacedID().split(":")[1]);
        fire(event);
    }
}
