package net.mackdoodler.primal.tileentity;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.mackdoodler.primal.handlers.MobButcheryDropsList.ButcherEntry;
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

	private ItemStackHandler inventory = new ItemStackHandler(27); //this number is arbitrary, and im assuming that no butchery lists will be over 27 itemstacks, but the Trojans didnt expect their fancy new horse to be filled with Greek scum, now did they?
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
	
	public boolean getUnharmed(){
		return unharmed;
	}
	
	public void harm(){
		unharmed = false;
	}
	
	public boolean fillInventory(List<ItemStack> list){
		
		if(!inventoryCreated){
			//inventory = new ItemStackHandler(list.size()); <- fucks up nbt storage when leaving and reentering a world, guess it has to be hardcoded if using a noncustom inventory :/
			for(int i=0;i<(list.size()<27?list.size():27);i++){
				System.out.println(list.get(i).getCount());
				inventory.setStackInSlot(i, list.get(i));
			}
			inventoryCreated = true;
			return true;
		}
		
		return false;
	}
	
	/*
	 * retrieves a random item from the corpse and returns it
	 */
	public ItemStack getRandomItem() {
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
		return cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? (T)inventory : super.getCapability(cap, side);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		super.readFromNBT(compound);
	}
	
	@Nonnull
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		compound.setTag("inventory", inventory.serializeNBT());
		return super.writeToNBT(compound);
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate)
	{
	    return (oldState.getBlock() != newSate.getBlock());
	}
}
