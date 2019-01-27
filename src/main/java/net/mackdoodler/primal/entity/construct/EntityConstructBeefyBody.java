package net.mackdoodler.primal.entity.construct;

import java.util.UUID;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.world.World;

public class EntityConstructBeefyBody extends EntityConstructCarrier {

	
	public EntityConstructBeefyBody(World worldIn) {
		super(worldIn, EnumLimbType.BODY, 4);
		//head, arm1, arm2, lowerbody
        this.setSize(1.5F, 2F);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void initSlots() {
		//has to be overridden to set the slots in each given construct piece, possibly could be done in the constructor with passing in an array of ConstructSlots?
		System.out.println("Initializing slots now for one beefy boi");
		//head
		slots[0]= new ConstructSlot(0D, 10.5D, 20D, EnumSlotType.HEAD);
		//arm1
		slots[1]= new ConstructSlot(0D, 0D, 0D, EnumSlotType.ARM);
		//arm2
		slots[2]= new ConstructSlot(0D, 0D, 0D, EnumSlotType.ARM);
		//lowerbod
		slots[3]= new ConstructSlot(0D, 0D, 0D, EnumSlotType.LOWERBODY);
		
	}
	
	@Override
	public double getMountedYOffset()
    {
        return ((double)(this.height))*1.0D;
    }
}
