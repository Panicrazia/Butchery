package net.mackdoodler.primal.entity.construct;

import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.mackdoodler.primal.entity.construct.EntityConstructCarrier.ConstructSlot;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.world.World;

public abstract class EntityConstruct extends EntityCreature implements IEntityOwnable{

	public EnumLimbType type;
	public EntityConstructCarrier daddy;
	
	//-1 if this part is not attatched to a slot
	public int formerSlot = -1;
	
	public EntityConstruct(World worldIn, @Nonnull EnumLimbType type) {
		super(worldIn);
		this.type=type;
		// TODO Auto-generated constructor stub
	}
	
	public EnumLimbType getLimbType(){
		return type;
	}
	
	@Override
	public UUID getOwnerId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Entity getOwner() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected boolean canBeRidden(Entity entityIn)
    {
        return false;
    }
	
	/**
	 * 
	 * @return true if this part is attached to something, false otherwise
	 */
    public boolean isAttached()
    {
        return this.daddy!=null;
    }
	
	/**
	 * 
	 * @return the EntityConstructCarrier this part is attached to, null if nothing
	 */
    @Nullable
    public Entity getFatherEntity()
    {
        return this.daddy;
    }

    /**
     * Special method to set the slot for the construct part on the carrier along with the normal riding code
     * if -1 is not passed and this should not fit then things will get fucky
     * 
     * @param entity to ride
     * @param Slot number to go into the big daddy
     * @return true if it is now riding
     */
    public boolean attatchPart(Entity entityIn, int slotNum)
    {
    	formerSlot = slotNum;
    	if(formerSlot == -1){
    		//if its -1 that means the slot is not viable to attachment right now
    		return false;
    	}
    	else{
    		//the slot was viable, attachment code goes here
            this.daddy = (EntityConstructCarrier) entityIn;
        	this.daddy.getSlotFromNumber(formerSlot).attatchPart(this);
        	return true;
    	}
    }
    
    /**
     * Dismounts this entity from the entity it is riding.
     */
    public void detatchPart()
    {
		System.out.println("getting fully detatched now");
        if (this.daddy != null)
        {
            this.daddy.getSlotFromNumber(this.daddy.getSlotOfPassenger(this)).removePart();//possibly add a check to see if this was successful?
            this.daddy = null;
        }
    }
	
	/**
     * If the rider should be dismounted from the entity when the entity goes under water
     *
     * @param rider The entity that is riding
     * @return if the entity should be dismounted when under water
     */
	@Override
	public boolean shouldDismountInWater(Entity rider)
    {
        return false;
    }
}
