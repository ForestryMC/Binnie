package binnie.genetics.gui.analyst;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IBreedingTracker;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;

import binnie.core.Binnie;
import binnie.core.api.genetics.IBreedingSystem;
import binnie.core.api.gui.Alignment;
import binnie.core.api.gui.IArea;
import binnie.core.api.gui.ITitledWidget;
import binnie.core.api.gui.IWidget;
import binnie.core.genetics.ManagerGenetics;
import binnie.core.gui.Widget;
import binnie.core.gui.controls.ControlTextCentered;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.controls.scroll.ControlScrollableContent;
import binnie.core.gui.events.EventKey;
import binnie.core.gui.minecraft.InventoryType;
import binnie.core.gui.minecraft.Window;
import binnie.core.gui.minecraft.WindowInventory;
import binnie.core.gui.minecraft.control.ControlPlayerInventory;
import binnie.core.gui.minecraft.control.ControlSlide;
import binnie.core.gui.minecraft.control.ControlSlot;
import binnie.core.gui.window.Panel;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.util.I18N;
import binnie.core.util.Log;
import binnie.genetics.Genetics;
import binnie.genetics.api.analyst.IAnalystPagePlugin;
import binnie.genetics.core.GeneticsGUI;
import binnie.genetics.item.GeneticsItems;
import binnie.genetics.modules.ModuleMachine;

public class WindowAnalyst extends Window {
	@Nullable
	private IWidget baseWidget;
	private ControlScrollableContent leftPage;
	private ControlScrollableContent rightPage;
	@Nullable
	private Control tabBar;
	private Panel analystPanel;
	private final List<ITitledWidget> analystPages;
	@Nullable
	private IArea analystPageSize;
	private final boolean isDatabase;
	private final boolean isMaster;
	private final boolean lockedSearch;
	@Nullable
	private IIndividual current;
	@Nullable
	private IBreedingSystem currentSystem;
	private Control analystNone;
	private ControlSlide slideUpInv;

	public WindowAnalyst(EntityPlayer player, @Nullable IInventory inventory, Side side, boolean database, boolean master) {
		super(312, 230, player, inventory, side);
		analystPages = new ArrayList<>();
		isDatabase = database;
		isMaster = master;
		lockedSearch = isDatabase;
	}

	public static GeneticsGUI.WindowFactory create(boolean database, boolean master) {
		return (player, inventory, side) -> new WindowAnalyst(player, inventory, side, database, master);
	}

	@Override
	protected String getModId() {
		return Genetics.instance.getModId();
	}

	@Override
	protected String getBackgroundTextureName() {
		return "Analyst";
	}

	private void setupValidators() {
		if (!isDatabase) {
			getWindowInventory().setValidator(0, new AnalystSlotValidator(this));
			getWindowInventory().setValidator(1, new SlotValidator.Item(GeneticsItems.DNADye.stack(1), ModuleMachine.getSpriteDye()));
		}
	}

