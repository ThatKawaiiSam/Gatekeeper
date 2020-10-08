package io.github.thatkawaiisam.gatekeeper;

import io.github.thatkawaiisam.plugintemplate.bungee.BungeePluginTemplate;
import io.github.thatkawaiisam.plugintemplate.bungee.handlers.BungeeCommandHandler;
import io.github.thatkawaiisam.plugintemplate.bungee.handlers.BungeeModuleHandler;
import io.github.thatkawaiisam.plugintemplate.shared.ConstructorInject;

import java.util.ArrayList;

public class GatekeeperPlugin extends BungeePluginTemplate {

    @Override
    public void onEnable() {
        if (getHandlers() == null) {
            setHandlers(new ArrayList<>());
            // Commands.
            getHandlers().add(new BungeeCommandHandler(this, "io.github.gatekeeper.commands"));
            // Modules.
            getHandlers().add(new BungeeModuleHandler(
                    this,
                    "io.github.thatkawaiisam.gatekeeper.modules",
                    new ConstructorInject().chain(GatekeeperPlugin.class, this)
            ));
        }
        enableHandlers();
    }

    @Override
    public void onDisable() {
        disableHandlers();
    }
}
