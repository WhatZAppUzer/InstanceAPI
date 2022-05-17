package de.whatsappuser.instanceapi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import de.whatsappuser.instanceapi.configuration.InstanceConfig;
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

    private InstanceConfig instanceConfig;
    @Override
    public void onLoad() {
        instance = this;
        this.gson = new GsonBuilder().setPrettyPrinting().serializeNulls().setLongSerializationPolicy(LongSerializationPolicy.STRING).create();
        this.shopManager = new ShopManager();
        this.persist = new Persist(this);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    public static InstanceCore getInstance() {
        return instance;
    }

    public void loadConfig() {
        this.instanceConfig = this.persist.getFile(InstanceConfig.class).exists() ? this.persist.load(InstanceConfig.class) : new InstanceConfig();
    }

    public void saveConfig() {
        if(this.instanceConfig != null) this.persist.save(this.instanceConfig);
    }
}
