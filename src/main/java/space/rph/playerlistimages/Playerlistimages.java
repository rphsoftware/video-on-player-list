package space.rph.playerlistimages;

import com.comphenix.protocol.ProtocolLibrary;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class Playerlistimages extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        PluginState.enabled = true;
        PluginState.protocolManager = ProtocolLibrary.getProtocolManager();
        PluginState.prefManager = new PlayerPreferencesManager();
        PluginState.plugin = this;
        PluginState.userOptOut = new HashSet<>();
        PluginState.messages = new HashMap<>();
        PluginState.video = false;
        PluginState.videoContents = null;
        PluginState.videoFrameCurrent = 0;
        PluginState.videoFramesTotal = 0;

        this.saveDefaultConfig();

        // Get messages
        List<String> optout = this.getConfig().getStringList("optout");

        optout.forEach(playerId -> {
            PluginState.userOptOut.add(UUID.fromString(playerId));
        });

        Map<String, Object> mcache = this.getConfig().getConfigurationSection("messages").getValues(false);
        mcache.forEach((key, value) -> {
            PluginState.messages.put(key, (String)value);
        });

        this.getCommand("enableimages").setExecutor(new EnableImagesCommand());
        this.getCommand("disableimages").setExecutor(new DisableImagesCommand());
        this.getCommand("showimage").setExecutor(new ShowImageCommand());
        this.getCommand("clearimage").setExecutor(new ClearImageCommand());
        this.getCommand("playvideo").setExecutor(new PlayVideoCommand());

        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                PluginState.tick();
            }
        }, 0, 1);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic//
        try {
            new BroadcastUpdate("{\"text\":\"\"}", true, true, false);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        PluginState.enabled = false;
        PluginState.protocolManager = null;
        PluginState.prefManager = null;
        PluginState.plugin = null;
        PluginState.userOptOut = null;
        PluginState.messages = null;
        PluginState.video = false;
        PluginState.videoContents = null;
        PluginState.videoFrameCurrent = 0;
        PluginState.videoFramesTotal = 0;

        HandlerList.unregisterAll(this);
    }
}
