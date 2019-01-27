package net.mackdoodler.primal.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * ModelConstructBody - Lezarius
 * Created using Tabula 7.0.0
 */
public class ModelConstructPreview extends ModelBase {
    public ModelRenderer bodyCore;
    public ModelRenderer head;
    public ModelRenderer bodyLower;
    public ModelRenderer shoulderRight;
    public ModelRenderer shoulderLeft;
    public ModelRenderer bodyLower2;
    public ModelRenderer pelvisLow;
    public ModelRenderer pelvisUpp;
    public ModelRenderer pelvisLeft;
    public ModelRenderer pelvisRight;
    public ModelRenderer legLeftUpper;
    public ModelRenderer legLeft;
    public ModelRenderer footLeft;
    public ModelRenderer legRightUpper;
    public ModelRenderer legRight;
    public ModelRenderer footRight;
    public ModelRenderer armRight;
    public ModelRenderer armLowerRight;
    public ModelRenderer armLeft;
    public ModelRenderer armLowerLeft;

    public ModelConstructPreview() {
        this.textureWidth = 256;
        this.textureHeight = 128;
        this.armLowerRight = new ModelRenderer(this, 32, 68);
        this.armLowerRight.setRotationPoint(0.0F, 12.3F, 0.0F);
        this.armLowerRight.addBox(-3.5F, -2.0F, -3.1F, 7, 18, 8, 0.0F);
        this.setRotateAngle(armLowerRight, -0.22759093446006054F, 0.0F, -0.31869712141416456F);
        this.pelvisUpp = new ModelRenderer(this, 179, 30);
        this.pelvisUpp.setRotationPoint(0.0F, 0.0F, -0.6F);
        this.pelvisUpp.addBox(-7.0F, 0.0F, -6.1F, 14, 4, 14, 0.0F);
        this.footLeft = new ModelRenderer(this, 209, 48);
        this.footLeft.setRotationPoint(0.0F, 11.7F, 0.0F);
        this.footLeft.addBox(-4.0F, 0.0F, -7.0F, 8, 3, 13, 0.0F);
        this.setRotateAngle(footLeft, -0.22759093446006054F, 0.0F, 0.0F);
        this.shoulderLeft = new ModelRenderer(this, 198, 8);
        this.shoulderLeft.setRotationPoint(13.0F, -1.0F, -1.5F);
        this.shoulderLeft.addBox(0.0F, -8.0F, -7.0F, 8, 8, 14, 0.0F);
        this.legLeft = new ModelRenderer(this, 177, 48);
        this.legLeft.mirror = true;
        this.legLeft.setRotationPoint(-0.1F, 13.0F, -1.4F);
        this.legLeft.addBox(-4.0F, 0.0F, -2.0F, 8, 14, 8, 0.0F);
        this.setRotateAngle(legLeft, 0.29565877528783946F, 0.0F, 0.0F);
        this.bodyCore = new ModelRenderer(this, 0, 0);
        this.bodyCore.mirror = true;
        this.bodyCore.setRotationPoint(0.0F, -28.0F, 3.0F);
        this.bodyCore.addBox(-13.0F, -10.0F, -10.0F, 26, 19, 20, 0.0F);
        this.head = new ModelRenderer(this, 72, 0);
        this.head.setRotationPoint(0.0F, 2.6F, -10.0F);
        this.head.addBox(-4.0F, -6.0F, -8.0F, 8, 8, 8, 0.0F);
        this.footRight = new ModelRenderer(this, 124, 63);
        this.footRight.setRotationPoint(0.0F, 11.7F, 0.0F);
        this.footRight.addBox(-4.0F, 0.0F, -7.0F, 8, 3, 13, 0.0F);
        this.setRotateAngle(footRight, -0.22759093446006054F, 0.0F, 0.0F);
        this.legLeftUpper = new ModelRenderer(this, 48, 43);
        this.legLeftUpper.setRotationPoint(5.0F, 1.4F, 0.0F);
        this.legLeftUpper.addBox(-4.0F, 0.0F, -4.0F, 8, 14, 11, 0.0F);
        this.setRotateAngle(legLeftUpper, -0.048869219055841226F, -0.091106186954104F, 0.0F);
        this.pelvisLeft = new ModelRenderer(this, 0, 39);
        this.pelvisLeft.mirror = true;
        this.pelvisLeft.setRotationPoint(1.0F, 1.5F, -0.9F);
        this.pelvisLeft.addBox(0.0F, -2.0F, -5.2F, 10, 8, 14, 0.0F);
        this.setRotateAngle(pelvisLeft, 0.0F, -0.05235987755982988F, 0.0F);
        this.pelvisLow = new ModelRenderer(this, 155, 22);
        this.pelvisLow.setRotationPoint(0.0F, 8.0F, -1.0F);
        this.pelvisLow.addBox(-3.0F, 0.0F, -5.8F, 6, 6, 13, 0.0F);
        this.armRight = new ModelRenderer(this, 199, 64);
        this.armRight.setRotationPoint(-3.4F, 0.0F, 0.0F);
        this.armRight.addBox(-4.2F, -4.0F, -4.2F, 8, 18, 10, 0.0F);
        this.setRotateAngle(armRight, 0.045553093477052F, 0.091106186954104F, 0.136659280431156F);
        this.legRightUpper = new ModelRenderer(this, 86, 54);
        this.legRightUpper.setRotationPoint(-5.0F, 1.4F, 0.0F);
        this.legRightUpper.addBox(-4.0F, 0.0F, -4.0F, 8, 14, 11, 0.0F);
        this.setRotateAngle(legRightUpper, -0.048869219055841226F, 0.091106186954104F, 0.0F);
        this.legRight = new ModelRenderer(this, 0, 61);
        this.legRight.setRotationPoint(0.1F, 13.0F, -1.4F);
        this.legRight.addBox(-4.0F, 0.0F, -2.0F, 8, 14, 8, 0.0F);
        this.setRotateAngle(legRight, 0.29565877528783946F, 0.0F, 0.0F);
        this.armLeft = new ModelRenderer(this, 156, 70);
        this.armLeft.mirror = true;
        this.armLeft.setRotationPoint(3.8F, 0.0F, 0.0F);
        this.armLeft.addBox(-4.2F, -4.0F, -4.2F, 8, 18, 10, 0.0F);
        this.setRotateAngle(armLeft, 0.045553093477052F, -0.091106186954104F, -0.136659280431156F);
        this.pelvisRight = new ModelRenderer(this, 129, 41);
        this.pelvisRight.setRotationPoint(-1.0F, 1.5F, -0.9F);
        this.pelvisRight.addBox(-10.0F, -2.0F, -5.2F, 10, 8, 14, 0.0F);
        this.setRotateAngle(pelvisRight, 0.0F, 0.05235987755982988F, 0.0F);
        this.shoulderRight = new ModelRenderer(this, 168, 0);
        this.shoulderRight.mirror = true;
        this.shoulderRight.setRotationPoint(-13.0F, -1.0F, -1.5F);
        this.shoulderRight.addBox(-8.0F, -8.0F, -7.0F, 8, 8, 14, 0.0F);
        this.bodyLower2 = new ModelRenderer(this, 75, 28);
        this.bodyLower2.setRotationPoint(0.0F, 8.0F, 0.0F);
        this.bodyLower2.addBox(-8.5F, 0.0F, -7.8F, 17, 9, 17, 0.0F);
        this.bodyLower = new ModelRenderer(this, 92, 0);
        this.bodyLower.setRotationPoint(0.0F, 6.0F, -1.5F);
        this.bodyLower.addBox(-10.0F, 0.0F, -8.0F, 20, 10, 18, 0.0F);
        this.armLowerLeft = new ModelRenderer(this, 62, 71);
        this.armLowerLeft.setRotationPoint(-0.3F, 12.3F, 0.0F);
        this.armLowerLeft.addBox(-3.5F, -2.0F, -3.1F, 7, 18, 8, 0.0F);
        this.setRotateAngle(armLowerLeft, -0.22759093446006054F, 0.0F, 0.31869712141416456F);
        this.armRight.addChild(this.armLowerRight);
        this.pelvisLow.addChild(this.pelvisUpp);
        this.legLeft.addChild(this.footLeft);
        this.bodyCore.addChild(this.shoulderLeft);
        this.legLeftUpper.addChild(this.legLeft);
        this.bodyCore.addChild(this.head);
        this.legRight.addChild(this.footRight);
        this.pelvisLeft.addChild(this.legLeftUpper);
        this.pelvisLow.addChild(this.pelvisLeft);
        this.bodyLower2.addChild(this.pelvisLow);
        this.shoulderRight.addChild(this.armRight);
        this.pelvisRight.addChild(this.legRightUpper);
        this.legRightUpper.addChild(this.legRight);
        this.shoulderLeft.addChild(this.armLeft);
        this.pelvisLow.addChild(this.pelvisRight);
        this.bodyCore.addChild(this.shoulderRight);
        this.bodyLower.addChild(this.bodyLower2);
        this.bodyCore.addChild(this.bodyLower);
        this.armLeft.addChild(this.armLowerLeft);
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
