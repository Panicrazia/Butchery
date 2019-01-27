package net.mackdoodler.primal.handlers;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import net.mackdoodler.primal.items.ItemCorpse;
import net.mackdoodler.primal.items.PrimalItems;
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
	 * code for applying tranquilizer to an entity
	 * @param target the entity getting tranquilized
	 * @param tranquilizerStrengthCeiling the maximum level of tranquilizer this method call will make
	 */
	public static void applyTranquilizer(EntityLivingBase target, int tranquilizerStrengthCeiling){
		applyTranquilizer(target, tranquilizerStrengthCeiling, 0);
	}
	
	/**
	 * code for applying tranquilizer to an entity
	 * @param target the entity getting tranquilized
	 * @param tranquilizerStrengthCeiling the maximum level of tranquilizer this method call will make
	 * @param tranquilizerStrengthFloor how much tranquilizer to add with this call
	 */
	public static void applyTranquilizer(EntityLivingBase target, int tranquilizerStrengthCeiling, int tranquilizerStrengthFloor){
		if(!target.world.isRemote){
			PotionEffect effect = target.getActivePotionEffect(PrimalPotions.DROWSY_POTION);
			//if the target is already affected by the some tranquilization effect
			if (effect != null) {
				if(effect.getAmplifier() + tranquilizerStrengthFloor >= tranquilizerStrengthCeiling){
					tranquilizerStrengthFloor = tranquilizerStrengthCeiling - effect.getAmplifier() - 1;
				}
				boolean particles = effect.getAmplifier() + tranquilizerStrengthFloor + 1 >= getTranqKillThreshhold(target);
				target.addPotionEffect(new PotionEffect(PrimalPotions.DROWSY_POTION, 300, target.removeActivePotionEffect(PrimalPotions.DROWSY_POTION).getAmplifier() + tranquilizerStrengthFloor + 1, false, particles));
			}
			else{
				target.addPotionEffect(new PotionEffect(PrimalPotions.DROWSY_POTION, 300, tranquilizerStrengthFloor, false, (tranquilizerStrengthFloor >= getTranqKillThreshhold(target))));
			}
		}
	}
	
	/**
	 * 
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
	 * Removes all previous drops of the mob and makes them drop a corpse instead if they were killed by tranquilizing
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
				 * System.out.println(EntityList.getClass(new ResourceLocation(entityName.toString())));
				 * System.out.println(EntityList.getClass(new ResourceLocation("minecraft:sheep")));
				 *
				 */
				
				if (entityName != null) {
					//ItemCorpse corpse = ItemCorpse.TYPES_BY_ID.get(entityName);
					//if (corpse != null) {
					ItemStack dropStack = /*corpse.getItemStack();*/new ItemStack(PrimalItems.itemCorpse,1);
					
					dropStack.setTagCompound(new NBTTagCompound());
					
					dropStack.getTagCompound().setString("corpseType", entityName.toString());
					
					//TODO: if the mob is tamed add a "Crystalline Tear of Loyalty" item which has the flavor text "sells for 25 gold"
					
					if (!dropStack.isEmpty()) {
						//maybe right here have the items that would have dropped get put into the corpse item (for other items such as modded stuff)
						event.getDrops().clear();
						final EntityItem drop = new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, dropStack);
						drop.setDefaultPickupDelay();
						event.getDrops().add(drop);
						//event.setCanceled(true);
					}
					//}
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
	 * initializes all vanilla mobs tranquilizer resistances
	 */
	public static void makeTranqResistances(){
		
		//small bois
		tranqResistances.put("minecraft:bat", 1);
		tranqResistances.put("minecraft:chicken", 1);
		tranqResistances.put("minecraft:ocelot", 2);
		tranqResistances.put("minecraft:parrot", 1);
		tranqResistances.put("minecraft:silverfish", 1);
		tranqResistances.put("minecraft:rabbit", 1);
		
		//big bois
		tranqResistances.put("minecraft:pig", 3);
		tranqResistances.put("minecraft:sheep", 3);
		tranqResistances.put("minecraft:llama", 4);
		tranqResistances.put("minecraft:wolf", 3);
		tranqResistances.put("minecraft:cow", 4);
		tranqResistances.put("minecraft:donkey", 4);
		tranqResistances.put("minecraft:horse", 4);
		tranqResistances.put("minecraft:mooshroom", 4);
		tranqResistances.put("minecraft:mule", 4);
		tranqResistances.put("minecraft:cave_spider", 5);
		tranqResistances.put("minecraft:spider", 5);
		tranqResistances.put("minecraft:polar_bear", 8);
		
		//minecrafti bois
		tranqResistances.put("minecraft:ghast", 2);
		tranqResistances.put("minecraft:creeper", 5);
		tranqResistances.put("minecraft:blaze", 6);
		
		//useless bois
		tranqResistances.put("minecraft:squid", 3);
		tranqResistances.put("minecraft:guardian", 5);
		tranqResistances.put("minecraft:elder_guardian", 8);
		
		//endi bois
		tranqResistances.put("minecraft:enderman", 8);
		tranqResistances.put("minecraft:endermite", 3);
		
		//humaboids
		tranqResistances.put("minecraft:villager", 4);
		tranqResistances.put("minecraft:vindication_illager", 4);
		tranqResistances.put("minecraft:evocation_illager", 4);
		tranqResistances.put("minecraft:witch", 8);
		
		//zombois
		tranqResistances.put("minecraft:zombie", 5);
		tranqResistances.put("minecraft:zombie_villager", 5);
		tranqResistances.put("minecraft:husk", 5);
		tranqResistances.put("minecraft:zombie_pigman", 5);
		tranqResistances.put("minecraft:zombie_horse", 7);
		
		//boni bois
		tranqResistances.put("minecraft:skeleton", 8);
		tranqResistances.put("minecraft:wither_skeleton", 9);
		tranqResistances.put("minecraft:stray", 8);
		tranqResistances.put("minecraft:skeleton_horse", 10);
		
		//slimi bois
		tranqResistances.put("minecraft:slime", 50);
		tranqResistances.put("minecraft:magma_cube", 50);
		
		//build-a-bois
		tranqResistances.put("minecraft:iron_golem", 50);
		tranqResistances.put("minecraft:snow_golem", 50);
		tranqResistances.put("minecraft:shulker", 50);
		tranqResistances.put("minecraft:vex", 50);
		
		//bossi bois
		tranqResistances.put("minecraft:wither", 10);
		tranqResistances.put("minecraft:enderdragonboss", 10);
	}
}
