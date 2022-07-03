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
    protected IAlleleFlowerSpecies allele0;
    protected IAlleleFlowerSpecies allele1;
    protected IAllele[] template;
    protected int chance;

    FlowerMutation(IAlleleFlowerSpecies allele0, IAlleleFlowerSpecies allele1, IAllele[] template, int chance) {
        this.allele0 = allele0;
        this.allele1 = allele1;
        this.template = template;
        this.chance = chance;
    }

    @Override
    public IAlleleFlowerSpecies getAllele0() {
        return allele0;
    }

    @Override
    public IAlleleFlowerSpecies getAllele1() {
        return allele1;
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
        return allele.getUID().equals(allele0.getUID()) || allele.getUID().equals(allele1.getUID());
    }

    @Override
    public IAllele getPartner(IAllele allele) {
        return allele.getUID().equals(allele0.getUID()) ? allele1 : allele0;
    }

    @Override
    public boolean isSecret() {
        return false;
    }

    @Override
    public float getChance(IAllele allele0, IAllele allele1, IGenome genome0, IGenome genome1) {
        if (allele0 == null || allele1 == null) {
            return 0.0f;
        }
        if (this.allele0.getUID().equals(allele0.getUID())
                && this.allele1.getUID().equals(allele1.getUID())) {
            return getBaseChance();
        }
        if (this.allele1.getUID().equals(allele0.getUID())
                && this.allele0.getUID().equals(allele1.getUID())) {
            return getBaseChance();
        }
        return 0.0f;
    }

    @Override
    public ISpeciesRoot getRoot() {
        return BotanyCore.getFlowerRoot();
    }

    @Override
    public Collection<String> getSpecialConditions() {
        return new ArrayList<>();
    }
}
