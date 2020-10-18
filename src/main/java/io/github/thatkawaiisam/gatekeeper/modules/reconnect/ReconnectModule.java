package io.github.thatkawaiisam.gatekeeper.modules.reconnect;

import io.github.thatkawaiisam.gatekeeper.GatekeeperPlugin;
import io.github.thatkawaiisam.plugintemplate.bungee.BungeeModule;

public class ReconnectModule extends BungeeModule<GatekeeperPlugin> {

    /**
     * Reconnect Module.
     *
     * @param plugin instance.
     */
    public ReconnectModule(GatekeeperPlugin plugin) {
        super("reconnect", plugin, false);
    }

    @Override
    public void onEnable() {
        addListener(new ReconnectListener(this));
    }

    @Override
    public void onDisable() {

    }

}
