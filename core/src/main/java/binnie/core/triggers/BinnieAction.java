package binnie.core.triggers;

import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import buildcraft.api.core.render.ISprite;
import buildcraft.api.statements.IActionExternal;
import buildcraft.api.statements.IStatement;
import buildcraft.api.statements.IStatementContainer;
import buildcraft.api.statements.IStatementParameter;
import buildcraft.api.statements.StatementManager;
import forestry.core.triggers.Sprite;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

class BinnieAction implements IActionExternal {
	private static int incrementalID;

	public static BinnieAction actionPauseProcess;
	public static BinnieAction actionCancelTask;

	private final String desc;
	@Nullable
	@SideOnly(Side.CLIENT)
	private Sprite icon;
	private final String tag;
	private final String modID;
	private final String iconFile;
	private int id;

	BinnieAction(final String desc, final String tag, final String iconFile) {
		this(desc, tag, BinnieCore.getInstance(), iconFile);
	}

	private BinnieAction(final String desc, final String tag, final AbstractMod mod, final String iconFile) {
		this.id = BinnieAction.incrementalID++;
		this.tag = tag;
		StatementManager.registerStatement(this);
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
		return this;
	}

	@Override
	public void actionActivate(TileEntity target, EnumFacing side, IStatementContainer source, IStatementParameter[] parameters) {
	}

	@Override
	public IStatement[] getPossible() {
		return new IStatement[0];
	}

	static {
		BinnieAction.incrementalID = 800;
	}
}
