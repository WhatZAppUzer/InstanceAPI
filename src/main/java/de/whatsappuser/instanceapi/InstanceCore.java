package de.whatsappuser.instanceapi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import de.whatsappuser.instanceapi.command.CreateShopCommand;
import de.whatsappuser.instanceapi.command.ShowInventoryCommand;
import de.whatsappuser.instanceapi.configuration.Config;
import de.whatsappuser.instanceapi.configuration.ShopInventory;
import de.whatsappuser.instanceapi.serializer.Persist;
import de.whatsappuser.instanceapi.shop.ShopManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class InstanceCore extends JavaPlugin {

    private ShopManager shopManager;
    private static InstanceCore instance;

    private Gson gson;

    private Persist persist;

    private Config instanceConfig;
    private ShopInventory shopInventory;

    @Override
    public void onLoad() {
        instance = this;
        this.gson = new GsonBuilder().setPrettyPrinting().serializeNulls().setLongSerializationPolicy(LongSerializationPolicy.STRING).create();
        this.shopManager = new ShopManager();
        if(!getDataFolder().exists()) getDataFolder().mkdir();
    }

    @Override
    public void onEnable() {
        this.persist = new Persist(this);
        loadConfig();
        saveConfig();
        getCommand("createShop").setExecutor(new CreateShopCommand(this));
        getCommand("showinv").setExecutor(new ShowInventoryCommand(this));
    }

    @Override
    public void onDisable() {

    }

    public static InstanceCore getInstance() {
        return instance;
    }

    public void loadConfig() {
        this.instanceConfig = this.persist.getFile(Config.class).exists() ? this.persist.load(Config.class) : new Config();
        this.shopInventory = this.persist.getFile(ShopInventory.class).exists() ? this.persist.load(ShopInventory.class) : new ShopInventory(new de.whatsappuser.instanceapi.shop.inventory.ShopInventory(0, 27, "ExampleInventory"));
    }

    public void saveConfig() {
        if(this.instanceConfig != null) this.persist.save(this.instanceConfig);
        if(this.shopInventory != null) this.persist.save(this.shopInventory);
    }
}
