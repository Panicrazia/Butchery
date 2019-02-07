package net.mackdoodler.butchery.common;

import net.mackdoodler.butchery.api.ButcheryEntries;
import net.mackdoodler.butchery.api.TranquilizerHandler;
import net.mackdoodler.butchery.common.capabilities.ITranquilizer;
import net.mackdoodler.butchery.common.capabilities.Tranquilizer;
import net.mackdoodler.butchery.common.capabilities.TranquilizerStorage;
import net.mackdoodler.butchery.common.items.ButcheryItems;
import net.mackdoodler.butchery.common.potions.ButcheryPotions;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

	public void registerItemRenderer(Item itemBase, int i, String name) {
	}

	public void preInit(FMLPreInitializationEvent e) {
	}

	public void init(FMLInitializationEvent e) {
	}

	public void postInit(FMLPostInitializationEvent e) {
	}

}
