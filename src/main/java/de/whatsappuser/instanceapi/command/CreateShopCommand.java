package de.whatsappuser.instanceapi.command;

import de.whatsappuser.instanceapi.InstanceCore;
import org.bukkit.Bukkit;
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
    private final List<UUID> shopcreate;

    public CreateShopCommand(InstanceCore core) {
        this.core = core;
        this.shopcreate = new ArrayList<>();
        Bukkit.getPluginManager().registerEvents(this, core);
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
            e.setCancelled(true);
            if (e.getMessage().contains("cancel")) {
                player.sendMessage(this.core.getInstanceConfig().prefix + "§cDer Prozess wurde beendet.");
                this.shopcreate.remove(player.getUniqueId());
                return;
            }
            for (de.whatsappuser.instanceapi.shop.inventory.ShopInventory shopInventory : this.core.getShopInventory().getShopInventories()) {
                if(shopInventory.getInventoryName().equalsIgnoreCase(e.getMessage())) {
                    player.sendMessage(this.core.getInstanceConfig().prefix + "§cDieser Name ist bereits vergeben");
                    return;
                }
            }
            for (int i = 0; i < this.core.getShopInventory().getShopInventories().size(); i++) {
                de.whatsappuser.instanceapi.shop.inventory.ShopInventory shopInventory = new de.whatsappuser.instanceapi.shop.inventory.ShopInventory(this.core.getShopInventory().getShopInventories().size()+1, 9, e.getMessage());
                this.core.getShopInventory().getShopInventories().add(shopInventory);
                player.sendMessage(this.core.getInstanceConfig().prefix + "§aShop §7'§e" + e.getMessage() + "§7'#§e" + shopInventory.getInventoryId() + "§7' §awurde erstellt.");
                this.shopcreate.remove(player.getUniqueId());
                this.core.saveConfig();
                return;
            }
        }
    }
}
