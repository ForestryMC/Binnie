package binnie.extrabees.alveary;

import javax.annotation.Nonnull;

import net.minecraft.util.IStringSerializable;

public enum EnumAlvearyLogicType implements IStringSerializable {

	FRAME {
		@Override
		public AbstractAlvearyLogic createLogic(TileEntityExtraBeesAlvearyPart tile) {
			return new AlvearyLogicFrameHousing(tile);
		}
	},
	HATCHERY {
		@Override
		public AbstractAlvearyLogic createLogic(TileEntityExtraBeesAlvearyPart tile) {
			return new AlvearyLogicHatchery();
		}
	},
	LIGHTING {
		@Override
		public AbstractAlvearyLogic createLogic(TileEntityExtraBeesAlvearyPart tile) {
			return new AlvearyLogicLighting();
		}
	},
	MUTATOR {
		@Override
		public AbstractAlvearyLogic createLogic(TileEntityExtraBeesAlvearyPart tile) {
			return new AlvearyLogicMutator();
		}
	},
	RAINSHIELD {
		@Override
		public AbstractAlvearyLogic createLogic(TileEntityExtraBeesAlvearyPart tile) {
			return new AlvearyLogicRainShield();
		}
	},
	STIMULATOR {
		@Override
		public AbstractAlvearyLogic createLogic(TileEntityExtraBeesAlvearyPart tile) {
			return new AlvearyLogicStimulator();
		}
	},
	TRANSMISSION {
		@Override
		public AbstractAlvearyLogic createLogic(TileEntityExtraBeesAlvearyPart tile) {
			return new AlvearyLogicTransmitter();
		}
	};

	public static EnumAlvearyLogicType[] VALUES = values();

	public abstract AbstractAlvearyLogic createLogic(TileEntityExtraBeesAlvearyPart tile);

	@Nonnull
	public String getName() {
		return toString().toLowerCase();
	}

}
