package net.mackdoodler.primal.blocks;

import net.mackdoodler.primal.PrimalMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockLog.EnumAxis;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DarkWood extends BlockRotatedPillar{
	
	protected String name = "classical_wood";
	
	public DarkWood() {
		super(Material.WOOD);
		setTranslationKey(name);
		setRegistryName(name);
		setCreativeTab(PrimalMod.creativeTab);

        this.setHardness(2.0F);
        this.setResistance(1.5F);
        
        //this.setSoundType(SoundType.WOOD);
		//^seems to add a decent chunk of load time/freezing or something i dunno, when it was active times were long
        
		//setDefaultState(blockState.getBaseState().withProperty(AXIS, EnumAxis.NONE));
	}
	
	public void registerItemModel(Item itemBlock){
		PrimalMod.proxy.registerItemRenderer(itemBlock, 0, name);
	}
	
	public Item createItemBlock(){
		return new ItemBlock(this).setRegistryName(getRegistryName());
	}
	/*
	@Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        world.setBlockState(pos, state.withProperty(LOG_AXIS, getFacingFromEntity(pos, placer)), 2);
    }
	*/

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, AXIS);
    }
	
}
