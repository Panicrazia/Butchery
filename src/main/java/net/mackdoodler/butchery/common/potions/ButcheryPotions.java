package net.mackdoodler.butchery.common.potions;

import net.mackdoodler.butchery.ButcheryMod;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ButcheryPotions {
	
	public static final Potion DROWSY_POTION = new PotionDrowsy().setRegistryName(new ResourceLocation(ButcheryMod.MODID+":drowsy"));
	
	public static void init() {
		GameRegistry.findRegistry(Potion.class).register(DROWSY_POTION);
	}
}
