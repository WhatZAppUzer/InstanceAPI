package de.whatsappuser.instanceapi.command;

import de.whatsappuser.instanceapi.InstanceCore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CreateShopCommand implements CommandExecutor, Listener {

    private InstanceCore core;
    private List<UUID> shopcreate;

    public CreateShopCommand(InstanceCore core) {
        this.core = core;
        this.shopcreate = new ArrayList<>();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player))
             return true;

        Player player = (Player) sender;
        if(!player.hasPermission("instancecore.*") || !player.hasPermission("instancecore.createshop")) {
            return true;
        }
        if(args.length == 0 && ! this.shopcreate.contains(player.getUniqueId())) {
            player.sendMessage(this.core.getInstanceConfig().prefix + "Wie soll der Shop heißen? §cProzess beenden? §7'cancel'");
            this.shopcreate.add(player.getUniqueId());
            return true;
        }
        return false;
    }

    @EventHandler
    public void onAsync(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        if(this.shopcreate.contains(player.getUniqueId())) {
            if (e.getMessage().contains("cancel")) {
                player.sendMessage(this.core.getInstanceConfig().prefix + "§cDer Prozess wurde beendet.");
                this.shopcreate.remove(player.getUniqueId());
                return;
            }

        }
    }
}
