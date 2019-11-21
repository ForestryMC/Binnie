package binnie.core.api.genetics;

import com.mojang.authlib.GameProfile;
import forestry.api.core.ForestryEvent;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IBreedingTracker;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IClassification;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.IMutation;
import forestry.api.genetics.ISpeciesRoot;
import forestry.api.genetics.ISpeciesType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

public interface IBreedingSystem {
	ISpeciesRoot getSpeciesRoot();

	Collection<IAlleleSpecies> getAllSpecies();

	Collection<IAlleleSpecies> getDiscoveredSpecies(World world, GameProfile player);

	List<IClassification> getAllBranches();

	Collection<IClassification> getDiscoveredBranches(World world, GameProfile player);

	List<IMutation> getDiscoveredMutations(World world, GameProfile player);

	List<IMutation> getResultantMutations(final IAlleleSpecies species);

	List<IMutation> getFurtherMutations(final IAlleleSpecies species);

	boolean isMutationDiscovered(IMutation mutation, World world, GameProfile name);

	float getChance(IMutation mutation, EntityPlayer player, IAlleleSpecies firstSpecies, IAlleleSpecies secondSpecies);

	String getAlleleName(final IChromosomeType chromosome, final IAllele allele);

	String getChromosomeName(final IChromosomeType chromo);

	String getChromosomeShortName(final IChromosomeType chromo);

	@Nullable
	IIndividual getConversion(final ItemStack stack);

	ItemStack getConversionStack(final ItemStack stack);

	@Nullable
	IIndividual getIndividual(String uid);

	void calculateArrays();

	void addExtraAlleles(final IChromosomeType p0, final TreeSet<IAllele> p1);

	String getDescriptor();

	String getIdent();

	ItemStack getItemStackRepresentitive();

	int getColour();

	int getDiscoveredSpeciesCount();

	int getTotalSpeciesCount();

	float getDiscoveredSpeciesPercentage();

	int getTotalSecretCount();

	int getDiscoveredSecretCount();

	float getDiscoveredBranchPercentage();

	int getTotalBranchCount();

	int getDiscoveredBranchCount();

	String getEpitome();

	String getEpitome(float discoveredPercentage);

	Collection<IMutation> getAllMutations();

	void calculateAlleles(ISpeciesRoot speciesRoot);

	void calculateBranches(ISpeciesRoot speciesRoot);

	void calculateMutations(ISpeciesRoot speciesRoot);

	boolean isBlacklisted(IAllele allele);

	boolean isMutationDiscovered(IMutation mutation, IBreedingTracker tracker);

	boolean isSpeciesDiscovered(IAlleleSpecies species, World world, GameProfile name);

	boolean isSpeciesDiscovered(IAlleleSpecies species, IBreedingTracker tracker);

	boolean isSecret(IAlleleSpecies species);

	boolean isSecret(IClassification branch);

	Collection<IClassification> getDiscoveredBranches(IBreedingTracker tracker);

	Collection<IAlleleSpecies> getDiscoveredSpecies(IBreedingTracker tracker);

	@SideOnly(Side.CLIENT)
	TextureAtlasSprite getUndiscoveredIcon();

	@SideOnly(Side.CLIENT)
	TextureAtlasSprite getDiscoveredIcon();

	Class<? extends IBreedingTracker> getTrackerClass();

	@SubscribeEvent
	void onSyncBreedingTracker(ForestryEvent.SyncedBreedingTracker event);

	void syncTracker(IBreedingTracker tracker);

	void onSyncBreedingTracker(IBreedingTracker tracker);

	String getName();

	boolean isDNAManipulable(ItemStack stack);

	boolean isDNAManipulable(ISpeciesType type);

	IIndividual getDefaultIndividual();

	ISpeciesType getDefaultType();

	ISpeciesType[] getActiveTypes();

	Collection<IChromosomeType> getActiveKaryotype();

	ItemStack getDefaultMember(String uid);

	IFieldKitPlugin getFieldKitPlugin();
}
