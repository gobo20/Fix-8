package com.example.eventplugin.commands;

import com.example.eventplugin.EventPlugin;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class EventCommand implements BasicCommand {
    @Override
    public void execute(CommandSourceStack source, String[] args) {
        CommandSender sender = source.getSender();

        if (!sender.isOp() && !sender.hasPermission("event.admin")) {
            sender.sendMessage("§cKeine Rechte!");
            return;
        }

        if (args.length == 0) {
            sender.sendMessage("§7/event <Spieler> | /event set <red|blue>");
            return;
        }

        EventPlugin plugin = EventPlugin.getInstance();

        if (args[0].equalsIgnoreCase("set")) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage("§cNur als Spieler!");
                return;
            }
            if (args.length < 2) {
                sender.sendMessage("§c/event set <red|blue>");
                return;
            }

            String key = args[1].toLowerCase().startsWith("r") ? "red" : "blue";
            Location loc = player.getLocation();
            plugin.getConfig().set("locations." + key + ".world", loc.getWorld().getName());
            plugin.getConfig().set("locations." + key + ".x", loc.getX());
            plugin.getConfig().set("locations." + key + ".y", loc.getY());
            plugin.getConfig().set("locations." + key + ".z", loc.getZ());
            plugin.getConfig().set("locations." + key + ".yaw", loc.getYaw());
            plugin.getConfig().set("locations." + key + ".pitch", loc.getPitch());
            plugin.saveConfig();
            sender.sendMessage("§aSpawn für " + key + " gesetzt!");
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage("§cSpieler nicht online!");
            return;
        }

        TeamCommand.addInvited(target);
        target.sendMessage("");
        target.sendMessage("§8§l[§9Event§8] §fDu wurdest eingeladen!");
        target.sendMessage("§7Schreibe §9/Blau §7für §9Blaues Team");
        target.sendMessage("§7Schreibe §c/Rot §7für §cRotes Team");
        target.sendMessage("");
        sender.sendMessage("§a" + target.getName() + " eingeladen.");
    }

    @Override
    public Collection<String> suggest(CommandSourceStack source, String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 1) {
            list.add("set");
            for (Player player : Bukkit.getOnlinePlayers()) {
                list.add(player.getName());
            }
        } else if (args.length == 2 && args[0].equalsIgnoreCase("set")) {
            list.addAll(Arrays.asList("red", "blue"));
        }
        return list;
    }

    @Override
    public String permission() {
        return "event.admin";
    }
}
