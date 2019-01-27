package net.mackdoodler.primal.potions;

import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class PrimalPotions {
	
	public static final Potion DROWSY_POTION = new PotionDrowsy().setRegistryName(new ResourceLocation("primal:drowsy"));
	
	public static void init() {
		GameRegistry.findRegistry(Potion.class).register(DROWSY_POTION);
	}
}
