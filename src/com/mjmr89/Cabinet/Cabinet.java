package com.mjmr89.Cabinet;

import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.bukkit.Server;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.PluginDescriptionFile;
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