package net.mackdoodler.primal.entity.render;

import net.mackdoodler.primal.PrimalMod;
import net.mackdoodler.primal.entity.EntityCorpse;
import net.mackdoodler.primal.entity.construct.EntityConstructPreview;
import net.mackdoodler.primal.entity.model.ModelConstructHead;
import net.mackdoodler.primal.entity.model.ModelConstructPreview;
import net.mackdoodler.primal.entity.render.RenderConstructPreview.Factory;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.model.ModelArmorStand;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderCorpse extends RenderLivingBase<EntityCorpse>{

	public static final Factory FACTORY = new Factory();

	/*
	 * most likely need a hashmap or something with all the eligible models, 
	 * downside is that all corpses that have a model before turning into a pile of meat
	 * for things that dont have a model in the map they can just have the normal pile of meat model for the entire time
	 * 
	 * alternatively if I really want to I can go full morph by iChun and just use black magic to get the model, but fuck that
	 */
	
	public ModelBase modelToUse;
	public ModelBase meatyModel = new ModelConstructPreview();
	
	public RenderCorpse(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelConstructPreview(), 0.0f);
	}
	
	
	@Override
	protected ResourceLocation getEntityTexture(EntityCorpse entity) {
		// TODO Texture might have to be different depending on how this works
		return new ResourceLocation(PrimalMod.modId+":textures/entity/construct_body2.png");
	}
	
	@Override
    public void doRender(EntityCorpse entity, double x, double y, double z, float entityYaw, float partialTicks){
		
		GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y+1.5f, (float)z);
        GlStateManager.rotate(180.0f, 1.0f, 0, 0);
        
        //after all the translations and whatnot
		if((!entity.getUnharmed()) /*  && has a custom model available */){
			this.modelToUse = this.meatyModel;

	        this.bindEntityTexture(entity);
		}
		else{
			this.modelToUse = new ModelConstructHead();

	        //this.bindEntityTexture(entity);
		}

        
		this.modelToUse.render(entity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        //GlStateManager.translate(0, , 0);
        if (this.renderOutlines)
        {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }
        
        GlStateManager.popMatrix();
		//meatyModel.render(entity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		
        //super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

    public static class Factory implements IRenderFactory<EntityCorpse> {

        @Override
        public Render<? super EntityCorpse> createRenderFor(RenderManager manager) {
            return new RenderCorpse(manager);
        }

    }
}