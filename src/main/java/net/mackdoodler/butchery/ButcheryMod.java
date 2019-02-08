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

@Mod(modid = ButcheryMod.MODID, name =ButcheryMod.MODNAME, version = ButcheryMod.VERSION, acceptedMinecraftVersions = "[1.12.2]", dependencies = "after:crafttweaker;")
public class ButcheryMod {
	/* 
	 * butchery mod ideas TODO:
	 * 
	 * placeable drugged meat that carnivores and zombies path to and eat
	 * 
	 * tranquilizer splash potions using the drowsy potion effect which continuously applies more tranquilizers up to like 20 or some shit i dunno
	 * 
	 * tranquilizer arrows (5 tranq per?)
	 * 
	 * item which places down a vegitized slime, crafted with 4 slimeballs in a square
	 * 	^possibly a custom entityItem for these which when put into the world destroys itself and spawns a vegitized slime
	 *  ^slimes spawned with this item dont despawn
	 * 
	 * add oredict in recipes
	 * 
	 * test if ocelots drop the correct item
	 * 
	 * large slimes leave behind a slime residue (basically the thinnest snow layer but slimey) that slows and can be shovelled for a slimeball, or knocked away into slimeballs using water
	 */
	
	public static final String MODID = "butchery";
	public static final String MODNAME = "Butchery";
	public static final String VERSION = "1.0.0";
	
	@Mod.Instance(MODID)
	public static ButcheryMod instance;
	
	public static final ButcheryCreativeTab creativeTab = new ButcheryCreativeTab();
	
	@SidedProxy(serverSide = "net.mackdoodler.butchery.common.CommonProxy", clientSide = "net.mackdoodler.butchery.client.ClientProxy")
	public static IProxy proxy;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		System.out.println(MODNAME + " is loading!");
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
