package com.codylamer.itemsweaver.events;

import com.denizenscript.denizen.events.BukkitScriptEvent;
import com.denizenscript.denizen.objects.ItemTag;
import com.denizenscript.denizen.objects.LocationTag;
import com.denizenscript.denizen.objects.MaterialTag;
import com.denizenscript.denizen.objects.PlayerTag;
import com.denizenscript.denizen.utilities.implementation.BukkitScriptEntryData;
import com.denizenscript.denizencore.objects.ObjectTag;
import com.denizenscript.denizencore.objects.core.ElementTag;
import com.denizenscript.denizencore.scripts.ScriptEntryData;
import dev.lone.itemsadder.api.Events.CustomBlockInteractEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CustomBlockInteractScriptEvent extends BukkitScriptEvent implements Listener {

    public CustomBlockInteractScriptEvent() {
        registerCouldMatcher("ia player interact block");
        registerSwitches("namespaced", "id", "using", "with", "action", "face");
    }

    CustomBlockInteractEvent event;
    PlayerTag player;
    ItemTag item_in_hand;
    ElementTag namespaced;
    ElementTag id;
    ElementTag action;
    ElementTag face;
    ElementTag hand;

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
        if (!runGenericSwitchCheck(path, "using", hand.asString())) {
            return false;
        }
        if (!runGenericSwitchCheck(path, "action", action.asString())) {
            return false;
        }
        if (!runGenericSwitchCheck(path, "face", face.asString())) {
            return false;
        }
        if (!runWithCheck(path, item_in_hand)) {
            return false;
        }
        return super.matches(path);
    }

    @Override
    public ObjectTag getContext(String name) {
        return switch (name) {
            case "player" -> player;
            case "location" -> new LocationTag(event.getBlockClicked().getLocation());
            case "namespaced" -> namespaced;
            case "id" -> id;
            case "material" -> new MaterialTag(event.getBlockClicked());
            case "action" -> action;
            case "item_in_hand" -> item_in_hand;
            case "face" -> face;
            case "hand" -> hand;
            case "ia_item" -> new ItemTag(event.getCustomBlockItem());
            default -> super.getContext(name);
        };
    }

    @Override
    public ScriptEntryData getScriptEntryData() {
        return new BukkitScriptEntryData(player, null);
    }

    @EventHandler
    public void onPlayerInteractCustomBlock(CustomBlockInteractEvent event) {
        this.event = event;
        player = new PlayerTag(event.getPlayer());
        action = new ElementTag(event.getAction());
        item_in_hand = new ItemTag(event.getItem());
        namespaced = new ElementTag(event.getNamespacedID().split(":")[0]);
        id = new ElementTag(event.getNamespacedID().split(":")[1]);
        face = new ElementTag(event.getBlockFace());
        hand = new ElementTag(event.getHand());
        fire(event);
    }
}
