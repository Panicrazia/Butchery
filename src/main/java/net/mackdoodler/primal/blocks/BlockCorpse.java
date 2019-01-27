package net.mackdoodler.primal.blocks;

import java.util.ArrayList;

import net.mackdoodler.primal.init.PrimalSoundEvents;
import net.mackdoodler.primal.items.PrimalItems;
import net.mackdoodler.primal.tileentity.TileEntityCorpse;
import net.mackdoodler.primal.tileentity.TileEntityRuneSocket;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCorpse extends BlockBase implements ITileEntityProvider{

	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyBool ORIENTATION = PropertyBool.create("orient");
	
	public BlockCorpse(String name) {
		super(Material.CLAY, name);
		setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(ORIENTATION, Boolean.valueOf(false)));
		setSoundType(SoundType.SLIME);
		setHardness(4f);
	}
	
	protected static final AxisAlignedBB AABB_CORPSE = new AxisAlignedBB(1/16D, 0, 1/16D, 15/16D, 12/16D, 15/16D);
	protected static final AxisAlignedBB AABB_CORPSE_COLLISION = new AxisAlignedBB(1/16D, 0, 1/16D, 15/16D, 4/16D, 15/16D);
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return AABB_CORPSE_COLLISION;
    }
	
	@Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.INVISIBLE;
        //return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return AABB_CORPSE;
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
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
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
	
	@Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
    	if (!worldIn.getBlockState(pos.down()).isSideSolid(worldIn, pos.down(), EnumFacing.UP)){
    		this.dropBlockAsItem(worldIn, pos, state, 0);
    	    worldIn.setBlockToAir(pos);
    	}
    }
	
	@Override 
	public ArrayList<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState blockstate, int fortune){
		ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
		
		return drops;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
    {
        IBlockState result = this.getDefaultState();
        if(meta>=4){
        	result = result.withProperty(ORIENTATION, Boolean.valueOf(true));
        }
        return result.withProperty(FACING, EnumFacing.byHorizontalIndex(meta));
    }

    @Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING, ORIENTATION);
	}

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        i+=((EnumFacing)state.getValue(FACING)).getHorizontalIndex();
        if (((Boolean)state.getValue(ORIENTATION)).booleanValue())
        {
            i+=4;
        }
        return i;
    }
    
    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
        world.removeTileEntity(pos);
        //maybe spawn a bone item here, just as a parting gift?
        super.breakBlock(world, pos, state);
    }
    
    public void carveItUp(World worldIn, BlockPos pos){
    	//System.out.println("carved baby");
    	
    	//maybe small chance to fire nova and light everything up when carving a blaze, or a sizzle sound when it has a creeper, neato tiny things like that
    	
        TileEntityCorpse tileEntity = (TileEntityCorpse) worldIn.getTileEntity(pos);
        if(tileEntity.getUnharmed()){
            worldIn.playSound(null, pos, SoundEvents.BLOCK_SLIME_BREAK, SoundCategory.BLOCKS, 1f, 1f);
            worldIn.playSound(null, pos, SoundEvents.BLOCK_SLIME_HIT, SoundCategory.BLOCKS, 1f, 1f);
            worldIn.playSound(null, pos, SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, SoundCategory.BLOCKS, .5f, 1f);
            for(int i = 0; i<=40; i++){
            	
            	//particleManager.addDestructionParticle(pos, stateParticle, world, x, y, z, d0 - 0.5, d1 - 0.5, d2 - 0.5);
            	worldIn.spawnParticle(EnumParticleTypes.BLOCK_CRACK, pos.getX()+worldIn.rand.nextFloat(), pos.getY()+worldIn.rand.nextFloat()/2, pos.getZ()+worldIn.rand.nextFloat(), worldIn.rand.nextGaussian()*1D, worldIn.rand.nextGaussian()*1D, worldIn.rand.nextGaussian()*1D, 214);
            }
            tileEntity.harm();
        }

        for(int i = 0; i<=5; i++){
        	
        	//particleManager.addDestructionParticle(pos, stateParticle, world, x, y, z, d0 - 0.5, d1 - 0.5, d2 - 0.5);
        	worldIn.spawnParticle(EnumParticleTypes.BLOCK_CRACK, pos.getX()+worldIn.rand.nextFloat(), pos.getY()+worldIn.rand.nextFloat()/2, pos.getZ()+worldIn.rand.nextFloat(), worldIn.rand.nextGaussian()*1D, worldIn.rand.nextGaussian()*1D, worldIn.rand.nextGaussian()*1D, 214);
        }
        worldIn.playSound(null, pos, SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.BLOCKS, .5f, 1f);
        //worldIn.playSound(null, pos, SoundEvents.ENTITY_SLIME_ATTACK, SoundCategory.BLOCKS, .5f, 1f);
        worldIn.playSound(null, pos, PrimalSoundEvents.MACHETE_CHOP, SoundCategory.BLOCKS, .5f, 1f);
        
        if(worldIn.isRemote){
    		return;
    	}
        
        ItemStack stak = tileEntity.getRandomItem();
        
        if(stak.isEmpty()){
        	
        	//its all good this thing gets rid of the tile entity too
        	worldIn.destroyBlock(pos, true);
        }
        
        double d0 = pos.getX()+.5;
        double d1 = pos.getY()+.5;
        double d2 = pos.getZ()+.5;
        
        EntityItem entityitem = new EntityItem(worldIn, d0, d1, d2, stak);
        
        worldIn.spawnEntity(entityitem);
    }
    
    private ItemStack getRandomDrop(World worldIn, BlockPos pos){
    	
    	
    	return ItemStack.EMPTY;
    }

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityCorpse();
	}
}
