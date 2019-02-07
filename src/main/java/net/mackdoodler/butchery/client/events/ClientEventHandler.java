package net.mackdoodler.butchery.client.events;

import net.mackdoodler.butchery.ButcheryMod;
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

@Mod.EventBusSubscriber(modid = ButcheryMod.modId)
public class ClientEventHandler {

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onTextureStitch(TextureStitchEvent event) {
    	//holy FUCK I spent literal DAYS trying to get particles working and THIS was the part I was missing jesus christ
        event.getMap().registerSprite(new ResourceLocation(ButcheryMod.modId, "particles/sleep_particle"));
    }
}