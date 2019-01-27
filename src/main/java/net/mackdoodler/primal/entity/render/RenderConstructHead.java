package net.mackdoodler.primal.entity.render;

import net.mackdoodler.primal.PrimalMod;
import net.mackdoodler.primal.entity.construct.EntityConstructHead;
import net.mackdoodler.primal.entity.model.ModelConstructHead;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderConstructHead extends RenderLiving{

	public static final Factory FACTORY = new Factory();

	public RenderConstructHead(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelConstructHead(), 0.5F);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		// TODO Auto-generated method stub
		return new ResourceLocation(PrimalMod.modId+":textures/entity/construct_head1.png");
	}
	
    public static class Factory implements IRenderFactory<EntityConstructHead> {

        @Override
        public Render<? super EntityConstructHead> createRenderFor(RenderManager manager) {
            return new RenderConstructHead(manager);
        }

    }
}
