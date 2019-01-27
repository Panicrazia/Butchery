package net.mackdoodler.primal.items;

import net.mackdoodler.primal.PrimalMod;
import net.mackdoodler.primal.potions.PrimalPotions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.oredict.OreDictionary;

public class ItemMango extends ItemFood {

	protected String name = "mango";
	
	public ItemMango() {
		super(3, 0.6f, false);
		setTranslationKey(name);
		setRegistryName(name);
		setCreativeTab(PrimalMod.creativeTab);
		
		this.setAlwaysEdible();
		this.setPotionEffect(new PotionEffect(MobEffects.LEVITATION, 50, 0, false, true), 1f);
	}
	
	public void registerItemModel(){
		PrimalMod.proxy.registerItemRenderer(this, 0, name);
	}
	
	public void initOreDict() {
		OreDictionary.registerOre("cropMango", this);
	}
	
}
