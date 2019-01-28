package net.mackdoodler.primal;

import net.mackdoodler.primal.blocks.ModBlocks;
import net.mackdoodler.primal.handlers.CapabilityHandler;
import net.mackdoodler.primal.handlers.CorpseHandler;
import net.mackdoodler.primal.items.ButcheryItems;
import net.mackdoodler.primal.potions.PotionEventHandler;
import net.mackdoodler.primal.proxy.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = PrimalMod.modId, name =PrimalMod.name, version = PrimalMod.version, acceptedMinecraftVersions = "[1.12.2]" )
public class PrimalMod {
	/*
	 * various TODO:
	 * make netherspores better and an actual thing <-- FUCK THAT LOL HAVE YOU SEEN TAHT NEW MOD BetterNether FOR 1.12.2 ITS FUCKING AAAAMAZING
	 * djinns? they would be dope af and do dope things
	 * krakrows, big ass pterodactyl things
	 * jaw masks
	 * krakraw beak plague masks that keep you from dying when you are in miasma
	 * hellfire furnace or whatever that has no actual inventory but shoots a large stream forward that cooks all items in its area
	 * 	augment runes could be air ones that push the (now cooked) items to the end of the furnace stream, allowing them to be picked up with some automation
	 * lab glass: literally just nicer looking green glass(or orange, whichever color formaldehyde isnt)
	 * redo rune sockets to not suck via capabilities
	 */
	
	/*
	 * meathook that can function as a grappling hook when you fire it and hits <something>, a pudge hook when it hits an enemy, 
	 * 			and places a meathook hanging block when shift clicking
	 * 
	 * meathook hanging blocks have a meathook hanging entity dangling attatched by a chain(think lead/dragon crystals), the meathook hanger can be powered/unpowered (or maybe use some winch block or something i dunno) to raise and lower the entity that is connected to it
	 * 
	 * ALSO minecraft.entity.MultiPartEntity EXISTS LOOK AT THE (it says dragon in the class so im assuming dragon) DRAGON CODE TO SEE HOW ITS IMPLIMENTED
	 * ^NOPE DRAGON CODE IS BASICALLY FUCKING WORTHLESS, THANKS MOJANG THERES NOT EVEN A LANDING ANIMATION WTF
	 */
	
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
	
	/*
	 * necromancy mod TODO:
	 * 
	 * Stitchery block (multiblock? ;o) where you just put items in and then the given body part comes out, def multiblock to utilize runes
	 * 
	 * parts to add:
	 * X eyes
	 * X light bones (birds)
	 * X brains
	 * get fluids (blood and squid ink) from putting a calidarium under the center of the butcher mat?
	 * X hoofs
	 * skulls (just the normal skeleton brand should be fine), but still listed here just incase
	 * X glands (poison, gunpowder, silk, etc)(you can open them by right clicking to get a decent amount or use them in creations)
	 * X skin (flesh)
	 * X guts
	 */
	
	public static final String modId = "primal";
	public static final String name = "Primal";
	public static final String version = "1.0.0";
	
	@Mod.Instance(modId)
	public static PrimalMod instance;
	
	public static final PrimalCreativeTab creativeTab = new PrimalCreativeTab();
	
	@SidedProxy(serverSide = "net.mackdoodler.primal.proxy.CommonProxy", clientSide = "net.mackdoodler.primal.proxy.ClientProxy")
	public static CommonProxy proxy;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		System.out.println(name + " is loading!");
		MinecraftForge.EVENT_BUS.register(new PotionEventHandler());
		MinecraftForge.EVENT_BUS.register(new CorpseHandler());
		MinecraftForge.EVENT_BUS.register(new CapabilityHandler());
		proxy.preInit(event);
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
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
			ModBlocks.register(event.getRegistry());
		}
		
		@SubscribeEvent
		public static void registerItems(RegistryEvent.Register<Item> event) {
			ButcheryItems.register(event.getRegistry());
			ModBlocks.registerItemBlocks(event.getRegistry());
		}
		
		@SubscribeEvent
		public static void registerItems(ModelRegistryEvent event) {
			ButcheryItems.registerModels();
			ModBlocks.registerModels();
		}
	}
}
