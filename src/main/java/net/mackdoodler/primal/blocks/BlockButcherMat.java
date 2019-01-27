package net.mackdoodler.primal.blocks;

import java.util.Random;

import net.mackdoodler.primal.items.ButcheryItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockStateMatcher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockButcherMat extends BlockBase{

	public static final PropertyBool EDGE = PropertyBool.create("edge");
	public static final PropertyBool STICK = PropertyBool.create("stick");
	public static final PropertyBool ORIENTATION = PropertyBool.create("orient");
	public static final PropertyBool CENTER = PropertyBool.create("center");
	
	protected static final AxisAlignedBB AABB_MAT = new AxisAlignedBB(0, 14/16D, 0, 1D, 1D, 1D);
	
	public BlockButcherMat(String name) {
		super(Material.CLOTH, name);
		setDefaultState(this.blockState.getBaseState().withProperty(EDGE, Boolean.valueOf(false)).withProperty(STICK, Boolean.valueOf(false)).withProperty(ORIENTATION, Boolean.valueOf(false)).withProperty(CENTER, Boolean.valueOf(false)));
		setHardness(5f);
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return AABB_MAT;
    }
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return AABB_MAT;
    }
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
	
	@Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
	
	@Override
	public EnumPushReaction getPushReaction(IBlockState state)
    {
        return EnumPushReaction.DESTROY;
    }

	@Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
	
	/*
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		System.out.println(findPosition(state,worldIn,pos));
		return true;
    }*/
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
		//if its the center block then drop the item, otherwise no
		if(state.getValue(CENTER).booleanValue() && !state.getValue(STICK).booleanValue() && !state.getValue(EDGE).booleanValue()){
	        return ButcheryItems.itemButcherMat;
		}
		return Items.AIR;
    }
	
	/**
     * Called when a neighboring block was changed and marks that this state should perform any checks during a neighbor
     * change. Cases may include when redstone power is updated, cactus blocks popping off due to a neighboring solid
     * block, etc.
     */
	@Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
		//check if it is a stick or an edge and if so then the block they are 'anchored' to is still solid, if not then pop the mat off
		//System.out.println("neighbor called, they wanted sugar");
        if (state.getValue(STICK)){
        	if (state.getValue(ORIENTATION)){
        		if(!worldIn.getBlockState(pos.north()).getMaterial().isSolid()){
        			destroyMat(state,worldIn,pos);
        		}
            }
        	else if(!worldIn.getBlockState(pos.west()).getMaterial().isSolid()){
        		destroyMat(state,worldIn,pos);
    		}
        }
        else if(state.getValue(EDGE)){
        	if (state.getValue(ORIENTATION)){
        		if(!worldIn.getBlockState(pos.south()).getMaterial().isSolid()){
        			destroyMat(state,worldIn,pos);
        		}
            }
        	else if(!worldIn.getBlockState(pos.east()).getMaterial().isSolid()){
        		destroyMat(state,worldIn,pos);
    		}
        }
        else{
        	return;
        }
    }
	
    /**
     * Called before the Block is set to air in the world. Called regardless of if the player's tool can actually
     * collect this block
     */
	
    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
    {
    	destroyMat(state,worldIn,pos);
    }
    
    
    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest)
    {
    	this.onBlockHarvested(world, pos, state, player);
        return true;
    }
    
    @Override
    public boolean isTopSolid(IBlockState state){
    	return true;
    }
    
    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
    
    @Override
	public IBlockState getStateFromMeta(int meta)
    {
        IBlockState result = this.getDefaultState();
        if(meta>=8){
        	result = result.withProperty(CENTER, Boolean.valueOf(true));
        }
        if(meta%8>=4){
        	result = result.withProperty(ORIENTATION, Boolean.valueOf(true));
        }
        if(meta%4>=2){
        	result = result.withProperty(EDGE, Boolean.valueOf(true));
        }
        if(meta%2>=1){
        	result = result.withProperty(STICK, Boolean.valueOf(true));
        }
        return result;
    }

    @Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, STICK, EDGE, ORIENTATION, CENTER);
	}

    /**
     * Convert the BlockState into the correct metadata value
     */
    @Override
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        if (((Boolean)state.getValue(STICK)).booleanValue())
        {
        	i+=1;
        }
        if (((Boolean)state.getValue(EDGE)).booleanValue())
        {
            i+=2;
        }
        if (((Boolean)state.getValue(ORIENTATION)).booleanValue())
        {
            i+=4;
        }
        if (((Boolean)state.getValue(CENTER)).booleanValue())
        {
            i+=8;
        }
        return i;
    }
    
    /*
     * helper method to destroy all 9 tiles of the mat
     */
    private void destroyMat(IBlockState state, World worldIn, BlockPos pos){
    	//System.out.println("breakin bitch");
    	
    	this.dropBlockAsItem(worldIn, pos, state, 0);
    	worldIn.setBlockState(pos, net.minecraft.init.Blocks.AIR.getDefaultState(), worldIn.isRemote ? 11 : 3);
    	
    	int numbah = findPosition(state,worldIn,pos);
    	BlockPos positron = pos;
    	switch(numbah){
    	case 1:
    		positron=pos.west(2);
    		break;
    	case 2:
    		positron=pos.west(2).north(1);
    		break;
		case 3:
			positron=pos.west(2).north(2);
			break;
		case 4:
			positron=pos.west(1);
			break;
		case 5:
			positron=pos.west(1).north(1);
			break;
		case 6:
			positron=pos.west(1).north(2);
			break;
		case 7:
			positron=pos;
			break;
		case 8:
			positron=pos.north(1);
			break;
		case 9:
			positron=pos.north(2);
			break;
    	}
    	
    	for(int i=0;i<3;i++){
    		for(int j=0;j<3;j++){
    			if (worldIn.getBlockState(positron.east(i).south(j)).getBlock() == this)
	            {
	    			this.dropBlockAsItem(worldIn, positron.east(i).south(j), worldIn.getBlockState(positron.east(i).south(j)), 0);
	    			worldIn.setBlockState(positron.east(i).south(j), net.minecraft.init.Blocks.AIR.getDefaultState(), worldIn.isRemote ? 11 : 3);
	            }
        	}
    	}
    }
    
    /*
     * helper method to find out which part of the butchers mat this is
     * east(x+)
     *	1 2 3
     *	4 5 6  south (z+)
     *	7 8 9
     */
    private int findPosition(IBlockState state, World worldIn, BlockPos pos){
    	
    	boolean facingX = state.getValue(ORIENTATION);
    	
    	if(state.getValue(CENTER).booleanValue() && !state.getValue(STICK).booleanValue() && !state.getValue(EDGE).booleanValue()){
	        return 5;
		}
    	if(facingX){
    		if(state.getValue(STICK).booleanValue()){
    			if(state.getValue(CENTER).booleanValue()){
    				return 4;
    			}
    			if(worldIn.getBlockState(pos.west()).getBlock() instanceof BlockButcherMat){
    				if(worldIn.getBlockState(pos.west()).getValue(CENTER)){
    					return 1;
    				}
    			}
    			return 7;
    		}
    		if(state.getValue(EDGE).booleanValue()){
    			if(state.getValue(CENTER).booleanValue()){
    				return 6;
    			}
    			if(worldIn.getBlockState(pos.west()).getBlock() instanceof BlockButcherMat){
    				if(worldIn.getBlockState(pos.west()).getValue(CENTER)){
    					return 3;
    				}
    			}
    			return 9;
    		}
    		if(worldIn.getBlockState(pos.west()).getBlock() instanceof BlockButcherMat){
				if(worldIn.getBlockState(pos.west()).getValue(CENTER)){
					return 2;
				}
			}
			return 8;
    	}else{
    		if(state.getValue(STICK).booleanValue()){
    			if(state.getValue(CENTER).booleanValue()){
    				return 8;
    			}
    			if(worldIn.getBlockState(pos.south()).getBlock() instanceof BlockButcherMat){
    				if(worldIn.getBlockState(pos.south()).getValue(CENTER)){
    					return 7;
    				}
    			}
    			return 9;
    		}
    		if(state.getValue(EDGE).booleanValue()){
    			if(state.getValue(CENTER).booleanValue()){
    				return 2;
    			}
    			if(worldIn.getBlockState(pos.south()).getBlock() instanceof BlockButcherMat){
    				if(worldIn.getBlockState(pos.south()).getValue(CENTER)){
    					return 1;
    				}
    			}
    			return 3;
    		}
    		if(worldIn.getBlockState(pos.south()).getBlock() instanceof BlockButcherMat){
				if(worldIn.getBlockState(pos.south()).getValue(CENTER)){
					return 4;
				}
			}
			return 6;
    	}
    }
}
