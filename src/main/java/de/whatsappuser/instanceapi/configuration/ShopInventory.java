package de.whatsappuser.instanceapi.configuration;

import java.util.ArrayList;
import java.util.List;

public class ShopInventory {

    public List<de.whatsappuser.instanceapi.shop.inventory.ShopInventory> shopInventories;

    public ShopInventory(List<de.whatsappuser.instanceapi.shop.inventory.ShopInventory> shopInventories) {
        this.shopInventories = shopInventories;
    }

    public ShopInventory(de.whatsappuser.instanceapi.shop.inventory.ShopInventory shopInventory) {
        this.shopInventories = new ArrayList<>();
        this.shopInventories.add(shopInventory);
    }

    public List<de.whatsappuser.instanceapi.shop.inventory.ShopInventory> getShopInventories() {
        return shopInventories;
    }
}
