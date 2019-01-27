package net.mackdoodler.primal.items;

import net.mackdoodler.primal.items.bodyparts.BodyPartBone;
import net.mackdoodler.primal.items.bodyparts.BodyPartBrain;
import net.mackdoodler.primal.items.bodyparts.BodyPartEye;
import net.mackdoodler.primal.items.bodyparts.BodyPartFlesh;
import net.mackdoodler.primal.items.bodyparts.BodyPartFoot;
import net.mackdoodler.primal.items.bodyparts.BodyPartGland;
import net.mackdoodler.primal.items.bodyparts.BodyPartMisc;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class PrimalItems {

	public static ItemBase ingotCopper = new ItemBase("ingot_copper");
	public static ItemBase ornateRod = new ItemBase("ornate_rod");
	public static PrimalCodex primalCodex = new PrimalCodex();
	public static ButcherCleaver butcherCleaver = new ButcherCleaver("butcher_cleaver");
	public static DivinationRod divinationRod = new DivinationRod();
	public static TuningFork tuningFork = new TuningFork("tuning_fork");
	
	public static ItemRune itemRune = new ItemRune();
	public static ItemButcherMat itemButcherMat = new ItemButcherMat("item_butcher_mat");
	public static ItemCorpse itemCorpse = new ItemCorpse("item_corpse");
	
	//reame the mango paste to mango dust, keep it as paste in the localization
	public static ItemMangoSeed mangoSeed = new ItemMangoSeed();
	public static ItemMango mango = new ItemMango();
	public static ItemBase mangoPaste = new ItemBase("mango_paste");
	
	public static ItemLunarSeed lunarSeed = new ItemLunarSeed();
	public static ItemTranquilizerFood itemTranquilizer = new ItemTranquilizerFood("tranquilizer");
	public static ItemThrowableDust lunarDust = new ItemThrowableDust("lunar_lilly_dust");
	
	public static BodyPartBone bodyPartBone = new BodyPartBone("body_part_bone");
	public static BodyPartBrain bodyPartBrain = new BodyPartBrain("body_part_brain");
	public static BodyPartEye bodyPartEye = new BodyPartEye("body_part_eye");
	public static BodyPartFlesh bodyPartFlesh = new BodyPartFlesh("body_part_flesh");
	public static BodyPartFoot bodyPartFoot = new BodyPartFoot("body_part_foot");
	public static BodyPartGland bodyPartGland = new BodyPartGland("body_part_gland");
	public static BodyPartMisc bodyPartMisc = new BodyPartMisc("body_part_misc");
	
	//public static ItemBodyPartSpawn itemBodyPartSpawn = new ItemBodyPartSpawn("item_body_part_spawn");
	
	
	public static void register(IForgeRegistry<Item> registry) {
		
		registry.registerAll(
				ingotCopper,
				primalCodex,
				divinationRod,
				ornateRod,
				itemRune,
				tuningFork,
				
				itemButcherMat,
				butcherCleaver,
				itemCorpse,
				
				mangoSeed,
				mango,
				mangoPaste,
				
				lunarSeed,
				itemTranquilizer,
				lunarDust,
				
				bodyPartBone,
				bodyPartBrain,
				bodyPartEye,
				bodyPartFlesh,
				bodyPartFoot,
				bodyPartGland,
				bodyPartMisc//,
				
				//itemBodyPartSpawn
		);
		
	}

	public static void registerModels() {
		ornateRod.registerItemModel();
		ingotCopper.registerItemModel();
		primalCodex.registerItemModel();
		divinationRod.registerItemModel();
		itemRune.registerItemModel();
		tuningFork.registerItemModel();
		
		itemButcherMat.registerItemModel();
		butcherCleaver.registerItemModel();
		itemCorpse.registerItemModel();
		
		mangoSeed.registerItemModel();
		mango.registerItemModel();
		mangoPaste.registerItemModel();
		
		lunarSeed.registerItemModel();
		itemTranquilizer.registerItemModel();
		lunarDust.registerItemModel();
		
		bodyPartBone.registerItemModel();
		bodyPartBrain.registerItemModel();
		bodyPartEye.registerItemModel();
		bodyPartFlesh.registerItemModel();
		bodyPartFoot.registerItemModel();
		bodyPartGland.registerItemModel();
		bodyPartMisc.registerItemModel();
		
		//itemBodyPartSpawn.registerItemModel();
	}

}
