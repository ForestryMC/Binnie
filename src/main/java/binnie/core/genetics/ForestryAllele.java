package binnie.core.genetics;

import binnie.Binnie;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;

public class ForestryAllele {
	public enum Lifespan {
		Shortest,
		Shorter,
		Short,
		Shortened,
		Normal,
		Elongated,
		Long,
		Longer,
		Longest;

		public IAllele getAllele() {
			return AlleleManager.alleleRegistry.getAllele("forestry.lifespan" + this.toString());
		}
	}

	public enum Speed {
		Slowest,
		Slower,
		Slow,
		Normal,
		Fast,
		Faster,
		Fastest;

		public IAllele getAllele() {
			return AlleleManager.alleleRegistry.getAllele("forestry.speed" + this.toString());
		}
	}

	public enum Fertility {
		Low,
		Normal,
		High,
		Maximum;

		public IAllele getAllele() {
			return AlleleManager.alleleRegistry.getAllele("forestry.fertility" + this.toString());
		}
	}

	public enum Flowering {
		Slowest,
		Slower,
		Slow,
		Average,
		Fast,
		Faster,
		Fastest,
		Maximum;

		public IAllele getAllele() {
			return AlleleManager.alleleRegistry.getAllele("forestry.flowering" + this.toString());
		}
	}

	public enum Territory {
		Average,
		Large,
		Larger,
		Largest;

		public IAllele getAllele() {
			return AlleleManager.alleleRegistry.getAllele("forestry.territory" + this.toString());
		}
	}

	public enum Sappiness {
		Lowest,
		Lower,
		Low,
		Average,
		High,
		Higher,
		Highest;

		public IAllele getAllele() {
			return AlleleManager.alleleRegistry.getAllele("forestry.sappiness" + this.toString());
		}
	}

	public enum TreeHeight {
		Smallest,
		Smaller,
		Small,
		Average,
		Large,
		Larger,
		Largest,
		Gigantic;

		public IAllele getAllele() {
			return AlleleManager.alleleRegistry.getAllele("forestry.height" + this.toString());
		}
	}

	public enum Size {
		Smallest,
		Smaller,
		Small,
		Average,
		Large,
		Larger,
		Largest;

		public IAllele getAllele() {
			return AlleleManager.alleleRegistry.getAllele("forestry.size" + this.toString());
		}
	}

	public enum Saplings {
		Lowest,
		Lower,
		Low,
		Average,
		High,
		Higher,
		Highest;

		public IAllele getAllele() {
			return AlleleManager.alleleRegistry.getAllele("forestry.saplings" + name());
		}
	}

	public enum Yield {
		Lowest,
		Lower,
		Low,
		Average,
		High,
		Higher,
		Highest;

		public IAllele getAllele() {
			return AlleleManager.alleleRegistry.getAllele("forestry.yield" + this.toString());
		}
	}

	public enum Maturation {
		Slowest,
		Slower,
		Slow,
		Average,
		Fast,
		Faster,
		Fastest;

		public IAllele getAllele() {
			return AlleleManager.alleleRegistry.getAllele("forestry.maturation" + this.toString());
		}
	}

	public enum Bool {
		True,
		False;

		public IAllele getAllele() {
			return AlleleManager.alleleRegistry.getAllele("forestry.bool" + this.toString());
		}

		public static IAllele get(final boolean bool) {
			return (bool ? Bool.True : Bool.False).getAllele();
		}
	}

	public enum Growth {
		Tropical;

		public IAllele getAllele() {
			return AlleleManager.alleleRegistry.getAllele("forestry.growth" + this.toString());
		}
	}

	public enum Int {
		Int1,
		Int2,
		Int3,
		Int4,
		Int5,
		Int6,
		Int7,
		Int8,
		Int9,
		Int10;

		public IAllele getAllele() {
			return AlleleManager.alleleRegistry.getAllele("forestry.i" + (this.ordinal() + 1) + "d");
		}
	}

	public enum BeeSpecies {
		Modest,
		Noble,
		Forest,
		Rural,
		Marshy,
		Sinister,
		Tropical,
		Wintry,
		Merry,
		Austere,
		Imperial,
		Ended,
		Meadows,
		Common,
		Frugal,
		Unweary,
		Diligent,
		Majestic,
		Cultivated,
		Industrious,
		Valiant,
		Secluded,
		Hermitic,
		Spectral,
		Exotic,
		Fiendish,
		Monastic,
		Steadfast,
		Miry,
		Farmerly,
		Boggy,
		Demonic;

		public IAlleleBeeSpecies getAllele() {
			return (IAlleleBeeSpecies) AlleleManager.alleleRegistry.getAllele("forestry.species" + this.toString());
		}

		public IAllele[] getTemplate() {
			return Binnie.Genetics.getBeeRoot().getTemplate(this.getAllele().getUID());
		}
	}
}
