package binnie.botany.genetics;

import binnie.botany.api.IAlleleFlowerSpecies;
import binnie.botany.api.IFlowerMutation;
import binnie.botany.core.BotanyCore;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IGenome;
import forestry.api.genetics.ISpeciesRoot;

import java.util.ArrayList;
import java.util.Collection;

public class FlowerMutation implements IFlowerMutation {
    IAlleleFlowerSpecies allele0;
    IAlleleFlowerSpecies allele1;
    IAllele[] template;
    int chance;

    FlowerMutation(final IAlleleFlowerSpecies allele0, final IAlleleFlowerSpecies allele1, final IAllele[] template, final int chance) {
        this.allele0 = allele0;
        this.allele1 = allele1;
        this.template = template;
        this.chance = chance;
    }

    @Override
    public IAlleleFlowerSpecies getAllele0() {
        return this.allele0;
    }

    @Override
    public IAlleleFlowerSpecies getAllele1() {
        return this.allele1;
    }

    @Override
    public IAllele[] getTemplate() {
        return this.template;
    }

    @Override
    public float getBaseChance() {
        return this.chance;
    }

    @Override
    public boolean isPartner(final IAllele allele) {
        return allele.getUID().equals(this.allele0.getUID()) || allele.getUID().equals(this.allele1.getUID());
    }

    @Override
    public IAllele getPartner(final IAllele allele) {
        return allele.getUID().equals(this.allele0.getUID()) ? this.allele1 : this.allele0;
    }

    @Override
    public boolean isSecret() {
        return false;
    }

    @Override
    public float getChance(final IAllele allele0, final IAllele allele1, final IGenome genome0, final IGenome genome1) {
        if (allele0 == null || allele1 == null) {
            return 0.0f;
        }
        if (this.allele0.getUID().equals(allele0.getUID()) && this.allele1.getUID().equals(allele1.getUID())) {
            return this.getBaseChance();
        }
        if (this.allele1.getUID().equals(allele0.getUID()) && this.allele0.getUID().equals(allele1.getUID())) {
            return this.getBaseChance();
        }
        return 0.0f;
    }

    @Override
    public ISpeciesRoot getRoot() {
        return BotanyCore.getFlowerRoot();
    }

    @Override
    public Collection<String> getSpecialConditions() {
        return new ArrayList<String>();
    }
}
