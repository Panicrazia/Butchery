package net.mackdoodler.primal.handlers;

import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.HashMap;

import net.mackdoodler.primal.items.PrimalItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class MobHandler {
	//TODO: this whole thing should probably be merged with corpsehelper since they are for the same item/block
	
	public static Map allButcherEntries = new HashMap();
	
	public static class ButcherEntry {
		private List<IndividualButcherEntryEntries> entries = new ArrayList<IndividualButcherEntryEntries>();
		private int numEntries;
		
		public ButcherEntry addItemEntry(Item item, int min, int max){
			return addItemEntry(item, min, max, null);
		}
		public ButcherEntry addItemEntry(Item item, int min, int max, String specificity){
			entries.add(new IndividualButcherEntryEntries(item,min,max,specificity));
			numEntries++;
			return this;
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
	
	public static class IndividualButcherEntryEntries{
		private Item item;
		private int minimum;
		private int maximum;
		private String specificity;
		
		public IndividualButcherEntryEntries(Item item, int min, int max, String specificity){
			this.item=item;
			this.minimum=min;
			this.maximum=max;
			this.specificity=specificity;
		}
		
		public ItemStack getRandItemStack(){
			ItemStack stack;
			
			if(specificity!=null){
				stack = new ItemStack(item,getMinMaxValue());

				stack.setTagCompound(new NBTTagCompound());
				stack.getTagCompound().setString("partType", specificity);
				
			}
			else{
				stack = new ItemStack(item,getMinMaxValue());
			}

			return stack;
		}
		
		private int getMinMaxValue(){
			int numero =  minimum+(int)(Math.random()*(maximum-minimum+1));
			if(numero>0){
				return numero;
			}
			return 0;
		}
	}
	/*
	 * 
	 * 
	 * LOOK UP getInputStream IT MIGHT BE WHAT YOU WANT FOR THE CORPSE NAMES (also look at openblocks trophies again mbs theyve got something)
	 * NOPE BITCH ITS I18n.format() FUCKING LOVE THAT ABBREVIATION BTW
	 * vanilla death messages are made from EntityDamageSource in minecraft.util
	 * 
	 * meathook that can function as a grappling hook when you fire it and hits <something>, a pudge hook when it hits an enemy, 
	 * 			and places a meathook hanging block when shift clicking
	 * 
	 * meathook hanging blocks have a meathook hanging entity dangling attatched by a chain(think lead/dragon crystals), the meathook hanger can be powered/unpowered (or maybe use some winch block or something i dunno) to raise and lower the entity that is connected to it
	 * 
	 * someway to deactivate a mob to take off parts and whatnot
	 * 
	 * ALSO minecraft.entity.MultiPartEntity EXISTS LOOK AT THE (it says dragon in the class so im assuming dragon) DRAGON CODE TO SEE HOW ITS IMPLIMENTED
	 * ^NOPE DRAGON CODE IS BASICALLY FUCKING WORTHLESS, THANKS MOJANG THERES NOT EVEN A LANDING ANIMATION WTF
	 * 
	 * riding code examples are mainly in entity living base, entity and abstract skeleton (and probs others but those are the ones i know right now)
	 * 
	 * 
	 */
	
	public static ButcherEntry addButcherDrops(String identifier){
		allButcherEntries.put(identifier, new ButcherEntry());
		return (ButcherEntry) allButcherEntries.get(identifier);
	}
	/*
	 * change the below format to
	 * X.addItemEntry(leather, 1, 1);
	 * X.addItemEntry(bone, 1, 1);
	 * etc..
	 * 
	 */
	public static void makeButcheryList(){
		/* 'Passive' mobs */
		//vanilla
		addButcherDrops("minecraft:bat")
		.addItemEntry(Items.BONE, -1, 1);
		
		//vanilla
		addButcherDrops("minecraft:chicken")
		.addItemEntry(Items.BONE, -1, 1)
		.addItemEntry(Items.FEATHER, 4, 10)
		.addItemEntry(Items.CHICKEN, 1, 1)
		.addItemEntry(Items.EGG, -4, 1)
		//necromancy
		.addItemEntry(PrimalItems.bodyPartMisc, 1, 3, "Guts");
		
		//vanilla
		addButcherDrops("minecraft:cow")
		.addItemEntry(Items.BONE, 0, 2)
		.addItemEntry(Items.LEATHER, 2, 6)
		.addItemEntry(Items.BEEF, 10, 20)
		//necromancy
		.addItemEntry(PrimalItems.bodyPartBrain, -1, 1, "Animal Brain")
		.addItemEntry(PrimalItems.bodyPartEye, 0, 2, "Normal Eye")
		.addItemEntry(PrimalItems.bodyPartMisc, 0, 3, "Guts")
		.addItemEntry(PrimalItems.bodyPartGland, 0, 4, "Milk Gland")
		.addItemEntry(PrimalItems.bodyPartMisc, 0, 2, "Horn")
		.addItemEntry(PrimalItems.bodyPartFoot, 0, 4, "Hoof");
		
		//vanilla
		addButcherDrops("minecraft:donkey")
		.addItemEntry(Items.BONE, 0, 2)
		.addItemEntry(Items.BEEF, 3, 10)
		.addItemEntry(Items.LEATHER, 2, 6);
		
		//vanilla
		addButcherDrops("minecraft:horse")
		.addItemEntry(Items.BONE, 0, 2)
		.addItemEntry(Items.BEEF, 3, 10)
		.addItemEntry(Items.LEATHER, 2, 6);

		//vanilla
		addButcherDrops("minecraft:mooshroom")
		.addItemEntry(Blocks.BROWN_MUSHROOM.getItemDropped(null, null, 0), 0, 4)
		.addItemEntry(Blocks.RED_MUSHROOM.getItemDropped(null, null, 0), 0, 4)
		.addItemEntry(Items.BONE, 0, 1)
		.addItemEntry(Items.BEEF, 10, 20)
		.addItemEntry(Items.LEATHER, 0, 4);

		//vanilla
		addButcherDrops("minecraft:mule")
		.addItemEntry(Items.BONE, 0, 2)
		.addItemEntry(Items.BEEF, 3, 10)
		.addItemEntry(Items.LEATHER, 2, 6);

		//vanilla
		addButcherDrops("minecraft:ocelot")
		//rabbit corpse?
		.addItemEntry(Items.BONE, -1, 1)
		.addItemEntry(Items.BEEF, 0, 2);

		//vanilla
		addButcherDrops("minecraft:parrot")
		.addItemEntry(Items.BONE, -1, 1)
		.addItemEntry(Items.FEATHER, 4, 10)
		.addItemEntry(Items.CHICKEN, 1, 1);

		//vanilla
		addButcherDrops("minecraft:pig")
		.addItemEntry(Items.BONE, 0, 2)
		.addItemEntry(Items.PORKCHOP, 14, 28);

		//vanilla
		addButcherDrops("minecraft:rabbit")
		.addItemEntry(Items.BONE, -1, 1)
		.addItemEntry(Items.RABBIT_HIDE, 2, 4)
		.addItemEntry(Items.RABBIT_FOOT, 0, 1)
		.addItemEntry(Items.RABBIT, 1, 1);
		
		//vanilla
		addButcherDrops("minecraft:sheep")
		.addItemEntry(Blocks.WOOL.getItemDropped(null, null, 0), 1, 2)
		.addItemEntry(Items.BONE, 0, 2)
		.addItemEntry(Items.MUTTON, 7, 15);
		
		//vanilla
		addButcherDrops("minecraft:skeleton_horse")
		.addItemEntry(Items.BONE, 13, 26);
		
		//vanilla
		addButcherDrops("minecraft:zombie_horse")
		.addItemEntry(Items.ROTTEN_FLESH, 13, 26)
		.addItemEntry(Items.BONE, 0, 3);
		
		//vanilla
		addButcherDrops("minecraft:squid")
		//dye with no metadata should be squid ink so we should be fine
		.addItemEntry(Items.DYE, 3, 7);
		
		//vanilla
		addButcherDrops("minecraft:llama")
		.addItemEntry(Items.BONE, 0, 2)
		.addItemEntry(Items.BEEF, 3, 10)
		.addItemEntry(Items.LEATHER, 2, 6);
		
		//vanilla
		addButcherDrops("minecraft:polar_bear")
		.addItemEntry(Items.BONE, 0, 2)
		.addItemEntry(Items.BEEF, 48, 64)
		.addItemEntry(Items.BEEF, 0, 32)
		.addItemEntry(Items.FISH, 0, 2);
		
		//vanilla
		addButcherDrops("minecraft:wolf")
		.addItemEntry(Items.BONE, 0, 2)
		.addItemEntry(Items.BEEF, 2, 5)
		.addItemEntry(Items.BONE, 1, 2);
		
		//vanilla
		addButcherDrops("minecraft:cave_spider")
		.addItemEntry(Items.STRING, 2, 4)
		.addItemEntry(Items.SPIDER_EYE, 2, 5)
		//TODO: poison potion
		.addItemEntry(Items.POTIONITEM, 1, 2);
		
		//vanilla
		addButcherDrops("minecraft:enderman")
		.addItemEntry(Items.ENDER_PEARL, 3, 6);
		
		//vanilla
		addButcherDrops("minecraft:spider")
		.addItemEntry(Items.STRING, 4, 9)
		.addItemEntry(Items.SPIDER_EYE, 0, 2);
		
		//vanilla
		addButcherDrops("minecraft:zombie_pigman")
		.addItemEntry(Items.BONE, 0, 1)
		.addItemEntry(Items.ROTTEN_FLESH, 5, 10)
		.addItemEntry(Items.PORKCHOP, 4, 8)
		.addItemEntry(Items.GOLD_NUGGET, 3, 6);
		
		//vanilla
		addButcherDrops("minecraft:blaze")
		.addItemEntry(Items.BLAZE_ROD, 3, 6)
		.addItemEntry(Items.FIRE_CHARGE, -1, 1);
		
		//vanilla
		addButcherDrops("minecraft:creeper")
		.addItemEntry(Items.BONE, 0, 2)
		.addItemEntry(Items.GUNPOWDER, 4, 9);

		//vanilla
		addButcherDrops("minecraft:guardian")
		.addItemEntry(Items.BONE, 0, 1)
		.addItemEntry(Items.PRISMARINE_SHARD, 4, 8)
		.addItemEntry(Items.PRISMARINE_CRYSTALS, 3, 6)
		.addItemEntry(Items.FISH, 1, 1);
		
		//vanilla
		addButcherDrops("minecraft:elder_guardian")
		.addItemEntry(Items.BONE, 0, 1)
		.addItemEntry(Items.PRISMARINE_SHARD, 6, 12)
		.addItemEntry(Items.PRISMARINE_CRYSTALS, 4, 8)
		.addItemEntry(Items.FISH, 1, 1);
		
		//vanilla
		addButcherDrops("minecraft:endermite")
		.addItemEntry(Items.ENDER_PEARL, 1, 2);

		//vanilla
		addButcherDrops("minecraft:villager")
		.addItemEntry(Items.BONE, 2, 4)
		.addItemEntry(Items.BEEF, 2, 5)
		.addItemEntry(Items.EMERALD, -5, 1)
		//TODO: see if I can make these custom books, such as: diaries, minecraft bibles, stupid shit like that
		.addItemEntry(Items.BOOK, -10, 1);
		
		//vanilla
		addButcherDrops("minecraft:evocation_illager")
		.addItemEntry(Items.BONE, 2, 4)
		.addItemEntry(Items.BEEF, 2, 5)
		.addItemEntry(Items.TOTEM_OF_UNDYING, 1, 1)
		.addItemEntry(Items.EMERALD, 5, 7);
		
		//vanilla
		addButcherDrops("minecraft:vex")
		.addItemEntry(Items.IRON_SWORD, -5, 1);
		
		//vanilla
		addButcherDrops("minecraft:vindication_illager")
		.addItemEntry(Items.BONE, 2, 4)
		.addItemEntry(Items.BEEF, 2, 5)
		.addItemEntry(Items.EMERALD, 3, 5)
		.addItemEntry(Items.IRON_AXE, 0, 1)
		//see if I can make these custom books, such as: diaries, minecraft bibles, etc
		.addItemEntry(Items.BOOK, -10, 1);
		
		//vanilla
		addButcherDrops("minecraft:witch")
		//TODO: add sleeping powder
		.addItemEntry(Items.BONE, 0, 2)
		.addItemEntry(Items.BEEF, 2, 5)
		.addItemEntry(Items.GLASS_BOTTLE, -1, 1)
		.addItemEntry(Items.GLOWSTONE_DUST, -2, 2)
		.addItemEntry(Items.REDSTONE, -2, 2)
		.addItemEntry(Items.GUNPOWDER, -2, 2)
		.addItemEntry(Items.SPIDER_EYE, -2, 2)
		.addItemEntry(Items.FERMENTED_SPIDER_EYE, -2, 2)
		.addItemEntry(Items.SUGAR, -2, 2)
		.addItemEntry(Items.BLAZE_POWDER, -2, 2)
		.addItemEntry(Items.MAGMA_CREAM, -2, 2)
		.addItemEntry(Items.NETHER_WART, -2, 1)
		.addItemEntry(Items.SPECKLED_MELON, -3, 1)
		.addItemEntry(Items.GOLDEN_CARROT, -3, 1)
		.addItemEntry(Items.RABBIT_FOOT, -3, 1)
		.addItemEntry(Items.STICK, 1, 1)
		.addItemEntry(Items.DRAGON_BREATH, -8, 1)
		.addItemEntry(Items.EMERALD, -3, 1);
		
		//vanilla
		addButcherDrops("minecraft:iron_golem")
		.addItemEntry(Blocks.RED_FLOWER.getItemDropped(null, null, 0), 1, 1)
		.addItemEntry(Blocks.IRON_BLOCK.getItemDropped(null, null, 0), -1, 1)
		.addItemEntry(Items.IRON_INGOT, 7, 10)
		.addItemEntry(Items.IRON_NUGGET, 5, 9);
		
		//vanilla
		addButcherDrops("minecraft:ghast")
		.addItemEntry(Items.BONE, -1, 1)
		.addItemEntry(Items.GHAST_TEAR, 4, 6)
		.addItemEntry(Items.GUNPOWDER, 3, 5);
		
		//vanilla
		addButcherDrops("minecraft:husk")
		.addItemEntry(Blocks.SAND.getItemDropped(null, null, 0), 2, 4)
		.addItemEntry(Items.BONE, 0, 2)
		.addItemEntry(Items.ROTTEN_FLESH, 8, 19)
		.addItemEntry(Items.IRON_NUGGET, 0, 2);
		
		//vanilla
		addButcherDrops("minecraft:zombie")
		.addItemEntry(Items.BONE, 0, 2)
		.addItemEntry(Items.ROTTEN_FLESH, 12, 24)
		.addItemEntry(Items.IRON_NUGGET, 0, 2);
		
		//vanilla
		addButcherDrops("minecraft:zombie_villager")
		.addItemEntry(Items.BONE, 0, 2)
		.addItemEntry(Items.ROTTEN_FLESH, 12, 24)
		.addItemEntry(Items.IRON_NUGGET, 8, 16);
		
		//vanilla
		addButcherDrops("minecraft:magma_cube")
		.addItemEntry(Items.MAGMA_CREAM, 3, 5);
		
		//vanilla
		addButcherDrops("minecraft:shulker")
		.addItemEntry(Items.ENDER_PEARL, 0, 1)
		.addItemEntry(Items.SHULKER_SHELL, 1, 1);
		
		//vanilla
		addButcherDrops("minecraft:silverfish")
		.addItemEntry(Items.DIAMOND, -8, 1);
		
		//vanilla
		addButcherDrops("minecraft:skeleton")
		.addItemEntry(Items.BONE, 10, 20)
		.addItemEntry(Items.SKULL, 1, 1)
		.addItemEntry(Items.ARROW, 5, 7);
		
		//vanilla
		addButcherDrops("minecraft:wither_skeleton")
		.addItemEntry(Items.BONE, 8, 14)
		//TODO: make this a wither skull
		.addItemEntry(Items.SKULL, 1, 1);

		//vanilla
		addButcherDrops("minecraft:stray")
		.addItemEntry(Items.BONE, 10, 20)
		.addItemEntry(Items.SKULL, 1, 1)
		.addItemEntry(Items.ARROW, 5, 7);
		
		//vanilla
		addButcherDrops("minecraft:slime")
		.addItemEntry(Items.SLIME_BALL, 5, 8);
		
		//vanilla
		addButcherDrops("minecraft:snow_golem")
		//TODO: carved pumpkin instead of normal pumpkin
		.addItemEntry(Blocks.PUMPKIN.getItemDropped(null, null, 0), 2, 4)
		.addItemEntry(Items.SNOWBALL, 8, 16);
		
		//vanilla
		addButcherDrops("minecraft:wither")
		.addItemEntry(Blocks.SOUL_SAND.getItemDropped(null, null, 0), 2, 4)
		.addItemEntry(Items.BONE, 0, 2)
		//TODO: wither skulls instead of normal skull
		.addItemEntry(Items.SKULL, 1, 3)
		.addItemEntry(Items.NETHER_STAR, 1, 3)
		.addItemEntry(Items.EXPERIENCE_BOTTLE, 5, 10);
		
		//vanilla
		addButcherDrops("minecraft:enderdragonboss")
		//TODO: getItemDropped might be special for dragon eggs
		.addItemEntry(Blocks.DRAGON_EGG.getItemDropped(null, null, 0), 1, 1)
		//TODO: add in a block that can harvest these frames, because giving an unbreakable block is a bad idea
		.addItemEntry(Blocks.END_PORTAL_FRAME.getItemDropped(null, null, 0), 0, 1)
		//TODO: dragon head instead of normal skull
		.addItemEntry(Items.SKULL, 1, 1)
		.addItemEntry(Items.BONE, 10, 20)
		.addItemEntry(Items.BEEF, 20, 52)
		.addItemEntry(Items.ENDER_PEARL, 25, 50)
		.addItemEntry(Items.DRAGON_BREATH, 10, 20)
		.addItemEntry(Items.EXPERIENCE_BOTTLE, 20, 40);
	}
}
