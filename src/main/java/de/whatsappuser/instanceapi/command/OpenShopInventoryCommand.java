package de.whatsappuser.instanceapi.command;

import de.whatsappuser.instanceapi.InstanceCore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OpenShopInventoryCommand implements CommandExecutor {

    private InstanceCore core;

    public OpenShopInventoryCommand(InstanceCore core) {
        this.core = core;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        if(!player.hasPermission("instancecore.*") || !player.hasPermission("instancecore.openShop")) {
            return true;
        }
        if(args.length == 0) {
            player.sendMessage(this.core.getInstanceConfig().prefix + "§cBitte geben sie entweder eine Id oder den Namen von dem Shop.");
            return true;
        }
        if(args.length == 1) {

        }
        return false;
    }
}
