package net.mackdoodler.butchery.common.handlers;

import net.mackdoodler.butchery.api.TranquilizerHandler;
import net.mackdoodler.butchery.common.items.ButcheryItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CorpseHandler {
	
	/**
	 * Removes all previous drops of the mob and makes them drop a corpse instead
	 * 
	 * only drops a corpse if the damage source that killed them was tranquilizers
	 * @param event
	 */
	@SubscribeEvent(priority=EventPriority.LOWEST)
	public void dropCorpse(final LivingDropsEvent event) {
		EntityLivingBase entity = event.getEntityLiving();
		
		if(event.getSource()==TranquilizerHandler.TRANQUILIZER){
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
					ItemStack dropStack = new ItemStack(ButcheryItems.itemCorpse,1);
					
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
	 * helper method to check if mobs are eligible to drop loot
	 * @param entity
	 * @return
	 */
	private static boolean canDrop(Entity entity) {
		final World world = entity.world;
		return world != null && world.getGameRules().getBoolean("doMobLoot");
	}
}
