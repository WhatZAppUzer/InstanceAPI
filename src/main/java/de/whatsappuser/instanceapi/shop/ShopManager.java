package de.whatsappuser.instanceapi.shop;

import de.whatsappuser.instanceapi.shop.inventory.ShopInventory;
import de.whatsappuser.instanceapi.shop.item.ShopItem;
import lombok.Getter;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

@Getter
public class ShopManager {

    private final HashMap<Inventory, ShopItem> shopItemsInInventory;
    private final HashMap<Integer, ShopInventory> shopInventories;

    public ShopManager() {
        this.shopItemsInInventory = new HashMap<>();
        this.shopInventories = new HashMap<>();
    }

}
