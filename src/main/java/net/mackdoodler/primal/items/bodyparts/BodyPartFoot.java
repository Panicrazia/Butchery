package net.mackdoodler.primal.items.bodyparts;

import net.mackdoodler.primal.items.ItemBase;
import net.mackdoodler.primal.items.bodyparts.enums.EnumBrainType;
import net.mackdoodler.primal.items.bodyparts.enums.EnumFootType;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BodyPartFoot extends BodyPartMisc implements IBodyPart {

	/*
	 * for parts like hoofs, suctioncups, and other bullshit, I know foot is sort of a strange word for it but it was the best I could think of, ok?
	 * (hands might also go here)
	 * 
	 * hoofs
	 * bird foot
	 * suctioncup
	 * spiderleg
	 * 
	 */
	
	
	public BodyPartFoot(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void registerItemModel() {
        ModelResourceLocation partModelHoof = new ModelResourceLocation(getRegistryName()+"_hoof", "inventory");

        ModelBakery.registerItemVariants(this, partModelHoof);

        ModelLoader.setCustomMeshDefinition(this, new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
            	
            	if ( stack.hasTagCompound() && stack.getTagCompound().hasKey("partType"))
        		{
            		String type = stack.getTagCompound().getString("partType");
            		switch(type){
            		case("Hoof"):
            			return partModelHoof;
            		}
        		}
            	return partModelHoof;
            }
        });
    }
	
	public int getLandSpeedFromStack(ItemStack stack){
		if ( stack.hasTagCompound() && stack.getTagCompound().hasKey("partType"))
		{
    		String type = stack.getTagCompound().getString("partType");

            return EnumFootType.getByName(type).getLandSpeed();
		}
		return 0;
	}
	
	public int getAquaticSpeedFromStack(ItemStack stack){
		if ( stack.hasTagCompound() && stack.getTagCompound().hasKey("partType"))
		{
    		String type = stack.getTagCompound().getString("partType");

            return EnumFootType.getByName(type).getAquaticSpeed();
		}
		return 0;
	}
}
