package net.mackdoodler.primal.entity.render;

import net.mackdoodler.primal.PrimalMod;
import net.mackdoodler.primal.entity.construct.EntityConstructPreview;
import net.mackdoodler.primal.entity.model.ModelConstructPreview;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderConstructPreview extends RenderLiving{

	public static final Factory FACTORY = new Factory();

	public RenderConstructPreview(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelConstructPreview(), 0.5F);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		// TODO Auto-generated method stub
		return new ResourceLocation(PrimalMod.modId+":textures/entity/construct_body2.png");
	}
	

    public static class Factory implements IRenderFactory<EntityConstructPreview> {

        @Override
        public Render<? super EntityConstructPreview> createRenderFor(RenderManager manager) {
            return new RenderConstructPreview(manager);
        }

    }
}
