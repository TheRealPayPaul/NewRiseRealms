package com.paulpanosch.newriserealms;

import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.function.Predicate;

public class EventManager implements Listener {
    @EventHandler
    public void onPlayerCraft(PrepareItemCraftEvent event) {
        CraftingInventory inv = event.getInventory();
        if (inv.containsAtLeast(ItemManager.getPowerfulCrystal(), 1)) {
            inv.setResult(new ItemStack(Material.AIR));
        }
    }

    @EventHandler
    public void onPlayerLeftClick(PlayerInteractEvent event) {
        if (event.getAction() != Action.LEFT_CLICK_AIR) return;

        Player player = event.getPlayer();

        Location loc = player.getLocation().add(0,1.65,0);
        Vector vec = loc.getDirection();

        Entity ppp = player;
        Predicate<Entity> p = entity -> !ppp.equals(entity);

        RayTraceResult res = player.getWorld().rayTrace(loc, vec, 200, FluidCollisionMode.NEVER, false, .1, p);
        if (res.getHitEntity() != null) {
            if (res.getHitEntity().isGlowing()) {
                res.getHitEntity().setGlowing(false);
            } else {
                res.getHitEntity().setGlowing(true);
            }
        }
    }
}
