package net.mackdoodler.primal.capabilities;

import net.mackdoodler.primal.PrimalMod;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class CapabilityMonsterAI implements ICapabilitySerializable<NBTBase>{
	
	public static final ResourceLocation MONSTER_AI_CAPABILITY_ID = new ResourceLocation(PrimalMod.modId, "MonsterAI"); 
	
	@CapabilityInject(IMonsterAI.class) 
	public static final Capability<IMonsterAI> MONSTER_AI_CAPABILITY = null; 

	private IMonsterAI instance = MONSTER_AI_CAPABILITY.getDefaultInstance(); 

	@Override 
	public boolean hasCapability(Capability<?> capability, EnumFacing facing){
		return capability == MONSTER_AI_CAPABILITY; 
	}

	@Override 
	public <T> T getCapability(Capability<T> capability, EnumFacing facing){
		return capability == MONSTER_AI_CAPABILITY ? MONSTER_AI_CAPABILITY.<T> cast(this.instance) : null;
	}

	@Override 
	public NBTBase serializeNBT(){
		return MONSTER_AI_CAPABILITY.getStorage().writeNBT(MONSTER_AI_CAPABILITY, this.instance, null);
	}

	@Override 
	public void deserializeNBT(NBTBase nbt){
		MONSTER_AI_CAPABILITY.getStorage().readNBT(MONSTER_AI_CAPABILITY, this.instance, null, nbt);
	}
}
