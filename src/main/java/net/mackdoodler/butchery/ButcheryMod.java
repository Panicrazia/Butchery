package net.mackdoodler.butchery;

import net.mackdoodler.butchery.api.ButcheryEntries;
import net.mackdoodler.butchery.api.TranquilizerHandler;
import net.mackdoodler.butchery.common.CommonProxy;
import net.mackdoodler.butchery.common.blocks.ButcheryBlocks;
import net.mackdoodler.butchery.common.capabilities.CapabilityHandler;
import net.mackdoodler.butchery.common.capabilities.ITranquilizer;
import net.mackdoodler.butchery.common.capabilities.Tranquilizer;
import net.mackdoodler.butchery.common.capabilities.TranquilizerStorage;
import net.mackdoodler.butchery.common.handlers.CorpseHandler;
import net.mackdoodler.butchery.common.handlers.SlimeHandler;
import net.mackdoodler.butchery.common.items.ButcheryItems;
import net.mackdoodler.butchery.common.potions.ButcheryPotions;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = ButcheryMod.modId, name =ButcheryMod.name, version = ButcheryMod.version, acceptedMinecraftVersions = "[1.12.2]" )
public class ButcheryMod {
	/* 
	 * butchery mod TODO:
	 * 
	 * placeable drugged meat that animals and zombies eat
	 * 
	 * expand tranqilizer system even moar
	 * 
	 * sleepy dust particles
	 * 	-use with lunar dust, slime explosions and if possible instead of the vanilla potion particles
	 * 
	 * clean up and disconnect from all this primal bullshit for release
	 */
	
	public static final String modId = "butchery";
	public static final String name = "Butchery";
	public static final String version = "1.0.0";
	
	@Mod.Instance(modId)
	public static ButcheryMod instance;
	
	public static final ButcheryCreativeTab creativeTab = new ButcheryCreativeTab();
	
	@SidedProxy(serverSide = "net.mackdoodler.butchery.common.CommonProxy", clientSide = "net.mackdoodler.butchery.client.ClientProxy")
	public static CommonProxy proxy;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		System.out.println(name + " is loading!");
		MinecraftForge.EVENT_BUS.register(new CorpseHandler());
		MinecraftForge.EVENT_BUS.register(new CapabilityHandler());
		MinecraftForge.EVENT_BUS.register(new SlimeHandler());
		MinecraftForge.EVENT_BUS.register(new TranquilizerHandler());
		
		ButcheryPotions.init();
		CapabilityManager.INSTANCE.register(ITranquilizer.class, new TranquilizerStorage(), Tranquilizer::new);
		
		proxy.preInit(event);
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		ButcheryEntries.makeButcheryList();
		TranquilizerHandler.setTranqResistances();
		
		proxy.init(event);
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
		proxy.postInit(event);
	}
	
	@Mod.EventBusSubscriber
	public static class RegistrationHandler {
		
		@SubscribeEvent
		public static void registerBlocks(RegistryEvent.Register<Block> event) {
			ButcheryBlocks.register(event.getRegistry());
		}
		
		@SubscribeEvent
		public static void registerItems(RegistryEvent.Register<Item> event) {
			ButcheryItems.register(event.getRegistry());
			ButcheryBlocks.registerItemBlocks(event.getRegistry());
		}
		
		@SubscribeEvent
		public static void registerItems(ModelRegistryEvent event) {
			ButcheryItems.registerModels();
			ButcheryBlocks.registerModels();
		}
	}
}
