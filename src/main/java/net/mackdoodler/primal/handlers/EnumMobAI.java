package net.mackdoodler.primal.handlers;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Function;

import javax.annotation.Nullable;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import net.mackdoodler.primal.items.bodyparts.enums.EnumBodyPartType;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFindEntityNearest;
import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraftforge.common.capabilities.Capability;

public enum EnumMobAI {
	
	/*
	 * this list does not include mob specific ai that are located in the actual entity files (ex: slime, ghast)
	 * 
	 */
	/*
	EntityAIAttackMelee;	//needs entityCreature
	EntityAIAttackRanged;	//needs to implement IRangedAttackMob
	EntityAIAttackRangedBow;	//needs entityMob that implements IRangedAttackMob
	EntityAIAvoidEntity;	//needs entityCreature, needs entity to avoid
	EntityAIBeg;	//needs wolf
	EntityAIBreakDoor;	//
	EntityAICreeperSwell;	//needs creeper
	EntityAIDefendVillage;	//needs ironGolem
	EntityAIDoorInteract;	//needs ground pathing
	EntityAIEatGrass;	//technically useable for all entities, since the grass bonus method is in the base entity class
	EntityAIFindEntityNearest;	//should NOT be used for entityCreature and above ("Use NearestAttackableTargetGoal.class for PathfinerMob mobs!")
	EntityAIFindEntityNearestPlayer;	//should NOT be used for entityCreature and above ("Use NearestAttackableTargetGoal.class for PathfinerMob mobs!")
	EntityAIFleeSun;	//needs entityCreature, might error with no head equipment slot
	EntityAIFollow;	//needs flying or ground pathing
	EntityAIFollowGolem;	//needs villager, does nothing when they are adults
	EntityAIFollowOwner;	//needs entityTameable
	EntityAIFollowOwnerFlying;	//needs entityTameable
	EntityAIFollowParent;	//needs entityAnimal
	EntityAIHarvestFarmland;	//needs villager
	EntityAIHurtByTarget;	//needs entityCreature, needs blacklist for optional reinforcement mobs
	EntityAILandOnOwnersShoulder;	//needsEntityShoulderRiding
	EntityAILeapAtTarget;	//
	EntityAILlamaFollowCaravan;	//needs llama
	EntityAILookAtTradePlayer;	//needs villager
	EntityAILookAtVillager;	//needs ironGolem
	EntityAILookIdle;	//
	EntityAIMate;	//needs entityAnimal
	EntityAIMoveIndoors;	//needs entityCreature
	EntityAIMoveThoughVillage;	//needs entityCreature, needs ground pathing
	EntityAIMoveToBlock;	//needs entityCreature
	EntityAIMoveTowardsRestriction;	//needs entityCreature
	EntityAIMoveTowardsTarget;	//needs entityCreature
	EntityAINearestAttackableTarget;	//needs entityCreature and a target class
	EntityAIOcelotAttack;	//suprisingly, needs entityLiving and thats it
	EntityAIOcelotSit;	//needs entityOcelot
	EntityAIOpenDoor;	//needs entityLiving
	EntityAIOwnerHurtByTarget;	//needs entityTameable
	EntityAIOwnerHurtTarget;	//needs entityTameable
	EntityAIPanic;	//needs entityCreature
	EntityAIPlay; //needs villager, will do nothing if villager isnt a child
	EntityAIRestrictOpenDoor;	//entity creature, needs a village door, needs ground pathing
	EntityAIRestrictSun;	//needs entityCreature, an entity without a slot for helmets might error?, is code for avoiding sun durring daytme
	EntityAIRunAroundLikeCrazy;	//needs abstractHorse, is ai for horse running around when untamed but being ridden
	EntityAISit;	//needs entityTameable
	EntityAISkeletonRiders;	//needs skeleton horse (also is incredibly specific like wtf lol)
	EntityAISwimming;	//
	EntityAITarget;	//needs entityCreature
	EntityAITargetNonTamed;	//needs entityTameable
	EntityAITempt;	//needs entityCreature, is ai for baiting animals, needs ground pathing
	EntityAITradePlayer;	//needs villager
	EntityAIVillagerInteract;	//needs villager
	EntityAIVillagerMate;	//needs villager
	EntityAIWander;	//needs entityCreature
	EntityAIWanderAvoidWater;	//needs entityCreature, checks if in water
	EntityAIWanderAvoidWaterFlying;	//needs entityCreature, checks if in water, avoids being above water by 20 blocks
	EntityAIWatchClosest;	//
	EntityAIWatchClosest2;	//watchClosest but with mutexbit of 3 instead of 2
	EntityAIZombieAttack;   //needs zombie, is just EntityAIAttackMelee but with code to raise arms
	//located in entitySlime
	EntitySlime.AISlimeFloat;	//needs slime
	EntitySlime.AISlimeAttack;	//needs slime
	EntitySlime.AISlimeFaceRandom;	//needs slime
	EntitySlime.AISlimeHop;	//needs slime
	*/
	
