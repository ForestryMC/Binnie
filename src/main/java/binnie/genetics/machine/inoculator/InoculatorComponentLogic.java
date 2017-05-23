package binnie.genetics.machine.inoculator;

import binnie.core.machines.Machine;
import binnie.core.machines.power.ComponentProcessSetCost;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IProcess;
import binnie.genetics.api.IGene;
import binnie.genetics.api.IItemSerum;
import binnie.genetics.genetics.Engineering;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IGenome;
import forestry.api.genetics.IIndividual;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class InoculatorComponentLogic extends ComponentProcessSetCost implements IProcess {
	private float bacteriaDrain;

	public InoculatorComponentLogic(Machine machine) {
		super(machine, 600000, 12000);
		bacteriaDrain = 0.0f;
	}

	@Override
	public int getProcessLength() {
		return super.getProcessLength() * getNumberOfGenes();
	}

	@Override
	public int getProcessEnergy() {
		return super.getProcessEnergy() * getNumberOfGenes();
	}

	private int getNumberOfGenes() {
		ItemStack serum = getUtil().getStack(Inoculator.SLOT_SERUM_VIAL);
		if (serum == null) {
			return 1;
		}
		return Engineering.getGenes(serum).length;
	}

	@Override
	public String getTooltip() {
		int n = getNumberOfGenes();
		return "Inoculating with " + n + " gene" + ((n > 1) ? "s" : "");
	}

	@Override
	public ErrorState canWork() {
		if (getUtil().isSlotEmpty(Inoculator.SLOT_TARGET)) {
			return new ErrorState.NoItem("No Individual to Inoculate", Inoculator.SLOT_TARGET);
		}
		if (getUtil().isSlotEmpty(Inoculator.SLOT_SERUM_VIAL)) {
			return new ErrorState.NoItem("No Serum", Inoculator.SLOT_SERUM_VIAL);
		}
		if (getUtil().isTankEmpty(Inoculator.TANK_VECTOR)) {
			return new ErrorState.InsufficientLiquid("Not enough liquid", Inoculator.TANK_VECTOR);
		}

		ErrorState state = isValidSerum();
		if (state != null) {
			return state;
		}
		
		ItemStack stack = getUtil().getStack(Inoculator.SLOT_SERUM_VIAL);
		if (stack != null && Engineering.getCharges(stack) == 0) {
			return new ErrorState("Empty Serum", "Serum is empty");
		}
		return super.canWork();
	}

	public ErrorState isValidSerum() {
		ItemStack serum = getUtil().getStack(Inoculator.SLOT_SERUM_VIAL);
		ItemStack target = getUtil().getStack(Inoculator.SLOT_TARGET);
		IGene[] genes = Engineering.getGenes(serum);
		if (genes.length == 0) {
			return new ErrorState("Invalid Serum", "Serum does not hold any genes");
		}
		if (!genes[0].getSpeciesRoot().isMember(target)) {
			return new ErrorState("Invalid Serum", "Mismatch of Serum Type and Target");
		}

		IIndividual individual = genes[0].getSpeciesRoot().getMember(target);
		boolean hasAll = true;
		for (IGene gene : genes) {
			if (hasAll) {
				IGenome genome = individual.getGenome();
				IChromosomeType chromosome = gene.getChromosome();
				String geneAlleleUID = gene.getAllele().getUID();
				IAllele a = genome.getActiveAllele(chromosome);
				IAllele b = genome.getInactiveAllele(chromosome);
				hasAll = a.getUID().equals(geneAlleleUID)
					&& b.getUID().equals(geneAlleleUID);
			}
		}

		if (hasAll) {
			return new ErrorState("Defunct Serum", "Individual already possesses this allele");
		}
		return null;
	}

	@Override
	public ErrorState canProgress() {
		return super.canProgress();
	}

	@Override
	protected void onFinishTask() {
		super.onFinishTask();
		ItemStack serum = getUtil().getStack(Inoculator.SLOT_SERUM_VIAL);
		ItemStack target = getUtil().getStack(Inoculator.SLOT_TARGET);
		IIndividual ind = AlleleManager.alleleRegistry.getIndividual(target);
		if (!ind.isAnalyzed()) {
			ind.analyze();
			NBTTagCompound nbttagcompound = new NBTTagCompound();
			ind.writeToNBT(nbttagcompound);
			target.setTagCompound(nbttagcompound);
		}

		IGene[] genes = ((IItemSerum) serum.getItem()).getGenes(serum);
		for (IGene gene : genes) {
			Inoculator.setGene(gene, target, 0);
			Inoculator.setGene(gene, target, 1);
		}
		getUtil().damageItem(Inoculator.SLOT_SERUM_VIAL, 1);
	}

	@Override
	protected void onTickTask() {
		bacteriaDrain += 15.0f * getProgressPerTick() / 100.0f;
		if (bacteriaDrain >= 1.0f) {
			getUtil().drainTank(0, 1);
			bacteriaDrain--;
		}
	}
}
