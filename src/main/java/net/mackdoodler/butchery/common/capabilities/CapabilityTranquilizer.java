package net.mackdoodler.butchery.common.capabilities;

import net.mackdoodler.butchery.ButcheryMod;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class CapabilityTranquilizer implements ICapabilitySerializable<NBTBase>{
	
	public static final ResourceLocation TRANQUILIZER_CAPABILITY_ID = new ResourceLocation(ButcheryMod.modId, "Tranquilizer"); 
	
	@CapabilityInject(ITranquilizer.class) 
	public static final Capability<ITranquilizer> TRANQUILIZER_CAPABILITY = null; 

	private ITranquilizer instance = TRANQUILIZER_CAPABILITY.getDefaultInstance(); 

	@Override 
	public boolean hasCapability(Capability<?> capability, EnumFacing facing){
		return capability == TRANQUILIZER_CAPABILITY; 
	}

	@Override 
	public <T> T getCapability(Capability<T> capability, EnumFacing facing){
		return capability == TRANQUILIZER_CAPABILITY ? TRANQUILIZER_CAPABILITY.<T> cast(this.instance) : null;
	}

	@Override 
	public NBTBase serializeNBT(){
		return TRANQUILIZER_CAPABILITY.getStorage().writeNBT(TRANQUILIZER_CAPABILITY, this.instance, null);
	}

	@Override 
	public void deserializeNBT(NBTBase nbt){
		TRANQUILIZER_CAPABILITY.getStorage().readNBT(TRANQUILIZER_CAPABILITY, this.instance, null, nbt);
	}
}
