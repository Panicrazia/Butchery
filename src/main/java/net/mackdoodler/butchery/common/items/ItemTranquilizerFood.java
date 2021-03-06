package net.mackdoodler.butchery.common.items;

import net.mackdoodler.butchery.ButcheryMod;
import net.mackdoodler.butchery.api.TranquilizerHandler;
import net.mackdoodler.butchery.client.ClientProxy;
import net.mackdoodler.butchery.common.potions.ButcheryPotions;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.oredict.OreDictionary;

public class ItemTranquilizerFood extends ItemFood {

	protected String name;
	
	public ItemTranquilizerFood(String name) {
		super(0, 0, false);
		this.name = name;
		setTranslationKey(name);
		setRegistryName(name);
		setCreativeTab(ButcheryMod.creativeTab);
		setHasSubtypes(true);
		
		this.setAlwaysEdible();
		this.setPotionEffect(new PotionEffect(ButcheryPotions.DROWSY_POTION, 600 * 20, 5, false, false), 1f);
	}
	
	public void registerItemModel(){
		 for(int i = 0; i < 2; i++){
			 ModelLoader.setCustomModelResourceLocation(this, i, new ModelResourceLocation(getRegistryName(), "meta=" + i));
		 }
	}
	
	@Override
	/**
     * Returns true if the item can be used on the given entity, e.g. shears on sheep.
     */
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand)
    {
		switch(stack.getItemDamage()){
		case 0:
			//Lunar Lilly
			if(target instanceof EntityCow || 
					target instanceof EntityPig || 
					target instanceof EntitySheep || 
					target instanceof AbstractHorse || 
					target instanceof EntityRabbit || 
					target instanceof EntityBat){
				TranquilizerHandler.applyTranquilizer(target, 4);
				stack.shrink(1);
				return true;
			}
		case 1:
			//Drugged Meat
			if(target instanceof EntityZombie || 
					target instanceof EntitySpider || 
					target instanceof EntityWolf || 
					target instanceof EntityOcelot || 
					target instanceof EntityPolarBear){
				TranquilizerHandler.applyTranquilizer(target, 10, 10);
				stack.shrink(1);
				return true;
			}
			break;
		default:
			return false;
		}
		return false;
    }
	
	
	@Override
	public int getMetadata(int damage) {
		return damage;
	}
	
	@Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list){
        if(this.isInCreativeTab(tab)){
        	for(int i = 0; i < 2; i++){
        		list.add(new ItemStack(this, 1, i));
        	}
        }
    }
	
	@Override
	public String getTranslationKey(ItemStack stack) {
		return super.getTranslationKey() + "." + stack.getMetadata();
	}
}
