package net.mackdoodler.primal.client.events;

import net.mackdoodler.primal.PrimalMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(modid = PrimalMod.modId)
public class ClientEventHandler {

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onTextureStitch(TextureStitchEvent event) {
    	System.out.println("yesss the tehinx call");
        event.getMap().registerSprite(new ResourceLocation(PrimalMod.modId, "particles/sleep_particle"));
    }

    /*
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        ParticleRenderer.getParticleRenderer().update();
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onClientRender(RenderWorldLastEvent event) {
        ParticleRenderer.getParticleRenderer().render(event.getPartialTicks());
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onPreRender(RenderGameOverlayEvent.Pre e) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.player;
        if (player == null)
            return;    // just in case

        if (e.getType() == RenderGameOverlayEvent.ElementType.HOTBAR) {
            RenderRitualOverlay.getRenderRitualOverlay().renderOverlays(mc, e.getResolution().getScaledWidth(), e.getResolution().getScaledHeight());
        }
    }*/
}