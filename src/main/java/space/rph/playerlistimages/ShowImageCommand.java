package space.rph.playerlistimages;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;


public class ShowImageCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("playerlistimages.admin")) {
            if (args.length < 1) {
                return false;
            }

            String fullName = PluginState.plugin.getDataFolder() + File.separator + args[0] + ".json";

            sender.sendMessage("Trying to load image from " + fullName);
            try {
                String content = new String(Files.readAllBytes(Paths.get(fullName)), StandardCharsets.UTF_8);
                PluginState.lastShown = content;
                new BroadcastUpdate(content, false, false, false);
            } catch (IOException | InterruptedException e) {
                sender.sendMessage("An error has occured. See console for details");
                e.printStackTrace();
            }
        } else {
            sender.sendMessage("Permission denied.");
        }
        return true;
    }
}
