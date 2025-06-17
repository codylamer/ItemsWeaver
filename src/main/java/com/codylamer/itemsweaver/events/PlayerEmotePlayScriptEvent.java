package com.codylamer.itemsweaver.events;

import com.denizenscript.denizen.events.BukkitScriptEvent;
import com.denizenscript.denizen.objects.PlayerTag;
import com.denizenscript.denizen.utilities.implementation.BukkitScriptEntryData;
import com.denizenscript.denizencore.objects.ObjectTag;
import com.denizenscript.denizencore.objects.core.ElementTag;
import com.denizenscript.denizencore.scripts.ScriptEntryData;
import dev.lone.itemsadder.api.Events.PlayerEmotePlayEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerEmotePlayScriptEvent extends BukkitScriptEvent implements Listener {

    public PlayerEmotePlayScriptEvent() {
        registerCouldMatcher("ia player play <'emote'>");
    }

    PlayerEmotePlayEvent event;
    PlayerTag player;

    @Override
    public boolean matches(ScriptPath path) {
        if (!runInCheck(path, event.getPlayer().getLocation())) {
            return false;
        }
        if (!path.eventArgLowerAt(3).equals("emote") && !(path.eventArgLowerAt(3).equals(event.getEmoteName()))) {
            return false;
        }
        return super.matches(path);
    }

    @Override
    public ObjectTag getContext(String name) {
        return switch (name) {
            case "player" -> player;
            case "emote" -> new ElementTag(event.getEmoteName());
            default -> super.getContext(name);
        };
    }

    @Override
    public ScriptEntryData getScriptEntryData() {
        return new BukkitScriptEntryData(player, null);
    }

    @EventHandler
    public void onPlayerPlayEmote(PlayerEmotePlayEvent event) {
        this.event = event;
        player = new PlayerTag(event.getPlayer());
        fire(event);
    }
}
