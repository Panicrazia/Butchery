package net.mackdoodler.butchery.client;

import net.mackdoodler.butchery.ButcheryMod;
import net.mackdoodler.butchery.client.particles.SleepParticles;
import net.mackdoodler.butchery.client.renderer.tileentity.TileEntityCorpseRenderer;
import net.mackdoodler.butchery.common.CommonProxy;
import net.mackdoodler.butchery.common.blocks.ButcheryBlocks;
import net.mackdoodler.butchery.common.tileentity.TileEntityCorpse;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy {

	private static final float SLEEP_PARTICLE_GENERATION_CONSTANT = .5f;
	
	public static void spawnSleepParticlesForEntity(EntityLivingBase entity){
		SleepParticles particle = new SleepParticles(Minecraft.getMinecraft().getTextureManager(), entity.getEntityWorld(), 
				entity.posX + entity.world.rand.nextFloat() * entity.width * SLEEP_PARTICLE_GENERATION_CONSTANT*2 - entity.width * SLEEP_PARTICLE_GENERATION_CONSTANT, 
				entity.posY + 0.5D + entity.world.rand.nextFloat() * entity.height * .5F, 
				entity.posZ + entity.world.rand.nextFloat() * entity.width * SLEEP_PARTICLE_GENERATION_CONSTANT*2 - entity.width * SLEEP_PARTICLE_GENERATION_CONSTANT,
				0f,
				0f);
		
		Minecraft.getMinecraft().effectRenderer.addEffect(particle);
	}
	
	public static void spawnSleepParticles(World worldIn, double posXIn, double posYIn, double posZIn, double motionXIn, double motionZIn){
		SleepParticles particle = new SleepParticles(Minecraft.getMinecraft().getTextureManager(), worldIn, posXIn, posYIn, posZIn, motionXIn, motionZIn);
		Minecraft.getMinecraft().effectRenderer.addEffect(particle);
	}
	
	public void registerItemRenderer(Item item, int meta, String id) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(ButcheryMod.modId + ":" + id, "inventory"));
	}
	
    public void preInit(FMLPreInitializationEvent e) {
        OBJLoader.INSTANCE.addDomain(ButcheryMod.modId);
    }
	
    public void init(FMLInitializationEvent e) {
    }
	
    public void postInit(FMLPostInitializationEvent e) {
    }
}
