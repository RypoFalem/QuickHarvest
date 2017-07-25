package io.github.rypofalem.quickharvest;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class QuickHarvestPlugin extends JavaPlugin implements Listener{

	public void onEnable(){
		this.getServer().getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onRightClickBlock(PlayerInteractEvent event){
		if(event.getHand() == EquipmentSlot.OFF_HAND) return;
		if(event.getAction() != Action.RIGHT_CLICK_BLOCK)return;
		if(!event.hasBlock()) return;
		Block block = event.getClickedBlock();
		Crop crop = Crop.getCropFromBlock(block.getType());
		if(crop == null) return;
		if(block.getData() != crop.getBlockData()) return;
		if(crop.getSeed() != event.getPlayer().getEquipment().getItemInMainHand().getType()) return;

		
		Player player = event.getPlayer();
		BlockState state = block.getState();
		state.setRawData((byte)0);
		ItemStack inHand = new ItemStack(crop.getSeed(), 1);
		BlockPlaceEvent blockPlaceEvent = new BlockPlaceEvent(block, state, null, inHand, player, true, EquipmentSlot.HAND);
		Bukkit.getServer().getPluginManager().callEvent(blockPlaceEvent);
		if(blockPlaceEvent.isCancelled()) return;

		inHand = player.getEquipment().getItemInMainHand();
		inHand.setAmount(inHand.getAmount() - 1);
		block.breakNaturally(); //triggers onItemSpawn
		block.setType(crop.getBlock());
		block.setData((byte)0);
	}
}