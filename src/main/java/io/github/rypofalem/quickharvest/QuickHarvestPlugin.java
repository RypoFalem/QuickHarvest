package io.github.rypofalem.quickharvest;

import com.winthier.generic_events.GenericEvents;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
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
		//perform checks
		if(event.getHand() == EquipmentSlot.OFF_HAND) return;
		if(event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		if(!event.hasBlock()) return;
		Block block = event.getClickedBlock();
		Crop crop = Crop.getCropFromBlock(block.getType());
		if(crop == null) return;
		if(block.getType() != crop.getBlock()) return;
		if(!(block.getBlockData() instanceof Ageable)){
			//all crop blocks are ageable in craftbukkit but just in case...
			return;
		}
		Ageable ageable = (Ageable) block.getBlockData();
		if(ageable.getAge() != ageable.getMaximumAge()) return;
		Player player = event.getPlayer();
		ItemStack inHand = player.getEquipment().getItemInMainHand();
		if(crop.getSeed() != event.getPlayer().getEquipment().getItemInMainHand().getType()) return;

		BlockState state = block.getState();
		ageable.setAge(0);
		state.setBlockData(ageable);
//		boolean nCPEnabled = Bukkit.getPluginManager().isPluginEnabled("NoCheatPlus");
//		if(nCPEnabled) NCPExemptionManager.exemptPermanently(player, CheckType.BLOCKBREAK_NOSWING);
//		BlockBreakEvent blockBreakEvent = new BlockBreakEvent(block, player);
//		Bukkit.getServer().getPluginManager().callEvent(blockBreakEvent);
//		if(nCPEnabled) NCPExemptionManager.unexempt(player, CheckType.BLOCKBREAK_NOSWING);
//		if(blockBreakEvent.isCancelled()) return;
//
//		if(nCPEnabled) NCPExemptionManager.exemptPermanently(player, CheckType.BLOCKPLACE_NOSWING);
//		BlockPlaceEvent blockPlaceEvent = new BlockPlaceEvent(block, state, block.getRelative(BlockFace.DOWN), inHand, player, true, EquipmentSlot.HAND);
//		Bukkit.getServer().getPluginManager().callEvent(blockPlaceEvent);
//		if(nCPEnabled) NCPExemptionManager.unexempt(player, CheckType.BLOCKPLACE_NOSWING);
//		if(blockPlaceEvent.isCancelled()) return;

		//do the damn thing
		if(GenericEvents.playerCanBuild(player, block)){
			inHand.setAmount(inHand.getAmount() - 1);
			block.breakNaturally();
			block.setType(crop.getBlock());
			block.setBlockData(ageable);
		}

	}
}