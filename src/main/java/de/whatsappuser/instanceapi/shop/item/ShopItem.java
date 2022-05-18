package de.whatsappuser.instanceapi.shop.item;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

@Getter
@Setter
public class ShopItem {

    private int price, inventorySlot, amount;
    private short subId;
    private Material material;
    private String name, displayName;
    private List<String> description;

    public ShopItem(Material material, short subId, int amount, String name, String displayName, int price, int inventorySlot, List<String> description) {
        this.name = name;
        this.displayName = displayName;
        this.price = price;
        this.inventorySlot = inventorySlot;
        this.description = description;
    }

    public ItemStack getItem() {
        ItemStack itemStack = new ItemStack(this.material, this.amount, this.subId);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(this.displayName + " §e" + this.price + "$");
        meta.setLore(this.description);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public void addEnchantment(Enchantment enchantment, int id) {
        getItem().getItemMeta().addEnchant(enchantment, id, true);
    }

    public void removeEnchantment(Enchantment enchantment) {
        getItem().getItemMeta().removeEnchant(enchantment);
    }
}
