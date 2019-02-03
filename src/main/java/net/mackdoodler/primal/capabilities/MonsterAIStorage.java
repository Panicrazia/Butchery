package net.mackdoodler.primal.capabilities;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.util.Constants;

public class MonsterAIStorage implements IStorage<IMonsterAI> 
{ 
	@Override 
	public NBTBase writeNBT(Capability<IMonsterAI> capability, IMonsterAI instance, EnumFacing side){
		NBTTagCompound compound = new NBTTagCompound();
		int arrayListSize = instance.getMobAI().size();
		
		compound.setBoolean("modified", instance.getModified());
		compound.setInteger("sleepDosage", arrayListSize);
		compound.setInteger("sleepThreshhold", arrayListSize);
		compound.setInteger("sleepTimer", arrayListSize);
		compound.setInteger("arrayListSize", arrayListSize);
		for(int i = 0; i<arrayListSize; i++){
			compound.setString("mobAIString"+i, instance.getMobAI().get(i));
		}
		
		return compound;
	}

	@Override 
	public void readNBT(Capability<IMonsterAI> capability, IMonsterAI instance, EnumFacing side, NBTBase nbt){
		NBTTagCompound compound = (NBTTagCompound)nbt; 
		int arrayListSize = compound.hasKey("arrayListSize", Constants.NBT.TAG_INT) ? compound.getInteger("arrayListSize") : 0;
		ArrayList<String> monsterAI= new ArrayList<String>(0);
		
		for(int i = 0; i<arrayListSize; i++){
			monsterAI.set(i, compound.getString("mobAIString"+i));
		}
		
		instance.setMobAI(monsterAI);
		instance.setModified(compound.hasKey("modified") ? (compound.getBoolean("modified")) : false);
		instance.addSleepDosage((int) (compound.hasKey("sleepDosage") ? (compound.getInteger("sleepTimer")) : 0));
		instance.setSleepThreshhold((int) (compound.hasKey("sleepThreshhold") ? (compound.getInteger("sleepThreshhold")) : 0));
		instance.setSleepTimer((int) (compound.hasKey("sleepTimer") ? (compound.getInteger("sleepTimer")) : 0));
	}
}
