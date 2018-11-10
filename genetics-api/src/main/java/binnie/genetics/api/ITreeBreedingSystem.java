package binnie.genetics.api;

import java.util.Collection;
import java.util.Set;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.mojang.authlib.GameProfile;

import forestry.api.genetics.IAlleleSpecies;

import binnie.core.api.genetics.IBreedingSystem;

public interface ITreeBreedingSystem extends IBreedingSystem {
	Collection<IAlleleSpecies> getTreesThatBearFruit(ItemStack fruit, boolean master, World world, GameProfile player);

	Collection<IAlleleSpecies> getTreesThatCanBearFruit(ItemStack fruit, boolean master, World world, GameProfile player);

	Collection<IAlleleSpecies> getTreesThatHaveWood(ItemStack wood, boolean master, World world, GameProfile player);

	Set<ItemStack> getAllFruits();

	Set<ItemStack> getAllWoods();
}
