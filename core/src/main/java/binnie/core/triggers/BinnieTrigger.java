package binnie.core.triggers;

import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import buildcraft.api.core.render.ISprite;
import buildcraft.api.statements.IStatement;
import buildcraft.api.statements.IStatementContainer;
import buildcraft.api.statements.IStatementParameter;
import buildcraft.api.statements.ITriggerExternal;
import buildcraft.api.statements.StatementManager;
import forestry.core.triggers.Sprite;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

final class BinnieTrigger implements ITriggerExternal {
	private static int incrementalID;
	protected static BinnieTrigger triggerNoBlankTemplate;
	protected static BinnieTrigger triggerNoTemplate;
	protected static BinnieTrigger triggerIsWorking;
	protected static BinnieTrigger triggerIsNotWorking;
	protected static BinnieTrigger triggerCanWork;
	protected static BinnieTrigger triggerCannotWork;
	protected static BinnieTrigger triggerPowerNone;
	protected static BinnieTrigger triggerPowerLow;
	protected static BinnieTrigger triggerPowerMedium;
	protected static BinnieTrigger triggerPowerHigh;
	protected static BinnieTrigger triggerPowerFull;
	protected static BinnieTrigger triggerSerumFull;
	protected static BinnieTrigger triggerSerumPure;
	protected static BinnieTrigger triggerSerumEmpty;
	protected static BinnieTrigger triggerAcclimatiserNone;
	protected static BinnieTrigger triggerAcclimatiserHot;
	protected static BinnieTrigger triggerAcclimatiserCold;
	protected static BinnieTrigger triggerAcclimatiserWet;
	protected static BinnieTrigger triggerAcclimatiserDry;

	private final String desc;
	private final String tag;
	private final String modID;
	private final String iconFile;
	@SideOnly(Side.CLIENT)
	@Nullable
	private ISprite icon;
	private int id;

	public BinnieTrigger(final String desc, final String tag, final String iconFile) {
		this(desc, tag, BinnieCore.getInstance(), iconFile);
	}

	public BinnieTrigger(final String desc, final String tag, final AbstractMod mod, final String iconFile) {
		this.id = BinnieTrigger.incrementalID++;
		this.tag = tag;
		StatementManager.registerStatement(this);
		TriggerProvider.triggers.add(this);
		this.modID = mod.getModId();
		this.iconFile = iconFile;
		this.desc = desc;
	}

	@Override
	public String getDescription() {
		return this.desc;
	}

	@Override
	public String getUniqueTag() {
		return this.tag;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ISprite getSprite() {
		if (icon == null) {
			this.icon = new Sprite(new ResourceLocation(modID, String.format("textures/items/%s.png", iconFile)));
		}
		return icon;
	}

	@Override
	public int maxParameters() {
		return 0;
	}

	@Override
	public int minParameters() {
		return 0;
	}

	@Override
	public IStatementParameter createParameter(final int index) {
		return null;
	}

	@Override
	public IStatement rotateLeft() {
		return null;
	}

	@Override
	public boolean isTriggerActive(TileEntity target, EnumFacing side, IStatementContainer source, IStatementParameter[] parameters) {
		return false;
	}

	@Override
	public IStatement[] getPossible() {
		return new IStatement[0];
	}

	static {
		BinnieTrigger.incrementalID = 800;
	}
}
