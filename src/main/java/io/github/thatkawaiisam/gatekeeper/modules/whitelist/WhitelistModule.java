package io.github.thatkawaiisam.gatekeeper.modules.whitelist;

import io.github.thatkawaiisam.gatekeeper.GatekeeperPlugin;
import io.github.thatkawaiisam.plugintemplate.bungee.BungeeModule;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.config.Configuration;

import java.util.*;

@Getter @Setter
public class WhitelistModule extends BungeeModule<GatekeeperPlugin> {

    private Set<UUID> whitelisted = new HashSet<>();
    private WhitelistMode mode = WhitelistMode.OFF;
    private String kickMessage = "";
    private String bypassPermission = "";

    /**
     * Whitelist Module.
     *
     * @param plugin instance.
     */
    public WhitelistModule(GatekeeperPlugin plugin) {
        super("whitelist", plugin, true);
    }

    @Override
    public void onEnable() {
        // Register Commands.
        addCommand(new WhitelistCommand(this));

        // Register Listeners.
        addListener(new WhitelistListener(this));

        // Load values from configuration.
        Configuration configuration = getConfiguration().getConfiguration();
        if (!configuration.contains("Whitelisted")) {
            ProxyServer.getInstance().getLogger().info("Did not get whitelisted players from config as the section did not exist.");
        } else {
            for (String entry : configuration.getStringList("Whitelisted")) {
                this.whitelisted.add(UUID.fromString(entry));
            }
            ProxyServer.getInstance().getLogger().info("Loaded whitelisted players from configuration file.");
        }
        if (!configuration.contains("Status")) {
            ProxyServer.getInstance().getLogger().info("Did not get whitelisted status from config as value did not exist.");
        } else {
            this.mode = WhitelistMode.valueOf(configuration.getString("Status"));
        }
        this.kickMessage = configuration.getString("Kick-Message");
        this.bypassPermission = configuration.getString("Bypass-Permission");
    }

    @Override
    public void onDisable() {
        Configuration configuration = getConfiguration().getConfiguration();

        // Save data values to file.
        if (whitelisted.size() > 1) {
            List<String> entries = new ArrayList<>();
            for (UUID uuid : whitelisted) {
                entries.add(uuid.toString());
            }
            configuration.set("Whitelisted", entries);
            ProxyServer.getInstance().getLogger().info("Saved whitelisted players to configuration file.");
        }
        configuration.set("Status", this.mode.name());
        getConfiguration().save();

        // Clear list in the event of a reload.
        this.whitelisted.clear();
    }
}
