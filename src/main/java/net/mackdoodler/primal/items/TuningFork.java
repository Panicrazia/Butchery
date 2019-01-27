package net.mackdoodler.primal.items;

import java.util.List;

import javax.annotation.Nullable;

import net.mackdoodler.primal.PrimalMod;
import net.mackdoodler.primal.blocks.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.mackdoodler.primal.blocks.RuneSocket;

public class TuningFork extends ItemBase{
	
	private boolean tuning=false;
	
	public TuningFork(String name){
		super(name);
		this.maxStackSize = 1;
	}
	
	/**
	 * change the itemnbt to the correct way by making everything go through the itemstack
	 * (http://www.minecraftforge.net/forum/topic/25350-item-nbt/)
	 * 
	 * if not tuned and right click it tells you where the runes are linked to
	 * ^unable, make it instead some item lets you see where they are linked with some particles or do it how botania does with hovering or somethin
	 * 
	 */
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		if(!worldIn.isRemote)
		{
			IBlockState blockstateReciever = worldIn.getBlockState(pos);
			if(player.isSneaking()){
				if(tuning){
					ItemStack stack = player.getHeldItem(hand);
					NBTTagCompound tags = stack.getTagCompound();
					if(tags != null){
						BlockPos linkpos = new BlockPos(tags.getInteger("x"),tags.getInteger("y"),tags.getInteger("z"));
						IBlockState blockstateSender = worldIn.getBlockState(linkpos);
						
						//check if both positions are runesockets (later add a check if they are mechanis, if its nessecary
						if(blockstateReciever.getBlock()==ModBlocks.runeSocket && blockstateSender.getBlock()==ModBlocks.runeSocket){
							
							//check if runes are too far away from each other
							if(Math.abs(pos.getX()-linkpos.getX())<32 && Math.abs(pos.getZ()-linkpos.getZ())<32 && Math.abs(pos.getY()-linkpos.getY())<64 && tags.getInteger("dim")==player.dimension){
								if(!pos.equals(linkpos)){
									//System.out.println("we are attempting to link");
									if(((RuneSocket)blockstateReciever.getBlock()).link(worldIn, pos, linkpos, true)){
										player.sendStatusMessage(new TextComponentTranslation(TextFormatting.AQUA + "Runes Successfully linked"), true);
									}
									else{
										player.sendStatusMessage(new TextComponentTranslation(TextFormatting.RED + "Link failed for some reason"), true);
									}
								}
								else{
									player.sendStatusMessage(new TextComponentTranslation(TextFormatting.RED + "Cannot link a rune to itself"), true);
								}
							}
							else{
								player.sendStatusMessage(new TextComponentTranslation(TextFormatting.RED + "Runes are too far away"), true);
							}
						}
					}
					tuning=false;
					return EnumActionResult.SUCCESS;
				}
				else{
					if(player.isSneaking())
					{
						if(worldIn.getBlockState(pos).getBlock()==ModBlocks.runeSocket){
							ItemStack stack = player.getHeldItem(hand);
							NBTTagCompound tags = stack.getTagCompound();
							if(tags == null)
							{
								stack.setTagCompound(new NBTTagCompound());
								tags = stack.getTagCompound();
							}
							tags.setInteger("x", pos.getX());
							tags.setInteger("y", pos.getY());
							tags.setInteger("z", pos.getZ());
							tags.setInteger("dim", player.dimension);
							//tags.setFloat("direction", player.rotationYaw);
		
							
							if(!worldIn.isRemote)
							{
								player.sendStatusMessage(new TextComponentTranslation(TextFormatting.AQUA + "This fork is currently linked to:" + " " + 
										tags.getInteger("x") + ", " +
										tags.getInteger("y") + ", " +
										tags.getInteger("z")), true);
							}
							tuning=true;
						}
						//player.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1, 1);
		
						return EnumActionResult.SUCCESS;
					}
				}
			}
		}
		return EnumActionResult.FAIL;
    }
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, @Nullable World playerIn, List<String> tooltip, ITooltipFlag advanced)
	{
		NBTTagCompound tags = stack.getTagCompound();
		
		if(tags != null)
		{
			if(tuning){
				tooltip.add(TextFormatting.GREEN + "Linked to: " + tags.getInteger("x") + ", " + tags.getInteger("y") + ", " + tags.getInteger("z"));
			}
			else{
				tooltip.add(TextFormatting.AQUA + "Unlinked");
			}
			
			//tooltip.add(TextFormatting.BLUE + "" + TextFormatting.ITALIC + "Sneak right click a block to link.");

		}else
		{
			tooltip.add(TextFormatting.AQUA + "Unlinked");
		}
	}
}
