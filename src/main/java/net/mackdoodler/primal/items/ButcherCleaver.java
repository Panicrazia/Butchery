package net.mackdoodler.primal.items;

import net.mackdoodler.primal.PrimalMod;
import net.mackdoodler.primal.blocks.BlockCorpse;
import net.mackdoodler.primal.blocks.ModBlocks;
import net.mackdoodler.primal.capabilities.CapabilityMonsterAI;
import net.mackdoodler.primal.capabilities.IMonsterAI;
import net.mackdoodler.primal.handlers.CorpseHandler;
import net.mackdoodler.primal.potions.PrimalPotions;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ButcherCleaver extends ItemBase{
	
	public ButcherCleaver(String name){
		super(name);
		
		setCreativeTab(PrimalMod.creativeTab);
		
		this.maxStackSize = 1;
	}
	
	/**
     * Called when a Block is right-clicked with this Item
     */
	@Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
    	if(worldIn.getBlockState(pos).getBlock() == ModBlocks.blockCorpse){
    		((BlockCorpse) worldIn.getBlockState(pos).getBlock()).carveItUp(worldIn,pos);
    	}
    	
        return EnumActionResult.PASS;
    }
	
	/**
     * Returns true if the item can be used on the given entity, e.g. shears on sheep.
     */
	@Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand){
		if(!playerIn.getEntityWorld().isRemote && target instanceof EntitySlime){
			if(target.hasCapability(CapabilityMonsterAI.MONSTER_AI_CAPABILITY, null)){
				
				IMonsterAI mai = target.getCapability(CapabilityMonsterAI.MONSTER_AI_CAPABILITY, null);
				if(!mai.getModified()){
					
					if(CorpseHandler.neuterEntity((EntityLiving)target)){
						
						mai.setModified(true);
						
						ItemStack core = new ItemStack(ButcheryItems.bodyPartBrain, 1);
						core.setTagCompound(new NBTTagCompound());
						core.getTagCompound().setString("partType", "Slimey Core");
						EntityItem entityitem = new EntityItem(target.getEntityWorld(), target.posX, target.posY, target.posZ, core);
						
						target.getEntityWorld().spawnEntity(entityitem);
						return true;
						
					}
				}
			}
		}

		System.out.println("Does this thing have a capability attatched? "+target.hasCapability(CapabilityMonsterAI.MONSTER_AI_CAPABILITY, null)); 
		return false;
    }
	
	@Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
		if(target instanceof EntityWither){
			target.attackEntityFrom(CorpseHandler.TRANQUILIZER, 1000f);
		}
		CorpseHandler.applyTranquilizer(target, 10);
        return true;
    }

	/*
	 * these next 4 methods are for pulling back the cleaver, currently unused for any actual purpose
	 */
	@Override
	public EnumAction getItemUseAction(ItemStack stack){
		return EnumAction.BOW;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack)
    {
        return 200;
    }
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
		ItemStack stack = player.getHeldItem(hand);
		player.setActiveHand(hand);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS,stack);
    }
	
	@Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft){
		if (entityLiving instanceof EntityPlayer){
			EntityPlayer player = (EntityPlayer) entityLiving;
			if(timeLeft<177){
				/*
				 * could do some vector things with seeing where the player is looking and then calling carve it up if its eligible
				 */
				System.out.println("CHOP");
			}
		}
    }
}
