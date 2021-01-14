package space.rph.playerlistimages;

import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerPreferencesManager  {
    public PlayerPreferencesManager() {

    }

    public void optOut(Player player) {
        PluginState.userOptOut.add(player.getUniqueId());
        PluginState.updateConfig();

        if (PluginState.messages.containsKey("disabled"))
            player.sendMessage(PluginState.messages.get("disabled"));
    }

    public void optIn(Player player) {
        PluginState.userOptOut.remove(player.getUniqueId());
        PluginState.updateConfig();

        if (PluginState.messages.containsKey("enabled"))
            player.sendMessage(PluginState.messages.get("enabled"));
    }

    public boolean isOptedIn(UUID uuid) {
        return !PluginState.userOptOut.contains(uuid);
    }
}
