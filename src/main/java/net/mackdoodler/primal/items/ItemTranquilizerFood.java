package net.mackdoodler.primal.items;

import org.objectweb.asm.ClassWriter;

import net.mackdoodler.primal.PrimalMod;
import net.mackdoodler.primal.handlers.CorpseHandler;
import net.mackdoodler.primal.potions.PrimalPotions;
import net.mackdoodler.primal.proxy.ClientProxy;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityVillager;
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
		super(0, 0, false);//switch this to true and have it tranq friendo wolves too
		this.name = name;
		setTranslationKey(name);
		setRegistryName(name);
		setCreativeTab(PrimalMod.creativeTab);
		setHasSubtypes(true);
		
		this.setAlwaysEdible();
		this.setPotionEffect(new PotionEffect(PrimalPotions.DROWSY_POTION, 300, 5, false, true), 1f);
	}
	
	public void registerItemModel(){
		 for(int i = 0; i < 2; i++){
			 //PrimalMod.proxy.registerItemRenderer(this, i, name);
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
			CorpseHandler.applyTranquilizer(target, 10);
			stack.shrink(1);
			return true;
		case 1:
			if(target instanceof EntityCow || target instanceof EntityZombie || target instanceof EntityChicken || target instanceof EntitySlime){
				if(target.world.isRemote){
	                
					ClientProxy.spawnSleepParticlesForEntity(target);
				}
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
		//return damage > max ? damage : max;
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
