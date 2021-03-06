package com.griefcraft;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

// import org.bukkit.Server;
import org.bukkit.event.Listener;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.event.entity.EntityListener;
// import org.bukkit.plugin.PluginDescriptionFile;
// import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.java.JavaPlugin;

import com.griefcraft.listeners.CreeperEntityListener;

public class FriendlyCreepers extends JavaPlugin {

	
	/**
	 * Properties
	 */
	private Properties properties;
	
	private EntityListener entityListener = new CreeperEntityListener(this);
	
		
	/**
	 * @return the Properties object
	 */
	public Properties getProperties() {
		return properties;
	}
	
	/**
	 * Log a string to the console
	 * 
	 * @param str
	 */
	public void log(String str) {
		System.out.println(String.format("[FriendlyCreepers]\t" + str));
	}
	
	@Override 
	public void onDisable() {
		log("Creepers are out of control yet again!");
	}

	@Override
	public void onEnable() {
		registerEvents();
		setup();

		log("Creepers are:\t" + (Boolean.parseBoolean(properties.getProperty("enable-creepers", "false")) ? "Enabled" : "Neutralized"));
		log("TNT is:\t\t" + (Boolean.parseBoolean(properties.getProperty("enable-tnt", "false")) ? "Enabled" : "Neutralized"));
	}

	/**
	 * Register a hook with default priority
	 * 
	 * @param hook
	 *            the hook to register
	 */
	private void registerEvent(Listener listener, Type eventType) {
		registerEvent(listener, eventType, Priority.Normal);
	}
	
	/**
	 * Register a hook
	 * 
	 * @param hook
	 *            the hook to register
	 * @priority the priority to use
	 */
	private void registerEvent(Listener listener, Type eventType, Priority priority) {
		log("-> " + eventType.toString());

		getServer().getPluginManager().registerEvent(eventType, listener, priority, this);
	}

	/**
	 * Register all of the events used by FriendlyCreepers
	 */
	private void registerEvents() {
		/* Entity events */
		registerEvent(entityListener, Type.ENTITY_EXPLODE);
	}

	/**
	 * Save the properties
	 */
	public void saveProperties() {
		try {
			File file = new File("plugins/FriendlyCreepers/config.ini");
			FileWriter fileWriter = new FileWriter(file);
			
			properties.store(fileWriter, "");
			
			fileWriter.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Make the config folder if it does not already exist and load configuration
	 */
	private void setup() {
		/*
		 * Creating the config folder if needed
		 */
		File file = new File("plugins/FriendlyCreepers");

		if(!file.exists()) {
			log("Creating directory plugins/FriendlyCreepers");
			file.mkdir();
		}
		
		/*
		 * The config file
		 */
		File propertiesFile = new File("plugins/FriendlyCreepers/config.ini");
		
		if(!propertiesFile.exists()) {
			log("Creating file plugins/FriendlyCreepers/config.ini");
			
			try {
				propertiesFile.createNewFile();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		/*
		 * Setup Properties
		 */
		properties = new Properties();
		
		/*
		 * Defaults
		 */
		properties.setProperty("enable-tnt", "false");
		properties.setProperty("enable-creepers", "false");
		
		/*
		 * Load the current config
		 */
		try {
			FileReader fileReader = new FileReader(propertiesFile);
			properties.load(fileReader);
			fileReader.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		/*
		 * Now save it
		 */
		saveProperties();
	}

}
