package de.whatsappuser.instanceapi.commands;

import de.whatsappuser.instanceapi.InstanceCore;
import de.whatsappuser.instanceapi.Utils;
import de.whatsappuser.instanceapi.commands.subcommands.FlyCommand;
import de.whatsappuser.instanceapi.commands.subcommands.ReloadCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommandManager implements CommandExecutor {

    private HashMap<String, SubCommand> subCommands = new HashMap<>();
    private List<SubCommand> subCommandList = new ArrayList<>();

    public CommandManager(String command) {
        InstanceCore.getInstance().getCommand(command).setExecutor(this);
        registerCommands();
    }

    public void registerCommands() {
        this.registerSubCommand(new FlyCommand());
        this.registerSubCommand(new ReloadCommand());
    }


    public void registerSubCommand(SubCommand command) {
        for (String alias : command.getAliases()) {
            this.subCommands.put(alias.toLowerCase(), command);
        }
        this.subCommandList.add(command);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(args.length != 0) {
            if(this.subCommands.containsKey(args[0])) {
                SubCommand command = this.subCommands.get(args[0]);
                if(!command.isPlayer() || sender instanceof Player) {
                    if(sender.hasPermission(command.getPermission()) || command.getPermission().isEmpty()) {
                        command.execute(sender, args);
                        return true;
                    } else {
                        sender.sendMessage(Utils.color(InstanceCore.getInstance().getMessages().noPermission.replace("*prefix*", InstanceCore.getInstance().getCoreConfig().prefix)));
                        return true;
                    }
                } else {
                    sender.sendMessage(Utils.color(InstanceCore.getInstance().getMessages().mustBeAPlayer.replace("*prefix*", InstanceCore.getInstance().getCoreConfig().prefix)));
                    return true;
                }
            }
        }
        sender.sendMessage(Utils.color(InstanceCore.getInstance().getMessages().commandHelp.replace("*prefix*", InstanceCore.getInstance().getCoreConfig().prefix)));
        for (SubCommand command : this.subCommandList) {
            sender.sendMessage(Utils.color("&b&l * &7" + command.getAliases().get(0) + ": §b" + command.getDescription()));
        }

        return true;
    }
}
