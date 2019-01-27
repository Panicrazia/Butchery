package net.mackdoodler.primal.items;

import net.mackdoodler.primal.PrimalMod;
import net.mackdoodler.primal.blocks.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class ItemRune extends ItemBase{

	public ItemRune() {
		super("item_rune");
		//setRegistryName("item_rune");
		setHasSubtypes(true);
	}
	
	@Override
	public int getMetadata(int damage) {
		return damage;
		//return damage > max ? damage : max;
	}
	
	@Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list){
        if(this.isInCreativeTab(tab)){
        	for(int i = 0; i < 11; i++){
        		list.add(new ItemStack(this, 1, i));
        	}
        }
    }
	
	@Override
	public void registerItemModel(){
		 for(int i = 0; i < 11; i++){
			 //PrimalMod.proxy.registerItemRenderer(this, i, name);
			 ModelLoader.setCustomModelResourceLocation(this, i, new ModelResourceLocation(getRegistryName(), "meta=" + i));
		 }
	}
	
	@Override
	public String getTranslationKey(ItemStack stack) {
		return super.getTranslationKey() + "." + stack.getMetadata();
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		/*if(worldIn.getBlockState(pos).getBlock().equals(Block.getBlockFromName(name))){
			
		}*/
		
        return EnumActionResult.SUCCESS;
    }
	
}
