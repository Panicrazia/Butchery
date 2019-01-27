package net.mackdoodler.primal.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * ModelConstructBeefyBody - Lezarius
 * Created using Tabula 7.0.0
 */
public class ModelConstructBeefyBody extends ModelBase {
    public ModelRenderer bodyCore;
    public ModelRenderer bodyMiddle;
    public ModelRenderer bodyLower;

    public ModelConstructBeefyBody() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.bodyLower = new ModelRenderer(this, 3, 3);
        this.bodyLower.setRotationPoint(0.0F, 8.0F, 0.0F);
        this.bodyLower.addBox(-8.5F, 0.0F, -7.8F, 17, 7, 17, 0.0F);
        this.bodyCore = new ModelRenderer(this, 0, 0);
        this.bodyCore.mirror = true;
        this.bodyCore.setRotationPoint(0.0F, 3.0F, 1.0F);
        this.bodyCore.addBox(-13.0F, -10.0F, -10.0F, 26, 19, 20, 0.0F);
        this.bodyMiddle = new ModelRenderer(this, 2, 2);
        this.bodyMiddle.setRotationPoint(0.0F, 6.0F, -1.5F);
        this.bodyMiddle.addBox(-10.0F, 0.0F, -8.0F, 20, 10, 18, 0.0F);
        this.bodyMiddle.addChild(this.bodyLower);
        this.bodyCore.addChild(this.bodyMiddle);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.bodyCore.render(f5);
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
