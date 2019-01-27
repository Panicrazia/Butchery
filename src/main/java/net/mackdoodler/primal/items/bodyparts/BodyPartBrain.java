package net.mackdoodler.primal.items.bodyparts;

import net.mackdoodler.primal.capabilities.CapabilityMonsterAI;
import net.mackdoodler.primal.capabilities.IMonsterAI;
import net.mackdoodler.primal.handlers.CorpseHandler;
import net.mackdoodler.primal.items.ItemBase;
import net.mackdoodler.primal.items.PrimalItems;
import net.mackdoodler.primal.items.bodyparts.enums.EnumBrainType;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BodyPartBrain extends BodyPartMisc{
	
	public BodyPartBrain(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	public int getProccessingPowerFromStack(ItemStack stack){
		if ( stack.hasTagCompound() && stack.getTagCompound().hasKey("partType"))
		{
    		String type = stack.getTagCompound().getString("partType");

            return EnumBrainType.getByName(type).getProcessingPower();
		}
		return 0;
	}
	
	/**
     * Returns true if the item can be used on the given entity, e.g. shears on sheep.
     */
	@Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand){
		if(!playerIn.getEntityWorld().isRemote && target instanceof EntitySlime){
			if(target.hasCapability(CapabilityMonsterAI.MONSTER_AI_CAPABILITY, null)){
				
				IMonsterAI mai = target.getCapability(CapabilityMonsterAI.MONSTER_AI_CAPABILITY, null);
				if(mai.getModified()){
					
					CorpseHandler.unNeuterEntity((EntityLiving) target);
					
					mai.setModified(false);
					
					stack.shrink(1);
					
					return true;
				}
			}
		}
		return false;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public void registerItemModel() {
        ModelResourceLocation partModelAnimal = new ModelResourceLocation(getRegistryName()+"_animal", "inventory");
        ModelResourceLocation partModelRotten = new ModelResourceLocation(getRegistryName()+"_rotten", "inventory");
        ModelResourceLocation partModelSlimey = new ModelResourceLocation(getRegistryName()+"_slimey", "inventory");

        ModelBakery.registerItemVariants(this, partModelAnimal, partModelRotten, partModelSlimey);

        ModelLoader.setCustomMeshDefinition(this, new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
            	
            	if ( stack.hasTagCompound() && stack.getTagCompound().hasKey("partType"))
        		{
            		String type = stack.getTagCompound().getString("partType");
            		switch(type){
            		case "Animal Brain":
            			return partModelAnimal;
            		case "Rotting Brain":
            			return partModelRotten;
            		case "Slimey Core":
            			return partModelSlimey;
            		}
        		}
            	return partModelAnimal;
            }
        });
    }
}
