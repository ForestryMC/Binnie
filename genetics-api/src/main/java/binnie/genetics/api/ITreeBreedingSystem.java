package binnie.genetics.api;

import java.util.Collection;
import java.util.Set;

import com.mojang.authlib.GameProfile;
import forestry.api.genetics.IAlleleSpecies;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface ITreeBreedingSystem {
	Collection<IAlleleSpecies> getTreesThatBearFruit(ItemStack fruit, boolean nei, World world, GameProfile player);

	Collection<IAlleleSpecies> getTreesThatCanBearFruit(ItemStack fruit, boolean nei, World world, GameProfile player);

	Collection<IAlleleSpecies> getTreesThatHaveWood(ItemStack wood, boolean nei, World world, GameProfile player);

	Set<ItemStack> getAllFruits();

	Set<ItemStack> getAllWoods();
}
