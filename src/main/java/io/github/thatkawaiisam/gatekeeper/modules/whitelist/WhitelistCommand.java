package io.github.thatkawaiisam.gatekeeper.modules.whitelist;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.thatkawaiisam.plugintemplate.bungee.BungeeModuleCommand;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@CommandAlias("gatekeeper")
public class WhitelistCommand extends BungeeModuleCommand<WhitelistModule> {

    private static ExecutorService executorService = Executors.newFixedThreadPool(2);
    private static JsonParser parser = new JsonParser();

    /**
     * Whitelist Commands.
     *
     * @param module instance.
     */
    public WhitelistCommand(WhitelistModule module) {
        super(module, module.getPlugin().getHandler("commands"));
    }

    @Subcommand("whitelist on")
    @CommandPermission("Gatekeeper.Admin")
    public void whitelistOn(CommandSender sender) {
        getModule().setMode(WhitelistMode.ON);
        sender.sendMessage(ChatColor.GREEN + "Network whitelist is now on.");
    }

    @Subcommand("whitelist off")
    @CommandPermission("Gatekeeper.Admin")
    public void whitelistOff(CommandSender sender) {
        getModule().setMode(WhitelistMode.OFF);
        sender.sendMessage(ChatColor.RED + "Network whitelist is now off.");
    }

    @Subcommand("whitelist add")
    @CommandPermission("Gatekeeper.Admin")
    public void whitelistAdd(CommandSender sender, String target) {
        getUUID(target).whenComplete(((uuid, throwable) -> {
            if (uuid == null) {
                sender.sendMessage(ChatColor.RED + "Unable to find a valid player.");
                return;
            }
            getModule().getWhitelisted().add(uuid);
            sender.sendMessage(ChatColor.GREEN + "Added " + target + " to the whitelist.");
        }));
    }

    @Subcommand("whitelist remove")
    @CommandPermission("Gatekeeper.Admin")
    public void whitelistRemove(CommandSender sender, String target) {
        getUUID(target).whenComplete(((uuid, throwable) -> {
            if (uuid == null) {
                sender.sendMessage(ChatColor.RED + "Unable to get UUID of whitelisted player.");
                return;
            }
            if (!getModule().getWhitelisted().contains(uuid)) {
                sender.sendMessage(ChatColor.RED + "Player is not currently whitelisted.");
                return;
            }
            getModule().getWhitelisted().remove(uuid);
            sender.sendMessage(ChatColor.RED + "Removed " + target + " from the whitelist.");
        }));
    }

    /**
     * Get UUID of player based on name.
     *
     * @param player to fetch UUID of.
     * @return UUID if player is valid.
     */
    // TODO: Put a this back into my utils.
    public static CompletableFuture<UUID> getUUID(String player) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String url = "https://api.ashcon.app/mojang/v2/user/" + player;
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestProperty("User-Agent",  "Mozilla/5.0");

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                JsonElement object = parser.parse(response.toString());
                JsonObject parsedObject = object.getAsJsonObject();
                if (!parsedObject.has("uuid")) {
                    return null;
                }
                return UUID.fromString(parsedObject.get("uuid").getAsString());
            } catch (Exception e) {
                return null;
            }
        }, executorService);
    }



}
