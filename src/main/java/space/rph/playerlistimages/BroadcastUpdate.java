package space.rph.playerlistimages;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;

import java.lang.reflect.InvocationTargetException;

public class BroadcastUpdate implements Runnable {
    private String content;
    private boolean override;
    private boolean updating;
    public BroadcastUpdate(String content, boolean override, boolean sync, boolean updating) throws InterruptedException {
        this.content = content;
        this.override = override;
        this.updating = updating;

        Thread t = new Thread(this);
        t.start();
        if (sync) {
            t.join();
        }
    }


    @Override
    public void run() {
        PacketContainer empty = null;
        PacketContainer a = PluginState.protocolManager.createPacket(PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER);

        a.getChatComponents()
                .write(0, WrappedChatComponent.fromJson(content))
                .write(1, WrappedChatComponent.fromText(""));
        
        if (updating) {
            empty = PluginState.protocolManager.createPacket(PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER);
            empty.getChatComponents()
                    .write(0, WrappedChatComponent.fromText(""))
                    .write(1, WrappedChatComponent.fromText(""));
        }

        PacketContainer finalEmpty = empty;
        PluginState.plugin.getServer().getOnlinePlayers().forEach(player -> {
            if (PluginState.prefManager.isOptedIn(player.getUniqueId()) || override) {
                try {
                    PluginState.protocolManager.sendServerPacket(player, a);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            } else {
                if (finalEmpty != null) {
                    try {
                        PluginState.protocolManager.sendServerPacket(player, finalEmpty);
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
