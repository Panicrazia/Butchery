package net.mackdoodler.primal.items;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import net.mackdoodler.primal.PrimalMod;
import net.mackdoodler.primal.blocks.BlockButcherMat;
import net.mackdoodler.primal.blocks.ModBlocks;
import net.mackdoodler.primal.handlers.CorpseHandler;
import net.mackdoodler.primal.handlers.MobButcheryDropsList;
import net.mackdoodler.primal.handlers.MobButcheryDropsList.ButcherEntry;
import net.mackdoodler.primal.tileentity.TileEntityCorpse;
import net.mackdoodler.primal.tileentity.TileEntityRuneSocket;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemCorpse extends ItemBase{

	public ItemCorpse(String name) {
		super(name);
	}
	
	@Override 
	public void registerItemModel(){
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(PrimalMod.modId + ":" + "block_corpse", "inventory"));
	}
	
	//probs shouldmake a method to get the display name so i dont have to do the calculations over and over in getItemStackDisplayName()
	
    // This method allows us to have different language translation keys for 
    // each item we add.
    @Override
    public String getTranslationKey(ItemStack stack) 
    {
		if (stack.hasTagCompound()) 
		{
		    NBTTagCompound itemData = stack.getTagCompound();
		    if (itemData.hasKey("corpseType"))
		    {
		    	return "item.corpse"+stack.getTagCompound().getString("corpseType");
		    }
		}
		return "item.corpse";
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public String getItemStackDisplayName(ItemStack stack)
    {
    	if ( stack.hasTagCompound() && stack.getTagCompound().hasKey("corpseType"))
		{
    		String type = stack.getTagCompound().getString("corpseType");
    		type = (Character.toUpperCase(type.charAt(type.indexOf(':')+1))+type.substring(type.indexOf(':')+2)).replace('_', ' ');

            return I18n.format("tooltip.corpsedesc", type);
		}
    	
    	return super.getItemStackDisplayName(stack);
    }
    
	@Override
	/**
     * Called when a Block is right-clicked with this Item
     * places the corpse in the world
     */
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        
        if (!iblockstate.isSideSolid(worldIn, pos, EnumFacing.UP))
        {
            return EnumActionResult.FAIL;
        }
        
        Block block = iblockstate.getBlock();

        if (!block.isReplaceable(worldIn, pos))
        {
            pos = pos.up();
            if(!block.isReplaceable(worldIn, pos)){
            	return EnumActionResult.FAIL;
            }
        }

        ItemStack itemstack = player.getHeldItem(hand);
        
        if (player.canPlayerEdit(pos, facing, itemstack))
        {
        	worldIn.playSound((EntityPlayer)null, pos, SoundType.SLIME.getPlaceSound(), SoundCategory.BLOCKS, 1f, 0.8f);
        	
        	Random randy = new Random();
        	worldIn.setBlockState(pos, ModBlocks.blockCorpse.getStateFromMeta(randy.nextInt(8)), 10);
        	
        	if (worldIn.getTileEntity(pos) instanceof TileEntityCorpse)
            {
        		TileEntityCorpse tileEntity = (TileEntityCorpse) worldIn.getTileEntity(pos);
        		if (worldIn.isRemote)
                {
            		tileEntity.setEntiyIdentity(itemstack.getTagCompound().getString("corpseType"));
            		return EnumActionResult.SUCCESS;
                }
        		
        		if(MobButcheryDropsList.butcherEntries.containsKey(itemstack.getTagCompound().getString("corpseType"))){
        			tileEntity.fillInventory(((ButcherEntry)(MobButcheryDropsList.butcherEntries.get(itemstack.getTagCompound().getString("corpseType")))).getItemStackSet());
                }
        	}
        	
        	itemstack.shrink(1);
        	return EnumActionResult.SUCCESS;
        }
        else
        {
            return EnumActionResult.FAIL;
        }
    }
    
    /* 
	public ItemStack getItemStack() {
		if (OpenBlocks.Blocks.trophy == null){
			return ItemStack.EMPTY;
		}
		return ItemTrophyBlock.putMetadata(new ItemStack(OpenBlocks.Blocks.trophy), this);
	}
	*/
}
