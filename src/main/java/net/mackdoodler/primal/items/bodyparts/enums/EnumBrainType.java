package net.mackdoodler.primal.items.bodyparts.enums;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

/*
 * rotting brain
 * animal brain
 * dragon brain
 * withered brain?
 * slimy core
 * civilized brain (name seems off, its the brain for people)
 * 
 * artificials:
 * enchanced brain
 * 
 * COMBATIBILITY WITH THAT ONE MOD THAT ADDS HAMPSTERS AND HAMPSTER WHEELS TO MAKE A HAMPSTER BRAIN (think overwatch's Hammon)
 * 
 * 
 */

public enum EnumBrainType {
	//every brain has a capacity, every command input into a construct has a capacity associated with it, a brain cannot have more commands than its capacity can hold
	//(commands are put into brains during neurosurgery) brains should also be by default locked in there but a special headtype can allow them to be taken out and input on the fly
	ROTTING("Rotting Brain", 0),
	ANIMAL("Animal Brain", 2),
	SLIMEY("Slimey Core", 1),
	VILLAGER("Villager Brain", 3),
	DRAGON("Dragon Brain", 4),
	ENHANCED("Enhanced Brain", 5);
	
	private final String namie;
    private final int processingPower;
    
    private static final Map<String, EnumBrainType> BY_NAME = Maps.<String, EnumBrainType>newHashMap();

    private EnumBrainType(String namie, int numeral)
    {
        this.namie = namie;
        this.processingPower = numeral;
    }
    
    public static Set<String> getNames()
    {
        return BY_NAME.keySet();
    }

    public String getName()
    {
        return this.namie;
    }

    public int getProcessingPower()
    {
        return this.processingPower;
    }
    
    @Nullable
    public static EnumBrainType getByName(String nameIn)
    {
        return BY_NAME.get(nameIn);
    }
}