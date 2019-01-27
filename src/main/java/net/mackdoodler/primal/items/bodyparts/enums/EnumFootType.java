package net.mackdoodler.primal.items.bodyparts.enums;

import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.Maps;

/* 
	 * hoofs
	 * bird foot
	 * suctioncup
	 * spiderleg
	 */

public enum EnumFootType {
	NORMAL("Normal Foot", 10, 5),
	BIRD("Bird Foot", 5, 5),
	HOOF("Hoof", 20, 5),//maybe add in a distinction between cloven hoofs and not (AND WHATEVER THE FUCK A LLAMA FOOT IS SERIOUSLY GOOGLE THAT SHIT IS HILARIOUS)
	//consider making the ghast and squids suction cups? then have the tentacles be creatable? mabs not cuz youd only use them for tentacles... but large tentacles...? either way something to think about
	GHASTTENTACLE("Ghast Tentacle", 3, 40),
	SQUIDTENTACLE("Squid Tentacle", 3, 20),
	SPIDER("Spider Leg", 10, 2);
	
	private final String namie;
	private final int land;
	private final int aquatic;

	private static final Map<String, EnumFootType> BY_NAME = Maps.<String, EnumFootType>newHashMap();
	
    /*
     * things to possibly add here?
     * 
     * step sound effect (probs not since the only time that will be gotten is with actual parts which wont have this in there)
     * 
     * weight limit? (probs should be a thing with how it all is)
     */
    private EnumFootType(String namie, int landSpeed, int aquaticSpeed)
    {
        this.namie = namie;
        this.land = landSpeed;
        this.aquatic = aquaticSpeed;
        
    }

    public String getName()
    {
        return this.namie;
    }
    
    
    @Nullable
    public static EnumFootType getByName(String nameIn)
    {
        return BY_NAME.get(nameIn);
    }

    public int getLandSpeed()
    {
        return this.land;
    }
    
    public int getAquaticSpeed(){
    	return this.aquatic;
    }
}
