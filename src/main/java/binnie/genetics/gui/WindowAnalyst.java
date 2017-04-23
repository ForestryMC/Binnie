// 
// Decompiled by Procyon v0.5.30
// 

package binnie.genetics.gui;

import forestry.api.genetics.AlleleManager;
import forestry.api.lepidopterology.IButterfly;
import binnie.botany.api.IFlower;
import forestry.api.apiculture.IBee;
import forestry.api.arboriculture.ITree;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.events.EventKey;
import binnie.core.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.core.craftgui.geometry.Position;
import binnie.core.craftgui.controls.scroll.ControlScrollBar;
import binnie.core.craftgui.minecraft.MinecraftGUI;
import binnie.core.craftgui.minecraft.InventoryType;
import binnie.core.craftgui.minecraft.control.ControlSlot;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.events.EventMouse;
import binnie.core.craftgui.Attribute;
import binnie.core.craftgui.Widget;
import forestry.api.genetics.IBreedingTracker;
import binnie.Binnie;
import binnie.genetics.machine.ModuleMachine;
import binnie.genetics.item.GeneticsItems;
import binnie.genetics.machine.Analyser;
import net.minecraft.item.ItemStack;
import binnie.core.machines.inventory.SlotValidator;
import binnie.genetics.Genetics;
import binnie.core.AbstractMod;
import java.util.ArrayList;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.inventory.IInventory;
import net.minecraft.entity.player.EntityPlayer;
import binnie.core.genetics.BreedingSystem;
import forestry.api.genetics.IIndividual;
import binnie.core.craftgui.minecraft.control.ControlSlide;
import binnie.core.craftgui.geometry.IArea;
import java.util.List;
import binnie.core.craftgui.window.Panel;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.controls.scroll.ControlScrollableContent;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.minecraft.Window;

public class WindowAnalyst extends Window
{
	IWidget baseWidget;
	ControlScrollableContent leftPage;
	ControlScrollableContent rightPage;
	Control tabBar;
	Panel analystPanel;
	List<ControlAnalystPage> analystPages;
	IArea analystPageSize;
	boolean isDatabase;
	boolean isMaster;
	boolean lockedSearch;
	private Control analystNone;
	private ControlSlide slideUpInv;
	IIndividual current;
	BreedingSystem currentSystem;

	public WindowAnalyst(final EntityPlayer player, final IInventory inventory, final Side side, final boolean database, final boolean master) {
		super(312.0f, 230.0f, player, inventory, side);
		this.baseWidget = null;
		this.tabBar = null;
		this.analystPages = new ArrayList<ControlAnalystPage>();
		this.analystPageSize = null;
		this.isDatabase = false;
		this.isMaster = false;
		this.lockedSearch = false;
		this.current = null;
		this.currentSystem = null;
		this.isDatabase = database;
		this.isMaster = master;
		this.lockedSearch = this.isDatabase;
	}

	@Override
	protected AbstractMod getMod() {
		return Genetics.instance;
	}

	@Override
	protected String getName() {
		return "Analyst";
	}

	private void setupValidators() {
		if (!this.isDatabase) {
			this.getWindowInventory().setValidator(0, new SlotValidator.Individual() {
				@Override
				public boolean isValid(final ItemStack itemStack) {
					return Analyser.isAnalysed(itemStack) || (Analyser.isAnalysable(itemStack) && WindowAnalyst.this.getWindowInventory().getStackInSlot(1) != null);
				}
			});
			this.getWindowInventory().setValidator(1, new SlotValidator.Item(GeneticsItems.DNADye.get(1), ModuleMachine.IconDye));
		}
	}

	@Override
	public void initialiseServer() {
		for (final BreedingSystem system : Binnie.Genetics.getActiveSystems()) {
			final IBreedingTracker tracker = system.getSpeciesRoot().getBreedingTracker(this.getWorld(), this.getUsername());
			if (tracker != null) {
				tracker.synchToPlayer(this.getPlayer());
			}
		}
		this.setupValidators();
	}

