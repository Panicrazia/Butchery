package net.mackdoodler.primal.items.bodyparts;

import net.mackdoodler.primal.items.ItemBase;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BodyPartEye extends BodyPartMisc {

	/*
	 * specific mentions are ocean guardian eyes, bat eyes, bat ears? (could make them sound controllable?)
	 */
	
	public BodyPartEye(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void registerItemModel() {
        ModelResourceLocation partModelNormal = new ModelResourceLocation(getRegistryName()+"_normal", "inventory");
        ModelResourceLocation partModelBat = new ModelResourceLocation(getRegistryName()+"_bat", "inventory");

        ModelBakery.registerItemVariants(this, partModelNormal, partModelBat);

        ModelLoader.setCustomMeshDefinition(this, new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
            	
            	if ( stack.hasTagCompound() && stack.getTagCompound().hasKey("partType"))
        		{
            		String type = stack.getTagCompound().getString("partType");
            		switch(type){
            		case "Normal Eye":
            			return partModelNormal;
            		case "Bat Eye":
            			return partModelBat;
            		}
        		}
            	return partModelNormal;
            }
        });
    }
}
