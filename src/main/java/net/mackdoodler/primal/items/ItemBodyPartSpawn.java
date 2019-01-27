package net.mackdoodler.primal.items;

import javax.annotation.Nullable;

import net.mackdoodler.primal.entity.EntityCorpse;
import net.mackdoodler.primal.entity.construct.EntityConstruct;
import net.mackdoodler.primal.entity.construct.EntityConstructBeefyBody;
import net.mackdoodler.primal.entity.construct.EntityConstructHead;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemBodyPartSpawn extends ItemBase{

	public ItemBodyPartSpawn(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	@Override
    public boolean itemInteractionForEntity(ItemStack itemstack, net.minecraft.entity.player.EntityPlayer player, EntityLivingBase entity, net.minecraft.util.EnumHand hand)
    {
		System.out.println(entity.toString());
		if (player.getEntityWorld().isRemote)
        {
			//i dunno if returning true is fine or whatever so if this causes problems then change it
            return true;
        }
        else if(entity instanceof EntityConstruct)
        {
            EntityConstruct newEntity = (EntityConstruct) spawnCreature(player.getEntityWorld(), new ResourceLocation("primal:construct_head"), player.posX, player.posY, player.posZ);

            //newEntity.startRiding(entity,false, 0);
            
            if (!player.capabilities.isCreativeMode)
            {
                itemstack.shrink(1);
            }
            return true;
        }
        else if(entity instanceof EntityCow){
            Entity newEntity = spawnCreature(player.getEntityWorld(), new ResourceLocation("minecraft:chicken"), player.posX, player.posY, player.posZ);

            newEntity.startRiding(entity);
        }
		
		return false;
    }
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
        ItemStack itemstack = player.getHeldItem(hand);
        
		if (worldIn.isRemote)
        {
            return EnumActionResult.SUCCESS;
        }
        else if (!player.canPlayerEdit(pos.offset(facing), facing, itemstack))
        {
            return EnumActionResult.FAIL;
        }
        else
        {
            BlockPos blockpos = pos.offset(facing);
            worldIn.spawnEntity(new EntityCorpse(worldIn, (double)blockpos.getX() + 0.5D, (double)blockpos.getY() + 0D, (double)blockpos.getZ()+ 0.5D));
            
            if (!player.capabilities.isCreativeMode)
            {
                itemstack.shrink(1);
            }
            return EnumActionResult.SUCCESS;
        }

    }
	
	/**
     * Spawns the creature specified by the egg's type in the location specified by the last three parameters.
     * Parameters: world, entityID, x, y, z.
     */
    @Nullable
    public static Entity spawnCreature(World worldIn, @Nullable ResourceLocation entityID, double x, double y, double z)
    {
            Entity entity = null;

            entity = EntityList.createEntityByIDFromName(entityID, worldIn);
            
            EntityLiving entityliving = (EntityLiving)entity;
            entity.setLocationAndAngles(x, y, z, MathHelper.wrapDegrees(worldIn.rand.nextFloat() * 360.0F), 0.0F);
            //entityliving.rotationYawHead = entityliving.rotationYaw;
            //entityliving.renderYawOffset = entityliving.rotationYaw;
            entityliving.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos(entityliving)), (IEntityLivingData)null);
            worldIn.spawnEntity(entity);
            //entityliving.playLivingSound();

            return entity;
    }
}