	@Override
	public void initialiseClient() {
		this.setTitle(this.isDatabase ? (this.isMaster ? "Master Registry" : "Registry") : "Analyst");
		final BreedingSystem system = Binnie.Genetics.beeBreedingSystem;
		final IIndividual ind = system.getDefaultIndividual();
		final ItemStack stack = system.getSpeciesRoot().getMemberStack(ind, system.getDefaultType());
		this.getWindowInventory().createSlot(0);
		this.baseWidget = new Widget(this);
		int x = 16;
		final int y = 28;
		if (this.isDatabase) {
			for (final BreedingSystem syst : Binnie.Genetics.getActiveSystems()) {
				new Control(this, x, y, 20.0f, 20.0f) {
					@Override
					public void initialise() {
						this.addAttribute(Attribute.MouseOver);
						this.addSelfEventHandler(new EventMouse.Down.Handler() {
							@Override
							public void onEvent(final EventMouse.Down event) {
								WindowAnalyst.this.setSystem(syst);
							}
						});
					}

					@Override
					public void getTooltip(final Tooltip tooltip) {
						tooltip.add(syst.getName());
					}

					@Override
					public void onRenderBackground() {
						CraftGUI.Render.colour(syst.getColour());
						final int outset = (WindowAnalyst.this.getSystem() == syst) ? 1 : 0;
						CraftGUI.Render.texture(CraftGUITexture.TabOutline, this.getArea().outset(outset));
						if (WindowAnalyst.this.getSystem() == syst) {
							CraftGUI.Render.colour(1140850688 + syst.getColour());
							CraftGUI.Render.texture(CraftGUITexture.TabSolid, this.getArea().outset(outset));
						}
						CraftGUI.Render.item(new IPoint(2.0f, 2.0f), syst.getItemStackRepresentative());
					}
				};
				x += 22;
			}
		}
		else {
			new ControlSlot(this, x, y + 1).assign(InventoryType.Window, 0);
			x += 22;
			new ControlSlot(this, x, y + 1).assign(InventoryType.Window, 1);
			x += 26;
			this.setupValidators();
		}
		this.tabBar = new Control(this, x, 28.0f, this.w() - 16.0f - x, 20.0f);
		this.analystPanel = new Panel(this, 16.0f, 54.0f, 280.0f, 164.0f, MinecraftGUI.PanelType.Outline) {
			@Override
			public void onRenderBackground() {
				CraftGUI.Render.gradientRect(this.getArea(), 1157627903, 1728053247);
				super.onRenderBackground();
			}

			@Override
			public void initialise() {
				this.setColour(4473924);
				final float sectionWidth = (this.w() - 8.0f - 4.0f) / 2.0f;
				WindowAnalyst.this.leftPage = new ControlScrollableContent<IWidget>(this, 3.0f, 3.0f, sectionWidth + 2.0f, this.h() - 8.0f + 2.0f, 0.0f) {
					@Override
					public void onRenderBackground() {
						if (this.getContent() == null) {
							return;
						}
						CraftGUI.Render.colour(this.getContent().getColour());
						CraftGUI.Render.texture(CraftGUITexture.TabOutline, this.getArea());
					}
				};
				new ControlScrollBar(this, sectionWidth + 2.0f - 3.0f, 6.0f, 3.0f, this.h() - 8.0f + 2.0f - 6.0f, WindowAnalyst.this.leftPage) {
					@Override
					public void onRenderBackground() {
						if (!this.isEnabled()) {
							return;
						}
						if (WindowAnalyst.this.leftPage.getContent() == null) {
							return;
						}
						CraftGUI.Render.gradientRect(this.getArea(), 1140850688 + WindowAnalyst.this.leftPage.getContent().getColour(), 1140850688 + WindowAnalyst.this.leftPage.getContent().getColour());
						CraftGUI.Render.solid(this.getRenderArea(), WindowAnalyst.this.leftPage.getContent().getColour());
					}
				};
				WindowAnalyst.this.rightPage = new ControlScrollableContent<IWidget>(this, 3.0f + sectionWidth + 4.0f, 3.0f, sectionWidth + 2.0f, this.h() - 8.0f + 2.0f, 0.0f) {
					@Override
					public void onRenderBackground() {
						if (this.getContent() == null) {
							return;
						}
						CraftGUI.Render.colour(this.getContent().getColour());
						CraftGUI.Render.texture(CraftGUITexture.TabOutline, this.getArea());
					}
				};
				new ControlScrollBar(this, sectionWidth + 2.0f - 3.0f + sectionWidth + 4.0f, 6.0f, 3.0f, this.h() - 8.0f + 2.0f - 6.0f, WindowAnalyst.this.rightPage) {
					@Override
					public void onRenderBackground() {
						if (!this.isEnabled()) {
							return;
						}
						if (WindowAnalyst.this.rightPage.getContent() == null) {
							return;
						}
						CraftGUI.Render.gradientRect(this.getArea(), 1140850688 + WindowAnalyst.this.rightPage.getContent().getColour(), 1140850688 + WindowAnalyst.this.rightPage.getContent().getColour());
						CraftGUI.Render.solid(this.getRenderArea(), WindowAnalyst.this.rightPage.getContent().getColour());
					}
				};
				WindowAnalyst.this.analystPageSize = new IArea(1.0f, 1.0f, sectionWidth, this.h() - 8.0f);
			}
		};
		if (!this.isDatabase) {
			this.slideUpInv = new ControlSlide(this, (this.getSize().x() - 244.0f) / 2.0f, this.getSize().y() - 80.0f + 1.0f, 244.0f, 80.0f, Position.Bottom);
			new ControlPlayerInventory(this.slideUpInv, true);
			this.slideUpInv.setSlide(false);
		}
		this.addEventHandler(new EventKey.Down.Handler() {
			@Override
			public void onEvent(final EventKey.Down event) {
				if (event.getKey() == 205) {
					WindowAnalyst.this.shiftPages(true);
				}
				if (event.getKey() == 203) {
					WindowAnalyst.this.shiftPages(false);
				}
			}
		});
		if (!this.isDatabase) {
			this.analystNone = new Control(this.analystPanel, 0.0f, 0.0f, this.analystPanel.w(), this.analystPanel.h()) {
				@Override
				public void initialise() {
					new ControlTextCentered(this, 20.0f, "Add a bee, tree, flower or butterfly to the top left slot. DNA Dye is required if it has not been analysed yet. This dye can also convert vanilla items to breedable individuals.").setColour(4473924);
					new ControlPlayerInventory(this);
				}
			};
		}
		this.setIndividual(null);
		this.setSystem(Binnie.Genetics.beeBreedingSystem);
	}

