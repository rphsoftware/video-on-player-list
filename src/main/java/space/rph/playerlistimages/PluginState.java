package space.rph.playerlistimages;

import com.comphenix.protocol.ProtocolManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class PluginState {
    public static boolean enabled = false;
    public static ProtocolManager protocolManager;
    public static PlayerPreferencesManager prefManager;
    public static HashMap<String, String> messages;
    public static HashSet<UUID> userOptOut;
    public static Playerlistimages plugin;
    public static String lastShown;
    public static boolean video;
    public static ZipFile videoContents;
    public static int videoFrameCurrent;
    public static int videoFramesTotal;

    public static void updateConfig() {
        ArrayList<String> converted = new ArrayList<>();
        userOptOut.forEach(id -> {
            converted.add(id.toString());
        });

        plugin.getConfig().set("optout", converted);
        plugin.saveConfig();

        try {
            new BroadcastUpdate(lastShown, false, false, true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void tick() {
        if (video) {
            // try to read frame
            try {
                ZipEntry frame = videoContents.getEntry(((Integer)videoFrameCurrent).toString() + ".json");
                if (frame != null) {
                    String res = new BufferedReader(
                            new InputStreamReader(videoContents.getInputStream(frame), StandardCharsets.UTF_8)
                    ).lines().collect(Collectors.joining(""));
                    lastShown = res;

                    new BroadcastUpdate(res, false, false, false);
                }

                videoFrameCurrent++;
                if (videoFrameCurrent >= videoFramesTotal) {
                    videoFrameCurrent = 0;
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
