package net.mackdoodler.primal.tileentity;

import javax.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import net.mackdoodler.primal.blocks.RuneSocket;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityRuneSocket extends TileEntity{

	private ItemStack stack = ItemStack.EMPTY;
	private int dimension;
	private int x, y, z;
	private boolean powered = false;
    //if this block is a master (also known as sender) for another rune socket
    private boolean master = false;
    //if the rune has a buddy then it is stored here, if it doesnt have one it is null
    //private RuneSocket buddy = null;
    
    public ItemStack getStack() {
        return stack;
    }
    
    public boolean getPowered() {
        return powered;
    }
    
    public boolean getMaster() {
        return master;
    }
    
    //curently unused
    public int getDimension()
	{
		return dimension;
	}
	
	public BlockPos getCoords()
	{
		return new BlockPos(x, y, z);
	}
	
	public void setMaster(boolean master){
		this.master = master;
	}
	
	public void setPowered(boolean powered){
		this.powered = powered;
	}
	
	public void setCoords(int posX, int posY, int posZ){
		//this.dimension = dimension;
		this.x = posX;
		this.y = posY;
		this.z = posZ;
	}

    public void setStack(ItemStack stack) {
        this.stack = stack;
        markDirty();
        if (getWorld() != null) {
            IBlockState state = getWorld().getBlockState(getPos());
            getWorld().notifyBlockUpdate(getPos(), state, state, 3);
        }
    }
    
    @Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate)
	{
	    return (oldState.getBlock() != newSate.getBlock());
	}

    @Override
    public NBTTagCompound getUpdateTag() {
        // getUpdateTag() is called whenever the chunkdata is sent to the
        // client. In contrast getUpdatePacket() is called when the tile entity
        // itself wants to sync to the client. In many cases you want to send
        // over the same information in getUpdateTag() as in getUpdatePacket().
        return writeToNBT(new NBTTagCompound());
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        // Prepare a packet for syncing our TE to the client. Since we only have to sync the stack
        // and that's all we have we just write our entire NBT here. If you have a complex
        // tile entity that doesn't need to have all information on the client you can write
        // a more optimal NBT here.
        NBTTagCompound nbtTag = new NBTTagCompound();
        this.writeToNBT(nbtTag);
        return new SPacketUpdateTileEntity(getPos(), 1, nbtTag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        // Here we get the packet from the server and read it into our client side tile entity
        this.readFromNBT(packet.getNbtCompound());
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
    	
        super.readFromNBT(compound);
        
        if (compound.hasKey("item")) {
            stack = new ItemStack(compound.getCompoundTag("item"));
        } else {
            stack = ItemStack.EMPTY;
        }
        
        
        if(compound.hasKey("coordinates")){
        	
        	NBTTagCompound tag = compound.getCompoundTag("coordinates");
        			
        	int[] coords = tag.getIntArray("coordinates");
        	this.dimension = coords[0];
    		this.x = coords[1];
    		this.y = coords[2];
    		this.z = coords[3];
        }
        if(compound.hasKey("powered")){
        	powered = compound.getBoolean("powered");
    	}
    	if(compound.hasKey("master")){
    		master = compound.getBoolean("master");
    	}
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        
    	super.writeToNBT(compound);
    	
    	if (!stack.isEmpty()) {
    		
            NBTTagCompound tagCompound = new NBTTagCompound();
            stack.writeToNBT(tagCompound);
            compound.setTag("item", tagCompound);
        }
    	
    	if(this.y!=0){
	    	NBTTagCompound tagCompound = new NBTTagCompound();
	    	tagCompound.setIntArray("coordinates", new int[]{dimension, x, y, z});
	    	compound.setTag("coordinates", tagCompound);
    	}
    	if(powered == true){
    		compound.setBoolean("powered", powered);
    	}
    	if(master == true){
    		compound.setBoolean("master", master);
    	}
    	
        return compound;
    }
}
