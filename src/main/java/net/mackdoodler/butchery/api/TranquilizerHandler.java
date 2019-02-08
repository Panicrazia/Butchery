package net.mackdoodler.butchery.api;

import java.util.HashMap;
import java.util.Map;

import net.mackdoodler.butchery.client.ClientProxy;
import net.mackdoodler.butchery.common.capabilities.CapabilityTranquilizer;
import net.mackdoodler.butchery.common.capabilities.ITranquilizer;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TranquilizerHandler {
	public static final DamageSource TRANQUILIZER = (new DamageSource("PrimalMod.Tranquilized").setDamageBypassesArmor().setDamageIsAbsolute());
	public static Map tranqResistances = new HashMap();
	
	/**
	 * Checks if the given mob should die from tranquilizing and spawns particles if their destiny is written in stone
	 * @param event
	 */
	@SubscribeEvent
	public void tranqulizerKill(LivingUpdateEvent event) {
		EntityLivingBase entity = event.getEntityLiving();

		if(entity.hasCapability(CapabilityTranquilizer.TRANQUILIZER_CAPABILITY, null)){
			ITranquilizer mai = entity.getCapability(CapabilityTranquilizer.TRANQUILIZER_CAPABILITY, null);

			//Necessary so we dont have to call a hashmap per tick per entity
			if(!mai.isThreshholdSet()){
				mai.setSleepThreshhold(TranquilizerHandler.getTranqKillThreshhold(entity));
				mai.setThreshholdSet(true);
			}
			
			if(mai.getSleepDosage() > 0){
				if(mai.getSleepDosage() >= mai.getSleepThreshhold()){
					if(entity.world.isRemote){
						if(entity.world.rand.nextInt(8) == 0){
							ClientProxy.spawnSleepParticlesForEntity(entity);
						}
					}
					else if (mai.getSleepTimer() < 0) {
						entity.attackEntityFrom(TranquilizerHandler.TRANQUILIZER, 1000f);
					}
				}
				if (mai.getSleepTimer() < 0) {
					if(entity instanceof EntitySlime){
						TranquilizerHandler.applyTranquilizer(entity, 50, 0);
					}
					else{
						TranquilizerHandler.applyTranquilizer(entity, 50, -1);
					}
				}
				mai.setSleepTimer(mai.getSleepTimer()-1);
			}
		}
	}
	
	/**
	 * applies 1 dosage to the passed in entity, if you want to apply more than one at a time then call 
	 * {@link TranquilizerHandler#applyTranquilizer(EntityLivingBase, int, int)}
	 * @param target the entity getting tranquilized
	 * @param tranquilizerCap the maximum level of tranquilizer this method call will make
	 */
	public static void applyTranquilizer(EntityLivingBase target, int tranquilizerCap){
		TranquilizerHandler.applyTranquilizer(target, tranquilizerCap, 1);
	}

	/**
	 * applies a set dosage to the passed in entity
	 * @param target the entity getting tranquilized
	 * @param tranquilizerCap the maximum level of tranquilizer this method call will make
	 * @param tranquilizerStrengthFloor how much tranquilizer to add with this call
	 */
	public static void applyTranquilizer(EntityLivingBase target, int tranquilizerCap, int tranquilizerStrengthFloor){
		if(target.hasCapability(CapabilityTranquilizer.TRANQUILIZER_CAPABILITY, null)){
			ITranquilizer mai = target.getCapability(CapabilityTranquilizer.TRANQUILIZER_CAPABILITY, null);
			if(mai.getSleepDosage() < tranquilizerCap){
				mai.addSleepDosage(tranquilizerStrengthFloor);
				mai.setSleepTimer(300);
			}
		}
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
		Integer resistance = (Integer) TranquilizerHandler.tranqResistances.get(EntityList.getKey(target.getClass()).toString());
		return (resistance != null)?(resistance):(4);
	}

	/**
	 * Returns the tranquilizer threshhold, 4 by default if no matching threshhold can be found
	 * @param target the entity
	 * @return the strength of tranquilizer that is needed to kill the entity
	 */
	public static int getTranqKillThreshhold(String target){
		Integer resistance = (Integer) TranquilizerHandler.tranqResistances.get(target);
		return (resistance != null)?(resistance):(4);
	}

	/**
	 * sets the kill threshhold for an entity, 4 is the default
	 * @param entity a string representing the entity, gotten from EntityList.getKey([YourEntity].getClass()).toString() if you dont know what it is
	 * @param resistance
	 */
	public static void setTranqResistance(String entity, int resistance){
		TranquilizerHandler.tranqResistances.put(entity, resistance);
	}

	/**
	 * initializes all vanilla mob tranquilizer resistances, subject to change
	 */
	public static void setTranqResistances(){
		
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
		setTranqResistance("minecraft:slime", 51);
		setTranqResistance("minecraft:magma_cube", 51);
		
		//build-a-bois (ie things that shouldn't be able to be tranquilized)
		setTranqResistance("minecraft:iron_golem", 51);
		setTranqResistance("minecraft:snow_golem", 51);
		setTranqResistance("minecraft:shulker", 51);
		setTranqResistance("minecraft:vex", 51);
		
		//bossi bois
		setTranqResistance("minecraft:wither", 25);
		setTranqResistance("minecraft:enderdragonboss", 35);
	}
	
}
