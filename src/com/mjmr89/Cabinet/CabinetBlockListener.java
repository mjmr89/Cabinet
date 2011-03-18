package com.mjmr89.Cabinet;

import net.minecraft.server.IInventory;
import org.bukkit.Material;

import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.inventory.CraftInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockRightClickEvent;
import org.bukkit.inventory.Inventory;

public class CabinetBlockListener extends BlockListener{
	Cabinet plugin;
	
	CabinetBlockListener(Cabinet plugin){
		this.plugin = plugin;
	}
	
	@Override
	public void onBlockRightClick(BlockRightClickEvent e){
		Block b = e.getBlock();
		Block bAbove = b.getWorld().getBlockAt(b.getX(),b.getY() + 1, b.getZ());
		
		if(b.getState() instanceof Chest &&
				solidBlock(bAbove)){
			Player p = e.getPlayer();
			Inventory inv = ((Chest)b.getState()).getInventory();
			
			CraftInventory cInventory = (CraftInventory)inv;
			CraftPlayer cPlayer = (CraftPlayer)p;
			cPlayer.getHandle().a((IInventory)cInventory.getInventory());
		}
		
		
	}
	
	boolean solidBlock(Block b){
		switch(b.getType()){
		case AIR:
		case TORCH:
		case LADDER:
		case STONE_BUTTON:
		case LEVER:
		case REDSTONE_TORCH_OFF:
		case REDSTONE_TORCH_ON:
		case PAINTING:
		case WALL_SIGN:
			
			return false;
		}
		return true;		
		
	}
	
	
}
