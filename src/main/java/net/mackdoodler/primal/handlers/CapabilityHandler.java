package net.mackdoodler.primal.handlers;

import net.mackdoodler.primal.PrimalMod;
import net.mackdoodler.primal.capabilities.CapabilityMonsterAI;
import net.mackdoodler.primal.capabilities.IMonsterAI;
import net.mackdoodler.primal.items.ButcheryItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CapabilityHandler {
	
	@SubscribeEvent
	public void attachCapability(AttachCapabilitiesEvent<Entity> event){
		if(event.getObject() instanceof EntityLivingBase && !(event.getObject() instanceof EntityPlayer)){
			event.addCapability(CapabilityMonsterAI.MONSTER_AI_CAPABILITY_ID, new CapabilityMonsterAI());
		}
	}
	
	@SubscribeEvent
	public void rewriteMonsterAI(EntityJoinWorldEvent event){
		if(event.getEntity() instanceof EntityLivingBase){
			
			EntityLivingBase target = (EntityLivingBase) event.getEntity();
			
			if(target.hasCapability(CapabilityMonsterAI.MONSTER_AI_CAPABILITY, null)){
	
				IMonsterAI mai = target.getCapability(CapabilityMonsterAI.MONSTER_AI_CAPABILITY, null);
				
				if(mai.getModified()){
	
					System.out.println("ENTITY DETECTED: PROCEEDING TO LOBOTOMISE");
					
					if(CorpseHandler.neuterEntity((EntityLiving)target)){
						System.out.println("sucessful lobotomization");
					}
					else{
						System.out.println("unsuccessful lobotomization");
					}
				}
			}
		}
	}
}