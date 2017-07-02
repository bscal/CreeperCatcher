package me.bscal.creepercatcher.item;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import me.bscal.creepercatcher.CreeperCatcher;
import me.bscal.creepercatcher.DataManager;

/**
 * Contains info on the egg ItemStack.
 * @author Jon
 *
 */
public class CaptureEgg {

	private final int amount;
	private final String name;
	private DataManager config = new DataManager((JavaPlugin) CreeperCatcher.getPlugin(), "config.yml");
	
	public CaptureEgg() {
		amount = 1;
		name = "Creeper Capture Egg";
	}
	
	public CaptureEgg(int amount) {
		this.amount = amount;
		name = "Creeper Capture Egg";
	}
	
	/**
	 * Returns itemstack of an egg.
	 * @return
	 */
	public ItemStack getEgg() {
		ItemStack egg = new ItemStack(Material.EGG, amount);
		ItemMeta im = egg.getItemMeta();
		im.setDisplayName(ChatColor.AQUA + name);
		List<String> lore = new ArrayList<String>();
		for(String line : config.getStringList("Catch_Egg.Lore")) {
			lore.add(ChatColor.GRAY + line);
		}
		im.setLore(lore);
		im.addEnchant(Enchantment.DAMAGE_ARTHROPODS, 1, true);
		im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		egg.setItemMeta(im);
		return egg;
	}
	
	/**
	 * Creates and adds the crafting recipe to the server.
	 */
	public void setupRecipe() {
		// Create our custom recipe variable
		ShapedRecipe recipe = new ShapedRecipe(getEgg());

		// Here we will set the places. E and S can represent anything, and the letters can be anything. Beware; this is case sensitive.
		recipe.shape("DDD", "EGE", "DDD");

		// Set what the letters represent.
		// E = Emerald, S = Stick
		recipe.setIngredient('E', Material.EMERALD_BLOCK);
		recipe.setIngredient('D', Material.DIAMOND_BLOCK);
		recipe.setIngredient('G', Material.EGG);

		// Finally, add the recipe to the bukkit recipes
		Bukkit.addRecipe(recipe);
	}
}
