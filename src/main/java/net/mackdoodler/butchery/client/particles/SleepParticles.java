package net.mackdoodler.butchery.client.particles;

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
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
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

	private static final VertexFormat VERTEX_FORMAT = (new VertexFormat())
															.addElement(DefaultVertexFormats.POSITION_3F)
															.addElement(DefaultVertexFormats.TEX_2F)
															.addElement(DefaultVertexFormats.COLOR_4UB)
															.addElement(DefaultVertexFormats.TEX_2S)
															.addElement(DefaultVertexFormats.NORMAL_3B)
															.addElement(DefaultVertexFormats.PADDING_1B);
	
	public ResourceLocation texture = new ResourceLocation("butchery:particles/sleep_particle");
    private float rotational = 0f;
	
	public SleepParticles(TextureManager textureManagerIn, World worldIn, double posXIn, double posYIn, double posZIn, double motionXIn, double motionZIn) {
		super(worldIn, posXIn, posYIn, posZIn);

        this.rand = new Random();
        this.particleAlpha = 1F;
        this.particleScale = (this.rand.nextFloat() * 0.5F + 0.5F) * 2.0F;
        this.particleMaxAge = (int)(100.0F / (this.rand.nextFloat() * 0.5F + 0.1F));
		this.motionY = -0.01D - rand.nextDouble()*.005;
		this.motionX = motionXIn*2.2;
		this.motionZ = motionZIn*2.2;
		this.rotational = .02f+rand.nextFloat() * 0.03F;
		
	    TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(texture.toString());
	    this.setParticleTexture(sprite);
	}
	
	@Override
	public void onUpdate(){
		
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		this.motionX*=.88f;
		this.motionZ*=.88f;
		
		if (this.particleAge++ >= this.particleMaxAge){
		    this.setExpired();
		    return;
		}
		
		if (this.onGround)
		{
			this.particleScale *= 0.92F;
		}
		else{
			this.particleScale *= 0.995F;
			this.prevParticleAngle=this.particleAngle;
			this.particleAngle+=rotational;
			move(this.motionX, this.motionY, this.motionZ);
		}
		
		if (this.particleScale < 0.2F){
		    this.setExpired();
		    return;
		}
	}
	
	/**
     * Retrieve what effect layer (what texture) the particle should be rendered with. 0 for the particle sprite sheet,
     * 1 for the main Texture atlas, and 3 for a custom texture
     */
	@Override
    public int getFXLayer()
    {
        return 1;
    }

}
