package de.whatsappuser.instanceapi.command;

import de.whatsappuser.instanceapi.InstanceCore;
import de.whatsappuser.instanceapi.shop.inventory.ShopInventory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShowInventoryCommand implements CommandExecutor {

    private final InstanceCore core;

    public ShowInventoryCommand(InstanceCore core) {
        this.core = core;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        if(!player.hasPermission("instancecore.showinventory")) return true;

        if(args.length == 0) {
            player.sendMessage(this.core.getInstanceConfig().prefix + "§aDiese Inventare sind verfügbar: ");
            for (ShopInventory shopInventory : this.core.getShopInventory().getShopInventories()) {
                player.sendMessage("-§e" + shopInventory.getInventoryId() + " §a" + shopInventory.getInventoryName());
            }
            return true;
        }
        if(args.length == 1) {
            ShopInventory shopInventory;
             int id;
             try {
                 id = Integer.parseInt(args[0]);
             } catch (NumberFormatException ex) {
                 player.sendMessage(this.core.getInstanceConfig().prefix + "§cBitte gebe eine gültige Zahl an!");
                 return true;
             }
             if(this.core.getShopManager().getShopInventory(id) == null) {
                 player.sendMessage(this.core.getInstanceConfig().prefix + "§cDieses Inventar existiert nict!");
                 return true;
             }
             shopInventory = this.core.getShopManager().getShopInventory(id);
            player.openInventory(shopInventory.getInventory());
        }
        return false;
    }
}
