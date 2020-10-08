package io.github.thatkawaiisam.gatekeeper.modules.motd;

import io.github.thatkawaiisam.gatekeeper.GatekeeperPlugin;
import io.github.thatkawaiisam.plugintemplate.bungee.BungeeModule;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.config.Configuration;

@Getter @Setter
public class MOTDModule extends BungeeModule<GatekeeperPlugin> {

    private String line1 = "", line2 = "";

    /**
     * MOTD Module.
     *
     * @param plugin instance.
     */
    public MOTDModule(GatekeeperPlugin plugin) {
        super("motd", plugin, true);
    }

    @Override
    public void onEnable() {
        // Load Values.
        Configuration configuration = getConfiguration().getConfiguration();
        if (!configuration.contains("Lines")) {
            ProxyServer.getInstance().getLogger().info("Could not find MOTD Config Section.");
        } else {
            line1 = configuration.getString("Lines.1");
            line2 = configuration.getString("Lines.2");
        }

        // Register Commands.
        addCommand(new MOTDCommand(this));

        // Register Listeners.
        addListener(new MOTDListener(this));
    }

    @Override
    public void onDisable() {
        Configuration configuration = getConfiguration().getConfiguration();
        configuration.set("Lines.1", line1);
        configuration.set("Lines.2", line2);
        getConfiguration().save();
    }
}
