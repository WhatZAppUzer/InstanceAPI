package de.whatsappuser.instanceapi.commands.subcommands;

import de.whatsappuser.instanceapi.Utils;
import de.whatsappuser.instanceapi.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class FlyCommand extends SubCommand {

    private List<UUID> flyList = new ArrayList<>();
    public FlyCommand() {
        super(true, "framework.fly", "enable/disable the fly-mode", Collections.singletonList("fly"));
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if(args.length == 1) {
            if(flyList.contains(player.getUniqueId())) {
                player.sendMessage(Utils.color(getMessages().flyDisabled.replace("*prefix*", getConfig().prefix)));
                player.setAllowFlight(false);
                this.flyList.remove(player.getUniqueId());
            } else {
                player.sendMessage(Utils.color(getMessages().flyEnabled.replace("*prefix*", getConfig().prefix)));
                player.setAllowFlight(true);
                this.flyList.add(player.getUniqueId());
            }
        } else if(args.length == 2) {
            String target = args[1];
            Player targetPlayer = Bukkit.getPlayer(target);
            if(targetPlayer != null) {
                if(flyList.contains(player.getUniqueId())) {
                    targetPlayer.sendMessage(Utils.color(getMessages().flyDisabled.replace("*prefix*", getConfig().prefix)));
                    player.sendMessage(Utils.color(getMessages().targetFlyDisabled.replace("*prefix*", getConfig().prefix).replace("*target*", targetPlayer.getDisplayName())));
                    targetPlayer.setAllowFlight(false);
                    this.flyList.remove(targetPlayer.getUniqueId());
                } else {
                    targetPlayer.sendMessage(Utils.color(getMessages().flyEnabled.replace("*prefix*", getConfig().prefix)));
                    player.sendMessage(Utils.color(getMessages().targetFlyEnabled.replace("*prefix*", getConfig().prefix).replace("*target*", targetPlayer.getDisplayName())));
                    targetPlayer.setAllowFlight(true);
                    this.flyList.add(targetPlayer.getUniqueId());
                }
            } else {
                player.sendMessage(Utils.color(getMessages().playerNotOnline.replace("*prefix*", getConfig().prefix)));
            }
        } else {
            player.sendMessage(Utils.color(getMessages().toManyArguments.replace("*prefix*", getConfig().prefix)));
        }
    }
}
