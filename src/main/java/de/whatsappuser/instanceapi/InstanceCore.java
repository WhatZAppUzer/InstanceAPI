package de.whatsappuser.instanceapi;

import de.whatsappuser.instanceapi.config.Config;
import de.whatsappuser.instanceapi.config.Messages;
import de.whatsappuser.instanceapi.serializer.Persist;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.PrintWriter;
import java.io.StringWriter;

@Getter
public class InstanceCore extends JavaPlugin {

    private static InstanceCore instance;

    private Persist persist;

    private Config coreConfig;
    private Messages messages;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        if (getDataFolder().exists())
            return;
        getDataFolder().mkdir();
        this.persist = new Persist();
        loadConfig();
        saveConfig();
    }

    @Override
    public void onDisable() {
        saveConfig();
        instance = null;
        this.persist = null;
    }

    //<editor-fold desc="loadConfig">
    public void loadConfig() {
        this.coreConfig = this.persist.getFile(Config.class).exists() ? this.persist.load(Config.class) : new Config();
        this.messages = this.persist.getFile(Messages.class).exists() ? this.persist.load(Messages.class) : new Messages();
    }
    //</editor-fold>

    //<editor-fold desc="saveConfig">
    public void saveConfig() {
        if (this.coreConfig != null) this.persist.save(this.coreConfig);
        if (this.messages != null) this.persist.save(this.messages);
    }
    //</editor-fold>

    //<editor-fold desc="reloadConfig">
    public void reloadConfig() {
        this.saveConfig();
        this.loadConfig();
        getLogger().info("Core Config was reloaded.");
    }
    //</editor-fold>

    //<editor-fold desc="getInstance">
    public static InstanceCore getInstance() {
        return instance;
    }
    //</editor-fold>

    //<editor-fold desc="sendErrorMessage">
    public void sendErrorMessage(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        e.printStackTrace(pw);
        getLogger().info(sw.getBuffer().toString());
        if (this.coreConfig != null || this.coreConfig.sendErrorReports)
            getLogger().info("Sending Error Report...");
    }
    //</editor-fold>
}
