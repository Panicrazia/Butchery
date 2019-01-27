package net.mackdoodler.primal.proxy;

import net.mackdoodler.primal.capabilities.IMonsterAI;
import net.mackdoodler.primal.capabilities.MonsterAI;
import net.mackdoodler.primal.capabilities.MonsterAIStorage;
import net.mackdoodler.primal.entity.PrimalEntities;
import net.mackdoodler.primal.handlers.CorpseHandler;
import net.mackdoodler.primal.handlers.MobHandler;
import net.mackdoodler.primal.items.PrimalItems;
import net.mackdoodler.primal.potions.PrimalPotions;
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
		// TODO Auto-generated method stub
		
	}

	public void preInit(FMLPreInitializationEvent e) {
		// TODO Auto-generated method stub
		PrimalPotions.init();
		PrimalEntities.init();
		CapabilityManager.INSTANCE.register(IMonsterAI.class, new MonsterAIStorage(), MonsterAI::new);
		/* this one down below also works
		CapabilityManager.INSTANCE.register(IMonsterAI.class, new MonsterAIStorage(), () -> new MonsterAI());
		*/
	}

	public void init(FMLInitializationEvent e) {
		// TODO Auto-generated method stub
		MobHandler.makeButcheryList();
		CorpseHandler.makeTranqResistances();
	}

	public void postInit(FMLPostInitializationEvent e) {
		// TODO Auto-generated method stub
		
	}

}
