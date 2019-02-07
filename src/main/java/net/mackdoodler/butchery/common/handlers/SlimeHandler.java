package net.mackdoodler.butchery.common.handlers;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import net.mackdoodler.butchery.api.TranquilizerHandler;
import net.mackdoodler.butchery.common.capabilities.CapabilityTranquilizer;
import net.mackdoodler.butchery.common.capabilities.ITranquilizer;
import net.mackdoodler.butchery.common.potions.ButcheryPotions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SlimeHandler {
	
	/**
	 * Makes slime explode their tranquilizer effect to nearby mobs when they meet their unfortunate end
	 * @param event
	 */
	@SubscribeEvent
	public void sleepySlimeExplode(LivingDeathEvent event) {
		EntityLivingBase entity = event.getEntityLiving();
		
		if(entity instanceof EntitySlime){
			PotionEffect effect = entity.getActivePotionEffect(ButcheryPotions.DROWSY_POTION);
			
			if (effect != null && !entity.world.isRemote) {
				for(Entity element : entity.getEntityWorld().getEntitiesWithinAABBExcludingEntity(entity, new AxisAlignedBB(entity.posX-1.5, entity.posY-1, entity.posZ-1.5, entity.posX+1.5, entity.posY+2, entity.posZ+1.5))){
					if(element instanceof EntityLivingBase){
						TranquilizerHandler.applyTranquilizer((EntityLivingBase) element, 50, effect.getAmplifier());
					}
				}
				//If a slime dies in the forest and he has tranquilizers, does he explode?
			}
		}
	}

	/**
	 * removes the ai of the passed in slime
	 * @param target whatever mob you wish to make a vegetable
	 * @return true if the target had ai before the method call
	 */
	public static boolean slimeVegitization(EntityLiving target){
		boolean wasAlive = false;
		if(target instanceof EntitySlime){
			for (Object a : target.tasks.taskEntries.toArray()){
				EntityAIBase ai = ((EntityAITaskEntry) a).action;
				target.tasks.removeTask(ai);
				wasAlive = true;
			}
		}
		return wasAlive;
	}
	
	/**
	 * if a slime is loaded in who has been vegitized it vegitizes it again
	 * @param event
	 */
	@SubscribeEvent
	public void slimeVegitizationOnLoad(EntityJoinWorldEvent event){
		if(event.getEntity() instanceof EntityLivingBase){
			
			EntityLivingBase target = (EntityLivingBase) event.getEntity();
			
			if(target.hasCapability(CapabilityTranquilizer.TRANQUILIZER_CAPABILITY, null)){
	
				ITranquilizer mai = target.getCapability(CapabilityTranquilizer.TRANQUILIZER_CAPABILITY, null);
				
				if(mai.getModified()){
					SlimeHandler.slimeVegitization((EntityLiving)target);
				}
			}
		}
	}

	/**
	 * unvegitizes a slime
	 * @param target slime to be unvegitized
	 */
	public static void slimeUnVegitization(EntityLiving target){
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
}
