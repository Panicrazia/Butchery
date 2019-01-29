package net.mackdoodler.primal.handlers;

import java.util.List;
import java.util.Map;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.HashMap;

import net.mackdoodler.primal.items.ButcheryItems;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class MobButcheryDropsList {
	
	public static Map<String, ButcherEntry> butcherEntries = new HashMap();
	
	public static void addEntry(String entityName, ItemStack output, ItemStack tool, int min, int max) {
		if(!output.isEmpty() && !tool.isEmpty()){
			ButcherEntry entry;
			if(butcherEntries.containsKey(entityName)){
				entry = butcherEntries.get(entityName);
			}
			else{
				entry = butcherEntries.put(entityName, new ButcherEntry());
			}
			entry.addItemEntry(tool, output, min, max);
		}
	}
	
	public static void removeEntry(String entityName, ItemStack output) {
		if(!output.isEmpty()){
			if(butcherEntries.containsKey(entityName)){
				butcherEntries.get(entityName).removeItemEntry(output);
			}
			else{
				throw new IllegalArgumentException(entityName+" is not registered as a valid butchery entry");
			}
		}
	}
	
	public static class ButcherEntry {
		private List<IndividualButcherEntry> entries = new ArrayList<IndividualButcherEntry>();
		
		public ButcherEntry() {
		
		}
		
		public ButcherEntry(IndividualButcherEntry[] entries) {
			for(IndividualButcherEntry entry : entries){
				this.entries.add(entry);
			}
		}
		
		public void addItemEntry(ItemStack itemChop, ItemStack item, int min, int max){
			entries.add(new IndividualButcherEntry(itemChop, item, min, max));
		}
		
		public void removeItemEntry(ItemStack item){
			while(entries.remove(new IndividualButcherEntry(item, item, 1, 1))){}
		}
		
		public List<IndividualButcherEntry> getEntries(){
			return entries;
		}
		
		/*
		public List<ItemStack> getItemStackSet(){
			List<ItemStack> listicles = new ArrayList<ItemStack>();
			
			for(int i=0;i<entries.size();i++){
				listicles.add(entries.get(i).getRandItemStack());
			}
			
			return listicles;
		}*/
	}
	
	public static class IndividualButcherEntry{
		private ItemStack chopItem;
		private ItemStack item;
		private int minimum;
		private int maximum;
		
		public IndividualButcherEntry(ItemStack chopItem, ItemStack item, int min, int max){
			this.chopItem=chopItem;
			this.item=item;
			this.minimum=min;
			this.maximum=max;
		}
		
		public ItemStack getTool(){
			return chopItem;
		}
		
		public ItemStack getItem(){
			return item;
		}
		
		public ItemStack getRandItemStack(){
			ItemStack stack = item;
			stack.setCount(getMinMaxValue());
			return stack;
		}
		
		private int getMinMaxValue(){
			int numero = (int)(Math.random()*(maximum-minimum+1))+minimum;
			System.out.println(numero);
			return (numero>0) ? numero : 0;
		}
		
		@Override
		public boolean equals(Object entry){
			if ((entry instanceof IndividualButcherEntry)){ 
				return this.item.equals(((IndividualButcherEntry) entry).getItem());
			}
			return false;
		}
	}
	

	public static void addButcherDrops(String identifier, IndividualButcherEntry... entries){
		butcherEntries.put(identifier, new ButcherEntry(entries));
	}
	
	public static void makeButcheryList(){
		/* 'Passive' mobs */
		//vanilla
		addButcherDrops("minecraft:bat",
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 0, 1));
		
		//vanilla
		addButcherDrops("minecraft:chicken",
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 0, 1),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.FEATHER), 4, 10),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.CHICKEN), 1, 1),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.EGG), -3, 1));
		
		//vanilla
		addButcherDrops("minecraft:cow",
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 0, 2),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.LEATHER), 2, 6),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BEEF), 10, 20));
		
		//vanilla
		addButcherDrops("minecraft:donkey",
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 0, 2),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BEEF), 3, 10),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.LEATHER), 2, 6));
		
		//vanilla
		addButcherDrops("minecraft:horse",
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 0, 2),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BEEF), 3, 10),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.LEATHER), 2, 6));

		//vanilla
		addButcherDrops("minecraft:mooshroom",
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Item.getItemFromBlock(Blocks.RED_MUSHROOM)), 0, 4),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Item.getItemFromBlock(Blocks.BROWN_MUSHROOM)), 0, 4),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 0, 1),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BEEF), 10, 20),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.LEATHER), 0, 4));

		//vanilla
		addButcherDrops("minecraft:mule",
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 0, 2),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BEEF), 3, 10),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.LEATHER), 2, 6));

		//vanilla
		addButcherDrops("minecraft:ocelot",
			//rabbit corpse?
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 0, 1),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BEEF), 0, 2));

		//vanilla
		addButcherDrops("minecraft:parrot",
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 0, 1),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.FEATHER), 4, 10),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.CHICKEN), 1, 1));

		//vanilla
		addButcherDrops("minecraft:pig",
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 0, 2),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.PORKCHOP), 14, 28));

		//vanilla
		addButcherDrops("minecraft:rabbit",
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 0, 1),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.RABBIT_HIDE), 2, 4),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.RABBIT_FOOT), 0, 1),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.RABBIT), 1, 1));
		
		//vanilla
		addButcherDrops("minecraft:sheep",
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Item.getItemFromBlock(Blocks.WOOL), 1, 14), 1, 2),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 0, 2),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.MUTTON), 7, 15));
		
		//vanilla
		addButcherDrops("minecraft:skeleton_horse",
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 13, 26));
		
		//vanilla
		addButcherDrops("minecraft:zombie_horse",
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.ROTTEN_FLESH), 13, 26),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 0, 3));
		
		//vanilla
		addButcherDrops("minecraft:squid",
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.DYE), 3, 7));
		
		//vanilla
		addButcherDrops("minecraft:llama",
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 0, 2),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BEEF), 3, 10),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.LEATHER), 2, 6));
		
		//vanilla
		addButcherDrops("minecraft:polar_bear",
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 0, 2),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BEEF), 48, 64),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BEEF), 0, 32),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.FISH), 0, 2));
		
		//vanilla
		addButcherDrops("minecraft:wolf",
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 0, 2),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BEEF), 2, 5),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 1, 2));
			
		//vanilla
		addButcherDrops("minecraft:cave_spider",
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.STRING), 2, 4),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.SPIDER_EYE), 2, 5),
			//TODO: poison potion
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.POTIONITEM), 1, 2));
		
		//vanilla
		addButcherDrops("minecraft:enderman",
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.ENDER_PEARL), 3, 6));
		
		//vanilla
		addButcherDrops("minecraft:spider",
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.STRING), 4, 9),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.SPIDER_EYE), 0, 2));
		
		//vanilla
		addButcherDrops("minecraft:zombie_pigman",
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 0, 1),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.ROTTEN_FLESH), 5, 10),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.PORKCHOP), 4, 8),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.GOLD_NUGGET), 3, 6));
		
		//vanilla
		addButcherDrops("minecraft:blaze",
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BLAZE_ROD), 3, 6),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.FIRE_CHARGE), -1, 1));
		
		//vanilla
		addButcherDrops("minecraft:creeper",
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 0, 2),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.SKULL, 1, 4), -2, 1),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.GUNPOWDER), 4, 9));

		//vanilla
		addButcherDrops("minecraft:guardian",
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 0, 1),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.PRISMARINE_SHARD), 4, 8),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.PRISMARINE_CRYSTALS), 3, 6),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.FISH), 1, 1));
		
		//vanilla
		addButcherDrops("minecraft:elder_guardian",
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 0, 1),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.PRISMARINE_SHARD), 6, 12),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.PRISMARINE_CRYSTALS), 4, 8),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.FISH), 1, 1));
			
		//vanilla
		addButcherDrops("minecraft:endermite",
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.ENDER_PEARL), 1, 2));

		//vanilla
		addButcherDrops("minecraft:villager",
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 2, 4),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.SKULL, 1, 0), -2, 1),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BEEF), 2, 5),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.EMERALD), -5, 1),
			//see if I can make these custom books, such as: diaries, minecraft bibles, stupid shit like that
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BOOK), -6, 1));
		
		//vanilla
		addButcherDrops("minecraft:evocation_illager",
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 2, 4),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.SKULL, 1, 0), -2, 1),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BEEF), 2, 5),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.TOTEM_OF_UNDYING), 1, 1),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.EMERALD), 5, 7),
			//see if I can make these custom books, such as: diaries, minecraft bibles, stupid shit like that
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BOOK), -6, 1));
		
		//vanilla
		addButcherDrops("minecraft:vex",
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.IRON_SWORD), -5, 1));
			
		//vanilla
		addButcherDrops("minecraft:vindication_illager",
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 2, 4),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.SKULL, 1, 0), -2, 1),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BEEF), 2, 5),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.EMERALD), 3, 5),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.IRON_AXE), 0, 1),
			//see if I can make these custom books, such as: diaries, minecraft bibles, stupid shit like that
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BOOK), -6, 1));
		
		//vanilla
		addButcherDrops("minecraft:witch",
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(ButcheryItems.lunarDust), 0, 2),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 0, 2),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.SKULL, 1, 0), -2, 1),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BEEF), 2, 5),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.GLASS_BOTTLE), -1, 1),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.GLOWSTONE_DUST), -2, 2),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.REDSTONE), -2, 2),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.GUNPOWDER), -2, 2),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.SPIDER_EYE), -2, 2),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.FERMENTED_SPIDER_EYE), -2, 2),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.SUGAR), -2, 2),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BLAZE_POWDER), -2, 2),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.MAGMA_CREAM), -2, 2),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.NETHER_WART), -2, 1),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.SPECKLED_MELON), -3, 1),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.GOLDEN_CARROT), -3, 1),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.RABBIT_FOOT), -3, 1),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.STICK), 1, 1),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.DRAGON_BREATH), -8, 1),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.EMERALD), -3, 1));
		
		//vanilla
		addButcherDrops("minecraft:iron_golem",
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Item.getItemFromBlock(Blocks.RED_FLOWER)), 1, 1),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Item.getItemFromBlock(Blocks.IRON_BLOCK)), -1, 1),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.IRON_INGOT), 7, 10),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.IRON_NUGGET), 5, 9));
			
		//vanilla
		addButcherDrops("minecraft:ghast",
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), -1, 1),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.GHAST_TEAR), 4, 6),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.GUNPOWDER), 3, 5));
			
		//vanilla
		addButcherDrops("minecraft:husk",
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Item.getItemFromBlock(Blocks.SAND)), 2, 4),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 0, 2),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.ROTTEN_FLESH), 8, 19),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.IRON_NUGGET), 0, 2));
		
		//vanilla
		addButcherDrops("minecraft:zombie",
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 0, 2),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.SKULL, 1, 2), -2, 1),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.ROTTEN_FLESH), 12, 24),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.IRON_NUGGET), 0, 2));
		
		//vanilla
		addButcherDrops("minecraft:zombie_villager",
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 0, 2),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.SKULL, 1, 2), -2, 1),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.ROTTEN_FLESH), 12, 24),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.IRON_NUGGET), 8, 16));
			
		//vanilla
		addButcherDrops("minecraft:magma_cube",
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.MAGMA_CREAM), 3, 5));
		
		//vanilla
		addButcherDrops("minecraft:shulker",
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.ENDER_PEARL), 0, 1),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.SHULKER_SHELL), 1, 1));
		
		//vanilla
		addButcherDrops("minecraft:silverfish",
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver),new ItemStack(Items.DIAMOND), -8, 1));
		
		//vanilla
		addButcherDrops("minecraft:skeleton",
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 10, 20),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.SKULL, 1, 0), 1, 1),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.ARROW), 5, 7));
		
		//vanilla
		addButcherDrops("minecraft:wither_skeleton",
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 8, 14),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.SKULL, 1, 1), 1, 1));

		//vanilla
		addButcherDrops("minecraft:stray",
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 10, 20),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.SKULL, 1, 0), 1, 1),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.ARROW), 5, 7));
		
		//vanilla
		addButcherDrops("minecraft:slime",
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.SLIME_BALL), 5, 8));
		
		//vanilla
		addButcherDrops("minecraft:snow_golem",
			//carved pumpkin instead of normal pumpkin | me, weeks later looking at my todos: all pumpkins are carved smh
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Item.getItemFromBlock(Blocks.PUMPKIN)), 2, 4),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.SNOWBALL), 8, 16));
		
		//vanilla
		addButcherDrops("minecraft:wither",
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Item.getItemFromBlock(Blocks.SOUL_SAND)), 0, 4),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 0, 2),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.SKULL, 1, 1), 0, 3),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.NETHER_STAR), 1, 3),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.EXPERIENCE_BOTTLE), 5, 10));
		
		//vanilla
		addButcherDrops("minecraft:enderdragonboss",
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Item.getItemFromBlock(Blocks.DRAGON_EGG)), 1, 1),
			//new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver),new ItemStack(Item.getItemFromBlock(Blocks.END_PORTAL_FRAME), 0, 1),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.SKULL, 1, 5), 1, 1),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 10, 20),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BEEF), 20, 52),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.ENDER_PEARL), 25, 50),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.DRAGON_BREATH), 10, 20),
			new IndividualButcherEntry(new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.EXPERIENCE_BOTTLE), 20, 40));
	}
}
