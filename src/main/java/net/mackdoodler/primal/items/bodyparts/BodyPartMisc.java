package net.mackdoodler.primal.items.bodyparts;

import net.mackdoodler.primal.items.ItemBase;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BodyPartMisc extends ItemBase implements IBodyPart {

	/*
	 * for parts that just simply dont belong in the other categories, maybe merge this with some of the others that arent as populated
	 * 
	 * default is guts
	 */
	
	public BodyPartMisc(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	@SideOnly(Side.CLIENT)
    @Override
    public String getItemStackDisplayName(ItemStack stack)
    {
    	if ( stack.hasTagCompound() && stack.getTagCompound().hasKey("partType"))
		{
    		String type = stack.getTagCompound().getString("partType");

            return I18n.format("tooltip.partdesc", type);
		}
    	
    	return super.getItemStackDisplayName(stack);
    }
	
	public String getPartType(ItemStack stack){
		
		
		
		
		return "Guts";
	}

	@Override
	@SideOnly(Side.CLIENT)
    public void registerItemModel() {
        ModelResourceLocation partModelGuts = new ModelResourceLocation(getRegistryName()+"_guts", "inventory");
        ModelResourceLocation partModelHorn = new ModelResourceLocation(getRegistryName()+"_horn", "inventory");

        ModelBakery.registerItemVariants(this, partModelGuts, partModelHorn);

        ModelLoader.setCustomMeshDefinition(this, new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
            	
            	if ( stack.hasTagCompound() && stack.getTagCompound().hasKey("partType"))
        		{
            		String type = stack.getTagCompound().getString("partType");
            		switch(type){
            		case "Guts":
            			return partModelGuts;
            		case "Horn":
            			return partModelHorn;
            		}
        		}
            	return partModelGuts;
            }
        });
    }
	/*
	@Override
	public ModelResourceLocation getModelLocation(ItemStack stack) {
		
		if ( stack.hasTagCompound() && stack.getTagCompound().hasKey("partType"))
		{
    		String type = stack.getTagCompound().getString("partType");

            return new ModelResourceLocation(this.getRegistryName()+"_"+type,"inventory");
		}
		// TODO Auto-generated method stub
		return null;
	}*/
}
