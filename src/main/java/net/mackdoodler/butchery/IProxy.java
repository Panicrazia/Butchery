package net.mackdoodler.butchery;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public interface IProxy {
	
	public void preInit(FMLPreInitializationEvent e);

	public void init(FMLInitializationEvent e);

	public void postInit(FMLPostInitializationEvent e);

	public void registerItemRenderer(Item item, int i, String name);
}
