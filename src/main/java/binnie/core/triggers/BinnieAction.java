// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.triggers;

import buildcraft.api.statements.IStatementContainer;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.tileentity.TileEntity;
import buildcraft.api.statements.IStatementParameter;
import net.minecraft.client.renderer.texture.IIconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import binnie.Binnie;
import buildcraft.api.statements.IStatement;
import buildcraft.api.statements.StatementManager;
import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import binnie.core.resource.BinnieIcon;
import buildcraft.api.statements.IActionExternal;

class BinnieAction implements IActionExternal
{
	private static int incrementalID;
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
		this.id = 0;
		this.id = BinnieAction.incrementalID++;
		this.tag = tag;
		StatementManager.registerStatement(this);
		this.icon = Binnie.Resource.getItemIcon(mod, iconFile);
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
	public IIcon getIcon() {
		return this.icon.getIcon();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(final IIconRegister iconRegister) {
		this.icon.registerIcon(iconRegister);
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
	}

	static {
		BinnieAction.incrementalID = 800;
	}
}
