package binnie.extrabees.alveary;

import javax.annotation.Nonnull;

import net.minecraft.util.IStringSerializable;

public enum EnumAlvearyLogicType implements IStringSerializable {

	FRAME {
		@Override
		public AlvearyLogic createLogic(TileEntityExtraBeesAlvearyPart tile) {
			return new AlvearyLogicFrameHousing(tile);
		}
	},
	HATCHERY {
		@Override
		public AlvearyLogic createLogic(TileEntityExtraBeesAlvearyPart tile) {
			return new AlvearyLogicHatchery();
		}
	},
	LIGHTING {
		@Override
		public AlvearyLogic createLogic(TileEntityExtraBeesAlvearyPart tile) {
			return new AlvearyLogicLighting();
		}
	},
	MUTATOR {
		@Override
		public AlvearyLogic createLogic(TileEntityExtraBeesAlvearyPart tile) {
			return new AlvearyLogicMutator();
		}
	},
	RAINSHIELD {
		@Override
		public AlvearyLogic createLogic(TileEntityExtraBeesAlvearyPart tile) {
			return new AlvearyLogicRainShield();
		}
	},
	STIMULATOR {
		@Override
		public AlvearyLogic createLogic(TileEntityExtraBeesAlvearyPart tile) {
			return new AlvearyLogicStimulator();
		}
	},
	TRANSMISSION {
		@Override
		public AlvearyLogic createLogic(TileEntityExtraBeesAlvearyPart tile) {
			return new AlvearyLogicTransmitter();
		}
	};

	public static final EnumAlvearyLogicType[] VALUES = values();

	public abstract AlvearyLogic createLogic(TileEntityExtraBeesAlvearyPart tile);

	@Nonnull
	public String getName() {
		return toString().toLowerCase();
	}

}
