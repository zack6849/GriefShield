package me.zack6849.griefshield;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.plugin.Plugin;

public class BlockPlace implements Listener {
	String prefix = ChatColor.YELLOW + "[" + ChatColor.GOLD + "griefshield"
			+ ChatColor.YELLOW + "] " + ChatColor.GREEN;
	Plugin plugin;

	public BlockPlace(noGrief noGrief) {
		plugin = noGrief;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void BlockBreak(BlockPlaceEvent event) {
		Player p = event.getPlayer();
		Block b = event.getBlock();
		String location = b.getX() + "," + b.getY() + "," + b.getZ();
		Material b2 = event.getBlock().getType();
		String kickmsg = plugin.getConfig().getString("kick-message");
		Boolean kick = plugin.getConfig().getBoolean("kick");
		String bl = b.getTypeId() + ":" + b.getData();
		List<String> nope = plugin.getConfig().getStringList("blacklist");
		if (nope.contains(bl)) {
			if (!p.hasPermission("griefshield.blacklist.bypass")) {
				event.setCancelled(true);
				p.sendMessage(ChatColor.RED + "You arent allowed to place this block!");
				Bukkit.broadcast(prefix + "player " + p.getName() + " Tried to place block " + bl + " ( " + b2 + " )" + " at " + location + " in world : " + p.getWorld().getName(), "griefshield.alert");
				if (kick) {
					p.kickPlayer(kickmsg);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void LavaFill(PlayerBucketFillEvent event) {
		String message = plugin.getConfig().getString("disallow-message");
		Block bn = event.getBlockClicked();
		Player p = event.getPlayer();
		String location = bn.getX() + "," + bn.getY() + "," + bn.getZ();
		String kickmsg = plugin.getConfig().getString("kick-message");
		Boolean kick = plugin.getConfig().getBoolean("kick");
		if (bn.getType().equals(Material.LAVA)|| bn.getType().equals(Material.STATIONARY_LAVA)) {
			if (plugin.getConfig().getBoolean("lava-fill-blocked")) {
				if (!event.getPlayer().hasPermission("griefshield.bucket.lava.fill")) {
					event.setCancelled(true);
					Bukkit.broadcast(prefix + "player " + p.getName() + " Tried to get lava with a bucket at " + location + " in world : " + p.getWorld().getName(),"griefshield.alert");
					p.sendMessage(prefix + message);
					if (kick) {
						p.kickPlayer(kickmsg);
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void WaterBucketFill(PlayerBucketFillEvent event) {
		String message = plugin.getConfig().getString("disallow-message");
		Block bn = event.getBlockClicked();
		String location = bn.getX() + "," + bn.getY() + "," + bn.getZ();
		Player p = event.getPlayer();
		String kickmsg = plugin.getConfig().getString("kick-message");
		Boolean kick = plugin.getConfig().getBoolean("kick");
		if (bn.getType().equals(Material.WATER)|| bn.getType().equals(Material.STATIONARY_WATER)) {
			if (plugin.getConfig().getBoolean("water-fill-blocked")) {
				if (!event.getPlayer().hasPermission("griefshield.bucket.water.fill")) {
					event.setCancelled(true);
					Bukkit.broadcast(prefix + "player " + p.getName() + " Tried to get water with a bucket at " + location + " in world : " + p.getWorld().getName(), "griefshield.alert");
					p.sendMessage(prefix + message);
					if (kick) {
						p.kickPlayer(kickmsg);
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void BucketEmptyLava(PlayerBucketEmptyEvent event) {
		Block b = event.getBlockClicked();
		String p = event.getPlayer().getName();
		String kickmsg = plugin.getConfig().getString("kick-message");
		Boolean kick = plugin.getConfig().getBoolean("kick");
		String location = b.getX() + "," + b.getY() + "," + b.getZ();
		if (event.getBucket().equals(Material.LAVA_BUCKET)) {
			if (plugin.getConfig().getBoolean("lava-blocked")) {
				if (!event.getPlayer().hasPermission("griefshield.bucket.lava.use")) {
					event.setCancelled(true);
					Bukkit.broadcast(prefix + "player " + p + " Tried to use a bucket of lava at " + location + " in world " + event.getPlayer().getWorld().getName(),"griefshield.alert");
					if (kick) {
						event.getPlayer().kickPlayer(kickmsg);
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void BucketEmptyWater(PlayerBucketEmptyEvent event) {
		Block b = event.getBlockClicked();
		String p = event.getPlayer().getName();
		String kickmsg = plugin.getConfig().getString("kick-message");
		Boolean kick = plugin.getConfig().getBoolean("kick");
		String location = b.getX() + "," + b.getY() + "," + b.getZ();
		if (event.getBucket().equals(Material.WATER_BUCKET)) {
			if (plugin.getConfig().getBoolean("water-blocked")) {
				if (!event.getPlayer().hasPermission("griefshield.bucket.water.use")) {
					event.setCancelled(true);
					Bukkit.broadcast(prefix + "player " + p + " Tried to use a bucket of water at " + location + " in world " + event.getPlayer().getWorld().getName(),"griefshield.alert");
					if (kick) {
						event.getPlayer().kickPlayer(kickmsg);
					}
				}
			}
		}
	}
}