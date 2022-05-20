package de.whatsappuser.instanceapi.command;

import de.whatsappuser.instanceapi.InstanceCore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommandManager implements CommandExecutor, TabCompleter {

    private HashMap<String, de.whatsappuser.instanceapi.command.Command> commands = new HashMap<>();
    private List<de.whatsappuser.instanceapi.command.Command> commandList = new ArrayList<>();

    public CommandManager(String command) {
        InstanceCore.getInstance().getCommand(command).setExecutor(this);
        InstanceCore.getInstance().getCommand(command).setTabCompleter(this);
    }

    public void registerCommand(de.whatsappuser.instanceapi.command.Command command) {
        for (String alias : command.getAliases())
            this.commands.put(alias.toLowerCase(), command);
        this.commandList.add(command);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length != 0) {
            if (this.commands.containsKey(args[0])) {
                de.whatsappuser.instanceapi.command.Command cmd = this.commands.get(args[0]);
                if (! cmd.isPlayer() || sender instanceof Player) {
                    if (sender.hasPermission(cmd.getPermission()) || cmd.getPermission().isEmpty()) {
                        cmd.execute(sender, args);
                    } else {
                        sender.sendMessage(InstanceCore.getInstance().getMessages().noPermission.replace("*prefix*", InstanceCore.getInstance().getCoreConfig().prefix));
                    }
                } else {
                    sender.sendMessage(InstanceCore.getInstance().getMessages().mustBeAPlayer.replace("*prefix*", InstanceCore.getInstance().getCoreConfig().prefix));
                }
                return true;
            }
        }
        sender.sendMessage(InstanceCore.getInstance().getMessages().commandHelp.replace("*prefix*", InstanceCore.getInstance().getCoreConfig().prefix));
        for (de.whatsappuser.instanceapi.command.Command c : this.commandList)
            sender.sendMessage("§c§l• §7" + c.getAliases().get(0) + "§8» §b" + c.getDescription());
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String s, String[] args) {
        if (args.length == 1) {
            ArrayList<String> result = new ArrayList<>();
            for (String command : this.commands.keySet())
                if (command.toLowerCase().startsWith(args[0].toLowerCase()))
                    result.add(command);

            return result;
        }
        if(this.commands.containsKey(args[0]))
            return this.commands.get(args[0]).tabComplete(sender, cmd, s, args);
        return null;
    }
}
