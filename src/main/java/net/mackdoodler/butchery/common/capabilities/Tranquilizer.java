package net.mackdoodler.butchery.common.capabilities;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class Tranquilizer implements ITranquilizer{

	private ArrayList<String> monsterAI = new ArrayList<String>(0);
	private boolean modified = false;
	private boolean threshholdSet = false;
	private int sleepDosage = 0;
	private int sleepThreshhold = 4;
	private int sleepTimer = 0;
	
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

	@Override
	public int getSleepDosage() {
		return sleepDosage;
	}

	@Override
	/**
	 * adds the parameter to the current dosage, dont send in negative values
	 * @param dosage
	 */
	public void addSleepDosage(int dosage) {
		this.sleepDosage += dosage;
	}

	@Override
	public int getSleepThreshhold() {
		return sleepThreshhold;
	}

	@Override
	public void setSleepThreshhold(int threshhold) {
		this.sleepThreshhold = threshhold;
		//this.threshholdSet = true;
	}
	
	@Override
	public void setThreshholdSet(boolean th) {
		this.threshholdSet = th;
	}
	
	@Override
	public boolean isThreshholdSet() {
		return threshholdSet;
	}

	@Override
	public int getSleepTimer() {
		return sleepTimer;
	}

	@Override
	public void setSleepTimer(int timer) {
			this.sleepTimer = timer;
	}

}
