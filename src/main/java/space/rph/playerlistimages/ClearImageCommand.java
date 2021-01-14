package space.rph.playerlistimages;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ClearImageCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("playerlistimages.admin")) {
            PluginState.video = false;
            PluginState.videoContents = null;
            PluginState.videoFrameCurrent = 0;
            PluginState.videoFramesTotal = 0;
            PluginState.lastShown = "{\"text\":\"\"}";
            try {
                new BroadcastUpdate(PluginState.lastShown, true, false, false);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            sender.sendMessage("Permission denied.");
        }
        return true;
    }
}
