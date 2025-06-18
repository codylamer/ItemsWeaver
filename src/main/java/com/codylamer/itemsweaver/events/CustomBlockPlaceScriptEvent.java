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
import dev.lone.itemsadder.api.Events.CustomBlockPlaceEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CustomBlockPlaceScriptEvent extends BukkitScriptEvent implements Listener {

    public CustomBlockPlaceScriptEvent() {
        registerCouldMatcher("ia player places block");
        registerSwitches("namespaced", "id", "against");
    }

    CustomBlockPlaceEvent event;
    PlayerTag player;
    MaterialTag old_material;
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
        if (!runGenericSwitchCheck(path, "against", old_material.name())) {
            return false;
        }
        return super.matches(path);
    }

    @Override
    public ObjectTag getContext(String name) {
        return switch (name) {
            case "player" -> player;
            case "location" -> new LocationTag(event.getBlock().getLocation());
            case "namespaced" -> namespaced;
            case "id" -> id;
            case "material" -> new MaterialTag(event.getBlock());
            case "old_material" -> old_material;
            case "item_in_hand" -> new ItemTag(event.getItemInHand());
            case "ia_item" -> new ItemTag(event.getCustomBlockItem());
            case "can_build" -> new ElementTag(event.isCanBuild());
            case "old_block_state" -> new ElementTag(event.getReplacedBlockState().toString());
            case "against" -> new LocationTag(event.getPlacedAgainst().getLocation());
            default -> super.getContext(name);
        };
    }

    @Override
    public ScriptEntryData getScriptEntryData() {
        return new BukkitScriptEntryData(player, null);
    }

    @EventHandler
    public void onPlayerPlaceCustomBlock(CustomBlockPlaceEvent event) {
        this.event = event;
        old_material = new MaterialTag(event.getPlacedAgainst());
        namespaced = new ElementTag(event.getNamespacedID().split(":")[0]);
        id = new ElementTag(event.getNamespacedID().split(":")[1]);
        player = new PlayerTag(event.getPlayer());
        fire(event);
    }
}
