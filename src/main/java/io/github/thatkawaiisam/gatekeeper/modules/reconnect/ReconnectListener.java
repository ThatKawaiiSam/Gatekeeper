package io.github.thatkawaiisam.gatekeeper.modules.reconnect;

import io.github.thatkawaiisam.gatekeeper.GatekeeperPlugin;
import io.github.thatkawaiisam.plugintemplate.bungee.BungeeModuleListener;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerKickEvent;
import net.md_5.bungee.event.EventHandler;

public class ReconnectListener extends BungeeModuleListener<ReconnectModule, GatekeeperPlugin> {

    /**
     * Reconnect Listener.
     *
     * @param module instance.
     */
    public ReconnectListener(ReconnectModule module) {
        super(module);
    }

    @EventHandler
    public void onServerKick(ServerKickEvent event) {
        ProxiedPlayer player = event.getPlayer();
        // TODO: Better handling of closing and banning.
        if (event.getKickReason().contains("Server closed")) {
            // TODO: Send them to a pool of lobby servers - connect to redstone.
            ServerInfo lobbyServer = getModule().getPlugin().getProxy().getServerInfo("Lobby");

            if (event.getKickedFrom() == lobbyServer) {
                return;
            }

            event.setCancelled(true);
            event.setCancelServer(lobbyServer);
        }
    }
}
