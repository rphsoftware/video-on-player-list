package space.rph.playerlistimages;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DisableImagesCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (((Player) sender).getPlayer());
            if (player != null) {
                PluginState.prefManager.optOut(player);
                return true;
            } else {
                sender.sendMessage("Only players may run this command.");
            }
        }
        sender.sendMessage("Only players may run this command.");
        return false;
    }
}