	/*
    this.tasks.addTask(1, new EntitySlime.AISlimeFloat(this));
    this.tasks.addTask(2, new EntitySlime.AISlimeAttack(this));
    this.tasks.addTask(3, new EntitySlime.AISlimeFaceRandom(this));
    this.tasks.addTask(5, new EntitySlime.AISlimeHop(this));
    this.targetTasks.addTask(1, new EntityAIFindEntityNearestPlayer(this));
    this.targetTasks.addTask(3, new EntityAIFindEntityNearest(this, EntityIronGolem.class));
	*/

	EntryEntityAIFindEntityNearestPlayer("EntityAIWander", EntityAIWander.class, EntityLiving.class),
	EntryEntityAIFindEntityNearest("EntityAIWander", EntityAIWander.class, EntityLiving.class, EntityLiving.class),
	EntryEntityAIWander("EntityAIWander", EntityAIWander.class, EntityCreature.class, double.class),
	
	
	EntryAISlimeFloat("SlimeAISlimeFloat", EntitySlime.class.getDeclaredClasses()[0], EntitySlime.class, double.class),
	EntryAISlimeAttack("SlimeAISlimeAttack", EntityAIWander.class, EntitySlime.class, double.class),
	EntryAISlimeFaceRandom("SlimeAISlimeFaceRandom", EntityAIWander.class, EntitySlime.class, double.class),
	EntryAISlimeHop("SlimeAISlimeHop", EntityAIWander.class, EntitySlime.class, double.class);
	
	/*
	EntitySlime.AISlimeFloat;	//needs slime
	EntitySlime.AISlimeAttack;	//needs slime
	EntitySlime.AISlimeFaceRandom;	//needs slime
	EntitySlime.AISlimeHop;	//needs slime
	*/
	
	private final String namie;
	private final Class <? extends EntityAIBase> AI;
	private final Class[] AIParameterList;
	
    private static final Map<String, EnumMobAI> BY_NAME = Maps.<String, EnumMobAI>newHashMap();
    /*
     * 
     * 
     * jandaosfnaoi
     */
    private EnumMobAI(String namie, Class AI, Class<?>... parameterTypes)
    {
        this.namie = namie;
        this.AI = AI;
        this.AIParameterList = parameterTypes;
    }
    
    /**
     * 
     * @param parameters the parameters for the creation of the AI you are attempting to make
     * @return returns a new instance of the given ai if the parameters are correct, 
     * returns an EntityAILookIdle if parameters are not eligible but the entity is, 
     * returns null if you dun fucked up
     */
    public EntityAIBase makeNewClass(Object... parameters){
    	EntityAIBase thingToMake;
    	
    	//if this isnt true then it will not work
    	if(AIParameterList.length == parameters.length){
    		
    		//ensures that even if making the ai fails we can return an idle ai that will still work
    		if(parameters[0] instanceof EntityLiving){
        		Constructor con;
        		
    			try {
    				
					con = AI.getConstructor(AIParameterList);
					
					try {
						
						thingToMake = (EntityAIBase) con.newInstance(parameters);
						return thingToMake;
						
					} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
							| InvocationTargetException e) {
						
						System.out.println("SOMETHING FUCKED UP AT THE CONSTRUCTOR INSTANTIATION FOR "+AI.toString());
						e.printStackTrace();
						return new EntityAILookIdle((EntityLiving) parameters[0]);
					}
				} catch (NoSuchMethodException | SecurityException e) {

					System.out.println("SOMETHING FUCKED UP AT THE CONSTRUCTOR FOR "+AI.toString());
					e.printStackTrace();
					return new EntityAILookIdle((EntityLiving) parameters[0]);
				}
    		}
    	}
        return null;
    }
    
    public String getName(){
        return this.namie;
    }
    
    @Nullable
    public static EnumMobAI getByName(String nameIn)
    {
        return BY_NAME.get(nameIn);
    }
}
