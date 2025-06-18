package com.codylamer.itemsweaver.events;

import com.denizenscript.denizen.events.BukkitScriptEvent;
import com.denizenscript.denizencore.objects.ObjectTag;
import com.denizenscript.denizencore.objects.core.ElementTag;
import dev.lone.itemsadder.api.Events.ItemsAdderLoadDataEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;


public class LoadDataScriptEvent extends BukkitScriptEvent implements Listener {

    public LoadDataScriptEvent() {
        registerCouldMatcher("ia load data (because <'cause'>)");
    }

    ItemsAdderLoadDataEvent event;
    ElementTag cause;

    @Override
    public boolean matches(ScriptPath path) {
        if (path.eventArgLowerAt(3).equals("because") && !runGenericCheck(path.eventArgLowerAt(4), cause.asString())) {
            return false;
        }
        return super.matches(path);
    }

    @Override
    public ObjectTag getContext(String name) {
        return switch (name) {
            case "cause" -> cause;
            default -> super.getContext(name);
        };
    }

    @EventHandler
    public void onLoadData(ItemsAdderLoadDataEvent event) {
        this.event = event;
        cause = new ElementTag(event.getCause());
        fire(event);
    }
}
