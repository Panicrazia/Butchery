package net.mackdoodler.primal.potions;

import net.mackdoodler.primal.capabilities.CapabilityMonsterAI;
import net.mackdoodler.primal.capabilities.IMonsterAI;
import net.mackdoodler.primal.handlers.CorpseHandler;
import net.mackdoodler.primal.proxy.ClientProxy;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PotionEventHandler {
	
	/**
	 * Checks if the given mob should die from tranquilizing and spawns particles if they will die
	 * @param event
	 */
	@SubscribeEvent
	public void sleepKill(LivingUpdateEvent event) {
		EntityLivingBase entity = event.getEntityLiving();

		if(entity.hasCapability(CapabilityMonsterAI.MONSTER_AI_CAPABILITY, null)){
			IMonsterAI mai = entity.getCapability(CapabilityMonsterAI.MONSTER_AI_CAPABILITY, null);

			//Necessary so we dont have to call a hashmap every tick per entity
			if(!mai.isThreshholdSet()){
				mai.setSleepThreshhold(CorpseHandler.getTranqKillThreshhold(entity));
			}
			
			if(mai.getSleepDosage() > 0){
				
				if(mai.getSleepDosage() >= mai.getSleepThreshhold()){
					if(entity.world.isRemote){
						if(entity.world.rand.nextInt(8) == 0){
							ClientProxy.spawnSleepParticlesForEntity(entity);
							return;
						}
					}
					if (mai.getSleepTimer() < 0) {
						entity.attackEntityFrom(CorpseHandler.TRANQUILIZER, 1000f);
					}
				}
				if (mai.getSleepTimer() < 0) {
					CorpseHandler.applyTranquilizer(entity, 50, -1);
				}
				mai.setSleepTimer(mai.getSleepTimer()-1);
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
