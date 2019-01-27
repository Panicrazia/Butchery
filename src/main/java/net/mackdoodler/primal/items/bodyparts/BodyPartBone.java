package net.mackdoodler.primal.items.bodyparts;

import net.mackdoodler.primal.items.ItemBase;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BodyPartBone extends BodyPartMisc {

	public BodyPartBone(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	@SideOnly(Side.CLIENT)
    public void registerItemModel() {
        ModelResourceLocation partModelLight = new ModelResourceLocation(getRegistryName()+"_light", "inventory");
        ModelResourceLocation partModelWither = new ModelResourceLocation(getRegistryName()+"_wither", "inventory");

        ModelBakery.registerItemVariants(this, partModelLight, partModelWither);

        ModelLoader.setCustomMeshDefinition(this, new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
            	
            	if ( stack.hasTagCompound() && stack.getTagCompound().hasKey("partType"))
        		{
            		String type = stack.getTagCompound().getString("partType");
            		switch(type){
            		case "Light Bone":
            			return partModelLight;
            		case "Withered Bone":
            			return partModelWither;
            		}
        		}
            	return partModelLight;
            }
        });
    }
}
