package net.mackdoodler.primal.items;

import net.mackdoodler.primal.PrimalMod;
import net.mackdoodler.primal.blocks.ModBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

public class PrimalCodex extends Item{
	
	public PrimalCodex(){
		setTranslationKey("primal_codex");
		setRegistryName("primal_codex");
		setCreativeTab(PrimalMod.creativeTab);
	}
	
	public void registerItemModel(){
		PrimalMod.proxy.registerItemRenderer(this, 0, "primal_codex");
	}
}
