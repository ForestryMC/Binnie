package binnie.botany.genetics;

import binnie.Constants;
import binnie.botany.api.*;
import binnie.botany.core.BotanyCore;
import forestry.api.core.EnumTemperature;
import forestry.api.genetics.*;
import forestry.core.genetics.alleles.AlleleHelper;
import forestry.core.genetics.alleles.EnumAllele;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum FlowerDefinition implements IFlowerDefinition {
    Dandelion("Dandelion", "taraxacum", "officinale", EnumFlowerType.Dandelion, EnumFlowerColor.Yellow){
    	@Override
    	protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
    	}
    	
    	@Override
    	protected void setAlleles(IAllele[] alleles) {
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.FERTILITY, EnumAllele.Fertility.HIGH);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.SHORTENED);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.LOWER);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.BOTH_1);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.BOTH_1);
    	}
    	
    	@Override
    	protected void registerMutations() {
    		// vanilla
    	}
    },
    Poppy("Poppy", "papaver", "rhoeas", EnumFlowerType.Poppy, EnumFlowerColor.Red){
    	@Override
    	protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
    		flowerSpecies.setTemperature(EnumTemperature.WARM);
    	}
    	
    	@Override
    	protected void setAlleles(IAllele[] alleles) {
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.FERTILITY, EnumAllele.Fertility.HIGH);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.SHORTER);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.BOTH_2);
    	}
    	
    	@Override
    	protected void registerMutations() {
    		// vanilla
    	}
    },
    Orchid("Orchid", "vanda", "coerulea", EnumFlowerType.Orchid, EnumFlowerColor.DeepSkyBlue){
    	@Override
    	protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
    		flowerSpecies.setPH(EnumAcidity.Acid);
    	}
    	
    	@Override
    	protected void setAlleles(IAllele[] alleles) {
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.LONG);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.LOW);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.BOTH_1);
    	}
    	
    	@Override
    	protected void registerMutations() {
    		// vanilla
    	}
    },
    Allium("Allium", "allium", "giganteum", EnumFlowerType.Allium, EnumFlowerColor.MediumPurple){
    	@Override
    	protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
    		flowerSpecies.setPH(EnumAcidity.Alkaline);
    	}
    	
    	@Override
    	protected void setAlleles(IAllele[] alleles) {  
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.LOW);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
    	}
    	
    	@Override
    	protected void registerMutations() {
    		// vanilla
    	}
    },
    Bluet("Bluet", "houstonia", "caerulea", EnumFlowerType.Bluet, EnumFlowerColor.Lavender, EnumFlowerColor.Khaki){
    	@Override
    	protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
    		flowerSpecies.setMoisture(EnumMoisture.Damp);
    	}
    	
    	@Override
    	protected void setAlleles(IAllele[] alleles) {
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.FERTILITY, EnumAllele.Fertility.LOW);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.LOWER);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.OliveDrab);
    	}
    	
    	@Override
    	protected void registerMutations() {
    		// vanilla
    	}
    },
    Tulip("Tulip", "tulipa", "agenensis", EnumFlowerType.Tulip, EnumFlowerColor.Violet){
    	@Override
    	protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
    	}
    	
    	@Override
    	protected void setAlleles(IAllele[] alleles) {
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.LONG);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.BOTH_1);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.OliveDrab);

    	}
    	
    	@Override
    	protected void registerMutations() {
    		// vanilla
    	}
    },
    Daisy("Daisy", "leucanthemum", "vulgare", EnumFlowerType.Daisy, EnumFlowerColor.White, EnumFlowerColor.Yellow){
    	@Override
    	protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
    		flowerSpecies.setTemperature(EnumTemperature.WARM);
    	}
    	
    	@Override
    	protected void setAlleles(IAllele[] alleles) {
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.LOW);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.BOTH_2);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.OliveDrab);
    	}
    	
    	@Override
    	protected void registerMutations() {
    		// vanilla
    	}
    },
    Cornflower("Cornflower", "centaurea", "cyanus", EnumFlowerType.Cornflower, EnumFlowerColor.SkyBlue){
    	@Override
    	protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
    	}
    	
    	@Override
    	protected void setAlleles(IAllele[] alleles) {
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.FERTILITY, EnumAllele.Fertility.HIGH);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.SHORTER);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.LOW);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.BOTH_1);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.OliveDrab);
    	}
    	
    	@Override
    	protected void registerMutations() {
            registerMutation(Dandelion, Tulip, 10);
    	}
    },
    Pansy("Pansy", "viola", "tricolor", EnumFlowerType.Pansy, EnumFlowerColor.Pink, EnumFlowerColor.Purple){
    	@Override
    	protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
    		flowerSpecies.setPH(EnumAcidity.Acid);
    		flowerSpecies.setTemperature(EnumTemperature.WARM);
    	}
    	
    	@Override
    	protected void setAlleles(IAllele[] alleles) {
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.SHORTENED);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.SeaGreen);
    	}
    	
    	@Override
    	protected void registerMutations() {
            registerMutation(Tulip, Viola, 5);
    	}
    },
    Iris("Iris", "iris", "germanica", EnumFlowerType.Iris, EnumFlowerColor.LightGray, EnumFlowerColor.Purple){
    	@Override
    	protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
    		flowerSpecies.setPH(EnumAcidity.Acid);
    		flowerSpecies.setTemperature(EnumTemperature.WARM);
    	}
    	
    	@Override
    	protected void setAlleles(IAllele[] alleles) {
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.FERTILITY, EnumAllele.Fertility.HIGH);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.SeaGreen);
    	}
    	
    	@Override
    	protected void registerMutations() {
            registerMutation(Orchid, Viola, 10);
    	}
    },
    Lavender("Lavender", "Lavandula", "angustifolia", EnumFlowerType.Lavender, EnumFlowerColor.MediumOrchid){
    	@Override
    	protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
    		flowerSpecies.setTemperature(EnumTemperature.WARM);
    	}
    	
    	@Override
    	protected void setAlleles(IAllele[] alleles) {
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.FERTILITY, EnumAllele.Fertility.HIGH);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.LOW);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.UP_1);
    	}
    	
    	@Override
    	protected void registerMutations() {
            registerMutation(Allium, Viola, 10);
    	}
    	
    },
    Viola("Viola", "viola", "odorata", EnumFlowerType.Viola, EnumFlowerColor.MediumPurple, EnumFlowerColor.SlateBlue){
    	@Override
    	protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
    		flowerSpecies.setPH(EnumAcidity.Acid);
    	}
    	
    	@Override
    	protected void setAlleles(IAllele[] alleles) {
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.SHORTENED);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.OliveDrab);
    	}
    	
    	@Override
    	protected void registerMutations() {
            registerMutation(Orchid, Poppy, 15);
    	}
    	
    },
    Daffodil("Daffodil", "narcissus", "pseudonarcissus", EnumFlowerType.Daffodil, EnumFlowerColor.Yellow, EnumFlowerColor.Gold){
    	@Override
    	protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
    	}
    	
    	@Override
    	protected void setAlleles(IAllele[] alleles) {
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.ELONGATED);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.FERTILITY, EnumAllele.Fertility.HIGH);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
    	}
    	
    	@Override
    	protected void registerMutations() {
            registerMutation(Dandelion, Poppy, 10);
    	}
    },
    Dahlia("Dahlia", "dahlia", "variabilis", EnumFlowerType.Dahlia, EnumFlowerColor.HotPink, EnumFlowerColor.DeepPink){
    	@Override
    	protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
    	}
    	
    	@Override
    	protected void setAlleles(IAllele[] alleles) {
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.UP_1);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.BOTH_2);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.OliveDrab);
    	}
    	
    	@Override
    	protected void registerMutations() {
            registerMutation(Daisy, Allium, 15);
    	}
    },
    Peony("Peony", "paeonia", "suffruticosa", EnumFlowerType.Peony, EnumFlowerColor.Thistle){
    	@Override
    	protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
    		flowerSpecies.setPH(EnumAcidity.Alkaline);
    	}
    	
    	@Override
    	protected void setAlleles(IAllele[] alleles) {
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.FERTILITY, EnumAllele.Fertility.LOW);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.LONG);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.UP_1);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.DarkGreen);
    	}
    	
    	@Override
    	protected void registerMutations() {
    		// vanilla
    	}
    },
    Rose("Rose", "rosa", "rubiginosa", EnumFlowerType.Rose, EnumFlowerColor.Red){
    	@Override
    	protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
    		flowerSpecies.setPH(EnumAcidity.Acid);
    	}
    	
    	@Override
    	protected void setAlleles(IAllele[] alleles) {
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.LONGER);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.FERTILITY, EnumAllele.Fertility.LOW);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.HIGH);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.UP_1);
    	}
    	
    	@Override
    	protected void registerMutations() {
    		// vanilla
    	}
    },
    Lilac("Lilac", "syringa", "vulgaris", EnumFlowerType.Lilac, EnumFlowerColor.Plum){
    	@Override
    	protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
    		flowerSpecies.setPH(EnumAcidity.Alkaline);
    	}
    	
    	@Override
    	protected void setAlleles(IAllele[] alleles) {
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.FERTILITY, EnumAllele.Fertility.LOW);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.LONGER);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.OliveDrab);
    	}
    	
    	@Override
    	protected void registerMutations() {
    	}
    },
    Hydrangea("Hydrangea", "hydrangea", "macrophylla", EnumFlowerType.Hydrangea, EnumFlowerColor.DeepSkyBlue){
    	@Override
    	protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
    		flowerSpecies.setMoisture(EnumMoisture.Damp);
    	}
    	
    	@Override
    	protected void setAlleles(IAllele[] alleles) {
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.LONGER);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.FERTILITY, EnumAllele.Fertility.LOW);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.HIGH);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.BOTH_1);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.DarkGreen);
    	}
    	
    	@Override
    	protected void registerMutations() {
            registerMutation(Peony, Bluet, 10);
    	}
    },
    Foxglove("Foxglove", "digitalis", "purpurea", EnumFlowerType.Foxglove, EnumFlowerColor.HotPink){
    	@Override
    	protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
    	}
    	
    	@Override
    	protected void setAlleles(IAllele[] alleles) {
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.LOW);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.FERTILITY, EnumAllele.Fertility.LOW);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.SHORTENED);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.UP_1);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.DarkGreen);
    	}
    	
    	@Override
    	protected void registerMutations() {
            registerMutation(Lilac, Zinnia, 5);
    		// vanilla
    	}
    },
    Zinnia("Zinnia", "zinnia", "elegans", EnumFlowerType.Zinnia, EnumFlowerColor.MediumVioletRed, EnumFlowerColor.Yellow){
    	@Override
    	protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
    	}
    	
    	@Override
    	protected void setAlleles(IAllele[] alleles) {
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.SHORTER);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.BOTH_1);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.BOTH_2);
        	AlleleHelper.instance.set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.MediumSeaGreen);
    	}
    	
    	@Override
    	protected void registerMutations() {
            Zinnia.registerMutation(Dahlia, Marigold, 5);
    	}
    },
    Chrysanthemum("Chrysanthemum", "chrysanthemum", "\u00ef?? grandiflorum", EnumFlowerType.Mums, EnumFlowerColor.Violet){
    	@Override
    	protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
    	}
    	
    	@Override
    	protected void setAlleles(IAllele[] alleles) {
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.FERTILITY, EnumAllele.Fertility.HIGH);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.LONG);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.HIGH);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
        	AlleleHelper.instance.set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.MediumSeaGreen);
    	}
    	
    	@Override
    	protected void registerMutations() {
            registerMutation(Geranium, Rose, 10);
    	}
    },
    Marigold("Marigold", "calendula", "officinalis", EnumFlowerType.Marigold, EnumFlowerColor.Gold, EnumFlowerColor.DarkOrange){
    	@Override
    	protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
    		flowerSpecies.setTemperature(EnumTemperature.WARM);
    	}
    	
    	@Override
    	protected void setAlleles(IAllele[] alleles) {
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.FERTILITY, EnumAllele.Fertility.HIGH);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.SHORTER);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.BOTH_2);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.UP_1);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
    	}
    	
    	@Override
    	protected void registerMutations() {
            registerMutation(Daisy, Dandelion, 10);
    	}
    },
    Geranium("Geranium", "geranium", "maderense", EnumFlowerType.Geranium, EnumFlowerColor.DeepPink){
    	@Override
    	protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
    		flowerSpecies.setTemperature(EnumTemperature.WARM);
    	}
    	
    	@Override
    	protected void setAlleles(IAllele[] alleles) {
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.FERTILITY, EnumAllele.Fertility.LOW);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.LONG);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.LOW);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.BOTH_1);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.BOTH_1);
        	AlleleHelper.instance.set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.MediumSeaGreen);
    	}
    	
    	@Override
    	protected void registerMutations() {
            registerMutation(Tulip, Orchid, 15);
    	}
    },
    Azalea("Azalea", "rhododendrons", "aurigeranum", EnumFlowerType.Azalea, EnumFlowerColor.HotPink){
    	@Override
    	protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
    		flowerSpecies.setPH(EnumAcidity.Acid);
    	}
    	
    	@Override
    	protected void setAlleles(IAllele[] alleles) {
    	}
    	
    	@Override
    	protected void registerMutations() {
            registerMutation(Orchid, Geranium, 5);
    	}
    },
    Primrose("Primrose", "primula", "vulgaris", EnumFlowerType.Primrose, EnumFlowerColor.Red, EnumFlowerColor.Gold){
    	@Override
    	protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
    		flowerSpecies.setPH(EnumAcidity.Acid);
    	}
    	
    	@Override
    	protected void setAlleles(IAllele[] alleles) {
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.LONG);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.UP_1);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.UP_1);
    	}
    	
    	@Override
    	protected void registerMutations() {
            registerMutation(Chrysanthemum, Auricula, 5);
    	}
    },
    Aster("Aster", "aster", "amellus", EnumFlowerType.Aster, EnumFlowerColor.MediumPurple, EnumFlowerColor.Goldenrod){
    	@Override
    	protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
    	}
    	
    	@Override
    	protected void setAlleles(IAllele[] alleles) {
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.HIGHER);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.BOTH_1);
    	}
    	
    	@Override
    	protected void registerMutations() {
            registerMutation(Daisy, Tulip, 10);
    	}
    	
    },
    Carnation("Carnation", "dianthus", "caryophyllus", EnumFlowerType.Carnation, EnumFlowerColor.Crimson, EnumFlowerColor.White){
    	@Override
    	protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
    		flowerSpecies.setPH(EnumAcidity.Alkaline);
    	}
    	
    	@Override
    	protected void setAlleles(IAllele[] alleles) {
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.HIGH);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
        	AlleleHelper.instance.set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.SeaGreen);
    	}
    	
    	@Override
    	protected void registerMutations() {
            registerMutation(Dianthus, Rose, 5);
    	}
    },
    Lily("Lily", "lilium", "auratum", EnumFlowerType.Lily, EnumFlowerColor.Pink, EnumFlowerColor.Gold){
    	@Override
    	protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
    		flowerSpecies.setTemperature(EnumTemperature.WARM);
    	}
    	
    	@Override
    	protected void setAlleles(IAllele[] alleles) {
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.LOW);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.LONG);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.FERTILITY, EnumAllele.Fertility.LOW);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.BOTH_1);
    	}
    	
    	@Override
    	protected void registerMutations() {
            registerMutation(Tulip, Chrysanthemum, 5);
    	}
    },
    Yarrow("Yarrow", "achillea", "millefolium", EnumFlowerType.Yarrow, EnumFlowerColor.Yellow){
    	@Override
    	protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
    		flowerSpecies.setPH(EnumAcidity.Acid);
    	}
    	
    	@Override
    	protected void setAlleles(IAllele[] alleles) {
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.FERTILITY, EnumAllele.Fertility.HIGH);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.LOW);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.UP_1);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
        	AlleleHelper.instance.set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.DarkOliveGreen);
    	}
    	
    	@Override
    	protected void registerMutations() {
            registerMutation(Dandelion, Orchid, 10);
    	}
    },
    Petunia("Petunia", "petunia", "\u00ef?? atkinsiana", EnumFlowerType.Petunia, EnumFlowerColor.MediumVioletRed, EnumFlowerColor.Thistle){
    	@Override
    	protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
    		flowerSpecies.setTemperature(EnumTemperature.WARM);
    	}
    	
    	@Override
    	protected void setAlleles(IAllele[] alleles) {
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.SHORTER);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.UP_1);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.UP_1);
    	}
    	
    	@Override
    	protected void registerMutations() {
            registerMutation(Tulip, Dahlia, 5);
    	}
    },
    Agapanthus("Agapanthus", "agapanthus", "praecox", EnumFlowerType.Agapanthus, EnumFlowerColor.DeepSkyBlue){
    	@Override
    	protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
    		flowerSpecies.setTemperature(EnumTemperature.WARM);
    	}
    	
    	@Override
    	protected void setAlleles(IAllele[] alleles) {
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.LOW);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.BOTH_1);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.BOTH_1);
        	AlleleHelper.instance.set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.DarkOliveGreen);
    	}
    	
    	@Override
    	protected void registerMutations() {
            registerMutation(Allium, Geranium, 5);
    	}
    },
    Fuchsia("Fuchsia", "fuchsia", "magellanica", EnumFlowerType.Fuchsia, EnumFlowerColor.DeepPink, EnumFlowerColor.MediumOrchid){
    	@Override
    	protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
    		flowerSpecies.setTemperature(EnumTemperature.WARM);
    	}
    	
    	@Override
    	protected void setAlleles(IAllele[] alleles) {
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.SHORTENED);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.BOTH_1);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.UP_1);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.BOTH_1);
        	AlleleHelper.instance.set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.SeaGreen);
    	}
    	
    	@Override
    	protected void registerMutations() {
            registerMutation(Foxglove, Dahlia, 5);
    	}
    },
    Dianthus("Dianthus", "dianthus", "barbatus", EnumFlowerType.Dianthus, EnumFlowerColor.Crimson, EnumFlowerColor.HotPink){
    	@Override
    	protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
    		flowerSpecies.setPH(EnumAcidity.Alkaline);
    	}
    	
    	@Override
    	protected void setAlleles(IAllele[] alleles) {
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.SHORT);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.HIGH);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.BOTH_2);
        	AlleleHelper.instance.set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.OliveDrab);
    	}
    	
    	@Override
    	protected void registerMutations() {
            registerMutation(Tulip, Poppy, 15);
    	}
    },
    Forget("Forget-me-nots", "myosotis", "arvensis", EnumFlowerType.Forget, EnumFlowerColor.LightSteelBlue){
    	@Override
    	protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
    		flowerSpecies.setPH(EnumAcidity.Acid);
    	}
    	
    	@Override
    	protected void setAlleles(IAllele[] alleles) {
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.LOWER);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.SHORT);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.UP_1);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.UP_1);
    	}
    	
    	@Override
    	protected void registerMutations() {
            registerMutation(Orchid, Bluet, 10);
    	}
    },
    Anemone("Anemone", "anemone", "coronaria", EnumFlowerType.Anemone, EnumFlowerColor.Red, EnumFlowerColor.MistyRose){
    	@Override
    	protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
    	}
    	
    	@Override
    	protected void setAlleles(IAllele[] alleles) {
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.FERTILITY, EnumAllele.Fertility.HIGH);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.LOW);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.BOTH_1);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.UP_1);
        	AlleleHelper.instance.set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.DarkOliveGreen);
    	}
    	
    	@Override
    	protected void registerMutations() {
            FlowerDefinition.Anemone.registerMutation(FlowerDefinition.Aquilegia, FlowerDefinition.Rose, 5);
    	}
    },
    Aquilegia("Aquilegia", "aquilegia", "vulgaris", EnumFlowerType.Aquilegia, EnumFlowerColor.SlateBlue, EnumFlowerColor.Thistle){
    	@Override
    	protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
    	}
    	
    	@Override
    	protected void setAlleles(IAllele[] alleles) {
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.UP_1);
        	AlleleHelper.instance.set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.MediumSeaGreen);
    	}
    	
    	@Override
    	protected void registerMutations() {
            registerMutation(Iris, Poppy, 5);
    	}
    },
    Edelweiss("Edelweiss", "leontopodium", "alpinum", EnumFlowerType.Edelweiss, EnumFlowerColor.White, EnumFlowerColor.Khaki){
    	@Override
    	protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
    		flowerSpecies.setPH(EnumAcidity.Alkaline);
    	}
    	
    	@Override
    	protected void setAlleles(IAllele[] alleles) {
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.LOWEST);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.FERTILITY, EnumAllele.Fertility.LOW);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
        	AlleleHelper.instance.set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.DarkOliveGreen);
    	}
    	
    	@Override
    	protected void registerMutations() {
            FlowerDefinition.Edelweiss.registerMutation(FlowerDefinition.Peony, FlowerDefinition.Bluet, 5);
    	}
    },
    Scabious("Scabious", "scabiosa", "columbaria", EnumFlowerType.Scabious, EnumFlowerColor.RoyalBlue){
    	@Override
    	protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
    	}
    	
    	@Override
    	protected void setAlleles(IAllele[] alleles) {
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.SHORTENED);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.LOW);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.UP_1);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
        	AlleleHelper.instance.set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.OliveDrab);
    	}
    	
    	@Override
    	protected void registerMutations() {
            registerMutation(Allium, Cornflower, 5);
    	}
    },
    Coneflower("Coneflower", "echinacea", "purpurea", EnumFlowerType.Coneflower, EnumFlowerColor.Violet, EnumFlowerColor.DarkOrange){
    	@Override
    	protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
    	}
    	
    	@Override
    	protected void setAlleles(IAllele[] alleles) {
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.HIGHER);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.BOTH_1);
        	AlleleHelper.instance.set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.DarkOliveGreen);
    	}
    	
    	@Override
    	protected void registerMutations() {
            registerMutation(Tulip, Cornflower, 5);
    	}
    },
    Gaillardia("Gaillardia", "gaillardia", "aristata", EnumFlowerType.Gaillardia, EnumFlowerColor.DarkOrange, EnumFlowerColor.Yellow){
    	@Override
    	protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
    		flowerSpecies.setMoisture(EnumMoisture.Damp);
    	}
    	
    	@Override
    	protected void setAlleles(IAllele[] alleles) {
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.FERTILITY, EnumAllele.Fertility.HIGH);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.LONG);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.HIGHER);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.BOTH_1);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.BOTH_2);
        	AlleleHelper.instance.set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.OliveDrab);
    	}
    	
    	@Override
    	protected void registerMutations() {
            registerMutation(Dandelion, Marigold, 5);
    	}
    },
    Auricula("Auricula", "primula", "auricula", EnumFlowerType.Auricula, EnumFlowerColor.Red, EnumFlowerColor.Yellow){
    	@Override
    	protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
    		flowerSpecies.setPH(EnumAcidity.Acid);
    	}
    	
    	@Override
    	protected void setAlleles(IAllele[] alleles) {
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.ELONGATED);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.HIGH);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.UP_1);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.UP_1);
        	AlleleHelper.instance.set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.DarkOliveGreen);
    	}
    	
    	@Override
    	protected void registerMutations() {
            registerMutation(Poppy, Geranium, 10);
    	}
    },
    Camellia("Camellia", "camellia", "japonica", EnumFlowerType.Camellia, EnumFlowerColor.Crimson){
    	@Override
    	protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
    		flowerSpecies.setPH(EnumAcidity.Acid);
    		flowerSpecies.setMoisture(EnumMoisture.Damp);
    		flowerSpecies.setTemperature(EnumTemperature.WARM);
    	}
    	
    	@Override
    	protected void setAlleles(IAllele[] alleles) {
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.HIGH);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.UP_1);
        	AlleleHelper.instance.set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.DarkOliveGreen);
    	}
    	
    	@Override
    	protected void registerMutations() {
            registerMutation(Hydrangea, Rose, 5);
    	}
    },
    Goldenrod("Goldenrod", "solidago", "canadensis", EnumFlowerType.Goldenrod, EnumFlowerColor.Gold){
    	@Override
    	protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
    	}
    	
    	@Override
    	protected void setAlleles(IAllele[] alleles) {
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.FERTILITY, EnumAllele.Fertility.HIGH);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.HIGHER);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.BOTH_1);
        	AlleleHelper.instance.set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.MediumSeaGreen);
    	}
    	
    	@Override
    	protected void registerMutations() {
            registerMutation(Lilac, Marigold, 10);
    	}
    },
    Althea("Althea", "althaea", "officinalis", EnumFlowerType.Althea, EnumFlowerColor.Thistle, EnumFlowerColor.MediumOrchid){
    	@Override
    	protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
    		flowerSpecies.setTemperature(EnumTemperature.WARM);
    	}
    	
    	@Override
    	protected void setAlleles(IAllele[] alleles) {
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.FERTILITY, EnumAllele.Fertility.LOW);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.ELONGATED);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.HIGH);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.BOTH_1);
        	AlleleHelper.instance.set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.DarkGreen);
    	}
    	
    	@Override
    	protected void registerMutations() {
            registerMutation(Hydrangea, Iris, 5);
    	}
    },
    Penstemon("Penstemon", "penstemon", "digitalis", EnumFlowerType.Penstemon, EnumFlowerColor.MediumOrchid, EnumFlowerColor.Thistle){
    	@Override
    	protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
    		flowerSpecies.setMoisture(EnumMoisture.Dry);
    		flowerSpecies.setTemperature(EnumTemperature.WARM);
    	}
    	
    	@Override
    	protected void setAlleles(IAllele[] alleles) {
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.LOW);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.FERTILITY, EnumAllele.Fertility.LOW);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.LONG);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.UP_1);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.UP_1);
        	AlleleHelper.instance.set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.OliveDrab);
    	}
    	
    	@Override
    	protected void registerMutations() {
            registerMutation(Peony, Lilac, 5);
    	}
    },
    Delphinium("Delphinium", "delphinium", "staphisagria", EnumFlowerType.Delphinium, EnumFlowerColor.DarkSlateBlue){
    	@Override
    	protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
    		flowerSpecies.setMoisture(EnumMoisture.Damp);
    	}
    	
    	@Override
    	protected void setAlleles(IAllele[] alleles) {
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.LOW);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.FERTILITY, EnumAllele.Fertility.LOW);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.LONGER);
      		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
        	AlleleHelper.instance.set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.DarkSeaGreen);
    	}
    	
    	@Override
    	protected void registerMutations() {
    	       registerMutation(Lilac, Bluet, 5);
    	}
    },
    Hollyhock("Hollyhock", "Alcea", "rosea", EnumFlowerType.Hollyhock, EnumFlowerColor.Black, EnumFlowerColor.Gold){
    	@Override
    	protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
    	}
    	
    	@Override
    	protected void setAlleles(IAllele[] alleles) {
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.LOW);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.FERTILITY, EnumAllele.Fertility.LOW);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.LONG);
    		AlleleHelper.instance.set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.UP_1);
    	}
    	
    	@Override
    	protected void registerMutations() {
            registerMutation(Delphinium, Lavender, 5);
    	}
    };

    IFlowerType type;
    String name;
    String binomial;
    String branchName;
    List<IAllele[]> variantTemplates;
    IClassification branch;
    EnumFlowerColor primaryColor, secondaryColor;

    private static void setupVariants() {
    	IFlowerRoot flowerRood = BotanyCore.getFlowerRoot();
    	flowerRood.addConversion(new ItemStack(Blocks.YELLOW_FLOWER, 1, 0), Dandelion.getTemplate());
    	flowerRood.addConversion(new ItemStack(Blocks.RED_FLOWER, 1, 0), Poppy.getTemplate());
        flowerRood.addConversion(new ItemStack(Blocks.RED_FLOWER, 1, 1), Orchid.getTemplate());
        flowerRood.addConversion(new ItemStack(Blocks.RED_FLOWER, 1, 2), Allium.getTemplate());
        flowerRood.addConversion(new ItemStack(Blocks.RED_FLOWER, 1, 3), Bluet.getTemplate());
        flowerRood.addConversion(new ItemStack(Blocks.RED_FLOWER, 1, 7), Tulip.getTemplate());
        flowerRood.addConversion(new ItemStack(Blocks.RED_FLOWER, 1, 8), Daisy.getTemplate());
        flowerRood.addConversion(new ItemStack(Blocks.DOUBLE_PLANT, 1, 1), Lilac.getTemplate());
        flowerRood.addConversion(new ItemStack(Blocks.DOUBLE_PLANT, 1, 4), Rose.getTemplate());
        flowerRood.addConversion(new ItemStack(Blocks.DOUBLE_PLANT, 1, 5), Peony.getTemplate());
        flowerRood.addConversion(new ItemStack(Blocks.RED_FLOWER, 1, 6), Tulip.addVariant(EnumFlowerColor.White));
        flowerRood.addConversion(new ItemStack(Blocks.RED_FLOWER, 1, 4), Tulip.addVariant(EnumFlowerColor.Crimson));
        flowerRood.addConversion(new ItemStack(Blocks.RED_FLOWER, 1, 5), Tulip.addVariant(EnumFlowerColor.DarkOrange));
    }
    public static FlowerDefinition[] VALUES = values();
    
    private final IAlleleFlowerSpecies species;
    
	private IAllele[] template;
	private IFlowerGenome genome;

    FlowerDefinition(final String name, final String branch, final String binomial, final IFlowerType type, final EnumFlowerColor colour) {
        this(name, branch, binomial, type, false, colour, colour);
    }
    
    FlowerDefinition(final String name, final String branch, final String binomial, final IFlowerType type, final EnumFlowerColor primaryColor, final EnumFlowerColor secondaryColor) {
        this(name, branch, binomial, type, true, primaryColor, secondaryColor);
    }
	
    FlowerDefinition(final String name, final String branch, final String binomial, final IFlowerType type, final boolean isDominant, final EnumFlowerColor colour) {
        this(name, branch, binomial, type, isDominant, colour, colour);
    }

    FlowerDefinition(final String name, final String branch, final String binomial, final IFlowerType flowerType, final boolean isDominant, final EnumFlowerColor primaryColor, final EnumFlowerColor secondaryColor) {
    	String uid = Constants.BOTANY_MOD_ID + ".flower" + this;
		String unlocalizedDescription = "botany.description.flower" + this;
		String unlocalizedName = "botany.flowers.species." + name;
		
        this.variantTemplates = new ArrayList<>();
        this.name = name;
        this.binomial = binomial;
        this.branchName = branch;
        this.type = flowerType;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        
		IAlleleFlowerSpeciesBuilder speciesBuilder = FlowerManager.flowerFactory.createSpecies(uid, unlocalizedName, "Binnie's Mod Team", unlocalizedDescription, isDominant, getBranch(), binomial, flowerType);
		setSpeciesProperties(speciesBuilder);
		this.species = speciesBuilder.build();
		if(branch != null){
	        this.branch.addMemberSpecies(species);
		}
    }
    
	protected abstract void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies);

	protected abstract void setAlleles(IAllele[] alleles);

	protected abstract void registerMutations();

    private IAllele[] addVariant(final EnumFlowerColor a, final EnumFlowerColor b) {
        final IAllele[] template = this.getTemplate();
        template[EnumFlowerChromosome.PRIMARY.ordinal()] = a.getAllele();
        template[EnumFlowerChromosome.SECONDARY.ordinal()] = b.getAllele();
        this.variantTemplates.add(template);
        return template;
    }

    private IAllele[] addVariant(final EnumFlowerColor a) {
        return this.addVariant(a, a);
    }

    public List<IAllele[]> getVariants() {
        return this.variantTemplates;
    }

    public void setBranch(final IClassification branch) {
        this.branch = branch;
    }

    public IClassification getBranch() {
    	if(branch == null){
	        final String scientific = branchName.substring(0, 1).toUpperCase() + branchName.substring(1).toLowerCase();
	        final String uid = "flowers." + branchName.toLowerCase();
	        IClassification branch = AlleleManager.alleleRegistry.getClassification("genus." + uid);
	        if (branch == null) {
	            branch = AlleleManager.alleleRegistry.createAndRegisterClassification(IClassification.EnumClassLevel.GENUS, uid, scientific);
	        }
	        this.branch = branch;
    	}
        return this.branch;
    }
    
	@Override
	public final IAllele[] getTemplate() {
		return Arrays.copyOf(template, template.length);
	}
	
	public IAlleleFlowerSpecies getSpecies() {
		return species;
	}
    
    @Override
    public IFlowerGenome getGenome() {
    	return genome;
    }
    
    @Override
    public IFlower getIndividual() {
    	return new Flower(genome, 0);
    }
    
    @Override
    public ItemStack getMemberStack(EnumFlowerStage flowerStage) {
    	IFlower flower = getIndividual();
    	return BotanyCore.getFlowerRoot().getMemberStack(flower, flowerStage);
    }
	
	public static void preInitFlowers(){
		MinecraftForge.EVENT_BUS.post(new AlleleSpeciesRegisterEvent(FlowerManager.flowerRoot));
		for(FlowerDefinition def : values()){
			IFlowerType type = def.species.getType();
			if(EnumFlowerType.highestSection < type.getSections()){
				EnumFlowerType.highestSection = type.getSections();
			}
		}
	}
    
	public static void initFlowers() {
		for (FlowerDefinition flower : values()) {
			flower.init();
		}
		setupVariants();
		for (FlowerDefinition flower : values()) {
			flower.registerMutations();
		}
	}
	
	private void init() {
		template = Arrays.copyOf(FlowerTemplates.getDefaultTemplate(), EnumFlowerChromosome.values().length);
		AlleleHelper.instance.set(template, EnumFlowerChromosome.SPECIES, species);
		AlleleHelper.instance.set(template, EnumFlowerChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.BOTH_1);
		AlleleHelper.instance.set(template, EnumFlowerChromosome.PRIMARY, primaryColor);
		AlleleHelper.instance.set(template, EnumFlowerChromosome.SECONDARY, secondaryColor);
		setAlleles(template);

		genome = BotanyCore.getFlowerRoot().templateAsGenome(template);

		BotanyCore.getFlowerRoot().registerTemplate(template);
		for(IAllele[] template : variantTemplates){
			BotanyCore.getFlowerRoot().registerTemplate(template);
		}
	}
    
	protected final IFlowerMutationBuilder registerMutation(final FlowerDefinition parent1, final FlowerDefinition parent2, final int chance) {
    	return FlowerManager.flowerFactory.createMutation(parent1.species, parent2.species, getTemplate(), chance);
    }
}
