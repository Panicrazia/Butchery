package net.mackdoodler.primal.blocks;

import net.mackdoodler.primal.PrimalMod;
import net.mackdoodler.primal.items.ItemRune;
import net.mackdoodler.primal.items.PrimalItems;
import net.mackdoodler.primal.items.PrimalCodex;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionAbsorption;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class RuneBlock extends BlockBase{

	//To-do: replace this dumb integer shit with actual words through a propertyEnum that make sense
	public static final PropertyInteger RUNE = PropertyInteger.create("meta", 0, 10);
	
	public RuneBlock(String name) {
		super(Material.ROCK, name);
		setDefaultState(this.blockState.getBaseState().withProperty(RUNE, 0));
		//setHasSubtypes(true);
	}

	@Override
	public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn){
		if(!worldIn.isRemote){
			if(entityIn instanceof EntityPlayer){
				EntityPlayer subject = (EntityPlayer)entityIn;
				
				switch(worldIn.getBlockState(pos).getValue(this.RUNE)){
				case 0:
					break;
				case 1:
					subject.addPotionEffect(new PotionEffect(MobEffects.LEVITATION,50,0, false, false));
					break;
				case 2:
					entityIn.setFire(10);
					break;
				case 3:
					subject.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE,500,4, false, false));
					break;
				case 4:
					subject.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION,1200,0, false, false));
					subject.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING,1200,0, false, false));
					break;
				case 5:
					subject.addPotionEffect(new PotionEffect(MobEffects.HASTE,50,4, false, false));
					break;
				case 6:
					if(!(subject.getActivePotionEffects().isEmpty() || (subject.getActivePotionEffects().size()==1 && subject.isPotionActive(MobEffects.ABSORPTION)))){
						//System.out.println(subject.getActivePotionEffects().toString());
						subject.clearActivePotions();
					}
					if(subject.getActivePotionEffects().isEmpty()){
						subject.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION,100,-6, true, false));
					}
					break;
				case 7:
					subject.addPotionEffect(new PotionEffect(MobEffects.REGENERATION,50,0, false, false));
					subject.addPotionEffect(new PotionEffect(MobEffects.SATURATION,50,0, false, false));
					break;
				case 8:
					subject.addPotionEffect(new PotionEffect(MobEffects.SPEED,50,10, false, false));
					break;
				case 9:
					subject.addPotionEffect(new PotionEffect(MobEffects.GLOWING,50,0, false, false));
					break;
				case 10:
					subject.addPotionEffect(new PotionEffect(MobEffects.WITHER,200,2, false, false));
					subject.addPotionEffect(new PotionEffect(MobEffects.POISON,200,2, false, false));
					subject.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS,200,0, false, false));
					break;
				}
				/*
			if(entityIn instanceof EntityPlayer){
				EntityPlayer subject = (EntityPlayer)entityIn;
				if(worldIn.getBlockState(pos).getValue(this.RUNE)==1){
					
				}
				else{
					subject.addPotionEffect(new PotionEffect(MobEffects.REGENERATION,20,100));
				}
			}
			
				subject.addPotionEffect(new PotionEffect(MobEffects.POISON,500,20));
				subject.addPotionEffect(new PotionEffect(MobEffects.GLOWING,50,0, false, false));
				//
			}*/
			}
		}
    }
	
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
		
		int blockMeta = this.getMetaFromState(worldIn.getBlockState(pos));
		
		if(playerIn.getHeldItem(hand).getItem() instanceof ItemRune){
			
			int itemMeta = playerIn.getHeldItem(hand).getMetadata();
			
			if(blockMeta!=0){
				worldIn.setBlockState(pos, this.getStateFromMeta(0));
				if(!worldIn.isRemote){
					worldIn.spawnEntity(new EntityItem(worldIn, pos.getX()+.5, pos.getY()+1, pos.getZ()+.5,new ItemStack(PrimalItems.itemRune, 1, blockMeta)));
				}
				
			}
			if(itemMeta!=blockMeta){
				worldIn.setBlockState(pos, this.getStateFromMeta(itemMeta));
				playerIn.getHeldItem(hand).shrink(1);
				
			}//make it drop the correct rune along with the actual block when broken
			
			
			/*
			if(worldIn.isRemote){
				playerIn.sendMessage(new TextComponentString("you are sneaking with an itemrune"));
			}*/
		}
        return false;
    }
	/*
	@Override
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
		for(int i = 0; i < 11; i++){
			list.add(new ItemStack(this, 1, i));
		}
	}
	*/
	@Override
	public IBlockState getStateFromMeta(int meta){
		return this.getDefaultState().withProperty(RUNE, meta);
	}
	
	@Override
	public int getMetaFromState(IBlockState state){
		return state.getValue(RUNE);
	}
	
	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, RUNE);
	}
	
	@Override
	public void registerItemModel(Item itemBlock){
		for(int i = 0; i < 11; i++){
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), i, new ModelResourceLocation(getRegistryName(), "meta=" + i));
		}
	}
}
