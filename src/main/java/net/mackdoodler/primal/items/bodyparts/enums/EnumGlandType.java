package net.mackdoodler.primal.items.bodyparts.enums;

import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.Maps;

/*
 * gunpowder
 * spider silk
 * poison
 * mushroom
 * wool?
 * blaze powder (or more generally fire)
 * ender
 * tear? (ghasts as a meme, could be used for water sources in things? idfk, i like the meme though so its happening (also from villagers))
 * pure distillation of endless hatred (silverfish, odv)(mybe llamas too?)
 * wither
 * ink would be a thing but ink sacs already are a thing
 * ender dragon breath
 * ghast gas - used to make balloon like flying
 * 
 * artificials:
 * miasmic/plague sac
 */

public enum EnumGlandType {
	GUNPOWDER("Powdery Gland", 0),
	SILK("Spider Silk Sac", 1),
	POISON("Poison Gland", 2),
	MUSHROOM("Mycellic Gland", 3),
	WOOL("Woolen Gland", 4),
	BLAZE("Firey Gland", 5),
	ENDER("Ender Gland", 6),
	TEAR("Tear Gland", 7),
	HATE("Sac of Pure and Endless Hatred", 8),
	WITHER("Withering Gland", 9),
	ENDDRAGON("Ender Dragon Breath Gland", 10),
	GHAST("Ghast Gas Sac", 11),
	MIASMIC("Miasmic Gland", 12),
	SLEEP("Drowsy Sac", 13),
	MILK("Milk Gland", 14);
	
	private final String namie;
    private final int numeral;
    
    private static final Map<String, EnumGlandType> BY_NAME = Maps.<String, EnumGlandType>newHashMap();

    //for now the constructor will be this
    
    private EnumGlandType(String namie, int numeral)
    {
        this.namie = namie;
        this.numeral = numeral;
    }

    public String getName()
    {
        return this.namie;
    }
    
    @Nullable
    public static EnumGlandType getByName(String nameIn)
    {
        return BY_NAME.get(nameIn);
    }

    public int getNumber()
    {
        return this.numeral;
    }
    
    
}
