package net.mackdoodler.primal.client.renderer.tileentity;

import java.util.List;

import org.lwjgl.opengl.GL11;

import net.mackdoodler.primal.blocks.ModBlocks;
import net.mackdoodler.primal.tileentity.TileEntityCorpse;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.pipeline.LightUtil;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityCorpseRenderer extends TileEntitySpecialRenderer<TileEntityCorpse> {

	/*
	 * things that dont render how I want them to and how they dont
	 * 
	 * horse fails at UpdateHorseSlots when making the entity (due to it only returning when serverside)
	 * donkey 			'
	 * mule				'
	 * llama			'
	 * skeleton horse	'
	 * zombie horse		'
	 * 
	 * slimes for whatever reason are placed strangely, should be fine with the same code but slight changes
	 * magma slimes		'
	 * alternatively a small goop thing intead of the blood and bones would work for me
	 * 
	 * wither/enderdragon need custom models cuz they are bosses and dont work
	 * 
	 * do it via a whitelist rather than a blacklist
	 * 
	 */

	//mabs have a random number be generated 0-3 or 7 on creation to make the meat model random

	private static final int INVENTORY_ROTATION = 0;

	private final String corpseType;

	public TileEntityCorpseRenderer(String corpseType) {
		this.corpseType = corpseType;
	}

	public TileEntityCorpseRenderer() {
		this(null);
	}

	@Override
	public void render(TileEntityCorpse corpse, double x, double y, double z, float partialTick, int destroyProgress, float alpha) {
		
		//if the corpse has an animal and the corpse is unharmed (unbutchered)
		if (corpse.getEntiyIdentity() != null && corpse.getUnharmed()) {
				renderCorpseEntity(corpse, x + 0.5, y, z + 0.5, corpse.getWorld());
		}
		else{
			renderPileOfMeat(corpse, x, y, z);
		}
	}

	private void renderPileOfMeat(TileEntityCorpse corpse, double x, double y, double z) {

		//Entity entity = EntityList.createEntityByIDFromName(new ResourceLocation(corpse.getEntiyIdentity()), null);
		
		GlStateManager.pushMatrix();

		
        RenderHelper.disableStandardItemLighting();
        this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        if (Minecraft.isAmbientOcclusionEnabled()) {
            GlStateManager.shadeModel(GL11.GL_SMOOTH);
        } else {
            GlStateManager.shadeModel(GL11.GL_FLAT);
        }

        World world = corpse.getWorld();

		//GL11.glScalef((entity.height*entity.width), (entity.height*entity.width), (entity.height*entity.width));
		
        GlStateManager.translate(-corpse.getPos().getX(), -corpse.getPos().getY(), -corpse.getPos().getZ());

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        //bufferBuilder.setTranslation(x/(entity.height*entity.width), y/(entity.height*entity.width), z/(entity.height*entity.width));
        bufferBuilder.setTranslation(x, y, z);
        bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);

		
        IBlockState state = ModBlocks.blockCorpse.getDefaultState();
        BlockRendererDispatcher dispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
        IBakedModel model = dispatcher.getModelForState(state);
        dispatcher.getBlockModelRenderer().renderModel(world, model, state, corpse.getPos(), bufferBuilder, true);
        tessellator.draw();

        bufferBuilder.setTranslation(0, 0, 0);
        
        RenderHelper.enableStandardItemLighting();
        GlStateManager.popMatrix();
		
		
	}

	private void renderCorpseEntity(TileEntityCorpse corpse, double x, double y, double z, World world) {
		
		switch(corpse.getEntiyIdentity()){
		//works fine with current code
		case "minecraft:cow":
		case "minecraft:pig":
		case "minecraft:chicken":
		case "minecraft:sheep":
		case "minecraft:bat":
		case "minecraft:creeper":
		case "minecraft:enderman":
		case "minecraft:evocation_illager":
		case "minecraft:husk":
		case "minecraft:ocelot":
		case "minecraft:mooshroom":
		case "minecraft:skeleton":
		case "minecraft:rabbit":
		case "minecraft:polar_bear":
		case "minecraft:parrot":
		case "minecraft:stray":
		case "minecraft:vex":
		case "minecraft:villager":
		case "minecraft:vindication_illager":
		case "minecraft:witch":
		case "minecraft:wither_skeleton":
		case "minecraft:wolf":
		case "minecraft:zombie":
		case "minecraft:zombie_villager":
		case "minecraft:zombie_pigman":

		//should not be rotated
		case "minecraft:cave_spider":
		case "minecraft:endermite":
		case "minecraft:silverfish":
		case "minecraft:spider":

		//seems sort of weird but otherwise is fine
		case "minecraft:blaze"://looks empty cuz of the blaze design
		case "minecraft:guardian"://tail moves really horribly
		case "minecraft:squid"://seems off center because of tentacles
			
		//too big or something along those lines
		case "minecraft:elder_guardian"://tail also moves really horribly
		case "minecraft:ghast":
			
		//the slime problem
		case "minecraft:magma_cube":
		case "minecraft:slime":
			
			//Class<? extends Entity> entity = EntityList.getClass(new ResourceLocation(type));
			Entity entity = EntityList.createEntityByIDFromName(new ResourceLocation(corpse.getEntiyIdentity()), null);
			if (entity != null) {
				GL11.glPushMatrix();
				GL11.glTranslated(x, y + 0.2, z);
				GL11.glTranslatef(-(entity.height/2), 0, 0);
				GL11.glRotatef(270f, 0, 0, 1f);
				//final double ratio = type.getScale();
				//GL11.glScaled(.5, .5, .5);
				
				World renderWorld = world;
				
				if (renderWorld != null) {
	
					//Render<Entity> renderer = Minecraft.getMinecraft().getRenderManager().getEntityClassRenderObject(EntityList.getClass(new ResourceLocation(type)));
					
					Render<Entity> renderer = Minecraft.getMinecraft().getRenderManager().getEntityRenderObject(entity);
					
					// yeah we don't care about fonts, but we do care that the
					// renderManager is available
					if (renderer != null && renderer.getFontRendererFromRenderManager() != null) {
	
						/*
						final boolean blurLast;
						final boolean mipmapLast;
						final AbstractTexture blocksTexture = getBlockTexture();
						if (blocksTexture != null) {
							blurLast = blocksTexture.blurLast;
							mipmapLast = blocksTexture.mipmapLast;
						} else {
							blurLast = false;
							mipmapLast = false;
						}
	
						GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
						final boolean lightmapEnabled = GL11.glGetBoolean(GL11.GL_TEXTURE_2D);
						GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
						*/
						
						synchronized (entity) {
							entity.world = renderWorld;
							renderer.doRender(entity, 0, 0, 0, 0, 0);
							entity.world = null;
						}
						
						/*
						GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
						if (lightmapEnabled) GlStateManager.enableTexture2D();
						else GlStateManager.disableTexture2D();
						GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
	
						if (blocksTexture != null) {
							blocksTexture.setBlurMipmap(blurLast, mipmapLast);
						}
						*/
					}
				}
				GL11.glPopMatrix();
			}
			
			break;
			
		//shit just doesnt work
		case "minecraft:horse":
		case "minecraft:donkey":
		case "minecraft:shukler":
		case "minecraft:skeleton_horse":
		case "minecraft:zombie_horse":
		case "minecraft:mule":
		case "minecraft:llama":

		//bosses which need custom models
		case "minecraft:wither":
		case "minecraft:ender_dragon":
			
		default:
			renderPileOfMeat(corpse, x - 0.5, y, z - 0.5);
			break;
		}
	}
}
	
