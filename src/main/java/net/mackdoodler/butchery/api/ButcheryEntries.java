package net.mackdoodler.butchery.api;

import java.util.List;
import java.util.Map;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.HashMap;

import net.mackdoodler.butchery.common.items.ButcheryItems;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ButcheryEntries {
	
	public static Map<String, ButcherEntry> butcherEntries = new HashMap();
	
	/**
	 * adds an entry to the butcher list, if this entity is not in the list already a new entry will be created for it
	 * @param entityName
	 * @param tool
	 * @param output
	 * @param min
	 * @param max
	 */
	public static void addEntry(String entityName, ItemStack tool, ItemStack output, int min, int max) {
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
	
	/**
	 * removes an entry to the butcher list, throws an exception if the entity is not initialized with one entry
	 * @param entityName
	 * @param output
	 */
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
		ItemStack tempStack;
		NBTTagCompound tempTag;
		/* 'Passive' mobs */
		//vanilla
		addButcherDrops("minecraft:bat");
		addEntry("minecraft:bat",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 0, 1);
		
		//vanilla
		addButcherDrops("minecraft:chicken");
		addEntry("minecraft:chicken",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 0, 2);
		addEntry("minecraft:chicken",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.FEATHER), 4, 10);
		addEntry("minecraft:chicken",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.CHICKEN), 1, 1);
		addEntry("minecraft:chicken",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.EGG), -2, 1);
		
		//vanilla
		addButcherDrops("minecraft:cow");
		addEntry("minecraft:cow",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 0, 2);
		addEntry("minecraft:cow",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.LEATHER), 5, 8);
		addEntry("minecraft:cow",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BEEF), 10, 20);
		
		//vanilla
		addButcherDrops("minecraft:donkey");
		addEntry("minecraft:donkey",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 0, 2);
		addEntry("minecraft:donkey",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BEEF), 3, 10);
		addEntry("minecraft:donkey",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.LEATHER), 2, 6);
		
		//vanilla
		addButcherDrops("minecraft:horse");
		addEntry("minecraft:horse",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 0, 2);
		addEntry("minecraft:horse",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BEEF), 3, 8);
		addEntry("minecraft:horse",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.LEATHER), 2, 6);

		//vanilla
		addButcherDrops("minecraft:mooshroom");
		addEntry("minecraft:mooshroom",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Item.getItemFromBlock(Blocks.RED_MUSHROOM)), 1, 4);
		addEntry("minecraft:mooshroom",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Item.getItemFromBlock(Blocks.BROWN_MUSHROOM)), 1, 4);
		addEntry("minecraft:mooshroom",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 0, 2);
		addEntry("minecraft:mooshroom",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.LEATHER), 5, 8);
		addEntry("minecraft:mooshroom",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BEEF), 10, 20);

		//vanilla
		addButcherDrops("minecraft:mule");
		addEntry("minecraft:mule",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 0, 2);
		addEntry("minecraft:mule",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BEEF), 3, 10);
		addEntry("minecraft:mule",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.LEATHER), 2, 6);

		//vanilla
		addButcherDrops("minecraft:ocelot");
		//rabbit corpse?
		addEntry("minecraft:ocelot",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 0, 1);
		addEntry("minecraft:ocelot",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BEEF), 0, 2);

		//vanilla
		addButcherDrops("minecraft:parrot");
		addEntry("minecraft:parrot",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 0, 1);
		addEntry("minecraft:parrot",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.FEATHER), 4, 10);
		addEntry("minecraft:parrot",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.CHICKEN), 1, 1);

		//vanilla
		addButcherDrops("minecraft:pig");
		addEntry("minecraft:pig",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 0, 2);
		addEntry("minecraft:pig",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.PORKCHOP), 14, 28);

		//vanilla
		addButcherDrops("minecraft:rabbit");
		addEntry("minecraft:rabbit",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 0, 1);
		addEntry("minecraft:rabbit",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.RABBIT_HIDE), 2, 4);
		addEntry("minecraft:rabbit",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.RABBIT_FOOT), 0, 1);
		addEntry("minecraft:rabbit",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.RABBIT), 1, 1);
		
		//vanilla
		addButcherDrops("minecraft:sheep");
		addEntry("minecraft:sheep",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Item.getItemFromBlock(Blocks.WOOL)), 0, 2);
		addEntry("minecraft:sheep",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 0, 2);
		addEntry("minecraft:sheep",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.MUTTON), 7, 15);
		
		//vanilla
		addButcherDrops("minecraft:skeleton_horse");
		addEntry("minecraft:skeleton_horse",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 13, 26);
		
		//vanilla
		addButcherDrops("minecraft:zombie_horse");
		addEntry("minecraft:zombie_horse",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.ROTTEN_FLESH), 13, 26);
		addEntry("minecraft:zombie_horse",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 0, 3);
		
		//vanilla
		addButcherDrops("minecraft:squid");
		addEntry("minecraft:squid",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.DYE), 3, 7);
		
		//vanilla
		addButcherDrops("minecraft:llama");
		addEntry("minecraft:llama",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 0, 2);
		addEntry("minecraft:llama",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BEEF), 3, 10);
		addEntry("minecraft:llama",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.LEATHER), 2, 6);
		
		//vanilla
		addButcherDrops("minecraft:polar_bear");
		addEntry("minecraft:polar_bear",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 0, 2);
		addEntry("minecraft:polar_bear",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BEEF), 48, 64);
		addEntry("minecraft:polar_bear",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BEEF), 0, 32);
		addEntry("minecraft:polar_bear",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.FISH), 0, 2);
		
		//vanilla
		addButcherDrops("minecraft:wolf");
		addEntry("minecraft:wolf",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 0, 2);
		addEntry("minecraft:wolf",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BEEF), 2, 5);
		addEntry("minecraft:wolf",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 1, 2);
		
		//vanilla
		addButcherDrops("minecraft:cave_spider");
		addEntry("minecraft:cave_spider",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.STRING), 2, 4);
		addEntry("minecraft:cave_spider",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.SPIDER_EYE), 2, 5);
		tempTag = new NBTTagCompound();
		tempTag.setString("Potion", "strong_poison");
		tempStack = new ItemStack(Items.POTIONITEM);
		tempStack.setTagCompound(tempTag);
		addEntry("minecraft:cave_spider", new ItemStack(ButcheryItems.butcherCleaver), tempStack, 1, 2);
		
		
		//vanilla
		addButcherDrops("minecraft:enderman");
		addEntry("minecraft:enderman",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.ENDER_PEARL), 3, 6);
		
		//vanilla
		addButcherDrops("minecraft:spider");
		addEntry("minecraft:spider",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.STRING), 4, 9);
		addEntry("minecraft:spider",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.SPIDER_EYE), 0, 2);
		
		//vanilla
		addButcherDrops("minecraft:zombie_pigman");
		addEntry("minecraft:zombie_pigman",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 0, 1);
		addEntry("minecraft:zombie_pigman",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.ROTTEN_FLESH), 5, 10);
		addEntry("minecraft:zombie_pigman",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.PORKCHOP), 4, 8);
		addEntry("minecraft:zombie_pigman",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.GOLD_NUGGET), 3, 6);
		
		//vanilla
		addButcherDrops("minecraft:blaze");
		addEntry("minecraft:blaze",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BLAZE_ROD), 3, 6);
		addEntry("minecraft:blaze",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.FIRE_CHARGE), -1, 1);
		
		//vanilla
		addButcherDrops("minecraft:creeper");
		addEntry("minecraft:creeper",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 0, 2);
		addEntry("minecraft:creeper",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.SKULL, 1, 4), -2, 1);
		addEntry("minecraft:creeper",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.GUNPOWDER), 4, 9);

		//vanilla
		addButcherDrops("minecraft:guardian");
		addEntry("minecraft:guardian",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 0, 1);
		addEntry("minecraft:guardian",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.PRISMARINE_SHARD), 4, 8);
		addEntry("minecraft:guardian",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.PRISMARINE_CRYSTALS), 3, 6);
		addEntry("minecraft:guardian",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.FISH), 1, 1);
		
		//vanilla
		addButcherDrops("minecraft:elder_guardian");
		addEntry("minecraft:elder_guardian",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 0, 1);
		addEntry("minecraft:elder_guardian",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.PRISMARINE_SHARD), 6, 12);
		addEntry("minecraft:elder_guardian",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.PRISMARINE_CRYSTALS), 4, 8);
		addEntry("minecraft:elder_guardian",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.FISH), 1, 1);
		
		//vanilla
		addButcherDrops("minecraft:endermite");
		addEntry("minecraft:endermite",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.ENDER_PEARL), 1, 2);

		//vanilla
		addButcherDrops("minecraft:villager");
		addEntry("minecraft:villager",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 2, 4);
		addEntry("minecraft:villager",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.SKULL, 1, 0), -2, 1);
		addEntry("minecraft:villager",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BEEF), 2, 5);
		addEntry("minecraft:villager",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.EMERALD), -5, 1);
		//see if I can make these custom books, such as: diaries, minecraft bibles, stupid shit like that
		addEntry("minecraft:villager",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BOOK), -6, 1);
		
		//vanilla
		addButcherDrops("minecraft:evocation_illager");
		addEntry("minecraft:evocation_illager",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 2, 4);
		addEntry("minecraft:evocation_illager",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.SKULL, 1, 0), -2, 1);
		addEntry("minecraft:evocation_illager",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BEEF), 2, 5);
		addEntry("minecraft:evocation_illager",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.TOTEM_OF_UNDYING), 1, 1);
		addEntry("minecraft:evocation_illager",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.EMERALD), 5, 7);
		//see if I can make these custom books, such as: diaries, minecraft bibles, stupid shit like that
		addEntry("minecraft:evocation_illager",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BOOK), -6, 1);
		
		//vanilla
		addButcherDrops("minecraft:vex");
		addEntry("minecraft:vex",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.IRON_SWORD), -5, 1);
		
		//vanilla
		addButcherDrops("minecraft:vindication_illager");
		addEntry("minecraft:vindication_illager",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 2, 4);
		addEntry("minecraft:vindication_illager",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.SKULL, 1, 0), -2, 1);
		addEntry("minecraft:vindication_illager",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BEEF), 2, 5);
		addEntry("minecraft:vindication_illager",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.EMERALD), 3, 5);
		addEntry("minecraft:vindication_illager",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.IRON_AXE), 0, 1);
		//see if I can make these custom books, such as: diaries, minecraft bibles, stupid shit like that
		addEntry("minecraft:vindication_illager",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BOOK), -6, 1);
		
		//vanilla
		addButcherDrops("minecraft:witch");
		addEntry("minecraft:witch",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(ButcheryItems.lunarDust), 0, 2);
		addEntry("minecraft:witch",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 0, 2);
		addEntry("minecraft:witch",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.SKULL, 1, 0), -2, 1);
		addEntry("minecraft:witch",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BEEF), 2, 5);
		addEntry("minecraft:witch",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.GLASS_BOTTLE), -1, 1);
		addEntry("minecraft:witch",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.GLOWSTONE_DUST), -2, 2);
		addEntry("minecraft:witch",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.REDSTONE), -2, 2);
		addEntry("minecraft:witch",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.GUNPOWDER), -2, 2);
		addEntry("minecraft:witch",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.SPIDER_EYE), -2, 2);
		addEntry("minecraft:witch",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.FERMENTED_SPIDER_EYE), -2, 2);
		addEntry("minecraft:witch",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.SUGAR), -2, 2);
		addEntry("minecraft:witch",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BLAZE_POWDER), -2, 2);
		addEntry("minecraft:witch",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.MAGMA_CREAM), -2, 2);
		addEntry("minecraft:witch",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.NETHER_WART), -2, 1);
		addEntry("minecraft:witch",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.SPECKLED_MELON), -3, 1);
		addEntry("minecraft:witch",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.GOLDEN_CARROT), -3, 1);
		addEntry("minecraft:witch",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.RABBIT_FOOT), -3, 1);
		addEntry("minecraft:witch",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.STICK), 1, 1);
		addEntry("minecraft:witch",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.DRAGON_BREATH), -8, 1);
		addEntry("minecraft:witch",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.EMERALD), -3, 1);
		
		//vanilla
		addButcherDrops("minecraft:iron_golem");
		addEntry("minecraft:iron_golem",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Item.getItemFromBlock(Blocks.RED_FLOWER)), -1, 1);
		addEntry("minecraft:iron_golem",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Item.getItemFromBlock(Blocks.IRON_BLOCK)), -1, 1);
		addEntry("minecraft:iron_golem",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.IRON_INGOT), 7, 10);
		addEntry("minecraft:iron_golem",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.IRON_NUGGET), 5, 9);
		
		//vanilla
		addButcherDrops("minecraft:ghast");
		addEntry("minecraft:ghast",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), -1, 1);
		addEntry("minecraft:ghast",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.GHAST_TEAR), 4, 6);
		addEntry("minecraft:ghast",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.GUNPOWDER), 3, 5);
		
		//vanilla
		addButcherDrops("minecraft:husk");
		addEntry("minecraft:husk",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Item.getItemFromBlock(Blocks.SAND)), 2, 4);
		addEntry("minecraft:husk",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 0, 2);
		addEntry("minecraft:husk",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.ROTTEN_FLESH), 8, 19);
		addEntry("minecraft:husk",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.IRON_NUGGET), 0, 2);
		
		//vanilla
		addButcherDrops("minecraft:zombie");
		addEntry("minecraft:zombie",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 0, 2);
		addEntry("minecraft:zombie",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.SKULL, 1, 2), -2, 1);
		addEntry("minecraft:zombie",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.ROTTEN_FLESH), 12, 24);
		addEntry("minecraft:zombie",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.IRON_NUGGET), 0, 2);
		
		//vanilla
		addButcherDrops("minecraft:zombie_villager");
		addEntry("minecraft:zombie_villager",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 0, 2);
		addEntry("minecraft:zombie_villager",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.SKULL, 1, 2), -2, 1);
		addEntry("minecraft:zombie_villager",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.ROTTEN_FLESH), 12, 24);
		addEntry("minecraft:zombie_villager",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.IRON_NUGGET), 8, 16);
		
		//vanilla
		addButcherDrops("minecraft:magma_cube");
		addEntry("minecraft:magma_cube",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.MAGMA_CREAM), 3, 5);
		
		//vanilla
		addButcherDrops("minecraft:shulker");
		addEntry("minecraft:shulker",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.ENDER_PEARL), 0, 1);
		addEntry("minecraft:shulker",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.SHULKER_SHELL), 1, 1);
		
		//vanilla
		addButcherDrops("minecraft:silverfish");
		addEntry("minecraft:silverfish",new ItemStack(ButcheryItems.butcherCleaver),new ItemStack(Items.DIAMOND), -8, 1);
		
		//vanilla
		addButcherDrops("minecraft:skeleton");
		addEntry("minecraft:skeleton",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 10, 20);
		addEntry("minecraft:skeleton",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.SKULL, 1, 0), 1, 1);
		addEntry("minecraft:skeleton",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.ARROW), 5, 7);
		
		//vanilla
		addButcherDrops("minecraft:wither_skeleton");
		addEntry("minecraft:wither_skeleton",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 8, 14);
		addEntry("minecraft:wither_skeleton",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.SKULL, 1, 1), 1, 1);

		//vanilla
		addButcherDrops("minecraft:stray");
		addEntry("minecraft:stray",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 10, 20);
		addEntry("minecraft:stray",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.SKULL, 1, 0), 1, 1);
		addEntry("minecraft:stray",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.ARROW), 5, 7);
		
		//vanilla
		addButcherDrops("minecraft:slime");
		addEntry("minecraft:slime",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.SLIME_BALL), 5, 8);
		
		//vanilla
		addButcherDrops("minecraft:snow_golem");
		//I spent an embarrassing amount of time trying to find out how to specifically get carved pumpkins, not realizing that all pumpkins are carved
		addEntry("minecraft:snow_golem",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Item.getItemFromBlock(Blocks.PUMPKIN)), 2, 4);
		addEntry("minecraft:snow_golem",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.SNOWBALL), 8, 16);
		
		//vanilla
		addButcherDrops("minecraft:wither");
		addEntry("minecraft:wither",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Item.getItemFromBlock(Blocks.SOUL_SAND)), 0, 4);
		addEntry("minecraft:wither",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 0, 2);
		addEntry("minecraft:wither",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.SKULL, 1, 1), 0, 3);
		addEntry("minecraft:wither",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.NETHER_STAR), 1, 3);
		addEntry("minecraft:wither",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.EXPERIENCE_BOTTLE), 5, 10);
		
		//vanilla
		addButcherDrops("minecraft:enderdragonboss");
		addEntry("minecraft:enderdragonboss",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Item.getItemFromBlock(Blocks.DRAGON_EGG)), 1, 1);
		//addEntry("minecraft:enderdragonboss",new ItemStack(ButcheryItems.butcherCleaver),new ItemStack(Item.getItemFromBlock(Blocks.END_PORTAL_FRAME)), 0, 1);
		addEntry("minecraft:enderdragonboss",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.SKULL, 1, 5), 1, 1);
		addEntry("minecraft:enderdragonboss",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BONE), 10, 20);
		addEntry("minecraft:enderdragonboss",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.BEEF), 20, 52);
		addEntry("minecraft:enderdragonboss",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.ENDER_PEARL), 25, 50);
		addEntry("minecraft:enderdragonboss",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.DRAGON_BREATH), 10, 20);
		addEntry("minecraft:enderdragonboss",new ItemStack(ButcheryItems.butcherCleaver), new ItemStack(Items.EXPERIENCE_BOTTLE), 20, 40);
	}
}