package io.github.thatkawaiisam.gatekeeper.modules.whitelist;

import io.github.thatkawaiisam.gatekeeper.GatekeeperPlugin;
import io.github.thatkawaiisam.plugintemplate.bungee.BungeeModuleListener;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.event.EventHandler;

import java.util.UUID;

public class WhitelistListener extends BungeeModuleListener<WhitelistModule, GatekeeperPlugin> {

    /**
     * Whitelist Listener.
     *
     * @param module instance.
     */
    public WhitelistListener(WhitelistModule module) {
        super(module);
    }

    @EventHandler
    public void onProxyJoin(LoginEvent event) {
        UUID uuid = event.getConnection().getUniqueId();

        // TODO: add a bypass permission.
        if (getModule().getMode() == WhitelistMode.OFF || getModule().getWhitelisted().contains(uuid)) {
            return;
        }

        event.setCancelled(true);
        event.setCancelReason(ChatColor.translateAlternateColorCodes('&', getModule().getKickMessage()));
    }
}
