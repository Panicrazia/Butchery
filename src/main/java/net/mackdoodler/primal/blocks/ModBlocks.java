package net.mackdoodler.primal.blocks;

import net.mackdoodler.primal.client.renderer.tileentity.TileEntityCorpseRenderer;
import net.mackdoodler.primal.tileentity.TileEntityCorpse;
import net.mackdoodler.primal.tileentity.TileEntityRuneSocket;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

public class ModBlocks {
	
	public static BlockOre oreCopper = new BlockOre("ore_copper");
	public static DarkWood classicalWood = new DarkWood();
	public static Calidarium cali = new Calidarium();
	public static BlockNetherGrass netherGrass = new BlockNetherGrass();
	
	public static RuneBlock runeBlock = new RuneBlock("rune_block");
	public static RuneSocket runeSocket = new RuneSocket("rune_socket");
	public static BlockAugmentConnection augmentConnection = new BlockAugmentConnection("augment_connection");
	
	public static LightningRod lightningRod = new LightningRod("lightning_rod");
	public static BlockButcherMat butcherMat = new BlockButcherMat("butcher_mat");
	public static BlockCorpse blockCorpse = new BlockCorpse("block_corpse");
	
	//crops
	public static BlockCropMango cropMango = new BlockCropMango();
	public static BlockCropLunarLilly cropLunarLilly = new BlockCropLunarLilly();
	
	//fleshcraft
	public static BlockBase rottenFleshBlock = new BlockBase(Material.ROCK, "rotten_flesh_block");
	public static BlockFleshworkHead rottenFleshBlockFace = new BlockFleshworkHead();
	
	public static void register(IForgeRegistry<Block> registry){
		registry.registerAll(
				oreCopper,
				cropMango,
				cropLunarLilly,
				classicalWood,
				cali,
				netherGrass,
				runeBlock,
				runeSocket,
				augmentConnection,
				lightningRod,
				butcherMat,
				blockCorpse,
				rottenFleshBlock,
				rottenFleshBlockFace
		);
		GameRegistry.registerTileEntity(TileEntityRuneSocket.class, new ResourceLocation("rune_socket"));
		GameRegistry.registerTileEntity(blockCorpse.getTileEntityClass(), new ResourceLocation("corpse"));
	}
	
	public static void registerItemBlocks(IForgeRegistry<Item> registry){
		registry.registerAll(
				oreCopper.createItemBlock(),
				classicalWood.createItemBlock(),
				cali.createItemBlock(),
				netherGrass.createItemBlock(),
				runeBlock.createItemBlock(),
				runeSocket.createItemBlock(),
				augmentConnection.createItemBlock(),
				lightningRod.createItemBlock(),
				rottenFleshBlock.createItemBlock(),
				rottenFleshBlockFace.createItemBlock()
		);
	}
	
	public static void registerModels(){
		oreCopper.registerItemModel(Item.getItemFromBlock(oreCopper));
		classicalWood.registerItemModel(Item.getItemFromBlock(classicalWood));
		cali.registerItemModel(Item.getItemFromBlock(cali));
		netherGrass.registerItemModel(Item.getItemFromBlock(netherGrass));
		runeBlock.registerItemModel(Item.getItemFromBlock(runeBlock));
		augmentConnection.registerItemModel(Item.getItemFromBlock(augmentConnection));
		lightningRod.registerItemModel(Item.getItemFromBlock(lightningRod));
		rottenFleshBlock.registerItemModel(Item.getItemFromBlock(rottenFleshBlock));
		rottenFleshBlockFace.registerItemModel(Item.getItemFromBlock(rottenFleshBlockFace));

		runeSocket.registerItemModel(Item.getItemFromBlock(runeSocket));
    	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCorpse.class, new TileEntityCorpseRenderer());
	}
	
	
}
