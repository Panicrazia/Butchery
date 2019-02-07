package net.mackdoodler.butchery.common.capabilities;

import net.mackdoodler.butchery.ButcheryMod;
import net.mackdoodler.butchery.common.items.ButcheryItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
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
			event.addCapability(CapabilityTranquilizer.TRANQUILIZER_CAPABILITY_ID, new CapabilityTranquilizer());
		}
	}
}