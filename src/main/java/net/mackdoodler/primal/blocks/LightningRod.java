package net.mackdoodler.primal.blocks;

import net.mackdoodler.primal.items.ItemRune;
import net.mackdoodler.primal.tileentity.TileEntityRuneSocket;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class LightningRod extends BlockBase{

	protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D);
	
	//flip it around so it points down, it will look better thn
	public LightningRod(String name) {
		super(Material.IRON, name);
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return AABB;
    }
	
	@Override
	/**
     * Get the geometry of the queried face at the given position and state. This is used to decide whether things like
     * buttons are allowed to be placed on the face, or how glass panes connect to the face, among other things.
     * <p>
     * Common values are {@code SOLID}, which is the default, and {@code UNDEFINED}, which represents something that
     * does not fit the other descriptions and will generally cause other things not to connect to the face.
     * 
     * @return an approximation of the form of the given face
     */
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
        return false;
    }
	
    @Override
    public boolean isBlockNormalCube(IBlockState blockState) {
        return false;
    }
    
    @Override
    @Deprecated
    //FUCKS EVERYTHING UP IF SET TO TRUE BECAUSE YA
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState blockState) {
        return false;
    }
    
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        if (!worldIn.isRemote)
        {
        	for (EnumFacing enumfacing : EnumFacing.values())
            {
                if (worldIn.isSidePowered(pos.offset(enumfacing), enumfacing))
                {
                	this.lightningStrike(state, worldIn, pos);
                	return;
                }
            }
        }
    }
    
    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
        	this.lightningStrike(state, world, pos);
        }
        // Return true also on the client to make sure that MC knows we handled this and will not try to place
        // a block on the client
        return true;
    }
    
    private void lightningStrike(IBlockState blockState, World world, BlockPos pos){
    	//loop to run down to the first point that isnt air or a lightning rod
    	if(world.isRaining()){
    		while(world.isAirBlock(pos) || world.getBlockState(pos).getBlock()==ModBlocks.lightningRod && pos.getY()>1){
        		pos=pos.down();
        	}
        	if(pos.getY()!=1){
        		world.addWeatherEffect(new EntityLightningBolt(world, pos.getX(),pos.getY(),pos.getZ(),false));
        	}
        	else{
        		//could not find suitable lightning strike location
        		if(world.isRemote){
        			System.out.println("Could not find suitable strike location");
        		}
        	}
    	}
    }
}