	public void setIndividual(final IIndividual ind) {
		if (!this.isDatabase) {
			if (ind == null) {
				this.analystNone.show();
				this.slideUpInv.hide();
			}
			else {
				this.analystNone.hide();
				this.slideUpInv.show();
			}
		}
		if (ind == this.current || (ind != null && this.current != null && ind.isGeneticEqual(this.current))) {
			return;
		}
		final boolean systemChange = (this.current = ind) != null && ind.getGenome().getSpeciesRoot() != this.getSystem().getSpeciesRoot();
		if (systemChange) {
			this.currentSystem = Binnie.Genetics.getSystem(ind.getGenome().getSpeciesRoot());
		}
		this.updatePages(systemChange);
	}

	public void setSystem(final BreedingSystem system) {
		if (system == this.currentSystem) {
			return;
		}
		this.currentSystem = system;
		this.current = null;
		this.updatePages(true);
	}

	public void updatePages(final boolean systemChange) {
		int oldLeft = -1;
		int oldRight = -1;
		if (!systemChange) {
			oldLeft = this.analystPages.indexOf(this.leftPage.getContent());
			oldRight = this.analystPages.indexOf(this.rightPage.getContent());
		}
		ControlAnalystPage databasePage = null;
		if (this.isDatabase && !systemChange) {
			databasePage = ((this.analystPages.size() > 0) ? this.analystPages.get(0) : null);
		}
		this.analystPages.clear();
		this.setPage(this.leftPage, null);
		this.setPage(this.rightPage, null);
		if (this.isDatabase) {
			this.analystPages.add((databasePage != null) ? databasePage : new AnalystPageDatabase(this.analystPanel, this.analystPageSize, this.currentSystem, this.isMaster));
		}
		if (this.current != null) {
			this.analystPages.add(new AnalystPageDescription(this.analystPanel, this.analystPageSize, this.current));
			this.analystPages.add(new AnalystPageGenome(this.analystPanel, this.analystPageSize, true, this.current));
			if (!this.isDatabase) {
				this.analystPages.add(new AnalystPageGenome(this.analystPanel, this.analystPageSize, false, this.current));
				this.analystPages.add(new AnalystPageKaryogram(this.analystPanel, this.analystPageSize, this.current));
			}
			if (!(this.current instanceof ITree)) {
				this.analystPages.add(new AnalystPageClimate(this.analystPanel, this.analystPageSize, this.current));
			}
			if (this.current instanceof IBee) {
				this.analystPages.add(new AnalystPageProducts(this.analystPanel, this.analystPageSize, (IBee) this.current));
			}
			else if (this.current instanceof ITree) {
				this.analystPages.add(new AnalystPageFruit(this.analystPanel, this.analystPageSize, (ITree) this.current));
				this.analystPages.add(new AnalystPageWood(this.analystPanel, this.analystPageSize, (ITree) this.current));
			}
			else if (this.current instanceof IFlower) {
				this.analystPages.add(new AnalystPageSoil(this.analystPanel, this.analystPageSize, (IFlower) this.current));
			}
			else if (this.current instanceof IButterfly) {
				this.analystPages.add(new AnalystPageSpecimen(this.analystPanel, this.analystPageSize, (IButterfly) this.current));
			}
			this.analystPages.add(new AnalystPageBiology(this.analystPanel, this.analystPageSize, this.current));
			if (this.current instanceof IBee || this.current instanceof IButterfly) {
				this.analystPages.add(new AnalystPageBehaviour(this.analystPanel, this.analystPageSize, this.current));
			}
			else if (this.current instanceof ITree) {
				this.analystPages.add(new AnalystPageGrowth(this.analystPanel, this.analystPageSize, this.current));
			}
			else if (this.current instanceof IFlower) {
				this.analystPages.add(new AnalystPageAppearance(this.analystPanel, this.analystPageSize, (IFlower) this.current));
			}
			this.analystPages.add(new AnalystPageMutations(this.analystPanel, this.analystPageSize, this.current, this.isMaster));
		}
		this.tabBar.deleteAllChildren();
		final float width = this.tabBar.w() / this.analystPages.size();
		float x = 0.0f;
		for (final ControlAnalystPage page : this.analystPages) {
			new ControlTooltip(this.tabBar, x, 0.0f, width, this.tabBar.h()) {
				ControlAnalystPage value;

				@Override
				public void getTooltip(final Tooltip tooltip) {
					tooltip.add(this.value.getTitle());
				}

				@Override
				protected void initialise() {
					super.initialise();
					this.addAttribute(Attribute.MouseOver);
					this.value = page;
					this.addSelfEventHandler(new EventMouse.Down.Handler() {
						@Override
						public void onEvent(final EventMouse.Down event) {
							final int currentIndex = WindowAnalyst.this.analystPages.indexOf(WindowAnalyst.this.rightPage.getContent());
							int clickedIndex = WindowAnalyst.this.analystPages.indexOf(value);
							if (WindowAnalyst.this.isDatabase) {
								if (clickedIndex != 0 && clickedIndex != currentIndex) {
									WindowAnalyst.this.setPage(WindowAnalyst.this.rightPage, value);
								}
							}
							else {
								if (clickedIndex < 0) {
									clickedIndex = 0;
								}
								if (clickedIndex < currentIndex) {
									++clickedIndex;
								}
								WindowAnalyst.this.setPage(WindowAnalyst.this.rightPage, null);
								WindowAnalyst.this.setPage(WindowAnalyst.this.leftPage, null);
								WindowAnalyst.this.setPage(WindowAnalyst.this.rightPage, WindowAnalyst.this.analystPages.get(clickedIndex));
								WindowAnalyst.this.setPage(WindowAnalyst.this.leftPage, WindowAnalyst.this.analystPages.get(clickedIndex - 1));
							}
						}
					});
				}

				@Override
				public void onRenderBackground() {
					final boolean active = this.value == WindowAnalyst.this.leftPage.getContent() || this.value == WindowAnalyst.this.rightPage.getContent();
					CraftGUI.Render.colour((active ? -16777216 : 1140850688) + this.value.getColour());
					CraftGUI.Render.texture(CraftGUITexture.TabSolid, this.getArea().inset(1));
					CraftGUI.Render.colour(this.value.getColour());
					CraftGUI.Render.texture(CraftGUITexture.TabOutline, this.getArea().inset(1));
					super.onRenderBackground();
				}
			};
			x += width;
		}
		if (this.analystPages.size() > 0) {
			this.setPage(this.leftPage, this.analystPages.get((oldLeft >= 0) ? oldLeft : 0));
		}
		if (this.analystPages.size() > 1) {
			this.setPage(this.rightPage, this.analystPages.get((oldRight >= 0) ? oldRight : 1));
		}
	}

