package com.paulpanosch.newriserealms;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.function.Predicate;

public class EventManager implements Listener {
    @EventHandler
    public void onPlayerCraft(PrepareItemCraftEvent event) {
        CraftingInventory inv = event.getInventory();
        for (ItemStack item : inv.getMatrix()) {
            if (item == null) continue;
            if (item.getItemMeta().getLocalizedName().equals("Powerful Crystal")) {
                inv.setResult(new ItemStack(Material.AIR));
                break;
            }
        }
    }

    @EventHandler
    public void onPlayerInteraction(PlayerInteractEvent event) {
        switch (event.getAction()) {
            case LEFT_CLICK_AIR -> onLeftClick(event);
            case RIGHT_CLICK_AIR -> onRightClick(event);
        }
    }

    private void onLeftClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta itemMeta = item.getItemMeta();

        if (itemMeta.getLocalizedName().equals("Powerful Crystal")) {
            PersistentDataContainer customDataContainer = itemMeta.getPersistentDataContainer();
            NamespacedKey key = new NamespacedKey(NewRiseRealms.getInstance(), "Mode");

            int modeNumber = customDataContainer.get(key, PersistentDataType.INTEGER);
            switch (modeNumber) {
                case 1 -> {
                    itemMeta.setDisplayName("Powerful Crystal (A)");
                    itemMeta.setCustomModelData(2);
                    customDataContainer.set(key, PersistentDataType.INTEGER, 2);
                }
                case 2 -> {
                    itemMeta.setDisplayName("Powerful Crystal (C)");
                    itemMeta.setCustomModelData(3);
                    customDataContainer.set(key, PersistentDataType.INTEGER, 3);
                }
                case 3 -> {
                    itemMeta.setDisplayName("Powerful Crystal (S)");
                    itemMeta.setCustomModelData(1);
                    customDataContainer.set(key, PersistentDataType.INTEGER, 1);
                }
            }
            item.setItemMeta(itemMeta);
        }
    }

    private void onRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        Location loc = player.getLocation().add(0,1.65,0);
        Vector vec = loc.getDirection();

        Entity ppp = player;
        Predicate<Entity> p = entity -> !player.equals(entity) && entity instanceof Player;

        RayTraceResult res = player.getWorld().rayTrace(loc, vec, 200, FluidCollisionMode.NEVER, false, .1, p);
        if (res == null) return;
        if (res.getHitEntity() == null) return;

        if (res.getHitEntity() instanceof Player target) {
            ItemStack item = player.getInventory().getItemInMainHand();
            ItemMeta itemMeta = item.getItemMeta();
            PersistentDataContainer customDataContainer = itemMeta.getPersistentDataContainer();
            NamespacedKey key = new NamespacedKey(NewRiseRealms.getInstance(), "Mode");

            int modeNumber = customDataContainer.get(key, PersistentDataType.INTEGER);
            switch (modeNumber) {
                case 1 -> {
                    target.setGameMode(GameMode.SURVIVAL);
                    player.sendMessage("Set " + target.getName() + " to survival");
                }
                case 2 -> {
                    target.setGameMode(GameMode.ADVENTURE);
                    player.sendMessage("Set " + target.getName() + " to adventure");
                }
                case 3 -> {
                    target.setGameMode(GameMode.CREATIVE);
                    player.sendMessage("Set " + target.getName() + " to creative");
                }
            }
            target.getWorld().spawnParticle(Particle.CLOUD, target.getLocation().add(0, 1, 0), 10, 0, 0, 0, .1);
            target.setVelocity(new Vector(0, .5, 0));
        }
    }
}
