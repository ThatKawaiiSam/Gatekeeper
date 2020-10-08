package io.github.thatkawaiisam.gatekeeper.modules.motd;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import io.github.thatkawaiisam.plugintemplate.bungee.BungeeModuleCommand;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;

@CommandAlias("gatekeeper")
public class MOTDCommand extends BungeeModuleCommand<MOTDModule> {

    /**
     * MOTD Commands.
     *
     * @param module instance.
     */
    public MOTDCommand(MOTDModule module) {
        super(module, module.getPlugin().getHandler("commands"));
    }

    @Subcommand("motd line1")
    @CommandPermission("Gatekeeper.Admin")
    public void setLine1(CommandSender sender, String value) {
        getModule().setLine1(value);
        sender.sendMessage(ChatColor.GREEN + "Set line 1 of the MOTD.");
    }

    @Subcommand("motd line2")
    @CommandPermission("Gatekeeper.Admin")
    public void setLine2(CommandSender sender, String value) {
        getModule().setLine2(value);
        sender.sendMessage(ChatColor.GREEN + "Set line 2 of the MOTD.");
    }
}
