package de.whatsappuser.instanceapi.shop.inventory;

import de.whatsappuser.instanceapi.InstanceCore;
import de.whatsappuser.instanceapi.shop.item.ShopItem;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ShopInventory {

    private final int inventoryId, inventorySize;
    private final String inventoryName;
    private final List<ShopItem> shopItems;

    public ShopInventory(int inventoryId, int inventorySize, String inventoryName) {
        this.inventoryId = inventoryId;
        this.inventorySize = inventorySize;
        this.inventoryName = inventoryName;
        this.shopItems = new ArrayList<>();
    }

    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(null, this.inventorySize, this.inventoryName);
        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack item = this.shopItems.get(i).getItem();
            inventory.setItem(this.shopItems.get(i).getInventorySlot(), item);
        }
        return inventory;
    }

    public Inventory getInventory(int inventoryId) {
        if(!InstanceCore.getInstance().getShopManager().getShopInventories().containsKey(inventoryId)) return null;

        return InstanceCore.getInstance().getShopManager().getShopInventories().get(inventoryId).getInventory();
    }
}
