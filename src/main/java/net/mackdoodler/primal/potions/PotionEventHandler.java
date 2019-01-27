package net.mackdoodler.primal.potions;

import net.mackdoodler.primal.handlers.CorpseHandler;
import net.mackdoodler.primal.proxy.ClientProxy;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PotionEventHandler {
	
	/**
	 * Checks if the given mob should die from tranquilizing
	 * @param event
	 */
	@SubscribeEvent
	public void sleepKill(LivingUpdateEvent event) {
		EntityLivingBase entity = event.getEntityLiving();
		PotionEffect effect = entity.getActivePotionEffect(PrimalPotions.DROWSY_POTION);
		if(entity.world.isRemote){
			//ClientProxy.spawnSleepParticles(entity);
		}
		if (effect != null && !entity.world.isRemote) {
			/*
			 * Code for when the drowsy effect is on a mob
			 * 
			 * things to think about:
			 * higher amps cause the duration to tick down faster? <- should use division instead to make sure we 
			 * 															dont hit negative values, and make higher 
			 * 															strengths more dangerous
			 * 
			 * also should add a sleep event that when you sleep with the drowsy effect you also die
			 */
			if (effect.getDuration() < 30) {
				if(entity instanceof EntitySlime){
					CorpseHandler.applyTranquilizer(entity, 50, -1);
				}
				else if(effect.getAmplifier() >= CorpseHandler.getTranqKillThreshhold(entity)){
					entity.attackEntityFrom(CorpseHandler.TRANQUILIZER, 1000f);
				}
				else if(effect.getAmplifier() > 0){
					//System.out.println("New effect with a strength of: "+(effect.getAmplifier()-1));
					CorpseHandler.applyTranquilizer(entity, 50, -2);
				}
			}
		}
	}
	
	/**
	 * Makes slime explode their tranquilizer effect to nearby mobs when they meet their unfortunate end
	 * @param event
	 */
	@SubscribeEvent
	public void sleepySlimeExplode(LivingDeathEvent event) {
		EntityLivingBase entity = event.getEntityLiving();
		
		if(entity instanceof EntitySlime){
			PotionEffect effect = entity.getActivePotionEffect(PrimalPotions.DROWSY_POTION);
			
			if (effect != null && !entity.world.isRemote) {
				for(Entity element : entity.getEntityWorld().getEntitiesWithinAABBExcludingEntity(entity, new AxisAlignedBB(entity.posX-1.5, entity.posY-1, entity.posZ-1.5, entity.posX+1.5, entity.posY+2, entity.posZ+1.5))){
					if(element instanceof EntityLivingBase){
						CorpseHandler.applyTranquilizer((EntityLivingBase) element, 50, effect.getAmplifier());
					}
				}
				//System.out.println("If a slime dies in the forest and he has tranquilizers, does it explode?");
			}
		}
	}
}
