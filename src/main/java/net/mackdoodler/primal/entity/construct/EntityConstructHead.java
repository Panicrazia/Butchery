package net.mackdoodler.primal.entity.construct;

import java.util.UUID;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.world.World;

public class EntityConstructHead  extends EntityConstruct {

	public EntityConstructHead(World worldIn) {
		super(worldIn, EnumLimbType.HEAD);
        this.setSize(.5F, .5F);
		// TODO Auto-generated constructor stub
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

}
