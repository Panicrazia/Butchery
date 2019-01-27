package net.mackdoodler.primal.capabilities;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class MonsterAI implements  IMonsterAI{

	private ArrayList<String> monsterAI = new ArrayList<String>(0);
	private boolean modified = false;
	
	@Override
	public ArrayList<String> getMobAI() {
		return monsterAI;
	}

	@Override
	public void setMobAI(ArrayList<String> currentAI) {
		for(String item : currentAI){
			monsterAI.add(item);
		}
	}

	@Override
	public boolean getModified() {
		return modified;
	}

	@Override
	public void setModified(boolean modified) {
		this.modified = modified;
	}
}
