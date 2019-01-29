package net.mackdoodler.primal.tileentity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.mackdoodler.primal.handlers.ButcheryItemStackHandler;
import net.mackdoodler.primal.handlers.MobButcheryDropsList.ButcherEntry;
import net.mackdoodler.primal.handlers.MobButcheryDropsList.IndividualButcherEntry;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityCorpse extends TileEntity{

	private ButcheryItemStackHandler inventory;
	private String entityIdentity;
	private boolean unharmed;
	private boolean inventoryCreated;
	
	public TileEntityCorpse(){
		super();
		unharmed = true;
		inventoryCreated = false;
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
	
	public boolean fillInventory(List<IndividualButcherEntry> list)
	{
		if(!inventoryCreated){
			
			List<ItemStack> tools = new ArrayList<ItemStack>();
			List<ItemStack> stacks = new ArrayList<ItemStack>();
			
			int totalItems = 0;
			
			for(int i=0;i<list.size();i++){
				tools.add(list.get(i).getTool());
				stacks.add(list.get(i).getRandItemStack());
				totalItems += stacks.get(i).getCount();
				//System.out.println(stacks.get(i).getCount());
			}
			
			//System.out.println("Total Items: "+totalItems);
			
			inventory = new ButcheryItemStackHandler();
			
			for(int i=0;i<stacks.size();i++){
				for(int j=0;j<stacks.get(i).getCount();j++){
					ItemStack stack = stacks.get(i).copy();
					stack.setCount(1);
					inventory.insertItem(tools.get(i).copy(), stack);
				}
			}
			
			inventoryCreated = true;
			return true;
		}
		return false;
	}
	
	public ItemStack getRandomItem(EntityPlayer player)
	{
		List<ItemStack> tools = inventory.getToolList();
		ItemStack tool = player.getHeldItemMainhand();
	    Map<Integer,Integer> locatinator= new HashMap<Integer,Integer>();
	    
    	int numberoUno = 0;
    	for(int i=0; i < tools.size(); i++){
    		if(tools.get(i).isItemEqual(tool)){
    			locatinator.put(numberoUno, i);
    			numberoUno++;
    		}
    	}
    	
		if(numberoUno > 0){
			numberoUno = (int)(Math.random() * numberoUno);
			return inventory.extractItem(locatinator.get(numberoUno));
		}
        return ItemStack.EMPTY;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		inventory = new ButcheryItemStackHandler();
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
