package net.mackdoodler.primal.tileentity;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.mackdoodler.primal.handlers.MobHandler.ButcherEntry;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityCorpse extends TileEntity{

	private final ItemStackHandler inventory = new ItemStackHandler(27); //this number is arbitrary, and im assuming that no butchery lists will be over 27 itemstacks, but the Trojans didnt expect their fancy new horse to be filled with Greek scum, now did they?
	private String entityIdentity;
	private boolean unharmed;
	private boolean inventoryCreated = false;
	
	public TileEntityCorpse(){
		super();
		unharmed = true;
	}
	
	public void setEntiyIdentity(String entityIdentity){
		this.entityIdentity = entityIdentity;
	}
	
	public String getEntiyIdentity(){
		return entityIdentity;
	}
	
	/*
	public boolean getRenderEligibility(){
		boolean eligible = false;
		//System.out.println(this.getEntiyIdentity());
		switch(this.getEntiyIdentity()){
		case "minecraft:cow":
		case "minecraft:pig":
			
			eligible = true;
			break;
			
		}
		
		return eligible;
	}*/
	
	public boolean getUnharmed(){
		return unharmed;
	}
	
	public void harm(){
		unharmed = false;
	}
	
	/*
	 * fills the corpse inventory
	 */
	public boolean fillInventory(List<ItemStack> list){
		if(!inventoryCreated){
			//inventory = new ItemStackHandler(list.size()); <- fucks up nbt storage when leaving and reentering a world, guess it has to be hardcoded if using a noncustom inventory :/
			for(int i=0;i<(list.size()<27?list.size():27);i++){
				
				//System.out.println(list.get(i).getCount());
				inventory.setStackInSlot(i, list.get(i));
			}
			return true;
		}
		return false;
	}
	
	/*
	 * retrieves a random item from the corpse and returns it
	 */
	public ItemStack getRandomItem() {
		//System.out.println(inventory.getSlots());
		int slot = (int)(Math.random()*inventory.getSlots());
		int counter = 0;

		while(inventory.getStackInSlot(slot).isEmpty()&&counter<inventory.getSlots()){
			slot++;
			counter++;
			if(slot>inventory.getSlots()-1){
				slot=0;
			}
		}
		return inventory.extractItem(slot, 1, false);
	}
	
	@Override
	public boolean hasCapability(@Nonnull Capability<?> cap, @Nonnull EnumFacing side)
	{
		return cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(cap, side);
	}

	@Nonnull
	@Override
	public <T> T getCapability(@Nonnull Capability<T> cap, @Nonnull EnumFacing side)
	{
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory);
		}
		return super.getCapability(cap, side);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		inventory.deserializeNBT(nbt);
	}
	
	@Nonnull
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		nbt = super.writeToNBT(nbt);
		nbt.merge(inventory.serializeNBT());
		return nbt;
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate)
	{
	    return (oldState.getBlock() != newSate.getBlock());
	}
	
	/*
	 * these last three methods arnt needed I thiiiink since the client shouldnt need to know anything, but if anything ever breaks ill reenable them I guess
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
    }*/
}