	public void shiftPages(final boolean right) {
		if (this.analystPages.size() < 2) {
			return;
		}
		final int leftIndex = this.analystPages.indexOf(this.leftPage.getContent());
		final int rightIndex = this.analystPages.indexOf(this.rightPage.getContent());
		if (right && rightIndex + 1 >= this.analystPages.size()) {
			return;
		}
		if (!this.lockedSearch && !right && leftIndex <= 0) {
			return;
		}
		if (!this.lockedSearch && !right && rightIndex <= 1) {
			return;
		}
		final int newRightIndex = rightIndex + (right ? 1 : -1);
		final int newLeftIndex = this.lockedSearch ? 0 : (newRightIndex - 1);
		float oldRightPercent = 0.0f;
		float oldLeftPercent = 0.0f;
		if (newLeftIndex == rightIndex) {
			oldRightPercent = this.rightPage.getPercentageIndex();
		}
		if (newRightIndex == leftIndex) {
			oldLeftPercent = this.leftPage.getPercentageIndex();
		}
		this.setPage(this.leftPage, null);
		this.setPage(this.rightPage, null);
		this.setPage(this.leftPage, this.analystPages.get(newLeftIndex));
		this.setPage(this.rightPage, this.analystPages.get(newRightIndex));
		this.analystPages.get(newLeftIndex).show();
		if (oldRightPercent != 0.0f) {
			this.leftPage.setPercentageIndex(oldRightPercent);
		}
		if (oldLeftPercent != 0.0f) {
			this.rightPage.setPercentageIndex(oldLeftPercent);
		}
	}

