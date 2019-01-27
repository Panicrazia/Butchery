package net.mackdoodler.primal.entity.construct;

import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.Maps;

public enum EnumSlotType {
	
	/*	
	LEG,
	QUADLEG,
	ARM,
	BODY,
	LOWERBODY,
	WING,
	HEAD,
	FIN,
	TAIL,
	TENTACLE;
	*/
	
	LEG("Leg", new EnumLimbType[]{EnumLimbType.LEG, EnumLimbType.QUADLEG, EnumLimbType.FIN, EnumLimbType.TENTACLE}),
	ARM("Arm", new EnumLimbType[]{EnumLimbType.ARM, EnumLimbType.QUADLEG, EnumLimbType.FIN, EnumLimbType.TENTACLE}),
	LOWERBODY("Arm", new EnumLimbType[]{EnumLimbType.LOWERBODY, EnumLimbType.TENTACLE}),
	WING("Arm", new EnumLimbType[]{EnumLimbType.WING, EnumLimbType.ARM, EnumLimbType.FIN, EnumLimbType.TENTACLE}),
	HEAD("Arm", new EnumLimbType[]{EnumLimbType.HEAD}),
	TAIL("Arm", new EnumLimbType[]{EnumLimbType.TAIL, EnumLimbType.TENTACLE}),
	TENTACLE("Tentacle", new EnumLimbType[]{EnumLimbType.TENTACLE});
	
	private final String namie;
	EnumLimbType[] types;

    private static final Map<String, EnumSlotType> BY_NAME = Maps.<String, EnumSlotType>newHashMap();
    
    //for now the constructor will be this
    
    private EnumSlotType(String namie, EnumLimbType[] types)
    {
        this.namie = namie;
        this.types = types;
    }

    public String getName()
    {
        return this.namie;
    }
    
    @Nullable
    public static EnumSlotType getByName(String nameIn)
    {
        return BY_NAME.get(nameIn);
    }

    public boolean getValidForSlotType(EnumLimbType type)
    {
    	for(EnumLimbType iter:types){
    		//TODO: I believe == is the correct one for enums but google it later to make sure
    		if(iter == type){
    			return true;
    		}
    	}
    	return false;
    }
}
