package net.mackdoodler.primal.entity;

import net.mackdoodler.primal.PrimalMod;
import net.mackdoodler.primal.entity.construct.EntityConstructBeefyBody;
import net.mackdoodler.primal.entity.construct.EntityConstructPreview;
import net.mackdoodler.primal.entity.construct.EntityConstructHead;
import net.mackdoodler.primal.entity.render.RenderConstructBeefyBody;
import net.mackdoodler.primal.entity.render.RenderConstructPreview;
import net.mackdoodler.primal.entity.render.RenderCorpse;
import net.mackdoodler.primal.entity.render.RenderConstructHead;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PrimalEntities {
	
	
	 public static void init() {
        int id = 0;
        /*
        EntityRegistry.registerModEntity(new ResourceLocation(PrimalMod.modId, "entity_corpse"), EntityCorpse.class, "entity_corpse", id++, PrimalMod.instance, 64, 3, true);
        EntityRegistry.registerModEntity(new ResourceLocation(PrimalMod.modId, "construct_preview"), EntityConstructPreview.class, "construct_preview", id++, PrimalMod.instance, 64, 3, true);
        EntityRegistry.registerModEntity(new ResourceLocation(PrimalMod.modId, "construct_head"), EntityConstructHead.class, "construct_head", id++, PrimalMod.instance, 64, 3, true);
        EntityRegistry.registerModEntity(new ResourceLocation(PrimalMod.modId, "construct_beefy_body"), EntityConstructBeefyBody.class, "construct_beefy_body", id++, PrimalMod.instance, 64, 3, true);
        */
    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
    	/*
        RenderingRegistry.registerEntityRenderingHandler(EntityCorpse.class, RenderCorpse.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityConstructPreview.class, RenderConstructPreview.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityConstructHead.class, RenderConstructHead.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityConstructBeefyBody.class, RenderConstructBeefyBody.FACTORY);
        */
    }
    
    
}
