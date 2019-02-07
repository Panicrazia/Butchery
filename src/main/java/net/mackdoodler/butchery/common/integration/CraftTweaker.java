package net.mackdoodler.butchery.common.integration;

import java.util.List;

import javax.annotation.Nonnull;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import net.mackdoodler.butchery.api.ButcheryEntries;
import net.mackdoodler.butchery.api.TranquilizerHandler;
import net.minecraft.entity.EntityList;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.butchery")
public class CraftTweaker {
	
	/**
	 * Add a butchery drop for a given entity
	 * @param entityName the string representation the entity (can be gotten via EntityList.getKey(entity.getClass()) if you do not know what it is)
	 * @param tool the item the player needs to be holding to get the item when chopping
	 * @param output the stack to drop
	 * @param min the minimum amount this item can drop, if you do not want it to always drop make this negative
	 * @param max the maximum amount this item can drop
	 */
	@ZenMethod
	public static void addEntry(String entityName, IItemStack tool, IItemStack output, int min, int max)
	{
		CraftTweakerAPI.apply(new Add(entityName, tool, output,  min, max));
	}
	
	/**
	 * Removes a butchery drop for a given entity
	 * @param entityName the string representation the entity (can be gotten via EntityList.getKey(entity.getClass()) if you do not know what it is)
	 * @param output the stack you wish to remove all entries for
	 */
	@ZenMethod
	public static void removeEntry(String entityName, IItemStack output)
	{
		CraftTweakerAPI.apply(new Remove(entityName, output));
	}
	
	/**
	 * Sets a given entitys tranquilizer kill threshold
	 * @param entityName the string representation the entity (can be gotten via EntityList.getKey(entity.getClass()) if you do not know what it is)
	 * @param number the number you wish to set to the tranquilizer kill threshold to
	 */
	@ZenMethod
	public static void setTranquilizer(String entityName, int number){
		TranquilizerHandler.setTranqResistance(entityName, number);
	}
	
	private static class Add implements IAction
	{
		String entityName;
		ItemStack output;
		ItemStack tool;
		int min;
		int max;

		public Add(String entityName, @Nonnull IItemStack tool, IItemStack output,  int min, int max) {
			this.entityName = entityName;
			this.tool = tool!=null?((ItemStack)tool.getInternal()):ItemStack.EMPTY;
			this.output = output!=null?((ItemStack)output.getInternal()):ItemStack.EMPTY;
			this.min = min;
			this.max = max;
		}

		@Override
		public void apply()
		{
			ButcheryEntries.addEntry(entityName, tool, output, min, max);
		}

		@Override
		public String describe()
		{
			return "Adding "+output.getDisplayName()+" to the butchery lootpool for "+entityName;
		}
	}
	
	private static class Remove implements IAction
	{
		String entityName;
		ItemStack output;
		
		public Remove(String entityName, IItemStack output){
			this.entityName = entityName;
			this.output = output!=null?((ItemStack)output.getInternal()):ItemStack.EMPTY;
		}

		@Override
		public void apply()
		{
			ButcheryEntries.removeEntry(entityName, output);
		}

		@Override
		public String describe()
		{
			return "Removing "+output.getDisplayName()+" from the butchery lootpool for "+entityName;
		}
	}
	
}
