package net.mackdoodler.primal.handlers;

import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.HashMap;

import net.mackdoodler.primal.items.ButcheryItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class MobButcheryDropsList {
	
	public static Map allButcherEntries = new HashMap();
	
	public static class ButcherEntry {
		private List<IndividualButcherEntry> entries = new ArrayList<IndividualButcherEntry>();
		private int numEntries;
		
		public ButcherEntry(IndividualButcherEntry[] entries) {
			for(IndividualButcherEntry entry : entries){
				this.entries.add(entry);
				numEntries++;
			}
		}
		
		public void addItemEntry(Item itemChop, Item item, int min, int max){
			addItemEntry(itemChop, item, min, max, 0, null);
		}
		
		public void addItemEntry(Item itemChop, Item item, int min, int max, int meta, String specificity){
			entries.add(new IndividualButcherEntry(itemChop, item, min, max, meta, specificity));
			numEntries++;
		}
		
		/*
		 * returns an ArrayList of ItemStacks that have all been generated from its internal entries
		 */
		public List<ItemStack> getItemstackSet(){
			
			List<ItemStack> listicles = new ArrayList<ItemStack>();
			
			for(int i=0;i<numEntries;i++){
				listicles.add(entries.get(i).getRandItemStack());
			}
			
			return listicles;
		}
		
		public int getNumberOfItems(){
			return numEntries;
		}
	}
	
	public static class IndividualButcherEntry{
		private Item chopItem;
		private Item item;
		private int minimum;
		private int maximum;
		private int meta = 0;
		private String specificity = null;
		
		/**
		 * 
		 * @param chopItem	the item needed to chop the corpse with to get this item
		 * @param item	the item given by this entry
		 * @param min	the minimum amount of items this stack can be
		 * @param max	the maximum amount of items this stack can be
		 */
		public IndividualButcherEntry(Item chopItem, Item item, int min, int max){
			this.chopItem=chopItem;
			this.item=item;
			this.minimum=min;
			this.maximum=max;
		}
		
		/**
		 * 
		 * @param chopItem	the item needed to chop the corpse with to get this item
		 * @param item	the item given by this entry
		 * @param min	the minimum amount of items this stack can be
		 * @param max	the maximum amount of items this stack can be
		 * @param specificity	something a little extra if your item needs it
		 */
		public IndividualButcherEntry(Item chopItem, Item item, int min, int max, int meta, String specificity){
			this.chopItem=chopItem;
			this.item=item;
			this.minimum=min;
			this.maximum=max;
			this.meta=meta;
			this.specificity=specificity;
		}
		
		public ItemStack getRandItemStack(){
			ItemStack stack = new ItemStack(item,getMinMaxValue(),meta);
			
			if(specificity!=null){
				stack.setTagCompound(new NBTTagCompound());
				stack.getTagCompound().setString("partType", specificity);
			}

			return stack;
		}
		
		private int getMinMaxValue(){
			//TODO: this method needs work, currently only works for min values that are positive
			int numero = minimum+(int)(Math.random()*(maximum-minimum+1));
			if(numero>0){
				return numero;
			}
			return 0;
		}
	}
	
	/*
	 * meathook that can function as a grappling hook when you fire it and hits <something>, a pudge hook when it hits an enemy, 
	 * 			and places a meathook hanging block when shift clicking
	 * 
	 * meathook hanging blocks have a meathook hanging entity dangling attatched by a chain(think lead/dragon crystals), the meathook hanger can be powered/unpowered (or maybe use some winch block or something i dunno) to raise and lower the entity that is connected to it
	 * 
	 * ALSO minecraft.entity.MultiPartEntity EXISTS LOOK AT THE (it says dragon in the class so im assuming dragon) DRAGON CODE TO SEE HOW ITS IMPLIMENTED
	 * ^NOPE DRAGON CODE IS BASICALLY FUCKING WORTHLESS, THANKS MOJANG THERES NOT EVEN A LANDING ANIMATION WTF
	 */

	public static void addButcherDrops(String identifier, IndividualButcherEntry... entries){
		allButcherEntries.put(identifier, new ButcherEntry(entries));
	}
	
	public static void makeButcheryList(){
		/* 'Passive' mobs */
		//vanilla
		addButcherDrops("minecraft:bat",
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.BONE, -1, 1));
		
		//vanilla
		addButcherDrops("minecraft:chicken",
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.BONE, -1, 1),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.FEATHER, 4, 10),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.CHICKEN, 1, 1),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.EGG, -4, 1),
			//necromancy
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, ButcheryItems.bodyPartMisc, 1, 3, 0, "Guts"));
		
		//vanilla
		addButcherDrops("minecraft:cow",
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.BONE, 0, 2),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.LEATHER, 2, 6),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.BEEF, 10, 20),
			//necromancy
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, ButcheryItems.bodyPartBrain, -1, 1, 0, "Animal Brain"),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, ButcheryItems.bodyPartEye, 0, 2, 0, "Normal Eye"),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, ButcheryItems.bodyPartMisc, 0, 3, 0, "Guts"),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, ButcheryItems.bodyPartGland, 0, 4, 0, "Milk Gland"),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, ButcheryItems.bodyPartMisc, 0, 2, 0, "Horn"),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, ButcheryItems.bodyPartFoot, 0, 4, 0, "Hoof"));
		
		//vanilla
		addButcherDrops("minecraft:donkey",
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.BONE, 0, 2),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.BEEF, 3, 10),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.LEATHER, 2, 6));
		
		//vanilla
		addButcherDrops("minecraft:horse",
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.BONE, 0, 2),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.BEEF, 3, 10),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.LEATHER, 2, 6));

		//vanilla
		addButcherDrops("minecraft:mooshroom",
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Item.getItemFromBlock(Blocks.RED_MUSHROOM), 0, 4),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Item.getItemFromBlock(Blocks.BROWN_MUSHROOM), 0, 4),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.BONE, 0, 1),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.BEEF, 10, 20),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.LEATHER, 0, 4));

		//vanilla
		addButcherDrops("minecraft:mule",
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.BONE, 0, 2),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.BEEF, 3, 10),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.LEATHER, 2, 6));

		//vanilla
		addButcherDrops("minecraft:ocelot",
			//rabbit corpse?
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.BONE, -1, 1),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.BEEF, 0, 2));

		//vanilla
		addButcherDrops("minecraft:parrot",
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.BONE, -1, 1),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.FEATHER, 4, 10),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.CHICKEN, 1, 1));

		//vanilla
		addButcherDrops("minecraft:pig",
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.BONE, 0, 2),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.PORKCHOP, 14, 28));

		//vanilla
		addButcherDrops("minecraft:rabbit",
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.BONE, -1, 1),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.RABBIT_HIDE, 2, 4),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.RABBIT_FOOT, 0, 1),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.RABBIT, 1, 1));
		
		//vanilla
		addButcherDrops("minecraft:sheep",
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Item.getItemFromBlock(Blocks.WOOL), 1, 2),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.BONE, 0, 2),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.MUTTON, 7, 15));
		
		//vanilla
		addButcherDrops("minecraft:skeleton_horse",
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.BONE, 13, 26));
		
		//vanilla
		addButcherDrops("minecraft:zombie_horse",
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.ROTTEN_FLESH, 13, 26),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.BONE, 0, 3));
		
		//vanilla
		addButcherDrops("minecraft:squid",
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.DYE, 3, 7, 0, null));
		
		//vanilla
		addButcherDrops("minecraft:llama",
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.BONE, 0, 2),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.BEEF, 3, 10),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.LEATHER, 2, 6));
		
		//vanilla
		addButcherDrops("minecraft:polar_bear",
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.BONE, 0, 2),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.BEEF, 48, 64),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.BEEF, 0, 32),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.FISH, 0, 2));
		
		//vanilla
		addButcherDrops("minecraft:wolf",
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.BONE, 0, 2),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.BEEF, 2, 5),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.BONE, 1, 2));
			
		//vanilla
		addButcherDrops("minecraft:cave_spider",
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.STRING, 2, 4),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.SPIDER_EYE, 2, 5),
			//TODO: poison potion
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.POTIONITEM, 1, 2));
		
		//vanilla
		addButcherDrops("minecraft:enderman",
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.ENDER_PEARL, 3, 6));
		
		//vanilla
		addButcherDrops("minecraft:spider",
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.STRING, 4, 9),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.SPIDER_EYE, 0, 2));
		
		//vanilla
		addButcherDrops("minecraft:zombie_pigman",
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.BONE, 0, 1),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.ROTTEN_FLESH, 5, 10),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.PORKCHOP, 4, 8),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.GOLD_NUGGET, 3, 6));
		
		//vanilla
		addButcherDrops("minecraft:blaze",
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.BLAZE_ROD, 3, 6),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.FIRE_CHARGE, -1, 1));
		
		//vanilla
		addButcherDrops("minecraft:creeper",
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.BONE, 0, 2),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.GUNPOWDER, 4, 9));

		//vanilla
		addButcherDrops("minecraft:guardian",
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.BONE, 0, 1),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.PRISMARINE_SHARD, 4, 8),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.PRISMARINE_CRYSTALS, 3, 6),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.FISH, 1, 1));
		
		//vanilla
		addButcherDrops("minecraft:elder_guardian",
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.BONE, 0, 1),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.PRISMARINE_SHARD, 6, 12),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.PRISMARINE_CRYSTALS, 4, 8),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.FISH, 1, 1));
			
		//vanilla
		addButcherDrops("minecraft:endermite",
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.ENDER_PEARL, 1, 2));

		//vanilla
		addButcherDrops("minecraft:villager",
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.BONE, 2, 4),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.BEEF, 2, 5),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.EMERALD, -5, 1),
			//see if I can make these custom books, such as: diaries, minecraft bibles, stupid shit like that
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.BOOK, -6, 1));
		
		//vanilla
		addButcherDrops("minecraft:evocation_illager",
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.BONE, 2, 4),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.BEEF, 2, 5),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.TOTEM_OF_UNDYING, 1, 1),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.EMERALD, 5, 7),
			//see if I can make these custom books, such as: diaries, minecraft bibles, stupid shit like that
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.BOOK, -6, 1));
		
		//vanilla
		addButcherDrops("minecraft:vex",
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.IRON_SWORD, -5, 1));
			
		//vanilla
		addButcherDrops("minecraft:vindication_illager",
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.BONE, 2, 4),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.BEEF, 2, 5),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.EMERALD, 3, 5),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.IRON_AXE, 0, 1),
			//see if I can make these custom books, such as: diaries, minecraft bibles, stupid shit like that
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.BOOK, -6, 1));
		
		//vanilla
		addButcherDrops("minecraft:witch",
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, ButcheryItems.lunarDust, 0, 2),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.BONE, 0, 2),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.BEEF, 2, 5),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.GLASS_BOTTLE, -1, 1),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.GLOWSTONE_DUST, -2, 2),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.REDSTONE, -2, 2),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.GUNPOWDER, -2, 2),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.SPIDER_EYE, -2, 2),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.FERMENTED_SPIDER_EYE, -2, 2),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.SUGAR, -2, 2),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.BLAZE_POWDER, -2, 2),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.MAGMA_CREAM, -2, 2),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.NETHER_WART, -2, 1),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.SPECKLED_MELON, -3, 1),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.GOLDEN_CARROT, -3, 1),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.RABBIT_FOOT, -3, 1),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.STICK, 1, 1),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.DRAGON_BREATH, -8, 1),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.EMERALD, -3, 1));
		
		//vanilla
		addButcherDrops("minecraft:iron_golem",
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Item.getItemFromBlock(Blocks.RED_FLOWER), 1, 1),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Item.getItemFromBlock(Blocks.IRON_BLOCK), -1, 1),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.IRON_INGOT, 7, 10),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.IRON_NUGGET, 5, 9));
			
		//vanilla
		addButcherDrops("minecraft:ghast",
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.BONE, -1, 1),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.GHAST_TEAR, 4, 6),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.GUNPOWDER, 3, 5));
			
		//vanilla
		addButcherDrops("minecraft:husk",
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Item.getItemFromBlock(Blocks.SAND), 2, 4),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.BONE, 0, 2),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.ROTTEN_FLESH, 8, 19),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.IRON_NUGGET, 0, 2));
		
		//vanilla
		addButcherDrops("minecraft:zombie",
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.BONE, 0, 2),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.ROTTEN_FLESH, 12, 24),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.IRON_NUGGET, 0, 2));
		
		//vanilla
		addButcherDrops("minecraft:zombie_villager",
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.BONE, 0, 2),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.ROTTEN_FLESH, 12, 24),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.IRON_NUGGET, 8, 16));
			
		//vanilla
		addButcherDrops("minecraft:magma_cube",
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.MAGMA_CREAM, 3, 5));
		
		//vanilla
		addButcherDrops("minecraft:shulker",
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.ENDER_PEARL, 0, 1),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.SHULKER_SHELL, 1, 1));
		
		//vanilla
		addButcherDrops("minecraft:silverfish",
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.DIAMOND, -8, 1));
		
		//vanilla
		addButcherDrops("minecraft:skeleton",
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.BONE, 10, 20),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.SKULL, 1, 1, 0, null),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.ARROW, 5, 7));
		
		//vanilla
		addButcherDrops("minecraft:wither_skeleton",
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.BONE, 8, 14),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.SKULL, 1, 1, 1, null));

		//vanilla
		addButcherDrops("minecraft:stray",
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.BONE, 10, 20),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.SKULL, 1, 1, 0, null),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.ARROW, 5, 7));
		
		//vanilla
		addButcherDrops("minecraft:slime",
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.SLIME_BALL, 5, 8));
		
		//vanilla
		addButcherDrops("minecraft:snow_golem",
			//carved pumpkin instead of normal pumpkin | me, weeks later looking at my todos: all pumpkins are carved smh
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Item.getItemFromBlock(Blocks.PUMPKIN), 2, 4),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.SNOWBALL, 8, 16));
		
		//vanilla
		addButcherDrops("minecraft:wither",
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Item.getItemFromBlock(Blocks.SOUL_SAND), 2, 4),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.BONE, 0, 2),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.SKULL, 1, 3, 1, null),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.NETHER_STAR, 1, 3),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.EXPERIENCE_BOTTLE, 5, 10));
		
		//vanilla
		addButcherDrops("minecraft:enderdragonboss",
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Item.getItemFromBlock(Blocks.DRAGON_EGG), 1, 1),
			//new IndividualButcherEntry(ButcheryItems.butcherCleaver, Item.getItemFromBlock(Blocks.END_PORTAL_FRAME), 0, 1),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.SKULL, 1, 1, 5, null),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.BONE, 10, 20),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.BEEF, 20, 52),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.ENDER_PEARL, 25, 50),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.DRAGON_BREATH, 10, 20),
			new IndividualButcherEntry(ButcheryItems.butcherCleaver, Items.EXPERIENCE_BOTTLE, 20, 40));
	}
}
