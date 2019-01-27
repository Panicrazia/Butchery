package net.mackdoodler.primal.items.bodyparts.enums;

import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.Maps;

public enum EnumBodyPartType {
	//for all body parts that dont need extra information
	
	//default
	GUTS("Guts"),
	
	//bones i dunno what special things these should do :/
	LIGHTBONE("Light Bone"),
	WITHERBONE("Withered Bone"),
	DRAGONBONE("Dragon Bone"),
	CARTILAGE("Cartilage"),
	
	//flesh, if possible make it alter the textures of the final parts
	HUMAN("Human Flesh"),
	ENDER("Ender Flesh"),
	
	//eyes (might need their own enum later on depending on how things go) (ie dif eyes giving dif effects/abilities (which they should))
	NORMALEYE("Normal Eye"),
	BATEYE("Bat Eye"),
	BATEAR("Bat Ear"),
	
	//misc
	HORN("Horn"),
	CHITIN("Chitin");
	
	private final String namie;

    private static final Map<String, EnumBodyPartType> BY_NAME = Maps.<String, EnumBodyPartType>newHashMap();

    //for now the constructor will be this
    
    private EnumBodyPartType(String namie)
    {
        this.namie = namie;
    }

    public String getName()
    {
        return this.namie;
    }
    
    @Nullable
    public static EnumBodyPartType getByName(String nameIn)
    {
        return BY_NAME.get(nameIn);
    }
}
