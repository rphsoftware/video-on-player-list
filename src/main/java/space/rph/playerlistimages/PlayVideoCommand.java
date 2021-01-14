package space.rph.playerlistimages;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class PlayVideoCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("playerlistimages.admin")) {
            if (args.length < 1) {
                return false;
            }

            String fullName = PluginState.plugin.getDataFolder() + File.separator + args[0] + ".zip";

            sender.sendMessage("Trying to load video from " + fullName);
            try {
                ZipFile file = new ZipFile(fullName);
                ZipEntry metadata = file.getEntry("meta.json");
                if (metadata != null) {
                    String res = new BufferedReader(
                        new InputStreamReader(file.getInputStream(metadata), StandardCharsets.UTF_8)
                    ).lines().collect(Collectors.joining(""));
                    JsonObject jsonString = new JsonParser().parse(res).getAsJsonObject();
                    Integer numframes = jsonString.get("numframes").getAsInt();
                    sender.sendMessage("Frames in video: " + numframes.toString());

                    PluginState.video = true;
                    PluginState.videoContents = file;
                    PluginState.videoFramesTotal = numframes;
                    PluginState.videoFrameCurrent = 0;
                } else {
                    sender.sendMessage("Not a valid video :(");
                }
            } catch (IOException e) {
                sender.sendMessage("An error has occured. See console for details");
                e.printStackTrace();
            }
        } else {
            sender.sendMessage("Permission denied.");
        }
        return true;
    }
}
