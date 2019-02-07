package net.mackdoodler.butchery.common.blocks;

import java.util.ArrayList;

import net.mackdoodler.butchery.ButcheryMod;
import net.mackdoodler.butchery.ButcherySoundEvents;
import net.mackdoodler.butchery.common.items.ButcheryItems;
import net.mackdoodler.butchery.common.tileentity.TileEntityCorpse;
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
import net.minecraft.entity.player.EntityPlayer;
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

public class BlockCorpse extends BlockTileEntity<TileEntityCorpse>{
	
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyBool ORIENTATION = PropertyBool.create("orient");
	
	protected static final AxisAlignedBB AABB_CORPSE = new AxisAlignedBB(1/16D, 0, 1/16D, 15/16D, 12/16D, 15/16D);
	protected static final AxisAlignedBB AABB_CORPSE_COLLISION = new AxisAlignedBB(1/16D, 0, 1/16D, 15/16D, 4/16D, 15/16D);
	
	public BlockCorpse(String name){
		super(Material.CLAY);
		setTranslationKey(name);
		setRegistryName(name);
		setCreativeTab(ButcheryMod.creativeTab);
		setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(ORIENTATION, Boolean.valueOf(false)));
		setSoundType(SoundType.SLIME);
		setHardness(4f);
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return AABB_CORPSE_COLLISION;
    }
	
	@Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.INVISIBLE;
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
		return new ArrayList<ItemStack>();
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
        super.breakBlock(world, pos, state);
    }
    
    public void carveItUp(EntityPlayer player, World worldIn, BlockPos pos){
    	
        TileEntityCorpse tileEntity = (TileEntityCorpse) worldIn.getTileEntity(pos);
        if(tileEntity.getUnharmed()){
            worldIn.playSound(null, pos, SoundEvents.BLOCK_SLIME_BREAK, SoundCategory.BLOCKS, 1f, 1f);
            worldIn.playSound(null, pos, SoundEvents.BLOCK_SLIME_HIT, SoundCategory.BLOCKS, 1f, 1f);
            worldIn.playSound(null, pos, SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, SoundCategory.BLOCKS, .5f, 1f);
            for(int i = 0; i<=40; i++){
            	worldIn.spawnParticle(EnumParticleTypes.BLOCK_CRACK, pos.getX()+worldIn.rand.nextFloat(), pos.getY()+worldIn.rand.nextFloat()/2, pos.getZ()+worldIn.rand.nextFloat(), worldIn.rand.nextGaussian()*1D, worldIn.rand.nextGaussian()*1D, worldIn.rand.nextGaussian()*1D, 214);
            }
            tileEntity.harm();
        }
        for(int i = 0; i<=5; i++){
        	worldIn.spawnParticle(EnumParticleTypes.BLOCK_CRACK, pos.getX()+worldIn.rand.nextFloat(), pos.getY()+worldIn.rand.nextFloat()/2, pos.getZ()+worldIn.rand.nextFloat(), worldIn.rand.nextGaussian()*1D, worldIn.rand.nextGaussian()*1D, worldIn.rand.nextGaussian()*1D, 214);
        }
        
        worldIn.playSound(null, pos, SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.BLOCKS, .5f, 1f);
        worldIn.playSound(null, pos, ButcherySoundEvents.MACHETE_CHOP, SoundCategory.BLOCKS, .5f, 1f);
        
        if(worldIn.isRemote){
    		return;
    	}
        
        ItemStack stak = tileEntity.getRandomItem(player);
        
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
	public Class<TileEntityCorpse> getTileEntityClass() {
		return TileEntityCorpse.class;
	}

	@Override
	public TileEntityCorpse createTileEntity(World world, IBlockState state) {
		return new TileEntityCorpse();
	}
}
