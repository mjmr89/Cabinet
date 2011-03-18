package com.mjmr89.Cabinet;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Cabinet extends JavaPlugin {

	// The plugin name.
	static final String pluginName = "Cabinet";

	private static final Pattern ratePattern = Pattern
			.compile("\\s*(\\d+)\\s*:\\s*(\\d+)\\s*");

	// Stuff used to interact with the server.
	final Logger log = Logger.getLogger("Minecraft");
	final Server server = this.getServer();

	// Objects used by the plugin.
	
	private final CabinetBlockListener blockListener = new CabinetBlockListener(
			this);
	
	
	public void onDisable() {
	}

	public void onEnable() {

		PluginManager pm = this.getServer().getPluginManager();

		pm.registerEvent(Type.BLOCK_RIGHTCLICKED, blockListener,
				Priority.Normal, this);

		PluginDescriptionFile pdfFile = this.getDescription();
		System.out.println(pdfFile.getName() + " version "
				+ pdfFile.getVersion() + " is enabled!");

	}

	@Override
	public void onLoad() {
		// TODO Auto-generated method stub
		
	}
}