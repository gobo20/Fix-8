package com.example.eventplugin.commands;

import com.example.eventplugin.EventPlugin;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class TeamCommand implements BasicCommand {
    private static final Set<UUID> invited = new HashSet<>();

    public static void addInvited(Player player) {
        invited.add(player.getUniqueId());
    }

    @Override
    public void execute(CommandSourceStack source, String[] args) {
        CommandSender sender = source.getSender();
        if (!(sender instanceof Player player)) {
            return;
        }

        String label = source.getLabel();
        String team = label.equalsIgnoreCase("blau") || label.equalsIgnoreCase("blue") ? "blue" : "red";
        joinTeam(player, team);
    }

    private void joinTeam(Player player, String team) {
        EventPlugin plugin = EventPlugin.getInstance();
        String path = "locations." + team;

        if (!plugin.getConfig().contains(path + ".world")) {
            player.sendMessage("§cSpawn für " + team + " nicht gesetzt! /event set " + team);
            return;
        }

        String worldName = plugin.getConfig().getString(path + ".world");
        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            player.sendMessage("§cDie Welt " + worldName + " wurde nicht gefunden!");
            return;
        }

        double x = plugin.getConfig().getDouble(path + ".x");
        double y = plugin.getConfig().getDouble(path + ".y");
        double z = plugin.getConfig().getDouble(path + ".z");
        float yaw = (float) plugin.getConfig().getDouble(path + ".yaw");
        float pitch = (float) plugin.getConfig().getDouble(path + ".pitch");

        player.teleport(new Location(world, x, y, z, yaw, pitch));
        player.getInventory().clear();

        Color color = team.equals("blue") ? Color.fromRGB(0, 0, 255) : Color.fromRGB(255, 0, 0);
        ItemStack helmet = coloredArmor(Material.LEATHER_HELMET, color);
        ItemStack chest = coloredArmor(Material.LEATHER_CHESTPLATE, color);
        ItemStack legs = coloredArmor(Material.LEATHER_LEGGINGS, color);
        ItemStack boots = coloredArmor(Material.LEATHER_BOOTS, color);

        player.getInventory().setHelmet(helmet);
        player.getInventory().setChestplate(chest);
        player.getInventory().setLeggings(legs);
        player.getInventory().setBoots(boots);

        if (team.equals("blue")) {
            player.sendMessage("§8[§9Event§8] §9BLAUES TEAM!");
        } else {
            player.sendMessage("§8[§9Event§8] §cROTES TEAM!");
        }
    }

    private ItemStack coloredArmor(Material material, Color color) {
        ItemStack item = new ItemStack(material);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        meta.setColor(color);
        meta.setUnbreakable(true);
        item.setItemMeta(meta);
        return item;
    }
}
