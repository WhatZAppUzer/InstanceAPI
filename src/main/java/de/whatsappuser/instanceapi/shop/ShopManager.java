package de.whatsappuser.instanceapi.shop;

import de.whatsappuser.instanceapi.InstanceCore;
import de.whatsappuser.instanceapi.shop.inventory.ShopInventory;
import de.whatsappuser.instanceapi.shop.item.ShopItem;
import lombok.Getter;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

@Getter
public class ShopManager {

    private final HashMap<Inventory, ShopItem> shopItemsInInventory;

    public ShopManager() {
        this.shopItemsInInventory = new HashMap<>();
    }

    public ShopInventory getShopInventory(String name) {
        for (int i = 0; i < InstanceCore.getInstance().getShopInventory().getShopInventories().size(); i++) {
            ShopInventory shopInventory = InstanceCore.getInstance().getShopInventory().getShopInventories().get(i);
            if(!shopInventory.getInventoryName().equalsIgnoreCase(name)) return null;
            return shopInventory;
        }
        return null;
    }

    public ShopInventory getShopInventory(int id) {
        for (ShopInventory shopInventory : InstanceCore.getInstance().getShopInventory().getShopInventories()) {
            if(shopInventory.getInventoryId() == id) {
                return shopInventory;
            }
        }
        return null;
    }
}