	@Override
	public void initialiseServer() {
		for (IBreedingSystem system : Binnie.GENETICS.getActiveSystems()) {
			ISpeciesRoot root = system.getSpeciesRoot();
			if (root != null) {
				IBreedingTracker tracker = root.getBreedingTracker(getWorld(), getUsername());
				if (tracker != null) {
					tracker.synchToPlayer(getPlayer());
				}
			}
		}
		//create slots
		getWindowInventory().createSlot(0);
		getWindowInventory().createSlot(1);
		setupValidators();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void initialiseClient() {
		setTitle(isDatabase ? (isMaster ? I18N.localise("genetics.gui.registry.master.title") : I18N.localise("genetics.gui.registry.title")) : I18N.localise("genetics.gui.analyst.title"));
		getWindowInventory().createSlot(0);
		baseWidget = new Widget(this);
		int x = 16;
		int y = 28;
		Collection<IBreedingSystem> activeSystems = Binnie.GENETICS.getActiveSystems();
		if (isDatabase) {
			for (IBreedingSystem syst : activeSystems) {
				new ControlSystemButton(x, y, this, syst);
				x += 22;
			}
		} else {
			new ControlSlot.Builder(this, x, y + 1).assign(InventoryType.WINDOW, 0);
			x += 22;
			new ControlSlot.Builder(this, x, y + 1).assign(InventoryType.WINDOW, 1);
			x += 26;
			setupValidators();
		}
		tabBar = new Control(this, x, 28, getWidth() - 16 - x, 20);
		analystPanel = new AnalystPanel(this);
		if (!isDatabase) {
			slideUpInv = new ControlSlide(this, (getSize().xPos() - 244) / 2, getSize().yPos() - 80 + 1, 244, 80, Alignment.BOTTOM);
			new ControlPlayerInventory(slideUpInv, true);
			slideUpInv.setSlide(false);
		}
		addEventHandler(EventKey.Down.class, event -> {
			if (event.getKey() == 205) {
				shiftPages(true);
			}
			if (event.getKey() == 203) {
				shiftPages(false);
			}
		});
		if (!isDatabase) {
			analystNone = new AnalystNoneControl(this);
		}
		setIndividual(null);

		IBreedingSystem first = Binnie.GENETICS.getFirstActiveSystem();
		setSystem(first);
	}

	@SideOnly(Side.CLIENT)
	public void updatePages(boolean systemChange) {
		int oldLeft = -1;
		int oldRight = -1;
		if (!systemChange) {
			oldLeft = analystPages.indexOf(leftPage.getContent());
			oldRight = analystPages.indexOf(rightPage.getContent());
		}
		ITitledWidget databasePage = null;
		if (isDatabase && !systemChange) {
			databasePage = ((analystPages.size() > 0) ? analystPages.get(0) : null);
		}
		analystPages.clear();
		setPage(leftPage, null);
		setPage(rightPage, null);
		createPages(databasePage);
		tabBar.deleteAllChildren();
		if (analystPages.size() > 0) {
			int width = tabBar.getWidth() / analystPages.size();
			int x = 0;
			for (ITitledWidget page : analystPages) {
				new ControlAnalystButton(tabBar, x, 0, width, tabBar.getHeight(), this, page);
				x += width;
			}
			setPage(leftPage, analystPages.get((oldLeft >= 0) ? oldLeft : 0));
			if (analystPages.size() > 1) {
				setPage(rightPage, analystPages.get((oldRight >= 0) ? oldRight : 1));
			}
		}
	}

	@SideOnly(Side.CLIENT)
	private void createPages(@Nullable ITitledWidget databasePage) {
		if (isDatabase) {
			analystPages.add((databasePage != null) ? databasePage : new AnalystPageDatabase(analystPanel, analystPageSize, currentSystem, isMaster));
		}
		if (current != null) {
			analystPages.add(new AnalystPageDescription(analystPanel, analystPageSize, current));
			analystPages.add(new AnalystPageGenome(analystPanel, analystPageSize, true, current));
			if (!isDatabase) {
				analystPages.add(new AnalystPageGenome(analystPanel, analystPageSize, false, current));
				analystPages.add(new AnalystPageKaryogram(analystPanel, analystPageSize, current));
			}

			createPages(current, analystPanel, analystPageSize, analystPages);

			analystPages.add(new AnalystPageMutations(analystPanel, analystPageSize, current, isMaster));
		}
		for (ITitledWidget page : analystPages) {
			page.hide();
		}
	}

	@SideOnly(Side.CLIENT)
	private static <T extends IIndividual> void createPages(T individual, IWidget parent, IArea pageSize, List<ITitledWidget> analystPages) {
		IAnalystPagePlugin<T> analystPageFactory = Genetics.getAnalystManager().getAnalystPagePlugin(individual);
		if (analystPageFactory != null) {
			analystPageFactory.addAnalystPages(individual, parent, pageSize, analystPages, Genetics.getAnalystManager());
		} else {
			Log.error("Could not find IAnalystPagePlugin for {}", individual.getClass());
		}
	}

	public void shiftPages(boolean right) {
		if (analystPages.size() < 2) {
			return;
		}
		int leftIndex = analystPages.indexOf(leftPage.getContent());
		int rightIndex = analystPages.indexOf(rightPage.getContent());
		if (right && rightIndex + 1 >= analystPages.size()) {
			return;
		}
		if (!lockedSearch && !right && leftIndex <= 0) {
			return;
		}
		if (!lockedSearch && !right && rightIndex <= 1) {
			return;
		}
		int newRightIndex = rightIndex + (right ? 1 : -1);
		int newLeftIndex = lockedSearch ? 0 : (newRightIndex - 1);
		float oldRightPercent = 0;
		float oldLeftPercent = 0;
		if (newLeftIndex == rightIndex) {
			oldRightPercent = rightPage.getPercentageIndex();
		}
		if (newRightIndex == leftIndex) {
			oldLeftPercent = leftPage.getPercentageIndex();
		}
		setPage(leftPage, null);
		setPage(rightPage, null);
		setPage(leftPage, analystPages.get(newLeftIndex));
		setPage(rightPage, analystPages.get(newRightIndex));
		analystPages.get(newLeftIndex).show();
		if (oldRightPercent != 0) {
			leftPage.setPercentageIndex(oldRightPercent);
		}
		if (oldLeftPercent != 0) {
			rightPage.setPercentageIndex(oldLeftPercent);
		}
	}

	public void setPage(ControlScrollableContent side, @Nullable IWidget page) {
		Control existingPage = (Control) side.getContent();
		if (existingPage != null) {
			existingPage.hide();
			side.setScrollableContent(null);
		}
		if (page != null) {
			page.show();
			side.setScrollableContent(page);
			side.setPercentageIndex(0);
			page.setPosition(side.getPosition().add(1, 1));
		}
	}

	@Override
	public void onWindowInventoryChanged() {
		super.onWindowInventoryChanged();
		WindowInventory inv = getWindowInventory();
		ItemStack stack = inv.getStackInSlot(0);
		if (!stack.isEmpty() && !ManagerGenetics.isAnalysed(stack)) {
			inv.setInventorySlotContents(0, ManagerGenetics.analyse(stack, getWorld(), getUsername()));
			inv.decrStackSize(1, 1);
		}

		if (isClient()) {
			//noinspection MethodCallSideOnly
			setStack(stack);
		}
	}

	@SideOnly(Side.CLIENT)
	public void setStack(ItemStack stack) {
		IIndividual ind = AlleleManager.alleleRegistry.getIndividual(stack);
		setIndividual(ind);
	}

	public IIndividual getIndividual() {
		return current;
	}

	@SideOnly(Side.CLIENT)
	public void setIndividual(@Nullable IIndividual ind) {
		if (!isDatabase) {
			if (ind == null) {
				analystNone.show();
				slideUpInv.hide();
			} else {
				analystNone.hide();
				slideUpInv.show();
			}
		}
		if (ind != current && (ind == null || current == null || !ind.isGeneticEqual(current))) {
			current = ind;
			boolean systemChange = current != null && ind.getGenome().getSpeciesRoot() != getSystem().getSpeciesRoot();
			if (systemChange) {
				currentSystem = Binnie.GENETICS.getSystem(ind.getGenome().getSpeciesRoot());
			}
			updatePages(systemChange);
		}
	}

	public IBreedingSystem getSystem() {
		return currentSystem;
	}

	@SideOnly(Side.CLIENT)
	public void setSystem(IBreedingSystem system) {
		if (system == currentSystem) {
			return;
		}
		currentSystem = system;
		current = null;
		updatePages(true);
	}

	public ControlScrollableContent getLeftPage() {
		return leftPage;
	}

	public void setLeftPage(ControlScrollableContent leftPage) {
		this.leftPage = leftPage;
	}

	public ControlScrollableContent getRightPage() {
		return rightPage;
	}

	public void setRightPage(ControlScrollableContent rightPage) {
		this.rightPage = rightPage;
	}

	public void setAnalystPageSize(IArea analystPageSize) {
		this.analystPageSize = analystPageSize;
	}

	public List<ITitledWidget> getAnalystPages() {
		return analystPages;
	}

	public boolean isDatabase() {
		return isDatabase;
	}

	private static class AnalystSlotValidator extends SlotValidator.Individual {
		private final WindowAnalyst windowAnalyst;

		public AnalystSlotValidator(WindowAnalyst windowAnalyst) {
			this.windowAnalyst = windowAnalyst;
		}

		@Override
		public boolean isValid(ItemStack itemStack) {
			if (ManagerGenetics.isAnalysed(itemStack)) {
				return true;
			}
			if (ManagerGenetics.isAnalysable(itemStack)) {
				WindowInventory windowInventory = windowAnalyst.getWindowInventory();
				return !windowInventory.getStackInSlot(1).isEmpty();
			}
			return false;
		}
	}

	@SideOnly(Side.CLIENT)
	private static class AnalystNoneControl extends Control {
		public AnalystNoneControl(WindowAnalyst windowAnalyst) {
			super(windowAnalyst.analystPanel, 0, 0, windowAnalyst.analystPanel.getWidth(), windowAnalyst.analystPanel.getHeight());

			new ControlTextCentered(this, 20, I18N.localise("genetics.gui.analyst.desc")).setColor(4473924);
			new ControlPlayerInventory(this);
		}
	}
}
