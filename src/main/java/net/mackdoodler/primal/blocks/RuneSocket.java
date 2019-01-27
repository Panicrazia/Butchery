package net.mackdoodler.primal.blocks;

import java.util.List;

import javax.annotation.Nullable;

import net.mackdoodler.primal.PrimalMod;
import net.mackdoodler.primal.items.ItemRune;
import net.mackdoodler.primal.tileentity.TileEntityRuneSocket;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.BlockLever;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RuneSocket extends BlockBase implements ITileEntityProvider{
	
	//To-do: move to api or something
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	protected static final AxisAlignedBB AABB_NORTH = new AxisAlignedBB(0.125D, 0.875D, 0.8125D, 0.875D, 0.125D, 1.0D);
    protected static final AxisAlignedBB AABB_SOUTH = new AxisAlignedBB(0.125D, 0.875D, 0.0D, 0.875D, 0.125D, 0.1875D);
    protected static final AxisAlignedBB AABB_WEST = new AxisAlignedBB(0.8125D, 0.875D, 0.125D, 1.0D, 0.125D, 0.875D);
    protected static final AxisAlignedBB AABB_EAST = new AxisAlignedBB(0.0D, 0.875D, 0.125D, 0.1875D, 0.125D, 0.875D);
    
	
	public RuneSocket(String name) {
        super(Material.WOOD, name);
        //this.setHarvestLevel("pickaxe", 0); // 1 is stone required (0 wood, 1 stone, 2 iron)
        this.setHardness(1.5F);
        this.setResistance(1.5F);
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}
	/*
	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState)
    {
		EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);

        switch (enumfacing)
        {
            case EAST:
            	addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_EAST);
            	break;
            case WEST:
            	addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WEST);
            	break;
            case SOUTH:
            	addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_SOUTH);
            	break;
            case NORTH:
            default:
            	addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_NORTH);
            	break;
        }
    }*/
	@Override
	public EnumPushReaction getPushReaction(IBlockState state)
    {
        return EnumPushReaction.DESTROY;
    }

	/*
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }*/
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);

        switch (enumfacing)
        {
            case EAST:
                return AABB_EAST;
            case WEST:
            	return AABB_WEST;
            case SOUTH:
            	return AABB_SOUTH;
            case NORTH:
            default:
            	return AABB_NORTH;
        }
    }

    @Override
	/**
     * Check whether this Block can be placed at pos, while aiming at the specified side of an adjacent block
     */
    public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side)
    {
        return canPlaceBlock(worldIn, pos, side);
    }

    @Override
    /**
     * Checks if this block can be placed exactly at the given position.
     */
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        for (EnumFacing enumfacing : EnumFacing.values())
        {
            if (canPlaceBlock(worldIn, pos, enumfacing))
            {
                return true;
            }
        }

        return false;
    }

    
    protected static boolean canPlaceBlock(World worldIn, BlockPos pos, EnumFacing direction)
    {
    	if(!(direction == EnumFacing.UP|| direction == EnumFacing.DOWN)){
    		BlockPos blockpos = pos.offset(direction.getOpposite());
    		IBlockState iblockstate = worldIn.getBlockState(blockpos);
    		return iblockstate.getBlockFaceShape(worldIn, blockpos, direction) == BlockFaceShape.SOLID;
    	}
    	return false;
    }

    @Override
    /**
     * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
     * IBlockstate
     */
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return canPlaceBlock(worldIn, pos, facing) ? this.getDefaultState().withProperty(FACING, facing) : this.getDefaultState().withProperty(FACING, EnumFacing.DOWN);
    }
    
    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        if (!canPlaceBlock(world, pos, (EnumFacing)state.getValue(FACING)))
        {
            this.dropBlockAsItem(world, pos, state, 0);
            world.setBlockToAir(pos);
            return;
        }
        
        if (!world.isRemote)
        {
        	TileEntityRuneSocket tileEntity = (TileEntityRuneSocket) world.getTileEntity(pos);
        	//if( has a mechanis rune ){
        	
        		//if rune has buddy
        		if(tileEntity.getCoords().getY()!=0){
        			//if rune is the master rune
        			if(tileEntity.getMaster()){
        				if(tileEntity.getPowered()!=this.shouldBePowered(world, pos, state)){
        					//System.out.println("altering reality");
        					this.changePowered(true, false, world, pos, (EnumFacing)state.getValue(FACING));
        					world.getBlockState(tileEntity.getCoords()).neighborChanged(world, tileEntity.getCoords(), this, pos);
        				}
        			}
        			else{
        				//sync slave power to master power
        				this.changePowered(false, ((TileEntityRuneSocket) world.getTileEntity(tileEntity.getCoords())).getPowered(), world, pos, (EnumFacing)state.getValue(FACING));
        			}
        		}
        		else{
        			if(tileEntity.getPowered()!=this.shouldBePowered(world, pos, state)){
    					this.changePowered(true, false, world, pos, (EnumFacing)state.getValue(FACING));
    				}
        		}
        	//}
        }
    }
    /**
     * returns true if this should be powered
     * @param worldIn
     * @param pos
     * @param state
     * @return
     */
    private boolean shouldBePowered(World worldIn, BlockPos pos, IBlockState state)
    {
        EnumFacing enumfacing = ((EnumFacing)state.getValue(FACING));
        //seems to spam this if the block is powered by redstone wire, which quite frankly is more annoying than anything else
        //System.out.println(worldIn.isSidePowered(pos.offset(enumfacing), enumfacing)+" | true means powered, false means unpowered");
        return worldIn.isSidePowered(pos.offset(enumfacing), enumfacing);
        
    }
    
   	/**
   	 * 
   	 * @param toggle if toggle is true then the power will be reversed
   	 * @param power if toggle is false then power will be set to this param
   	 * @param world
   	 * @param pos
   	 * @param facing
   	 */
    public void changePowered(boolean toggle, boolean power, World world, BlockPos pos, EnumFacing facing){
    	TileEntityRuneSocket tileEntity = (TileEntityRuneSocket) world.getTileEntity(pos);
    	if(toggle){
    		tileEntity.setPowered(!tileEntity.getPowered());
    	}
    	else{
    		tileEntity.setPowered(power);
    	}
    	this.notifyNeighbors(world, pos, facing);
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

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityRuneSocket();
	}
	
	 @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumfacing = EnumFacing.byHorizontalIndex(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
        {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

    public int getMetaFromState(IBlockState state)
    {
        return ((EnumFacing)state.getValue(FACING)).getIndex();
    }

    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerItemModel(Item itemBlock){
    	super.registerItemModel(itemBlock);
    	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRuneSocket.class, new RuneSocketTESR());
    }
	
    private TileEntityRuneSocket getTE(World world, BlockPos pos) {
        return (TileEntityRuneSocket) world.getTileEntity(pos);
    }
    
    //runepos is this rune and linkpos is the rune to be linked with this one
    public boolean link(World world, BlockPos runePos, BlockPos linkPos, boolean isMaster){
    	if(!world.isRemote){
    		TileEntityRuneSocket tileEntitySlave = this.getTE(world, runePos);
        	
        	//making sure the potential link is actually a rune socket
        	if(world.getBlockState(linkPos).getBlock() == ModBlocks.runeSocket){
        		
        		TileEntityRuneSocket tileEntityMaster = this.getTE(world, linkPos);
        		
        		//if the rune socket already has a link
        		if(tileEntitySlave.getCoords().getY()>0){
        			//break the link of the other tile entity
        			((RuneSocket) world.getBlockState(tileEntitySlave.getCoords()).getBlock()).breakFriendship(world, runePos, true);
            	}
        		//same thing but checking the slave instead
        		if(tileEntityMaster.getCoords().getY()>0){
        			((RuneSocket) world.getBlockState(tileEntityMaster.getCoords()).getBlock()).breakFriendship(world, linkPos, true);
            	}
        		
        		tileEntitySlave.setCoords(linkPos.getX(),linkPos.getY(),linkPos.getZ());
        		tileEntityMaster.setCoords(runePos.getX(),runePos.getY(),runePos.getZ());
        		tileEntityMaster.setMaster(true);
        		
        		this.notifyNeighbors(world, runePos, (EnumFacing)world.getBlockState(runePos).getValue(FACING));
        		this.notifyNeighbors(world, linkPos, (EnumFacing)world.getBlockState(linkPos).getValue(FACING));
        		return true;
        	}
    	}
    	return false;
    }
    
    private void breakFriendship(World world, BlockPos pos, boolean tellBuddy){
    	
    	TileEntityRuneSocket tileEntity = this.getTE(world, pos);
    	
    	//tell buddy to break connection aswell
    	if(tellBuddy){
    		((RuneSocket) world.getBlockState(tileEntity.getCoords()).getBlock()).breakFriendship(world, tileEntity.getCoords(), false);
    	}
    	
    	tileEntity.setCoords(0,0,0);
    	tileEntity.setMaster(false);
		tileEntity.setPowered(false);
		this.notifyNeighbors(world, pos, (EnumFacing)world.getBlockState(pos).getValue(FACING));
    }
    
    private void notifyNeighbors(World worldIn, BlockPos pos, EnumFacing facing)
    {
    	
        worldIn.notifyNeighborsOfStateChange(pos, this, false);
        worldIn.notifyNeighborsOfStateExcept(pos.offset(facing.getOpposite()), this, facing);
    }
    
    @Override
    public boolean canProvidePower(IBlockState state)
    {
        //if(this.getTE(state., pos))
    	return true;
    }
    
    @Override
    public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
    	if (((TileEntityRuneSocket) blockAccess.getTileEntity(pos)).getPowered())
        {
        	return (EnumFacing)(blockState.getValue(FACING)) == side ? 15 : 0;
        }
    	return 0;
    }
    
    @Override
    public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        if (((TileEntityRuneSocket) blockAccess.getTileEntity(pos)).getPowered())
        {
        	return (EnumFacing)(blockState.getValue(FACING)) == side ? 15 : 0;
        }
        return 0;
    }
    /*
    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack){
    	
    	super.harvestBlock(world, player, pos, state, te, stack);
    	this.notifyNeighbors(world, pos, (EnumFacing)state.getValue(FACING));
    }*/
    
    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
        //TileEntity tileEntity = world.getTileEntity(pos);
        
        if (world.getTileEntity(pos) instanceof TileEntityRuneSocket)
        {
        	TileEntityRuneSocket tileEntity = (TileEntityRuneSocket) world.getTileEntity(pos);
        	
        	if (((TileEntityRuneSocket) tileEntity).getPowered())
            {
        		this.notifyNeighbors(world, pos, (EnumFacing)state.getValue(FACING));
            }
        	
        	if(tileEntity.getCoords().getY()>0){
        		((RuneSocket) world.getBlockState(tileEntity.getCoords()).getBlock()).breakFriendship(world,tileEntity.getCoords(), false);
        	}
        	
            //InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityRuneSocket)tileentity);
        	EntityItem entityItem = new EntityItem(world, pos.getX()+.5, pos.getY(), pos.getZ()+.5, tileEntity.getStack());
            world.spawnEntity(entityItem);
        }
		
        super.breakBlock(world, pos, state);
    }

        

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
        	TileEntityRuneSocket te = getTE(world, pos);
            if (te.getStack().isEmpty()) {
            	if(player.getHeldItem(hand).getItem() instanceof ItemRune){
	                if (!player.getHeldItem(hand).isEmpty()) {
	                    // There is no item in the pedestal and the player is holding an item. We move that item
	                    // to the pedestal
	                    //te.setStack(new ItemStack(player.getHeldItem(hand).getItem(), 1, player.getHeldItem(hand).getMetadata()));
	                	te.setStack(player.getHeldItem(hand).splitStack(1));
	                    //player.inventory.setInventorySlotContents(player.inventory.currentItem, ItemStack.EMPTY);
	                    // Make sure the client knows about the changes in the player inventory
	                    player.openContainer.detectAndSendChanges();
	                }
                }
            } else {
            	if(player.isSneaking()) {
	                // There is a stack in the pedestal. In this case we remove it and try to put it in the
	                // players inventory if there is room
	                ItemStack stack = te.getStack();
	                te.setStack(ItemStack.EMPTY);
	                if (!player.inventory.addItemStackToInventory(stack)) {
	                    // Not possible. Throw item in the world
	                    EntityItem entityItem = new EntityItem(world, pos.getX()+.5, pos.getY(), pos.getZ()+.5, stack);
	                    world.spawnEntity(entityItem);
	                } else {
	                    player.openContainer.detectAndSendChanges();
	                }
            	}
            }
        	
        }

        // Return true also on the client to make sure that MC knows we handled this and will not try to place
        // a block on the client
        return true;
    }
}
