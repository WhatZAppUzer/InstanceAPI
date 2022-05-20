package de.whatsappuser.instanceapi.command;

import lombok.Getter;
import org.bukkit.command.CommandSender;

import java.util.List;

@Getter
public abstract class Command {

    private final boolean player;
    private final List<String> aliases;
    private final String permission;
    private final String description;

    public Command(boolean player, String permission, String description, List<String> aliases) {
        this.player = player;
        this.aliases = aliases;
        this.permission = permission;
        this.description = description;
    }

    protected abstract void execute(CommandSender sender, String[] args);

    protected abstract List<String> tabComplete(CommandSender sender, org.bukkit.command.Command command, String lable, String[] args);

}
