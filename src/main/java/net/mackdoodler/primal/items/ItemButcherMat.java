package net.mackdoodler.primal.items;

import net.mackdoodler.primal.blocks.BlockButcherMat;
import net.mackdoodler.primal.blocks.ModBlocks;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBed;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemButcherMat extends ItemBase{

	public ItemButcherMat(String name) {
		super(name);
		
	}
	
	/**
     * Called when a Block is right-clicked with this Item
     * places the butchers mat if eligible
     */
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (worldIn.isRemote)
        {
            return EnumActionResult.SUCCESS;
        }
        /*
        else if (facing != EnumFacing.UP)
        {
            return EnumActionResult.FAIL;
        }
        */
        else
        {
            IBlockState iblockstate = worldIn.getBlockState(pos);
            Block block = iblockstate.getBlock();

            if (!block.isReplaceable(worldIn, pos))
            {
            	switch(facing){
            	case UP:
                    pos = pos.up();
            		break;
            	case NORTH:
            		pos = pos.north(2);
            		break;
            	case EAST:
            		pos = pos.east(2);
            		break;
            	case SOUTH:
            		pos = pos.south(2);
            		break;
            	case WEST:
            		pos = pos.west(2);
            		break;
            	case DOWN:
            	default:
                	return EnumActionResult.FAIL;
            	}
            	
                if(!worldIn.getBlockState(pos).getBlock().isReplaceable(worldIn, pos)){
                	return EnumActionResult.FAIL;
                }
            }

            EnumFacing enumfacing = EnumFacing.byHorizontalIndex(MathHelper.floor((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3);
            //----shit diverges from the bed under this point----
            
            //if this boolean is true it is on the X axis, Z otherwise
            //boolean facingX = enumfacing.getAxis()==Axis.X;
            
            ItemStack itemstack = player.getHeldItem(hand);
            
            //probably could have been done with nested for loops and pos.offset if it works with negative values
            //checking if all adjacent tiles are valid to be placed
            if((!worldIn.getBlockState(pos.north()).getBlock().isReplaceable(worldIn, pos.north()))){
            	return EnumActionResult.FAIL;
            }
            if((!worldIn.getBlockState(pos.north().west()).getBlock().isReplaceable(worldIn, pos.north().west()))){
            	return EnumActionResult.FAIL;
            }
            if((!worldIn.getBlockState(pos.north().east()).getBlock().isReplaceable(worldIn, pos.north().east()))){
            	return EnumActionResult.FAIL;
            }
            if((!worldIn.getBlockState(pos.south()).getBlock().isReplaceable(worldIn, pos.south()))){
            	return EnumActionResult.FAIL;
            }
            if((!worldIn.getBlockState(pos.south().west()).getBlock().isReplaceable(worldIn, pos.south().west()))){
            	return EnumActionResult.FAIL;
            }
            if((!worldIn.getBlockState(pos.south().east()).getBlock().isReplaceable(worldIn, pos.south().east()))){
            	return EnumActionResult.FAIL;
            }
            if((!worldIn.getBlockState(pos.west()).getBlock().isReplaceable(worldIn, pos.west()))){
            	return EnumActionResult.FAIL;
            }
            if((!worldIn.getBlockState(pos.east()).getBlock().isReplaceable(worldIn, pos.east()))){
            	return EnumActionResult.FAIL;
            }
            
            //by this point it should be determined that the butchers mat placement will not be blocked by other locations
            //System.out.println("yep it would place now");
            if (player.canPlayerEdit(pos, facing, itemstack))
            {
        		//if placement is eligible for this way
        		if(worldIn.getBlockState(pos.north(2)).getMaterial().isSolid()&&
        				worldIn.getBlockState(pos.north(2).east()).getMaterial().isSolid()&&
        				worldIn.getBlockState(pos.north(2).west()).getMaterial().isSolid()&&
        				worldIn.getBlockState(pos.south(2)).getMaterial().isSolid()&&
        				worldIn.getBlockState(pos.south(2).east()).getMaterial().isSolid()&&
        				worldIn.getBlockState(pos.south(2).west()).getMaterial().isSolid()){

        			//then set the butchermat parts down
        			worldIn.setBlockState(pos, ModBlocks.butcherMat.getDefaultState()
            				.withProperty(BlockButcherMat.STICK, false)
            				.withProperty(BlockButcherMat.EDGE, false)
            				.withProperty(BlockButcherMat.ORIENTATION, true)
            				.withProperty(BlockButcherMat.CENTER, true), 10);
                	worldIn.setBlockState(pos.west(), ModBlocks.butcherMat.getDefaultState()
                			.withProperty(BlockButcherMat.STICK, false)
                			.withProperty(BlockButcherMat.EDGE, false)
                			.withProperty(BlockButcherMat.ORIENTATION, true)
                			.withProperty(BlockButcherMat.CENTER, false), 10);
                	worldIn.setBlockState(pos.east(), ModBlocks.butcherMat.getDefaultState()
                			.withProperty(BlockButcherMat.STICK, false)
                			.withProperty(BlockButcherMat.EDGE, false)
                			.withProperty(BlockButcherMat.ORIENTATION, true)
                			.withProperty(BlockButcherMat.CENTER, false), 10);
                	worldIn.setBlockState(pos.south(), ModBlocks.butcherMat.getDefaultState()
                			.withProperty(BlockButcherMat.STICK, false)
                			.withProperty(BlockButcherMat.EDGE, true)
                			.withProperty(BlockButcherMat.ORIENTATION, true)
                			.withProperty(BlockButcherMat.CENTER, true), 10);
                	worldIn.setBlockState(pos.south().west(), ModBlocks.butcherMat.getDefaultState()
                			.withProperty(BlockButcherMat.STICK, false)
                			.withProperty(BlockButcherMat.EDGE, true)
                			.withProperty(BlockButcherMat.ORIENTATION, true)
                			.withProperty(BlockButcherMat.CENTER, false), 10);
                	worldIn.setBlockState(pos.south().east(), ModBlocks.butcherMat.getDefaultState()
                			.withProperty(BlockButcherMat.STICK, false)
                			.withProperty(BlockButcherMat.EDGE, true)
                			.withProperty(BlockButcherMat.ORIENTATION, true)
                			.withProperty(BlockButcherMat.CENTER, false), 10);
                	worldIn.setBlockState(pos.north(), ModBlocks.butcherMat.getDefaultState()
                			.withProperty(BlockButcherMat.STICK, true)
                			.withProperty(BlockButcherMat.EDGE, false)
                			.withProperty(BlockButcherMat.ORIENTATION, true)
                			.withProperty(BlockButcherMat.CENTER, true), 10);
                	worldIn.setBlockState(pos.north().west(), ModBlocks.butcherMat.getDefaultState()
                			.withProperty(BlockButcherMat.STICK, true)
                			.withProperty(BlockButcherMat.EDGE, false)
                			.withProperty(BlockButcherMat.ORIENTATION, true)
                			.withProperty(BlockButcherMat.CENTER, false), 10);
                	worldIn.setBlockState(pos.north().east(), ModBlocks.butcherMat.getDefaultState()
                			.withProperty(BlockButcherMat.STICK, true)
                			.withProperty(BlockButcherMat.EDGE, false)
                			.withProperty(BlockButcherMat.ORIENTATION, true)
                			.withProperty(BlockButcherMat.CENTER, false), 10);
        		}
        		//if placement is eligible for this way
        		else if(worldIn.getBlockState(pos.east(2)).getMaterial().isSolid()&&
            				worldIn.getBlockState(pos.east(2).north()).getMaterial().isSolid()&&
            				worldIn.getBlockState(pos.east(2).south()).getMaterial().isSolid()&&
            				worldIn.getBlockState(pos.west(2)).getMaterial().isSolid()&&
            				worldIn.getBlockState(pos.west(2).north()).getMaterial().isSolid()&&
            				worldIn.getBlockState(pos.west(2).south()).getMaterial().isSolid()){

        			//then set the butchermat parts down
        			worldIn.setBlockState(pos, ModBlocks.butcherMat.getDefaultState()
            				.withProperty(BlockButcherMat.STICK, false)
            				.withProperty(BlockButcherMat.EDGE, false)
            				.withProperty(BlockButcherMat.ORIENTATION, false)
            				.withProperty(BlockButcherMat.CENTER, true), 10);
                	worldIn.setBlockState(pos.west(), ModBlocks.butcherMat.getDefaultState()
                			.withProperty(BlockButcherMat.STICK, true)
                			.withProperty(BlockButcherMat.EDGE, false)
                			.withProperty(BlockButcherMat.ORIENTATION, false)
                			.withProperty(BlockButcherMat.CENTER, true), 10);
                	worldIn.setBlockState(pos.east(), ModBlocks.butcherMat.getDefaultState()
                			.withProperty(BlockButcherMat.STICK, false)
                			.withProperty(BlockButcherMat.EDGE, true)
                			.withProperty(BlockButcherMat.ORIENTATION, false)
                			.withProperty(BlockButcherMat.CENTER, true), 10);
                	worldIn.setBlockState(pos.south(), ModBlocks.butcherMat.getDefaultState()
                			.withProperty(BlockButcherMat.STICK, false)
                			.withProperty(BlockButcherMat.EDGE, false)
                			.withProperty(BlockButcherMat.ORIENTATION, false)
                			.withProperty(BlockButcherMat.CENTER, false), 10);
                	worldIn.setBlockState(pos.south().west(), ModBlocks.butcherMat.getDefaultState()
                			.withProperty(BlockButcherMat.STICK, true)
                			.withProperty(BlockButcherMat.EDGE, false)
                			.withProperty(BlockButcherMat.ORIENTATION, false)
                			.withProperty(BlockButcherMat.CENTER, false), 10);
                	worldIn.setBlockState(pos.south().east(), ModBlocks.butcherMat.getDefaultState()
                			.withProperty(BlockButcherMat.STICK, false)
                			.withProperty(BlockButcherMat.EDGE, true)
                			.withProperty(BlockButcherMat.ORIENTATION, false)
                			.withProperty(BlockButcherMat.CENTER, false), 10);
                	worldIn.setBlockState(pos.north(), ModBlocks.butcherMat.getDefaultState()
                			.withProperty(BlockButcherMat.STICK, false)
                			.withProperty(BlockButcherMat.EDGE, false)
                			.withProperty(BlockButcherMat.ORIENTATION, false)
                			.withProperty(BlockButcherMat.CENTER, false), 10);
                	worldIn.setBlockState(pos.north().west(), ModBlocks.butcherMat.getDefaultState()
                			.withProperty(BlockButcherMat.STICK, true)
                			.withProperty(BlockButcherMat.EDGE, false)
                			.withProperty(BlockButcherMat.ORIENTATION, false)
                			.withProperty(BlockButcherMat.CENTER, false), 10);
                	worldIn.setBlockState(pos.north().east(), ModBlocks.butcherMat.getDefaultState()
                			.withProperty(BlockButcherMat.STICK, false)
                			.withProperty(BlockButcherMat.EDGE, true)
                			.withProperty(BlockButcherMat.ORIENTATION, false)
                			.withProperty(BlockButcherMat.CENTER, false), 10);
        		}
        		else{
        			return EnumActionResult.FAIL;
        		}
            	
            	/*
                IBlockState iblockstate1 = worldIn.getBlockState(blockpos);
                IBlockState iblockstate2 = Blocks.BED.getDefaultState().withProperty(BlockBed.OCCUPIED, false).withProperty(BlockBed.FACING, enumfacing).withProperty(BlockBed.PART, BlockBed.EnumPartType.FOOT);
                
                worldIn.setBlockState(pos, iblockstate2, 10);
                worldIn.setBlockState(blockpos, iblockstate2.withProperty(BlockBed.PART, BlockBed.EnumPartType.HEAD), 10);
                
                
                
                worldIn.notifyNeighborsRespectDebug(pos, block, false);
                worldIn.notifyNeighborsRespectDebug(blockpos, iblockstate1.getBlock(), false);

                    if (player instanceof EntityPlayerMP)
                    {
                        CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP)player, pos, itemstack);
                    }
                */
            	worldIn.playSound((EntityPlayer)null, pos, SoundType.CLOTH.getPlaceSound(), SoundCategory.BLOCKS, 1.0F, 0.8F);
            	itemstack.shrink(1);
            	return EnumActionResult.SUCCESS;
            }
            else
            {
                return EnumActionResult.FAIL;
            }
        }
    }

}
