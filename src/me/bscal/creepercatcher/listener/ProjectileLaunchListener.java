package me.bscal.creepercatcher.listener;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import me.bscal.creepercatcher.CreeperCatcher;
import me.bscal.creepercatcher.DataManager;

/*
 * Listens when projectile hits.
 */
public class ProjectileLaunchListener implements Listener{
	
	private DataManager config = new DataManager((JavaPlugin) CreeperCatcher.getPlugin(), "config.yml");
	ArrayList<Player> catchers = CreeperCatcher.getMainInstance().getCatcherManager().getCatchers();
	
	@EventHandler
	public void onProjectileLaunchEvent(PlayerInteractEvent e) {
		if(e.getItem().getItemMeta() != null && e.getItem().getItemMeta().getDisplayName().contains("Creeper Capture Egg") == true) {
			catchers.add(e.getPlayer());
		}
	}
	
	@EventHandler
	public void onEntityDamageEntityEvent(EntityDamageByEntityEvent e) {
		//Checks the projectile is an egg and name matches.
		if(!(e.getDamager() instanceof Egg)) {
			return;
		}
		
		Projectile projectile = (Projectile) e.getDamager();
		
		if(!(projectile.getShooter() instanceof Player)) {
			return;
		}
		Player shooter = (Player) projectile.getShooter();
		Entity creeper = e.getEntity();
		
		if(catchers.contains(shooter)) {
			//Removes the players from the list.
			catchers.remove(shooter);
			//Checks perms
			if(shooter.hasPermission("cc.user") || shooter.hasPermission("cc.admin")) {
				if(creeper instanceof Creeper) {
					double rand = Math.random() * 100;
					
					//Chance check
					if(rand > config.getDouble("Catch_Egg.Chance")) {
						//Success
						creeper.remove();
						shooter.sendMessage(ChatColor.AQUA + "You successfully caught a creeper!");
						shooter.getInventory().addItem(new ItemStack(Material.MONSTER_EGG, 1, (short) 50));
						return;
						}
					//Failure
					shooter.sendMessage(ChatColor.AQUA + "The egg tickled the creeper and that was it!");
					return;
				}
				failure(shooter);
			}
		}
	}
	
	/**
	 * Sends a message to player 
	 * @param p
	 */
	public void failure(Player p) {
		p.sendMessage(ChatColor.RED + "It had no effect!");
	}
}
