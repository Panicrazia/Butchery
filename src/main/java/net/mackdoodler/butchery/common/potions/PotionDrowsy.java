package net.mackdoodler.butchery.common.potions;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionDrowsy extends Potion{
	public static ResourceLocation POTION_ICONS = new ResourceLocation("butchery:textures/gui/potion_icons.png");
	public final String PotionName = "drowsy";
	
	public PotionDrowsy() {
		super(true, 11596542);
		setPotionName("effect." + PotionName);
		super.setIconIndex(0,0);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public int getStatusIconIndex() {
		Minecraft.getMinecraft().getTextureManager().bindTexture(POTION_ICONS);
		return super.getStatusIconIndex();
	}
}
