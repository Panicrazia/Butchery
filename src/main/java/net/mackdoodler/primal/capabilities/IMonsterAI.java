package net.mackdoodler.primal.capabilities;

import java.util.ArrayList;
import java.util.List;

public interface IMonsterAI {
	
	ArrayList<String> getMobAI();

	void setMobAI(ArrayList<String> currentAI);
	
	boolean getModified();
	
	void setModified(boolean modified);
}
