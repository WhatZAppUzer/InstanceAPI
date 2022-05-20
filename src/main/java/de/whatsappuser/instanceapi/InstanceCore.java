package de.whatsappuser.instanceapi;

import de.whatsappuser.instanceapi.commands.CommandManager;
import de.whatsappuser.instanceapi.commands.reloadConfigCommand;
import de.whatsappuser.instanceapi.config.Config;
import de.whatsappuser.instanceapi.config.Messages;
import de.whatsappuser.instanceapi.handler.HandlerManager;
import de.whatsappuser.instanceapi.handler.handlers.IHandler;
import de.whatsappuser.instanceapi.handler.handlers.mongo.MongoHandler;
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

    private CommandManager commandManager;

    private HandlerManager handlerManager;

    @Override
    public void onEnable() {
        super.onEnable();

        instance = this;

        getDataFolder().mkdir();

        this.persist = new Persist();

        loadConfigs();
        saveConfigs();

        this.commandManager = new CommandManager("framework");
        this.handlerManager = new HandlerManager();

        connectDatabase();
    }

    @Override
    public void onDisable() {
        saveConfigs();
        instance = null;
        this.persist = null;
    }

    //<editor-fold desc="loadConfig">
    public void loadConfigs() {
        this.coreConfig = this.persist.getFile(Config.class).exists() ? this.persist.load(Config.class) : new Config();
        this.messages = this.persist.getFile(Messages.class).exists() ? this.persist.load(Messages.class) : new Messages();
    }
    //</editor-fold>

    //<editor-fold desc="saveConfig">
    public void saveConfigs() {
        if (this.coreConfig != null) this.persist.save(this.coreConfig);
        if (this.messages != null) this.persist.save(this.messages);
    }
    //</editor-fold>

    //<editor-fold desc="reloadConfig">
    public void reloadConfig() {
        this.saveConfigs();
        this.loadConfigs();
        getLogger().info("Core Config was reloaded.");
    }
    //</editor-fold>

    //<editor-fold desc="getInstance">
    public static InstanceCore getInstance() {
        return instance;
    }
    //</editor-fold>

    //<editor-fold desc="connectDatabase">
    private void connectDatabase() {
        IHandler handler = new MongoHandler(this.coreConfig.host, this.coreConfig.user, this.coreConfig.password,
                this.coreConfig.authDatabase, this.coreConfig.database, this.coreConfig.port, this.coreConfig.auth);
        this.handlerManager.registerHandler(handler);
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