	public void setPage(final ControlScrollableContent side, final ControlAnalystPage page) {
		final ControlAnalystPage existingPage = (ControlAnalystPage) side.getContent();
		if (existingPage != null) {
			existingPage.hide();
			side.setScrollableContent(null);
		}
		if (page != null) {
			page.show();
			side.setScrollableContent(page);
			side.setPercentageIndex(0.0f);
			page.setPosition(side.pos().add(1.0f, 1.0f));
		}
	}

	@Override
	public void onWindowInventoryChanged() {
		super.onWindowInventoryChanged();
		if (this.getWindowInventory().getStackInSlot(0) != null && !Analyser.isAnalysed(this.getWindowInventory().getStackInSlot(0))) {
			this.getWindowInventory().setInventorySlotContents(0, Analyser.analyse(this.getWindowInventory().getStackInSlot(0)));
			this.getWindowInventory().decrStackSize(1, 1);
		}
		final IIndividual ind = AlleleManager.alleleRegistry.getIndividual(this.getWindowInventory().getStackInSlot(0));
		if (ind != null) {
			ind.getGenome().getSpeciesRoot().getBreedingTracker(this.getWorld(), this.getUsername()).registerBirth(ind);
		}
		if (this.isClient()) {
			this.setStack(this.getWindowInventory().getStackInSlot(0));
		}
		else if (this.isServer()) {
		}
	}

	public void setStack(final ItemStack stack) {
		final IIndividual ind = AlleleManager.alleleRegistry.getIndividual(stack);
		this.setIndividual(ind);
	}

	public IIndividual getIndividual() {
		return this.current;
	}

	public BreedingSystem getSystem() {
		return this.currentSystem;
	}
}
