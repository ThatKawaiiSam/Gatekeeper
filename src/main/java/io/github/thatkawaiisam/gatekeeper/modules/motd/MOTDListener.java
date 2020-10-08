package io.github.thatkawaiisam.gatekeeper.modules.motd;

import io.github.thatkawaiisam.gatekeeper.GatekeeperPlugin;
import io.github.thatkawaiisam.plugintemplate.bungee.BungeeModuleListener;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.event.EventHandler;

public class MOTDListener extends BungeeModuleListener<MOTDModule, GatekeeperPlugin> {

    /**
     * MOTD Listener.
     *
     * @param module instance.
     */
    public MOTDListener(MOTDModule module) {
        super(module);
    }

    @EventHandler
    public void onMOTD(ProxyPingEvent event) {
        ServerPing pingInfo = event.getResponse();
        pingInfo.setDescriptionComponent(
                new TextComponent(
                        ChatColor.translateAlternateColorCodes(
                                '&',
                                getModule().getLine1() + "\n" + getModule().getLine2()
                        )
                )
        );
        event.setResponse(pingInfo);
    }

}
