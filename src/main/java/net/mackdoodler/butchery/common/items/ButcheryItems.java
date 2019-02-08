package net.mackdoodler.butchery.common.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class ButcheryItems {

	public static DivinationRod divinationRod = new DivinationRod("divination_rod");	//remove for release

	public static ButcherCleaver butcherCleaver = new ButcherCleaver("butcher_cleaver");
	public static ItemButcherMat itemButcherMat = new ItemButcherMat("item_butcher_mat");
	public static ItemCorpse itemCorpse = new ItemCorpse("item_corpse");
	public static ItemSlimeyCore itemSlimeyCore = new ItemSlimeyCore("slimey_core");
	
	public static ItemLunarSeed lunarSeed = new ItemLunarSeed("lunar_seed");
	public static ItemTranquilizerFood itemTranquilizer = new ItemTranquilizerFood("tranquilizer");
	public static ItemThrowableDust lunarDust = new ItemThrowableDust("lunar_lilly_dust");
	
	public static void register(IForgeRegistry<Item> registry) {
		
		registry.registerAll(
				divinationRod,
				
				itemButcherMat,
				butcherCleaver,
				itemCorpse,
				itemSlimeyCore,
				lunarSeed,
				itemTranquilizer,
				lunarDust
		);
		
	}

	public static void registerModels() {
		divinationRod.registerItemModel();
		
		itemButcherMat.registerItemModel();
		butcherCleaver.registerItemModel();
		itemCorpse.registerItemModel();
		itemSlimeyCore.registerItemModel();
		lunarSeed.registerItemModel();
		itemTranquilizer.registerItemModel();
		lunarDust.registerItemModel();
	}

}
