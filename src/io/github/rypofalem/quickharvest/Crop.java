package io.github.rypofalem.quickharvest;

import org.bukkit.Material;

public enum Crop {
	WHEAT(Material.CROPS, Material.SEEDS, (byte)7, (byte)0), 
	CARROT(Material.CARROT, Material.CARROT_ITEM, (byte)7, (byte)0), 
	POTATO(Material.POTATO, Material.POTATO_ITEM, (byte)7, (byte)0),
	NETHERWART(Material.NETHER_WARTS, Material.NETHER_STALK, (byte)3, (byte)0),
	BEAT(Material.BEETROOT_BLOCK, Material.BEETROOT_SEEDS, (byte)3, (byte)0);
	
	private final Material block; //the material of the fully grown version of crop
	private final Material seed;
	private final byte blockData;
	private final byte seedData;
	
	Crop(Material block, Material seed){
		this(block, seed, (byte)0, (byte)0);
	}
	
	Crop(Material block, Material seed, byte blockData, byte seedData){
		this.block = block;
		this.seed = seed;
		this.blockData = blockData;
		this.seedData = seedData;
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
	
	byte getBlockData(){
		return blockData;
	}
	
	byte getSeedData(){
		return seedData;
	}
}