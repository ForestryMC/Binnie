package binnie.core.genetics;

import binnie.*;
import forestry.api.apiculture.*;
import forestry.api.genetics.*;

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
			return AlleleManager.alleleRegistry.getAllele("forestry.lifespan" + toString());
		}
	}

	public enum Speed {
		Slowest,
		Slower,
		Slow,
		Norm,
		Fast,
		Faster,
		Fastest;

		public IAllele getAllele() {
			return AlleleManager.alleleRegistry.getAllele("forestry.speed" + toString());
		}
	}

	public enum Fertility {
		Low,
		Normal,
		High,
		Maximum;

		public IAllele getAllele() {
			return AlleleManager.alleleRegistry.getAllele("forestry.fertility" + toString());
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
			return AlleleManager.alleleRegistry.getAllele("forestry.flowering" + toString());
		}
	}

	public enum Territory {
		Default,
		Large,
		Larger,
		Largest;

		public IAllele getAllele() {
			return AlleleManager.alleleRegistry.getAllele("forestry.territory" + toString());
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
			return AlleleManager.alleleRegistry.getAllele("forestry.sappiness" + toString());
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
			return AlleleManager.alleleRegistry.getAllele("forestry.height" + ((this == TreeHeight.Average) ? "Max10" : toString()));
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
			return AlleleManager.alleleRegistry.getAllele("forestry.size" + toString());
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
			String s = toString();
			if (this == Saplings.Average) {
				s = "Default";
			}
			if (this == Saplings.High) {
				s = "Double";
			}
			if (this == Saplings.Higher) {
				s = "Triple";
			}
			return AlleleManager.alleleRegistry.getAllele("forestry.saplings" + s);
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
			return AlleleManager.alleleRegistry.getAllele("forestry.yield" + ((this == Yield.Average) ? "Default" : toString()));
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
			return AlleleManager.alleleRegistry.getAllele("forestry.maturation" + toString());
		}
	}

	public enum Bool {
		True,
		False;

		public IAllele getAllele() {
			return AlleleManager.alleleRegistry.getAllele("forestry.bool" + toString());
		}

		public static IAllele get(boolean bool) {
			return (bool ? Bool.True : Bool.False).getAllele();
		}
	}

	public enum Growth {
		Tropical;

		public IAllele getAllele() {
			return AlleleManager.alleleRegistry.getAllele("forestry.growth" + toString());
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
			return AlleleManager.alleleRegistry.getAllele("forestry.i" + (ordinal() + 1) + "d");
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
			return (IAlleleBeeSpecies) AlleleManager.alleleRegistry.getAllele("forestry.species" + toString());
		}

		public IAllele[] getTemplate() {
			return Binnie.Genetics.getBeeRoot().getTemplate(getAllele().getUID());
		}
	}
}
