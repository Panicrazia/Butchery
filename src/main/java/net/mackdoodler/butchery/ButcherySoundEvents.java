package net.mackdoodler.butchery;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

public class ButcherySoundEvents {
	
	public static final SoundEvent MACHETE_CHOP = createSoundEvent("machete.chop");

	/**
	 * Create a {@link SoundEvent}.
	 *
	 * @param soundName The SoundEvent's name
	 * @return The SoundEvent
	 */
	private static SoundEvent createSoundEvent(String soundName) {
		final ResourceLocation soundID = new ResourceLocation(ButcheryMod.modId, soundName);
		return new SoundEvent(soundID).setRegistryName(soundID);
	}

	@Mod.EventBusSubscriber
	public static class RegistrationHandler {
		@SubscribeEvent
		public static void registerSoundEvents(RegistryEvent.Register<SoundEvent> event) {
			event.getRegistry().registerAll(
					MACHETE_CHOP
			);
		}
	}
}
