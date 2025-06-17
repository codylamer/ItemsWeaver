package com.codylamer.itemsweaver.events;

import com.denizenscript.denizen.events.BukkitScriptEvent;
import com.denizenscript.denizencore.objects.ObjectTag;

import dev.lone.itemsadder.api.Events.ItemsAdderPackCompressedEvent;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;


public class PackCompressedScriptEvent extends BukkitScriptEvent implements Listener {

    public PackCompressedScriptEvent() {
        registerCouldMatcher("ia resourcepack compressed");
    }

    ItemsAdderPackCompressedEvent event;

    @Override
    public boolean matches(ScriptPath path) {
        return super.matches(path);
    }

    @Override
    public ObjectTag getContext(String name) {
        return super.getContext(name);
    }

    @EventHandler
    public void onResourcePackCompressed(ItemsAdderPackCompressedEvent event) {
        this.event = event;
        fire(event);
    }
}
