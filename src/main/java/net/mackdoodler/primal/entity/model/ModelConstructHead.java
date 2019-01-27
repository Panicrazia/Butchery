package net.mackdoodler.primal.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * ModelConstructHead - Lezarius
 * Created using Tabula 7.0.0
 */
public class ModelConstructHead extends ModelBase {
    public ModelRenderer head;

    public ModelConstructHead() {
        this.textureWidth = 32;
        this.textureHeight = 16;
        this.head = new ModelRenderer(this, 0, 0);
        this.head.setRotationPoint(0F, 22.0F, 4.0F);
        this.head.addBox(-4.0F, -6.0F, -8.0F, 8, 8, 8, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.head.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
