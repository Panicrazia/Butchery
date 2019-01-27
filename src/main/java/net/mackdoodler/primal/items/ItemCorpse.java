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

    /*
    // This is a fun method which allows us to run some code when our item is
    // shown in a creative tab. I am going to use it to add all the brain 
    // types.
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List itemList) 
    {
        // This creates a loop with a counter. It will go through once for
        // every listing in brainTypes,  and gives us a number associated 
        // with each listing.
        for (int pos = 0; pos < CorpseHelper.brainTypes.length; pos++) 
        {
            // This creates a new ItemStack instance. The item parameter 
            // supplied is this item.
            ItemStack brainStack = new ItemStack(item);
            // By default, a new ItemStack does not have any nbt compound data. 
            // We need to give it some.
            brainStack.setTagCompound(new NBTTagCompound());
            // Now we set the type of the item, brainType is the key, and 
            // brainTypes[pos] is grabbing a
            // entry from the brainTypes array.
            brainStack.getTagCompound().setString("brainType", 
            		CorpseHelper.brainTypes[pos]);
            // And this adds it to the itemList, which is a list of all items
            // in the creative tab.
            itemList.add(brainStack);
        }
    }*/

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
    
    /*
    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
    	if ( stack.hasTagCompound() && stack.getTagCompound().hasKey("corpseType"))
		{
    		String type = stack.getTagCompound().getString("corpseType");
    		type = (Character.toUpperCase(type.charAt(type.indexOf(':')+1))+type.substring(type.indexOf(':')+2)).replace('_', ' ');
    		//type.index
			tooltip.add(I18n.format("tooltip.corpsedesc", type));
		}
		else
		{
			tooltip.add("Random Corpse");
		}
    }*/
    
	@Override
	/**
     * Called when a Block is right-clicked with this Item
     * places the corpse in the world
     */
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (worldIn.isRemote)
        {
            //return EnumActionResult.SUCCESS;
        }
        
        if (!worldIn.getBlockState(pos).isSideSolid(worldIn, pos, EnumFacing.UP))
        {
            return EnumActionResult.FAIL;
        }
        else
        {
            IBlockState iblockstate = worldIn.getBlockState(pos);
            Block block = iblockstate.getBlock();

            if (!block.isReplaceable(worldIn, pos))
            {
                pos = pos.up();
                if(!worldIn.getBlockState(pos).getBlock().isReplaceable(worldIn, pos)){
                	return EnumActionResult.FAIL;
                }
            }

            ItemStack itemstack = player.getHeldItem(hand);
            
            //System.out.println("The corpse type is "+itemstack.getTagCompound().getString("corpseType"));
            
            if (player.canPlayerEdit(pos, facing, itemstack))
            {
            	SoundType soundtype = iblockstate.getBlock().getSoundType(iblockstate, worldIn, pos, player);
            	worldIn.playSound((EntityPlayer)null, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
            	
            	Random randy = new Random();
            	worldIn.setBlockState(pos, ModBlocks.blockCorpse.getStateFromMeta(randy.nextInt(8)), 10);
            	if (worldIn.getTileEntity(pos) instanceof TileEntityCorpse)
                {
            		TileEntityCorpse tileEntity = (TileEntityCorpse) worldIn.getTileEntity(pos);
            		if (worldIn.isRemote)
                    {
                		tileEntity.setEntiyIdentity(itemstack.getTagCompound().getString("corpseType"));
                    }
                	tileEntity.fillInventory(((ButcherEntry)(MobButcheryDropsList.allButcherEntries.get(itemstack.getTagCompound().getString("corpseType")))).getItemstackSet());
                }
            	
            	/* Still dont know what this shit does
                worldIn.notifyNeighborsRespectDebug(pos, block, false);
                worldIn.notifyNeighborsRespectDebug(blockpos, iblockstate1.getBlock(), false);

                    if (player instanceof EntityPlayerMP)
                    {
                        CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP)player, pos, itemstack);
                    }
                */
            	
            	itemstack.shrink(1);
            	return EnumActionResult.SUCCESS;
            }
            else
            {
                return EnumActionResult.FAIL;
            }
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
