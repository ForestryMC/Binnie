package binnie.extratrees.wood.planks;

public enum ExtraBiomesPlank /*implements IPlankType*/ {
	/*Redwood(10185538),
	Fir(8288074),
	Acacia(12561022);

	int color;

	ExtraBiomesPlank(final int color) {
		this.color = color;
	}

	@Override
	public String getDisplayName() {
		return I18N.localise("extratrees.block.planks.ebxl." + this.toString().toLowerCase());
	}


	@Override
	public String getPlankTextureName() {
		return "";
	}

	@Override
	public String getDescription() {
		return I18N.localise("extratrees.block.planks.ebxl." + this.toString().toLowerCase() + ".desc");
	}

	@Override
	public int getColor() {
		return this.color;
	}

	@Override
	public ItemStack getStack() {
		return getStack(true);
	}

	@Override
	public ItemStack getStack(boolean fireproof) {
		try {
			final Class clss = Class.forName("extrabiomes.api.Stuff");
			Optional planks = (Optional) clss.getField("planks").get(null);
			if (planks.isPresent()) {
				final Block block = (Block) planks.get();
				return new ItemStack(block, 1, this.ordinal());
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public IIcon getIcon() {
		if (this.getStack() != null) {
			final int meta = this.getStack().getItemDamage();
			final Block block = ((ItemBlock) this.getStack().getItem()).field_150939_a;
			return block.getIcon(2, meta);
		}
		return null;
	}*/
}