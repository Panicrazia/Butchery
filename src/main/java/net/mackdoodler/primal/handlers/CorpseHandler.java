package net.mackdoodler.primal.handlers;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import net.mackdoodler.primal.items.ItemCorpse;
import net.mackdoodler.primal.capabilities.CapabilityMonsterAI;
import net.mackdoodler.primal.capabilities.IMonsterAI;
import net.mackdoodler.primal.items.ButcheryItems;
import net.mackdoodler.primal.potions.PrimalPotions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CorpseHandler {
	
	public static final DamageSource TRANQUILIZER = (new DamageSource("PrimalMod.Tranquilized").setDamageBypassesArmor().setDamageIsAbsolute());
	public static Map tranqResistances = new HashMap();
	
	/**
	 * applies 1 dosage to the passed in entity, if you want to apply more than one at a time then call 
	 * {@link #applyTranquilizer(EntityLivingBase, int, int)}
	 * @param target the entity getting tranquilized
	 * @param tranquilizerCap the maximum level of tranquilizer this method call will make
	 */
	public static void applyTranquilizer(EntityLivingBase target, int tranquilizerCap){
		applyTranquilizer(target, tranquilizerCap, 1);
	}
	
	/**
	 * applies a set dosage to the passed in entity
	 * @param target the entity getting tranquilized
	 * @param tranquilizerCap the maximum level of tranquilizer this method call will make
	 * @param tranquilizerStrengthFloor how much tranquilizer to add with this call
	 */
	public static void applyTranquilizer(EntityLivingBase target, int tranquilizerCap, int tranquilizerStrengthFloor){
		if(target.hasCapability(CapabilityMonsterAI.MONSTER_AI_CAPABILITY, null)){
			IMonsterAI mai = target.getCapability(CapabilityMonsterAI.MONSTER_AI_CAPABILITY, null);
			if(mai.getSleepDosage() < tranquilizerCap){
				mai.addSleepDosage(tranquilizerStrengthFloor);
				mai.setSleepTimer(300);
			}
		}
	}
	
	/**
	 * removes the ai of the passed in target
	 * @param target whatever mob you wish to make a vegetable
	 * @return true if the target had ai before the method call
	 */
	public static boolean neuterEntity(EntityLiving target){
		boolean wasAlive = false;
		for (Object a : target.tasks.taskEntries.toArray()){
			EntityAIBase ai = ((EntityAITaskEntry) a).action;
			target.tasks.removeTask(ai);
			wasAlive = true;
		}
		return wasAlive;
	}
	
	/**
	 * unvegitizes a slime
	 * @param target slime to be unvegitized
	 */
	public static void unNeuterEntity(EntityLiving target){
		boolean wasAlive = true;
		Constructor con;

		Class[] slimeyClasses = EntitySlime.class.getDeclaredClasses();
		/*
		System.out.println("unneutering the slime     "+slimeyClasses.length+" entries in this array;");
		System.out.println(slimeyClasses[0].toString());
		System.out.println(slimeyClasses[1].toString());
		System.out.println(slimeyClasses[2].toString());
		System.out.println(slimeyClasses[3].toString());
		System.out.println(slimeyClasses[4].toString());
		*/
		try {
			//AISlimeHop
			con = slimeyClasses[1].getConstructor(EntitySlime.class);
			con.setAccessible(true);
			target.tasks.addTask(1, (EntityAIBase) con.newInstance(target));
			//AISlimeFloat
			con = slimeyClasses[2].getConstructor(EntitySlime.class);
			con.setAccessible(true);
			target.tasks.addTask(1, (EntityAIBase) con.newInstance(target));
			//AISlimeFaceRandom
			con = slimeyClasses[3].getConstructor(EntitySlime.class);
			con.setAccessible(true);
			target.tasks.addTask(1, (EntityAIBase) con.newInstance(target));
			//AISlimeAttack
			con = slimeyClasses[4].getConstructor(EntitySlime.class);
			con.setAccessible(true);
			target.tasks.addTask(1, (EntityAIBase) con.newInstance(target));
			
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Removes all previous drops of the mob and makes them drop a corpse instead
	 * 
	 * only drops a corpse if the damage source that killed them was tranquilizers
	 * @param event
	 */
	@SubscribeEvent(priority=EventPriority.LOWEST)
	public void dropCorpse(final LivingDropsEvent event) {
		EntityLivingBase entity = event.getEntityLiving();
		
		if(event.getSource()==CorpseHandler.TRANQUILIZER){
			if (!entity.world.isRemote && canDrop(entity)) {
				ResourceLocation entityName = EntityList.getKey(entity.getClass());

				/*
				 * the below are equivalent and get you the class of the entity 
				 * 
				 * EntityList.getClass(new ResourceLocation(entityName.toString()));
				 * EntityList.getClass(new ResourceLocation("minecraft:sheep"));
				 *
				 */
				
				if (entityName != null) {
					//ItemCorpse corpse = ItemCorpse.TYPES_BY_ID.get(entityName);
					//if (corpse != null) {
					ItemStack dropStack = /*corpse.getItemStack();*/new ItemStack(ButcheryItems.itemCorpse,1);
					
					dropStack.setTagCompound(new NBTTagCompound());
					
					dropStack.getTagCompound().setString("corpseType", entityName.toString());
					
					//TODO: if the mob is tamed add a "Crystalline Tear of Loyalty" item which has the flavor text "sells for 25 gold"
					
					if (!dropStack.isEmpty()) {
						//TODO: maybe right here have the items that would have dropped get put into the corpse item (for other items such as modded stuff)
						event.getDrops().clear();
						final EntityItem drop = new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, dropStack);
						drop.setDefaultPickupDelay();
						event.getDrops().add(drop);
					}
				}
			}
		}
	}
	
	/**
	 * simple method to check if mobs are eligible to drop loot
	 * @param entity
	 * @return
	 */
	private static boolean canDrop(Entity entity) {
		final World world = entity.world;
		return world != null && world.getGameRules().getBoolean("doMobLoot");
	}
	
	/**
	 * Returns the tranquilizer threshhold, 4 by default if no matching threshhold can be found
	 * @param target the entity
	 * @return the strength of tranquilizer that is needed to kill the entity
	 */
	public static int getTranqKillThreshhold(EntityLivingBase target){
		if(target instanceof EntityPlayer){
			return 4;
		}
		Integer resistance = (Integer) tranqResistances.get(EntityList.getKey(target.getClass()).toString());
    	return (resistance != null)?(resistance):(4);
	}
	
	/**
	 * Returns the tranquilizer threshhold, 4 by default if no matching threshhold can be found
	 * @param target the entity
	 * @return the strength of tranquilizer that is needed to kill the entity
	 */
	public static int getTranqKillThreshhold(String target){
		Integer resistance = (Integer) tranqResistances.get(target);
    	return (resistance != null)?(resistance):(4);
	}
	
	/**
	 * sets the kill threshhold for an entity, 4 is the default
	 * @param entity a string representing the entity, gotten from EntityList.getKey([YourEntity].getClass()).toString() if you dont know what it is
	 * @param resistance
	 */
	public static void setTranqResistance(String entity, int resistance){
		tranqResistances.put(entity, resistance);
	}
	
	/**
	 * initializes all vanilla mob tranquilizer resistances
	 */
	public static void makeTranqResistances(){
		
		//small bois
		setTranqResistance("minecraft:bat", 1);
		setTranqResistance("minecraft:chicken", 1);
		setTranqResistance("minecraft:ocelot", 2);
		setTranqResistance("minecraft:parrot", 1);
		setTranqResistance("minecraft:silverfish", 1);
		setTranqResistance("minecraft:rabbit", 1);
		
		//big bois
		setTranqResistance("minecraft:pig", 3);
		setTranqResistance("minecraft:sheep", 3);
		setTranqResistance("minecraft:llama", 4);
		setTranqResistance("minecraft:wolf", 3);
		setTranqResistance("minecraft:cow", 4);
		setTranqResistance("minecraft:donkey", 4);
		setTranqResistance("minecraft:horse", 4);
		setTranqResistance("minecraft:mooshroom", 4);
		setTranqResistance("minecraft:mule", 4);
		setTranqResistance("minecraft:cave_spider", 5);
		setTranqResistance("minecraft:spider", 5);
		setTranqResistance("minecraft:polar_bear", 8);
		
		//minecrafti bois
		setTranqResistance("minecraft:ghast", 2);
		setTranqResistance("minecraft:creeper", 5);
		setTranqResistance("minecraft:blaze", 6);
		
		//useless bois
		setTranqResistance("minecraft:squid", 3);
		setTranqResistance("minecraft:guardian", 5);
		setTranqResistance("minecraft:elder_guardian", 8);
		
		//endi bois
		setTranqResistance("minecraft:enderman", 8);
		setTranqResistance("minecraft:endermite", 3);
		
		//humaboids
		setTranqResistance("minecraft:villager", 4);
		setTranqResistance("minecraft:vindication_illager", 4);
		setTranqResistance("minecraft:evocation_illager", 4);
		setTranqResistance("minecraft:witch", 8);
		
		//zombois
		setTranqResistance("minecraft:zombie", 5);
		setTranqResistance("minecraft:zombie_villager", 5);
		setTranqResistance("minecraft:husk", 5);
		setTranqResistance("minecraft:zombie_pigman", 5);
		setTranqResistance("minecraft:zombie_horse", 7);
		
		//boni bois
		setTranqResistance("minecraft:skeleton", 8);
		setTranqResistance("minecraft:wither_skeleton", 9);
		setTranqResistance("minecraft:stray", 8);
		setTranqResistance("minecraft:skeleton_horse", 8);
		
		//slimi bois
		setTranqResistance("minecraft:slime", 50);
		setTranqResistance("minecraft:magma_cube", 50);
		
		//build-a-bois
		setTranqResistance("minecraft:iron_golem", 50);
		setTranqResistance("minecraft:snow_golem", 50);
		setTranqResistance("minecraft:shulker", 50);
		setTranqResistance("minecraft:vex", 50);
		
		//bossi bois
		setTranqResistance("minecraft:wither", 10);
		setTranqResistance("minecraft:enderdragonboss", 10);
	}
}
