package net.mackdoodler.primal.particles;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleExplosionLarge;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SleepParticles extends Particle{

	private static final ResourceLocation SLEEP_PARTICLE_TEXTURE = new ResourceLocation("primal:textures/particles/sleep_particle.png");
	private static final ResourceLocation NEW_PARTICLE_TEXTURE = new ResourceLocation("primal:textures/particles/new_particles.png");
	private static final VertexFormat VERTEX_FORMAT = (new VertexFormat())
															.addElement(DefaultVertexFormats.POSITION_3F)
															.addElement(DefaultVertexFormats.TEX_2F)
															.addElement(DefaultVertexFormats.COLOR_4UB)
															.addElement(DefaultVertexFormats.TEX_2S)
															.addElement(DefaultVertexFormats.NORMAL_3B)
															.addElement(DefaultVertexFormats.PADDING_1B);
    
    
	public SleepParticles(TextureManager textureManagerIn, World worldIn, double posXIn, double posYIn, double posZIn, double motionXIn, double motionZIn) {
		super(worldIn, posXIn, posYIn, posZIn);

        this.rand = new Random();
        this.particleAlpha = .5F;
        this.particleScale = (this.rand.nextFloat() * 0.5F + 0.5F) * 2.0F;
        this.particleMaxAge = (int)(100.0F / (this.rand.nextFloat() * 0.9F + 0.1F));
		this.motionY = -0.02D;
		this.motionY -=rand.nextDouble()*.02;
		this.motionX = motionXIn;
		this.motionZ = motionZIn;
		
		//this.setParticleTexture(Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("primal:particles/sleep_particle"));

	}
	
	@Override
	/**
     * Renders the particle
     */
	
    public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ)
    {
		GL11.glPushMatrix();
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, this.particleAlpha);
		GlStateManager.disableLighting();
		RenderHelper.disableStandardItemLighting();
		
        Minecraft.getMinecraft().getTextureManager().bindTexture(SLEEP_PARTICLE_TEXTURE);
        
		float f = 0f;
		float f1 = 1f; //f + 0.0625f;
		float f2 = 0f;
        float f3 = 1f; //f2 + 0.0625f;
        float f4 = .05F * this.particleScale;
        float f5 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)partialTicks - interpPosX);
        float f6 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)partialTicks - interpPosY);
        float f7 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)partialTicks - interpPosZ);
		
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.disableLighting();
		RenderHelper.disableStandardItemLighting();
		
		Vec3d[] avec3d = new Vec3d[] {new Vec3d((double)(-rotationX * f4 - rotationXY * f4), (double)(-rotationZ * f4), (double)(-rotationYZ * f4 - rotationXZ * f4)), 
									new Vec3d((double)(-rotationX * f4 + rotationXY * f4), (double)(rotationZ * f4), (double)(-rotationYZ * f4 + rotationXZ * f4)), 
									new Vec3d((double)(rotationX * f4 + rotationXY * f4), (double)(rotationZ * f4), (double)(rotationYZ * f4 + rotationXZ * f4)), 
									new Vec3d((double)(rotationX * f4 - rotationXY * f4), (double)(-rotationZ * f4), (double)(rotationYZ * f4 - rotationXZ * f4))};
		
        if (this.particleAngle != 0.0F)
        {
            float f8 = this.particleAngle + (this.particleAngle - this.prevParticleAngle) * partialTicks;
            float f9 = MathHelper.cos(f8 * 0.5F);
            float f10 = MathHelper.sin(f8 * 0.5F) * (float)cameraViewDir.x;
            float f11 = MathHelper.sin(f8 * 0.5F) * (float)cameraViewDir.y;
            float f12 = MathHelper.sin(f8 * 0.5F) * (float)cameraViewDir.z;
            Vec3d vec3d = new Vec3d((double)f10, (double)f11, (double)f12);

            for (int l = 0; l < 4; ++l)
            {
                avec3d[l] = vec3d.scale(2.0D * avec3d[l].dotProduct(vec3d)).add(avec3d[l].scale((double)(f9 * f9) - vec3d.dotProduct(vec3d))).add(vec3d.crossProduct(avec3d[l]).scale((double)(2.0F * f9)));
            }
        }

        buffer.begin(7, VERTEX_FORMAT);
        
		buffer.pos((double)(f5 + avec3d[0].x),(double)(f6 + avec3d[0].y),(double)(f7 + avec3d[0].z)).tex((double)f1, (double)f3).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(0, 240).normal(0.0F, 1.0F, 0.0F).endVertex();
		buffer.pos((double)(f5 + avec3d[1].x), (double)(f6 + avec3d[1].y), (double)(f7 + avec3d[1].z)).tex((double)f1, (double)f2).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(0, 240).normal(0.0F, 1.0F, 0.0F).endVertex();
		buffer.pos((double)(f5 + avec3d[2].x), (double)(f6 + avec3d[2].y), (double)(f7 + avec3d[2].z)).tex((double)f, (double)f2).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(0, 240).normal(0.0F, 1.0F, 0.0F).endVertex();
		buffer.pos((double)(f5 + avec3d[3].x), (double)(f6 + avec3d[3].y), (double)(f7 + avec3d[3].z)).tex((double)f, (double)f3).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(0, 240).normal(0.0F, 1.0F, 0.0F).endVertex();

        Tessellator.getInstance().draw();
		GlStateManager.enableLighting();
		GL11.glPopMatrix();

		GlStateManager.enableLighting();
    }
	
	@Override
	public void onUpdate(){
		
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		this.motionX*=.95f;
		this.motionZ*=.95f;
		
		if (this.particleAge++ >= this.particleMaxAge){
		    this.setExpired();
		    return;
		}
		
		if (this.onGround)
		{
			this.particleScale *= 0.92F;
			if (this.particleScale < 0.2F){
			    this.setExpired();
			    return;
			}
		}
		else{
			this.prevParticleAngle=this.particleAngle;
			this.particleAngle+=.05f;
			move(this.motionX, this.motionY, this.motionZ);
		}
	}
	
	/**
     * Retrieve what effect layer (what texture) the particle should be rendered with. 0 for the particle sprite sheet,
     * 1 for the main Texture atlas, and 3 for a custom texture
     */
	@Override
    public int getFXLayer()
    {
        return 3;
    }

}
