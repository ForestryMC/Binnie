package binnie.genetics.gui;

import binnie.core.Binnie;
import binnie.core.BinnieCore;
import binnie.core.resource.BinnieSprite;
import binnie.genetics.api.analyst.IAnalystIcons;

public final class Icons implements IAnalystIcons {
	private final BinnieSprite iconNight = Binnie.RESOURCE.getItemSprite(BinnieCore.getInstance(), "gui/analyst.night");
	private final BinnieSprite iconDaytime = Binnie.RESOURCE.getItemSprite(BinnieCore.getInstance(), "gui/analyst.day");
	private final BinnieSprite iconAllDay = Binnie.RESOURCE.getItemSprite(BinnieCore.getInstance(), "gui/analyst.allday");
	private final BinnieSprite iconRain = Binnie.RESOURCE.getItemSprite(BinnieCore.getInstance(), "gui/analyst.rain");
	private final BinnieSprite iconNoRain = Binnie.RESOURCE.getItemSprite(BinnieCore.getInstance(), "gui/analyst.norain");
	private final BinnieSprite iconSky = Binnie.RESOURCE.getItemSprite(BinnieCore.getInstance(), "gui/analyst.sky");
	private final BinnieSprite iconNoSky = Binnie.RESOURCE.getItemSprite(BinnieCore.getInstance(), "gui/analyst.nosky");
	private final BinnieSprite iconFire = Binnie.RESOURCE.getItemSprite(BinnieCore.getInstance(), "gui/analyst.fire");
	private final BinnieSprite iconNoFire = Binnie.RESOURCE.getItemSprite(BinnieCore.getInstance(), "gui/analyst.nofire");
	private final BinnieSprite iconAdd = Binnie.RESOURCE.getItemSprite(BinnieCore.getInstance(), "gui/analyst.add");
	private final BinnieSprite iconArrow = Binnie.RESOURCE.getItemSprite(BinnieCore.getInstance(), "gui/analyst.arrow");
	private final BinnieSprite iconAdd0 = Binnie.RESOURCE.getItemSprite(BinnieCore.getInstance(), "gui/analyst.add.0");
	private final BinnieSprite iconArrow0 = Binnie.RESOURCE.getItemSprite(BinnieCore.getInstance(), "gui/analyst.arrow.0");
	private final BinnieSprite iconAdd1 = Binnie.RESOURCE.getItemSprite(BinnieCore.getInstance(), "gui/analyst.add.1");
	private final BinnieSprite iconArrow1 = Binnie.RESOURCE.getItemSprite(BinnieCore.getInstance(), "gui/analyst.arrow.1");

	@Override
	public BinnieSprite getIconNight() {
		return iconNight;
	}

	@Override
	public BinnieSprite getIconDaytime() {
		return iconDaytime;
	}

	@Override
	public BinnieSprite getIconAllDay() {
		return iconAllDay;
	}

	@Override
	public BinnieSprite getIconRain() {
		return iconRain;
	}

	@Override
	public BinnieSprite getIconNoRain() {
		return iconNoRain;
	}

	@Override
	public BinnieSprite getIconSky() {
		return iconSky;
	}

	@Override
	public BinnieSprite getIconNoSky() {
		return iconNoSky;
	}

	@Override
	public BinnieSprite getIconFire() {
		return iconFire;
	}

	@Override
	public BinnieSprite getIconNoFire() {
		return iconNoFire;
	}

	@Override
	public BinnieSprite getIconAdd() {
		return iconAdd;
	}

	@Override
	public BinnieSprite getIconArrow() {
		return iconArrow;
	}

	@Override
	public BinnieSprite getIconAdd0() {
		return iconAdd0;
	}

	@Override
	public BinnieSprite getIconArrow0() {
		return iconArrow0;
	}

	@Override
	public BinnieSprite getIconAdd1() {
		return iconAdd1;
	}

	@Override
	public BinnieSprite getIconArrow1() {
		return iconArrow1;
	}
}
