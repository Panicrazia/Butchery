package net.mackdoodler.primal.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockNetherGrass extends BlockBase{

	public static final PropertyBool BURNING = PropertyBool.create("burning");
	
	public BlockNetherGrass() {
		super(Material.ROCK, "nether_grass");
		this.setTickRandomly(true);
        this.setHarvestLevel("pickaxe", 1); // 1 is stone required (0 wood, 1 stone, 2 iron)
        this.setHardness(Blocks.NETHERRACK.getBlockHardness(null, null, null));
        this.setResistance(Blocks.NETHERRACK.getExplosionResistance(null));
        this.setDefaultState(this.blockState.getBaseState().withProperty(BURNING, false));
    }

    @Override
    public void updateTick(World world, BlockPos blockPos, IBlockState blockState, Random random) {
        if (!world.isRemote) {//if on server
            if (world.provider.doesWaterVaporize()) {//if in the nether
                int rate = blockState.getValue(BURNING) ? 12 : 4;
                IBlockState blockUp = world.getBlockState(blockPos.up());
                if (blockUp.isOpaqueCube()) {
                    world.setBlockState(blockPos, Blocks.NETHERRACK.getDefaultState());
                    return;
                }

                for (int i = 0; i < rate; ++i) {
                    BlockPos blockpos = blockPos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                    IBlockState blockOnTop = world.getBlockState(blockpos.up());
                    if (blockOnTop.isOpaqueCube()) {
                        continue; //ends the for loop early
                    }

                    IBlockState iblockstate = world.getBlockState(blockpos);

                    if (iblockstate.getBlock() == Blocks.NETHERRACK) {
                        world.setBlockState(blockpos, ModBlocks.netherGrass.getDefaultState());
                        
                        /*
                        //code to light the block above it on fire (why dude like what?)
                        if (blockOnTop.getMaterial() == Material.AIR) {
                            world.setBlockState(blockpos.up(), Blocks.FIRE.getDefaultState());
                        }*/
                    }
                }
            }
        }
    }

    @Override
    public IBlockState getActualState(IBlockState blockState, IBlockAccess blockAccess, BlockPos blockPos) {
        Block block = blockAccess.getBlockState(blockPos.up()).getBlock();
        return blockState.withProperty(BURNING, block == Blocks.FIRE || block == Blocks.LAVA || block == Blocks.FLOWING_LAVA);
    }

    @Override
    public Item getItemDropped(IBlockState blockState, Random random, int meta) {
        return Blocks.NETHERRACK.getItemDropped(Blocks.NETHERRACK.getDefaultState(), random, meta);
    }

    @Override
    public boolean canCreatureSpawn(IBlockState blockState, IBlockAccess blockAccess, BlockPos blockPos, EntityLiving.SpawnPlacementType spawnPlacementType) {
        return false;
    }

    @Override
    public int getMetaFromState(IBlockState blockState) {
        return 0;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, BURNING);
    }

}
