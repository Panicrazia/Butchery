package net.mackdoodler.primal.handlers;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

public class ButcheryItemStackHandler implements INBTSerializable<NBTTagCompound>{
	
    private List<ItemStack> tools = new ArrayList<ItemStack>();
    private List<ItemStack> stacks = new ArrayList<ItemStack>();
	
	public ButcheryItemStackHandler()
    {
		tools = new ArrayList<ItemStack>(0);
		stacks = new ArrayList<ItemStack>(0);
    }
    
    public ItemStack getToolInSlot(int slot)
    {
        return this.tools.get(slot);
    }
    
    public List<ItemStack> getToolList(){
    	return tools;
    }
    
    public int getSlots()
    {
        return this.tools.size();
    }
    
    @Nonnull
    public void insertItem(@Nonnull ItemStack tool, @Nonnull ItemStack stack)
    {
        this.tools.add(tool);
    	this.stacks.add(stack);
    }

    @Nonnull
    public ItemStack extractItem(int slot)
    {
		tools.remove(slot);
		return this.stacks.remove(slot);
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
    	NBTTagList nbtTagListTools = new NBTTagList();
	    NBTTagList nbtTagListStacks = new NBTTagList();
	    
	    for (int i = 0; i < tools.size(); i++)
	    {
            NBTTagCompound itemTagTools = new NBTTagCompound();
            tools.get(i).writeToNBT(itemTagTools);
            nbtTagListTools.appendTag(itemTagTools);
            
            NBTTagCompound itemTagStacks = new NBTTagCompound();
            stacks.get(i).writeToNBT(itemTagStacks);
            nbtTagListStacks.appendTag(itemTagStacks);
	    }
	    
	    NBTTagCompound nbt = new NBTTagCompound();
	    nbt.setTag("Tools", nbtTagListTools);
	    nbt.setTag("Stacks", nbtTagListStacks);
	    return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
    	NBTTagList tagListTools = nbt.getTagList("Tools", Constants.NBT.TAG_COMPOUND);
	    NBTTagList tagListStacks = nbt.getTagList("Stacks", Constants.NBT.TAG_COMPOUND);
	    
	    for (int i = 0; i < tagListTools.tagCount(); i++)
	    {
	        NBTTagCompound itemTagsTools = tagListTools.getCompoundTagAt(i);
        	tools.add(new ItemStack(itemTagsTools));
        	
	    	NBTTagCompound itemTagsStacks = tagListStacks.getCompoundTagAt(i);
        	stacks.add(new ItemStack(itemTagsStacks));
	    }
    }
}
