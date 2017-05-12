package binnie.extrabees.genetics;

import binnie.Binnie;
import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.apiculture.IBeeMutation;
import forestry.api.apiculture.IBeeRoot;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IGenome;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ExtraBeeMutation implements IBeeMutation {
	public static List<IBeeMutation> mutations = new ArrayList<>();

	protected IAllele[] template;
	protected int chance;

	private MutationRequirement req;
	private IAlleleBeeSpecies species0;
	private IAlleleBeeSpecies species1;

	public ExtraBeeMutation(IAlleleBeeSpecies allele0, IAlleleBeeSpecies allele1, ExtraBeesSpecies mutation, int chance) {
		this(allele0, allele1, mutation.getTemplate(), chance, null);
	}

	public ExtraBeeMutation(IAlleleBeeSpecies allele0, IAlleleBeeSpecies allele1, ExtraBeesSpecies mutation, int chance, MutationRequirement req) {
		this(allele0, allele1, mutation.getTemplate(), chance, req);
	}

	public ExtraBeeMutation(IAlleleBeeSpecies allele0, IAlleleBeeSpecies allele1, IAllele[] mutation, int chance) {
		this(allele0, allele1, mutation, chance, null);
	}

	public ExtraBeeMutation(IAlleleBeeSpecies allele0, IAlleleBeeSpecies allele1, IAllele[] mutation, int chance, MutationRequirement req) {
		this.chance = chance;
		this.req = req;
		species0 = allele0;
		species1 = allele1;
		template = mutation;

		if (species0 != null && species1 != null && template != null) {
			ExtraBeeMutation.mutations.add(this);
		}
	}

	public static void doInit() {
		
	}

	@Override
	public IAlleleSpecies getAllele0() {
		return species0;
	}

	@Override
	public IAlleleSpecies getAllele1() {
		return species1;
	}

	@Override
	public IAllele[] getTemplate() {
		return template;
	}

	@Override
	public float getBaseChance() {
		return chance;
	}

	@Override
	public boolean isPartner(IAllele allele) {
		String uid = allele.getUID();
		return uid.equals(species0.getUID())
			|| uid.equals(species1.getUID());
	}

	@Override
	public IAllele getPartner(IAllele allele) {
		return allele.getUID().equals(species0.getUID()) ? species1 : species0;
	}

	@Override
	public boolean isSecret() {
		return false;
	}

	@Override
	public float getChance(IBeeHousing housing, IAlleleBeeSpecies allele0, IAlleleBeeSpecies allele1, IBeeGenome genome0, IBeeGenome genome1) {
		if (species0 == null || species1 == null || allele0 == null || allele1 == null) {
			return 0.0f;
		}

		World world = housing.getWorld();
		if (req != null && !req.fufilled(housing, allele0, allele1, genome0, genome1)) {
			return 0.0f;
		}

		int processedChance = Math.round(chance * BeeManager.beeRoot.createBeeHousingModifier(housing).getMutationModifier(genome0, genome1, 1.0f)
				* Binnie.Genetics.getBeeRoot().getBeekeepingMode(world).getBeeModifier().getMutationModifier(genome0, genome1, 1.0f));
		if (species0.getUID().equals(allele0.getUID()) && species1.getUID().equals(allele1.getUID())) {
			return processedChance;
		}
		if (species1.getUID().equals(allele0.getUID()) && species0.getUID().equals(allele1.getUID())) {
			return processedChance;
		}
		return 0.0f;
	}

	@Override
	public Collection<String> getSpecialConditions() {
		List<String> conditions = new ArrayList<>();
		if (req != null) {
			Collections.addAll(conditions, req.tooltip());
		}
		return conditions;
	}

	@Override
	public IBeeRoot getRoot() {
		return Binnie.Genetics.getBeeRoot();
	}

	public abstract static class MutationRequirement {
		public abstract String[] tooltip();

		public abstract boolean fufilled(IBeeHousing housing, IAllele allele0, IAllele allele1, IGenome genome0, IGenome genome1);
	}

}
