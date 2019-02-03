package net.mackdoodler.primal.proxy;

import net.mackdoodler.primal.PrimalMod;
import net.mackdoodler.primal.blocks.ModBlocks;
import net.mackdoodler.primal.client.renderer.tileentity.TileEntityCorpseRenderer;
import net.mackdoodler.primal.entity.PrimalEntities;
import net.mackdoodler.primal.particles.SleepParticles;
import net.mackdoodler.primal.tileentity.TileEntityCorpse;
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

public class ClientProxy extends CommonProxy{

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
	
	@Override
	public void registerItemRenderer(Item item, int meta, String id) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(PrimalMod.modId + ":" + id, "inventory"));
	}
	
	@Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
        PrimalEntities.initModels();
        OBJLoader.INSTANCE.addDomain(PrimalMod.modId);
    }
	
	@Override
    public void init(FMLInitializationEvent e) {
        super.init(e);
    }
	
	@Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
    }
}
