package com.mjmr89.Cabinet;

import java.util.ArrayList;

import net.minecraft.server.IInventory;
import net.minecraft.server.InventoryLargeChest;

import org.bukkit.Material;
import org.bukkit.World;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.inventory.CraftInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockRightClickEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.Inventory;

public class CabinetBlockListener extends BlockListener {
	Cabinet plugin;

	CabinetBlockListener(Cabinet plugin) {
		this.plugin = plugin;
	}

	@Override
	public void onBlockRightClick(BlockRightClickEvent e) {
		Block b = e.getBlock();
		Player p = e.getPlayer();
		BlockFace bf = e.getDirection();
		Block airBlock;

		// if its a chest and covered at least partially
		if (b.getState() instanceof Chest && covered(b)) {
			// if its a double
			if(adjacentToChest(b)){
				Block b2 = getAdjacentChestBlock(b);
				openDoubleChest(b,b2,p);
				
			}else{
				openSingleChest(b,p);
			}
			
		}
		
		//checks to place chest next to a double chest
		airBlock = b.getFace(bf);
		
		if(e.getItemInHand().getType() == Material.CHEST){
			if(isNextToDoubleChest(airBlock)){
				plugin.getServer().broadcastMessage("U CAN HAS PLACE CHEST HERE!");
				airBlock.setType(Material.CHEST);
			}
		}
		
	}
	
	boolean isNextToDoubleChest(Block b){
		ArrayList<Block> list = getAdjacentChestBlocks(b);
		ArrayList<Block> list2;
		
		plugin.getServer().broadcastMessage("list size: " + list.size());
		
		if(list.size() > 0){
			for(Block block : list){
				list2 = getAdjacentChestBlocks(block);
				plugin.getServer().broadcastMessage("list2 size: " + list2.size());

				if(list2.size()>0){
					plugin.getServer().broadcastMessage("list2 > 0");

					return true;
				}
			}
		}
		
		
		return false;
	}
	
	ArrayList<Block> getAdjacentChestBlocks(Block b){
		ArrayList<Block> list = new ArrayList<Block>();
		World w = b.getWorld();
		int x = b.getX();
		int y = b.getY();
		int z = b.getZ();
		Block[] bArr = { w.getBlockAt(x + 1, y, z), w.getBlockAt(x - 1, y, z),
				w.getBlockAt(x, y, z + 1), w.getBlockAt(x, y, z - 1) };
		for (int i = 0; i < 4; i++) {
			if (bArr[i].getState() instanceof Chest) {
				list.add(bArr[i]);
			}
		}
		
		return list;
	}

	void openSingleChest(Block b, Player p) {
		Inventory inv = ((Chest) b.getState()).getInventory();

		CraftInventory cInventory = (CraftInventory) inv;
		CraftPlayer cPlayer = (CraftPlayer) p;
		cPlayer.getHandle().a((IInventory) cInventory.getInventory());
	}
	
	Block[] getDoubleChestOrder(Block b1, Block b2){
		
		if(b1.getX()<b2.getX() || b1.getZ() < b2.getZ()){
			return new Block[]{b1,b2};
		}
		return new Block[]{b2,b1};
		
		
	}

	void openDoubleChest(Block b1, Block b2, Player p) {
		Block[] blocks = getDoubleChestOrder(b1,b2);
		Chest c1 = (Chest)blocks[0].getState();
		Chest c2 = (Chest)blocks[1].getState();
		CraftPlayer cPlayer = (CraftPlayer) p;

		CraftInventory cInventory1 = (CraftInventory) c1.getInventory();
		CraftInventory cInventory2 = (CraftInventory) c2.getInventory();
		IInventory IChest = (IInventory) new InventoryLargeChest("Large chest",
				cInventory1.getInventory(), cInventory2.getInventory());
		cPlayer.getHandle().a(IChest);
	}

	boolean covered(Block b) {
		if (adjacentToChest(b)) {
			Block adjBlock = getAdjacentChestBlock(b);
			if (solidBlock(getBlockAbove(adjBlock)) || solidBlock(getBlockAbove(b))) {
				return true;
			}
		} 
		else {
			if (solidBlock(getBlockAbove(b))) {
				return true;
			}
		}
		return false;
	}

	Block getBlockAbove(Block b) {
		return b.getWorld().getBlockAt(b.getX(), b.getY() + 1, b.getZ());
	}

	boolean adjacentToChest(Block b) {
		if (getAdjacentChestBlock(b) == null) {
			return false;
		}
		return true;
	}

	Block getAdjacentChestBlock(Block b) {
		World w = b.getWorld();
		int x = b.getX();
		int y = b.getY();
		int z = b.getZ();
		Block[] bArr = { w.getBlockAt(x + 1, y, z), w.getBlockAt(x - 1, y, z),
				w.getBlockAt(x, y, z + 1), w.getBlockAt(x, y, z - 1) };
		for (int i = 0; i < 4; i++) {
			if (bArr[i].getState() instanceof Chest) {
				return bArr[i];
			}
		}
		return null;
	}

	boolean isPartiallyCovered(Block b) {
		int x = b.getX();
		int y = b.getY();
		int z = b.getZ();
		World w = b.getWorld();

		Block[] bAboves = { w.getBlockAt(x + 1, y + 1, z),
				w.getBlockAt(x - 1, y + 1, z), w.getBlockAt(x, y + 1, z + 1),
				w.getBlockAt(x, y + 1, z - 1) };
		for (int i = 0; i < 4; i++) {
			if (solidBlock(bAboves[i])
					&& w.getBlockAt(bAboves[i].getX(), bAboves[i].getY() - 1,
							bAboves[i].getZ()).getState() instanceof Chest) {
				return true;
			}
		}
		return false;
	}

	boolean solidBlock(Block b) {
		switch (b.getType()) {
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
