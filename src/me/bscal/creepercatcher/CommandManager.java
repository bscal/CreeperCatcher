package me.bscal.creepercatcher;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.bscal.creepercatcher.item.CaptureEgg;

public class CommandManager implements CommandExecutor{

	public boolean onCommand(CommandSender sender, Command cmd, String cmdString, String[] args) {
		if(cmd.getName().equalsIgnoreCase("creepercatcher")) {
			
			DataManager config = new DataManager((JavaPlugin) CreeperCatcher.getPlugin(), "config.yml");
			Player player = (Player) sender;
			
			if(args.length == 0) {
				player.performCommand("creepercatcher help");
				return true;
			}
			if(args[0].equalsIgnoreCase("help")) {
				player.sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
				player.sendMessage(ChatColor.YELLOW + "/cc help " + ChatColor.WHITE + "- Help menu");
				player.sendMessage(ChatColor.YELLOW + "/cc give [player] [amount] " + ChatColor.WHITE + "- Gives amount of captures eggs");
				player.sendMessage(ChatColor.YELLOW + "/cc reload " + ChatColor.WHITE + "- Reloads plugin");
				player.sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
			}
			else if(args[0].equalsIgnoreCase("give")) {
				if(player.hasPermission("cc.admin")) {
					int amount = 1;
					Player reciever = player;
					if(args.length == 2) {
						amount = Integer.parseInt(args[1]);
					}
					if(args.length == 3) {
						if(Bukkit.getPlayer(args[2]).isOnline() == false) {
							player.sendMessage(ChatColor.RED + "Player is not online or does not exist!");
							return true;
						}
						reciever = Bukkit.getPlayer(args[2]);
					}
					reciever.getInventory().addItem(new CaptureEgg(amount).getEgg());
					player.sendMessage(ChatColor.GREEN + "Gave " + amount + " capture egg(s) to " + reciever.getName());
				}
			}
			else if(args[0].equalsIgnoreCase("reload")) {
				if(player.hasPermission("cc.admin")) {
					player.sendMessage(ChatColor.GREEN + "CreeperCatcher successfully reloaded!");
					config.reload();
				}
			}
		}
		return true;
	}	
}