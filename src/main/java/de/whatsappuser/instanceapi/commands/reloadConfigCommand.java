package de.whatsappuser.instanceapi.commands;

import de.whatsappuser.instanceapi.InstanceCore;
import de.whatsappuser.instanceapi.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class reloadConfigCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player && !sender.hasPermission("framework.*") || !sender.hasPermission("framework.reload")) {
            sender.sendMessage(Utils.color(InstanceCore.getInstance().getMessages().noPermission.replace("*prefix*", InstanceCore.getInstance().getCoreConfig().prefix)));
            return true;
        }
        if(args.length != 0) {
            sender.sendMessage(Utils.color(InstanceCore.getInstance().getCoreConfig().prefix + "§cplease use only reloadconfig, no arguments."));
            return true;
        }
        InstanceCore.getInstance().loadConfigs();
        sender.sendMessage(Utils.color(InstanceCore.getInstance().getCoreConfig().prefix + "§aConfiguration reloaded."));
        return true;
    }
}
