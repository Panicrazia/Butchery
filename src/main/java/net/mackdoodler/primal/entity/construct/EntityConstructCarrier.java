package net.mackdoodler.primal.entity.construct;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.google.common.collect.Lists;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public abstract class EntityConstructCarrier extends EntityConstruct {

	/*
	 * 
	 * just burn this and entityconstruc tot the ground and change as little to the mounting code as possible,
	 * have constructs have a lastSlot var that this looks at when reassigning
	 * 
	 * these slots are only to refrence when either the specific coords, a quick refrence to the entity attatched to a slot, or the slottype is needed
	 * 
	 * the parts will all be technically riding this thing, just update passengers will get heavy use out of the slots to tell them where to go
	 * 
	 * 
	 * 
	 */
	ConstructSlot[] slots;
	
	public EntityConstructCarrier(World worldIn, EnumLimbType type, int slotNumber) {
		super(worldIn, type);
        slots = new ConstructSlot[slotNumber];
        initSlots();
	}
	
	public void initSlots() {
		//has to be overridden to set the slots in each given construct piece, possibly could be done in the constructor with passing in an array of ConstructSlots?
	}
	
	public ConstructSlot getSlotFromNumber(int slotNum){
		return slots[slotNum];
	}
	
	public double getMountedXOffsetGivenSlot(int slotNum)
    {
        return slots[slotNum].getCoords()[0];
    }
	public double getMountedYOffsetGivenSlot(int slotNum)
    {
        return slots[slotNum].getCoords()[1];
    }
	public double getMountedZOffsetGivenSlot(int slotNum)
    {
        return slots[slotNum].getCoords()[2];
    }
	
	/*
	 * method which fires depening on which slot was hit with the item (now this time the enderdragon multipart code might be helpful?)
	 * 
	 */
	
	/*
	@Override
	public List<Entity> getPassengers()
    {
		List<Entity> rideyBois = new ArrayList();
		
		for (ConstructSlot slot : this.slots){
			if(slot.getPart()!=null){
				rideyBois.add(slot.getPart());
			}
		}
		
		return rideyBois;
        //return (List<Entity>)(this.riddenByEntities.isEmpty() ? Collections.emptyList() : Lists.newArrayList(this.riddenByEntities));
    }*/
	
	@Override
    public void updatePassenger(Entity passenger)
    {
		if (this.isPassenger(passenger))
        {
    		//System.out.println("yeah im getting called now");
			if(passenger instanceof EntityConstruct){

	    		int slotNum = getSlotOfPassenger((EntityConstruct)passenger);
	    		

	    		if(slotNum == -1){
	        		System.out.println("WEVE GOT A PROBLEM IN updatePassenger IN THE CONSTRUCTCARRIER BOSS");
	    		}
	    		//System.out.println(passenger.toString());
	    		passenger.setPosition(
	    				this.posX + this.getMountedXOffsetGivenSlot(slotNum), 
	    				this.posY + this.getMountedYOffsetGivenSlot(slotNum), 
	    				this.posZ + this.getMountedZOffsetGivenSlot(slotNum));
	    		//System.out.println(passenger.toString());
	    	}
        }
    }
	
	/**
	 * @param passenger the passenger
	 * @return numerical position of the passenger otherwise, -1 if it is not a passenger of the construct part, -2 if things are real fucked up
	 */
	public int getSlotOfPassenger(EntityConstruct passenger) {
		//TODO: add a check if its actually a passenger, just incase (will need to alter isPassenger and possibly getPassengers)
		if (this.isPassenger(passenger)){
			for(int i = 0; i<slots.length;i++){
				if(slots[i].getPart().equals(passenger)){
					return i;
				}	
			}
			//return -2;
		}
		return -1;
	}
	
	/*
	@Override
	public boolean isPassenger(Entity entityIn)
    {
		for (ConstructSlot slot : this.slots){
			if(slot.getPart().equals(entityIn)){
				return true;
			}
		}
        return super.isPassenger(entityIn);
    }*/
	
	@Override
	protected boolean canFitPassenger(Entity passenger)
    {
		return this.getPassengers().size() < slots.length;
    }
	
	
	public void detatchAllParts()
    {
		/*
		System.out.println("well fuggers my dude");
		for (Entity entity : this.getPassengers()){
			entity.dismountRidingEntity();
		}*/
    }
	
	/*
	 * 
	 * MOTHER FUCKER EVERY TIME AN ENTITY RIDES ANOTHRER IT CALLS REMOVEPASSENGERS (IE REMOVING ALL PASSENGERS) 
	 * AND ADDS THEM ALL AGAIN
	 * 
	 * FUGGERS
	 * 
	 * so now i need a true detatch and then this fake ones which keep the slots knowledge of all of that shit happening
	 * AND to make it all work with the already in place stuff
	 * 
	 * 
	 * maaan it seems more and more that making this detatched from the mounting code would have been much better
	 * 
	 * yes, considering everything and the fact that the main reason i tumored the riding code is to get the 
	 * pos syncing which is easily done without all the riding cancer i will separate this system from the riding one
	 * 
	 */
	
	public static class ConstructSlot{
		
		private EntityConstruct part = null;
		private double[] coords = new double[3];
		private EnumSlotType slotType;
		//maybe have the slots interactable AABB in this area???, centered on the coord mabs not, might not give needed scope
		
		public ConstructSlot(double x, double y, double z, EnumSlotType slotType){
			setCoords(x,y,z);
			this.slotType=slotType;
		}
		
		public void setCoords(double x, double y, double z){
			coords[0]=x;
			coords[1]=y;
			coords[2]=z;
		}
		
		public double[] getCoords(){
			return coords;
		}
		
		/**
		 * @return returns null if no part is present
		 */
		public EntityConstruct getPart(){
			return part;
		}
		
		/**
		 * Attempts to attach the part to the given slot, returns false if the part isnt applicable to the slot type (and doesnt put it in the slot)
		 * @return false if the part does not fit in the slot, true if it does and has been put into the slot
		 */
		public boolean attatchPart(EntityConstruct givenPart){
			if(slotType.getValidForSlotType(givenPart.getLimbType())){
				part = givenPart;
				return true;
			}
			return false;
		}
		
		/**
		 * Attempts to remove the part in the current slot, and returns what was removed
		 * @return an EntityConstruct of the part removed, null if no part was in the slot
		 */
		public EntityConstruct removePart(){
			EntityConstruct temp = part;
			part = null;
			System.out.println("part removed");
			return temp;
		}
		
		/**
		 * @return true if the slot is empty (null), false otherwise
		 * @return
		 */
		public boolean isSlotEmpty(){
			return part == null;
		}
	}
}
