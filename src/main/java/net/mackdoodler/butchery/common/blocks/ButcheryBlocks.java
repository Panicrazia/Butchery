package net.mackdoodler.butchery.common.blocks;

import net.mackdoodler.butchery.ButcheryMod;
import net.mackdoodler.butchery.client.renderer.tileentity.TileEntityCorpseRenderer;
import net.mackdoodler.butchery.common.tileentity.TileEntityCorpse;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

public class ButcheryBlocks {
	
	public static BlockButcherMat butcherMat = new BlockButcherMat("butcher_mat");
	public static BlockCorpse blockCorpse = new BlockCorpse("block_corpse");
	public static BlockCropLunarLilly cropLunarLilly = new BlockCropLunarLilly("crop_lunar_lilly");
	
	public static void register(IForgeRegistry<Block> registry){
		registry.registerAll(
				butcherMat,
				blockCorpse,
				cropLunarLilly
		);
		GameRegistry.registerTileEntity(blockCorpse.getTileEntityClass(), new ResourceLocation(ButcheryMod.MODID+":corpse"));
	}
	
	public static void registerItemBlocks(IForgeRegistry<Item> registry){
		registry.registerAll(
			//createItemBlock methods go here
		);
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerModels(){
		//registerItemModel methods go here
    	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCorpse.class, new TileEntityCorpseRenderer());
	}
}
