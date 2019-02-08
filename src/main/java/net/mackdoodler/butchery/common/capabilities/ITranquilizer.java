package net.mackdoodler.butchery.common.capabilities;

import java.util.ArrayList;
import java.util.List;

public interface ITranquilizer {
	
	ArrayList<String> getMobAI();
	
	void setMobAI(ArrayList<String> currentAI);

	int getSleepDosage();
	
	void addSleepDosage(int dosage);
	
	int getSleepThreshhold();
	
	void setSleepThreshhold(int threshhold);
	
	boolean isThreshholdSet();
	
	void setThreshholdSet(boolean threshholdSet);
	
	int getSleepTimer();
	
	void setSleepTimer(int timer);
	
	boolean getModified();
	
	void setModified(boolean modified);
}
