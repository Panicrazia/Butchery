package net.mackdoodler.primal.items.bodyparts;

import net.mackdoodler.primal.items.ItemBase;
import net.mackdoodler.primal.items.bodyparts.enums.EnumBrainType;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BodyPartGland extends BodyPartMisc {

	/*
	 * gunpowder
	 * spider silk
	 * poison
	 * mushroom
	 * wool?
	 * blaze powder (or more generally fire)
	 * ender
	 * tear? (ghasts as a meme, could be used for water sources in things? idfk, i like the meme though so its happening (also from villagers))
	 * pure distillation of endless hatred (silverfish, odv)(mybe llamas too?)
	 * wither
	 * ink would be a thing but ink sacs already are a thing
	 * ender dragon breath
	 * ghast gas - used to make balloon like flying
	 * 
	 * artificials:
	 * miasmic/plague sac
	 */
	
	public BodyPartGland(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void registerItemModel() {
        ModelResourceLocation partModelMilk = new ModelResourceLocation(getRegistryName()+"_milk", "inventory");
        ModelResourceLocation partModelSilk = new ModelResourceLocation(getRegistryName()+"_silk", "inventory");

        ModelBakery.registerItemVariants(this, partModelMilk, partModelSilk);

        ModelLoader.setCustomMeshDefinition(this, new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
            	
            	if ( stack.hasTagCompound() && stack.getTagCompound().hasKey("partType"))
        		{
            		String type = stack.getTagCompound().getString("partType");
            		switch(type){
            		case "Milk Gland":
            			return partModelMilk;
            		case "Spider Silk Sac":
            			return partModelSilk;
            		}
        		}
            	return partModelMilk;
            }
        });
    }
	/*
	 * when right clicked
	 * 
	 * 
	 */
}
