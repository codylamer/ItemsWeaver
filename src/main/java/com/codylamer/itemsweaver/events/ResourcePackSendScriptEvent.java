package com.codylamer.itemsweaver.events;

import com.denizenscript.denizen.utilities.implementation.BukkitScriptEntryData;
import com.denizenscript.denizencore.scripts.ScriptEntryData;
import com.denizenscript.denizen.events.BukkitScriptEvent;
import com.denizenscript.denizencore.objects.ObjectTag;
import com.denizenscript.denizencore.objects.core.ElementTag;
import com.denizenscript.denizen.objects.PlayerTag;

import dev.lone.itemsadder.api.Events.ResourcePackSendEvent;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;


public class ResourcePackSendScriptEvent extends BukkitScriptEvent implements Listener {

    public ResourcePackSendScriptEvent() {
        registerCouldMatcher("ia send resourcepack to player");
    }

    ResourcePackSendEvent event;
    PlayerTag player;

    @Override
    public boolean matches(ScriptPath path) {
        return super.matches(path);
    }

    @Override
    public ObjectTag getContext(String name) {
        return switch (name) {
            case "player" -> player;
            case "url" -> new ElementTag(event.getUrl());
            case "hash" -> new ElementTag(event.getHash());
            case "is_ia_resourcepack" -> new ElementTag(event.isItemsAdderPack());
            default -> super.getContext(name);
        };
    }

    @Override
    public ScriptEntryData getScriptEntryData() {
        return new BukkitScriptEntryData(player, null);
    }

    @EventHandler
    public void onResourcePackSend(ResourcePackSendEvent event) {
        this.event = event;
        player = new PlayerTag(event.getPlayer());
        fire(event);
    }
}
