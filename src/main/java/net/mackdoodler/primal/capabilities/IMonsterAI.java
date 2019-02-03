package net.mackdoodler.primal.capabilities;

import java.util.ArrayList;
import java.util.List;

public interface IMonsterAI {
	
	ArrayList<String> getMobAI();
	
	void setMobAI(ArrayList<String> currentAI);

	int getSleepDosage();
	
	void addSleepDosage(int dosage);
	
	int getSleepThreshhold();
	
	void setSleepThreshhold(int threshhold);
	
	boolean isThreshholdSet();
	
	int getSleepTimer();
	
	void setSleepTimer(int timer);
	
	boolean getModified();
	
	void setModified(boolean modified);
}
