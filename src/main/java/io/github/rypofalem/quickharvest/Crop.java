package io.github.rypofalem.quickharvest;

import org.bukkit.Material;

public enum Crop {
	WHEAT(Material.WHEAT, Material.WHEAT_SEEDS),
	CARROT(Material.CARROTS, Material.CARROT),
	POTATO(Material.POTATOES, Material.POTATO),
	NETHERWART(Material.NETHER_WART, Material.NETHER_WART),
	BEAT(Material.BEETROOTS, Material.BEETROOT_SEEDS);
	
	private final Material block; //the material of the fully grown version of crop
	private final Material seed;
	
	Crop(Material block, Material seed){
		this.block = block;
		this.seed = seed;
	}
	
	static Crop getCropFromBlock(Material block){
		for(Crop crop : Crop.values()){
			if(crop.getBlock() == block) return crop;
		}
		return null;
	}
	
	static Crop getCropFromSeed(Material seed){
		for(Crop crop : Crop.values()){
			if(crop.getSeed() == seed) return crop;
		}
		return null;
	}
	
	Material getBlock(){
		return block;
	}
	
	Material getSeed(){
		return seed;
	}
}