package net.mackdoodler.primal.init.integration;

import java.util.List;

import javax.annotation.Nonnull;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import net.mackdoodler.primal.handlers.MobButcheryDropsList;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.butchery")
public class CraftTweaker {
	
	@ZenMethod
	public static void addRecipe(String entityName, IItemStack output, IItemStack tool, int min, int max)
	{
		CraftTweakerAPI.apply(new Add(entityName, output, tool, min, max));
	}

	@ZenMethod
	public static void removeEntry(String entityName, IItemStack output)
	{
		CraftTweakerAPI.apply(new Remove(entityName, output));
	}
	
	private static class Add implements IAction
	{
		String entityName;
		ItemStack output;
		ItemStack tool;
		int min;
		int max;

		public Add(String entityName, IItemStack output, @Nonnull IItemStack tool, int min, int max) {
			this.entityName = entityName;
			this.output = output!=null?((ItemStack)output.getInternal()):ItemStack.EMPTY;
			this.tool = tool!=null?((ItemStack)tool.getInternal()):ItemStack.EMPTY;
			this.min = min;
			this.max = max;
		}

		@Override
		public void apply()
		{
			MobButcheryDropsList.addEntry(entityName, output, tool, min, max);
		}

		@Override
		public String describe()
		{
			//TODO: change it from some animal to the entity name
			return "Adding "+output.getDisplayName()+" to the lootpool for some animal";
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
			MobButcheryDropsList.removeEntry(entityName, output);
		}

		@Override
		public String describe()
		{
			//TODO: make not stupid
			return "Removing some item from the lootpool for some animal";
		}
	}
	
}
