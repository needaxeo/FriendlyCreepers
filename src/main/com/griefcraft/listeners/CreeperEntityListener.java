package com.griefcraft.listeners;

import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityListener;

import com.griefcraft.FriendlyCreepers;

public class CreeperEntityListener extends EntityListener {

	/**
	 * The parent object
	 */
	private FriendlyCreepers parent;

	public CreeperEntityListener(FriendlyCreepers parent) {
		this.parent = parent;
	}

	@Override
	public void onEntityExplode(EntityExplodeEvent event) {

		if (event.isCancelled()) {
			return;
		}

		Entity entity = event.getEntity();
		boolean shouldExplode = true;

		if (entity instanceof TNTPrimed) {
			shouldExplode = Boolean.parseBoolean(parent.getProperties().getProperty("enable-tnt", "false"));
		} else if (entity instanceof Creeper) {
			shouldExplode = Boolean.parseBoolean(parent.getProperties().getProperty("enable-creepers", "false"));
		}

		if (!shouldExplode) {
			event.setCancelled(true);
		}
	}
}
