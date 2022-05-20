package de.whatsappuser.instanceapi.commands.subcommands;

import de.whatsappuser.instanceapi.InstanceCore;
import de.whatsappuser.instanceapi.Utils;
import de.whatsappuser.instanceapi.commands.SubCommand;
import org.bukkit.command.CommandSender;

import java.util.Collections;

public class ReloadCommand extends SubCommand {

    public ReloadCommand() {
        super(false, "framework.reload", "reload the configuration", Collections.singletonList("rl"));
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        InstanceCore.getInstance().loadConfigs();
        sender.sendMessage(Utils.color(InstanceCore.getInstance().getMessages().reloaded.replace("*prefix*", InstanceCore.getInstance().getCoreConfig().prefix)));
    }
}
