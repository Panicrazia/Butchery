package net.mackdoodler.primal.items.bodyparts;

import net.mackdoodler.primal.items.ItemBase;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BodyPartFlesh extends BodyPartMisc{

	public BodyPartFlesh(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void registerItemModel() {
        ModelResourceLocation partModelHumanFlesh = new ModelResourceLocation(getRegistryName()+"_human", "inventory");
        ModelResourceLocation partModelEnderFlesh = new ModelResourceLocation(getRegistryName()+"_ender", "inventory");

        ModelBakery.registerItemVariants(this, partModelHumanFlesh, partModelEnderFlesh);

        ModelLoader.setCustomMeshDefinition(this, new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
            	
            	if ( stack.hasTagCompound() && stack.getTagCompound().hasKey("partType"))
        		{
            		String type = stack.getTagCompound().getString("partType");
            		switch(type){
            		case "Human Flesh":
            			return partModelHumanFlesh;
            		case "Ender Flesh":
            			return partModelEnderFlesh;
            		}
        		}
            	return partModelHumanFlesh;
            }
        });
    }
}
