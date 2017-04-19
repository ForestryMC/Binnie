package binnie.core.triggers;

import binnie.Binnie;
import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import binnie.core.resource.BinnieIcon;
import buildcraft.api.statements.*;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;

class BinnieAction implements IActionExternal {
	private static int incrementalID = 800;
	public static BinnieAction actionPauseProcess;
	public static BinnieAction actionCancelTask;
	private String desc;
	private BinnieIcon icon;
	private String tag;
	private int id;

	BinnieAction(final String desc, final String tag, final String iconFile) {
		this(desc, tag, BinnieCore.instance, iconFile);
	}

	private BinnieAction(final String desc, final String tag, final AbstractMod mod, final String iconFile) {
		// TODO why? Where is used?
		id = BinnieAction.incrementalID++;
		this.tag = tag;
		StatementManager.registerStatement(this);
		icon = Binnie.Resource.getItemIcon(mod, iconFile);
		this.desc = desc;
	}

	@Override
	public String getDescription() {
		return desc;
	}

	@Override
	public String getUniqueTag() {
		return tag;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon() {
		return icon.getIcon();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(final IIconRegister iconRegister) {
		icon.registerIcon(iconRegister);
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
	public void actionActivate(final TileEntity target, final ForgeDirection side, final IStatementContainer source, final IStatementParameter[] parameters) {
		// ignored
	}
}
