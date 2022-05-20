package de.whatsappuser.instanceapi.commands;

import de.whatsappuser.instanceapi.InstanceCore;
import de.whatsappuser.instanceapi.config.Config;
import de.whatsappuser.instanceapi.config.Messages;
import lombok.Getter;
import org.bukkit.command.CommandSender;

import java.util.List;

@Getter
public abstract class SubCommand {

    private final Messages messages;
    private final Config config;
    private final boolean player;
    private final String permission;
    private final String description;
    private final List<String> aliases;

    public SubCommand(boolean player, String permission, String description, List<String> aliases) {
        this.player = player;
        this.permission = permission;
        this.description = description;
        this.aliases = aliases;
        this.config = InstanceCore.getInstance().getCoreConfig();
        this.messages = InstanceCore.getInstance().getMessages();
    }



    public abstract void execute(CommandSender sender, String[] args);

}
