package com.codylamer.itemsweaver.events;

import com.denizenscript.denizen.events.BukkitScriptEvent;
import com.denizenscript.denizen.objects.PlayerTag;
import com.denizenscript.denizen.utilities.implementation.BukkitScriptEntryData;
import com.denizenscript.denizencore.objects.ObjectTag;
import com.denizenscript.denizencore.objects.core.ElementTag;
import com.denizenscript.denizencore.scripts.ScriptEntryData;
import dev.lone.itemsadder.api.Events.PlayerEmoteEndEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerEmoteEndScriptEvent extends BukkitScriptEvent implements Listener {

    public PlayerEmoteEndScriptEvent() {
        registerCouldMatcher("ia player finish <'emote'> (because <'cause'>)");
    }

    PlayerEmoteEndEvent event;
    PlayerTag player;
    ElementTag emote;
    ElementTag cause;

    @Override
    public boolean matches(ScriptPath path) {
        if (!runInCheck(path, event.getPlayer().getLocation())) {
            return false;
        }
        if (!path.eventArgLowerAt(3).equals("emote") && !(path.eventArgLowerAt(3).equals(emote.asString()))) {
            return false;
        }
        if (path.eventArgLowerAt(4).equals("because") && !runGenericCheck(path.eventArgLowerAt(5), cause.asString())) {
            return false;
        }
        return super.matches(path);
    }

    @Override
    public ObjectTag getContext(String name) {
        return switch (name) {
            case "player" -> player;
            case "emote" -> emote;
            case "cause" -> cause;
            default -> super.getContext(name);
        };
    }

    @Override
    public ScriptEntryData getScriptEntryData() {
        return new BukkitScriptEntryData(player, null);
    }

    @EventHandler
    public void onPlayerEndEmote(PlayerEmoteEndEvent event) {
        this.event = event;
        player = new PlayerTag(event.getPlayer());
        emote = new ElementTag(event.getEmoteName());
        cause = new ElementTag(event.getCause());
        fire(event);
    }
}
