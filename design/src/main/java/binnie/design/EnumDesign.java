package binnie.design;

import java.util.ArrayList;
import java.util.List;

import binnie.core.Constants;
import binnie.core.util.I18N;
import binnie.design.api.IDesign;
import binnie.design.api.IDesignCategory;
import binnie.design.api.ILayout;
import net.minecraft.util.ResourceLocation;

public enum EnumDesign implements IDesign {
	Blank("blank"),
	Octagon("octagon", EnumPattern.Octagon.layout()),
	Diamond("diamond", EnumPattern.Diamond.layout()),
	Ringed("ringed", EnumPattern.Ringed.layout()),
	Squared("squared", EnumPattern.Squared.layout()),
	Multiply("multiply", EnumPattern.Multiply.layout()),
	Halved("halved"),
	Striped("striped"),
	ThinStriped("thin.striped"),
	Chequered("full.chequered", EnumPattern.Chequered.layout()),
	Tiled("full.tiled", EnumPattern.Tiled.layout()),
	ChequeredB("chequered", EnumPattern.Chequered.layout()),
	TiledB("tiled", EnumPattern.Tiled.layout()),
	VeryThinCorner("very.thin.cornered"),
	ThinCorner("thin.cornered"),
	Corner("cornered"),
	ThickCorner("thick.cornered"),
	Edged("edged", EnumPattern.Edged.layout()),
	ThinEdged("thin.edged", EnumPattern.ThinEdged.layout()),
	ThinBarred("thin.barred", EnumPattern.ThinBarred.layout()),
	Barred("barred", EnumPattern.Barred.layout()),
	ThickBarred("thick.barred", EnumPattern.ThickBarred.layout()),
	Diagonal("diagonal", EnumPattern.Edged.layout()),
	ThickDiagonal("thick.diagonal", EnumPattern.Halved.layout()),
	ThinSaltire("thin.saltire"),
	Saltire("saltire"),
	ThickSaltire("thick.saltire"),
	ThinCrossed("thin.crossed"),
	Crossed("crossed"),
	ThickCrossed("thick.crossed"),
	ThinTSection("thin.t.section"),
	TSection("t.section"),
	ThickTSection("thick.t.section"),
	ThinBarredCorner("thin.barred.corner"),
	BarredCorner("barred.corner"),
	ThickBarredCorner("thick.barred.corner"),
	ThinStripedCorner("thin.striped.corner"),
	StripedCorner("striped.corner"),
	Emblem1("emblem.1", EnumPattern.Emblem1.layout()),
	Emblem2("emblem.2", EnumPattern.Emblem2.layout()),
	Emblem3("emblem.3", EnumPattern.Emblem3.layout()),
	Emblem4("emblem.4", EnumPattern.Emblem4.layout()),
	Emblem5("emblem.5", EnumPattern.Emblem5.layout()),
	LetterA("letter.a", true),
	LetterB("letter.b", true),
	LetterC("letter.c"),
	LetterD("letter.d"),
	LetterE("letter.e"),
	LetterF("letter.f", true),
	LetterG("letter.g"),
	LetterH("letter.h"),
	LetterI("letter.i"),
	LetterJ("letter.j"),
	LetterK("letter.k"),
	LetterL("letter.l"),
	LetterM("letter.m"),
	LetterN("letter.n"),
	LetterO("letter.o"),
	LetterP("letter.p"),
	LetterQ("letter.q"),
	LetterR("letter.r"),
	LetterS("letter.s", true),
	LetterT("letter.t", true),
	LetterU("letter.u"),
	LetterV("letter.v"),
	LetterW("letter.w"),
	LetterX("letter.x"),
	LetterY("letter.y"),
	LetterZ("letter.z"),
	ThinCurvedCrossed("thin.curved.crossed"),
	ThinCurvedBarredCorner("thin.curved.barred.corner"),
	CurvedBarredCorner("curved.barred.corner"),
	ThinCurvedCorner("thin.curved.corner"),
	CurvedCorner("curved.corner"),
	ThinCurvedTSection("thin.curved.t.section"),
	CurvedTSection("curved.t.section"),
	BarredEnd("barred.end", true),
	DiagonalCorner("diagonal.corner", true),
	DiagonalTSection("diagonal.t.section"),
	DiagonalCurvedCorner("diagonal.curved.corner", true),
	DiagonalCurvedTSection("diagonal.curved.t.section"),
	OrnateBarred("ornate.barred", EnumPattern.OrnateBarred.layout()),
	SplitBarred("split.barred", EnumPattern.SplitBarred.layout()),
	SplitBarredCorner("split.barred.corner"),
	SplitBarredTSection("split.barred.t.section"),
	SplitCrossed("split.crossed"),
	SplitBarredEnd("split.barred.end", true),
	OrnateThinBarred("ornate.thin.barred", EnumPattern.OrnateThinBarred.layout()),
	Circle("circle", EnumPattern.Circle.layout()),
	Plus("plus", EnumPattern.Plus.layout()),
	Creeper("creeper", true),
	OrnateStripedCorner("ornate.striped.corner"),
	Test("testing.block"),
	DiagonalHalved("diagonal.halved"),
	Diagonal1Edged("cornered.diagonal"),
	Diagonal2Edged("opposite.cornered.diagonal"),
	ThickDiagonal1Edged("thick.cornered.diagonal"),
	ThinBarredEnd("thin.barred.end", true),
	ThickBarredEnd("thick.barred.end", true),
	OverlappedBarred("overlapped.barred", EnumPattern.Barred.layout()),
	OverlappedSplitBarred("overlapped.split.barred", EnumPattern.SplitBarred.layout());

	static {
		Category.DESIGN.addDesign(EnumDesign.Blank);
		Category.DESIGN.addDesign(EnumDesign.Octagon);
		Category.DESIGN.addDesign(EnumDesign.Diamond);
		Category.DESIGN.addDesign(EnumDesign.Ringed);
		Category.DESIGN.addDesign(EnumDesign.Squared);
		Category.DESIGN.addDesign(EnumDesign.Multiply);
		Category.DESIGN.addDesign(EnumDesign.Plus);
		Category.DESIGN.addDesign(EnumDesign.Circle);
		Category.DESIGN.addDesign(EnumDesign.Emblem1);
		Category.DESIGN.addDesign(EnumDesign.Emblem2);
		Category.DESIGN.addDesign(EnumDesign.Emblem3);
		Category.DESIGN.addDesign(EnumDesign.Emblem4);
		Category.DESIGN.addDesign(EnumDesign.Emblem5);
		Category.DESIGN.addDesign(EnumDesign.Creeper);
		Category.STRIPES.addDesign(EnumDesign.Chequered);
		Category.STRIPES.addDesign(EnumDesign.ChequeredB);
		Category.STRIPES.addDesign(EnumDesign.Tiled);
		Category.STRIPES.addDesign(EnumDesign.TiledB);
		Category.STRIPES.addDesign(EnumDesign.Striped);
		Category.STRIPES.addDesign(EnumDesign.ThinStriped);
		Category.STRIPES.addDesign(EnumDesign.ThinStripedCorner);
		Category.STRIPES.addDesign(EnumDesign.StripedCorner);
		Category.STRIPES.addDesign(EnumDesign.OrnateStripedCorner);
		Category.EDGES.addDesign(EnumDesign.Halved);
		Category.EDGES.addDesign(EnumDesign.Corner);
		Category.EDGES.addDesign(EnumDesign.ThickCorner);
		Category.EDGES.addDesign(EnumDesign.Edged);
		Category.EDGES.addDesign(EnumDesign.ThinCorner);
		Category.EDGES.addDesign(EnumDesign.ThinEdged);
		Category.EDGES.addDesign(EnumDesign.VeryThinCorner);
		Category.EDGES.addDesign(EnumDesign.ThinCurvedCorner);
		Category.EDGES.addDesign(EnumDesign.CurvedCorner);
		Category.BARRED.addDesign(EnumDesign.ThinBarred);
		Category.BARRED.addDesign(EnumDesign.ThinBarredCorner);
		Category.BARRED.addDesign(EnumDesign.ThinTSection);
		Category.BARRED.addDesign(EnumDesign.ThinCrossed);
		Category.BARRED.addDesign(EnumDesign.ThinBarredEnd);
		Category.BARRED.addDesign(EnumDesign.OrnateThinBarred);
		Category.BARRED.addDesign(EnumDesign.Barred);
		Category.BARRED.addDesign(EnumDesign.BarredCorner);
		Category.BARRED.addDesign(EnumDesign.TSection);
		Category.BARRED.addDesign(EnumDesign.Crossed);
		Category.BARRED.addDesign(EnumDesign.BarredEnd);
		Category.BARRED.addDesign(EnumDesign.OverlappedBarred);
		Category.BARRED.addDesign(EnumDesign.OrnateBarred);
		Category.BARRED.addDesign(EnumDesign.ThickBarred);
		Category.BARRED.addDesign(EnumDesign.ThickBarredCorner);
		Category.BARRED.addDesign(EnumDesign.ThickTSection);
		Category.BARRED.addDesign(EnumDesign.ThickCrossed);
		Category.BARRED.addDesign(EnumDesign.ThickBarredEnd);
		Category.BARRED.addDesign(EnumDesign.ThinCurvedBarredCorner);
		Category.BARRED.addDesign(EnumDesign.CurvedBarredCorner);
		Category.BARRED.addDesign(EnumDesign.ThinCurvedTSection);
		Category.BARRED.addDesign(EnumDesign.CurvedTSection);
		Category.BARRED.addDesign(EnumDesign.ThinCurvedCrossed);
		Category.BARRED.addDesign(EnumDesign.SplitBarred);
		Category.BARRED.addDesign(EnumDesign.SplitBarredCorner);
		Category.BARRED.addDesign(EnumDesign.SplitBarredTSection);
		Category.BARRED.addDesign(EnumDesign.SplitCrossed);
		Category.BARRED.addDesign(EnumDesign.SplitBarredEnd);
		Category.BARRED.addDesign(EnumDesign.OverlappedSplitBarred);
		Category.DIAGONAL.addDesign(EnumDesign.ThinSaltire);
		Category.DIAGONAL.addDesign(EnumDesign.Diagonal);
		Category.DIAGONAL.addDesign(EnumDesign.DiagonalCorner);
		Category.DIAGONAL.addDesign(EnumDesign.DiagonalTSection);
		Category.DIAGONAL.addDesign(EnumDesign.DiagonalCurvedCorner);
		Category.DIAGONAL.addDesign(EnumDesign.DiagonalCurvedTSection);
		Category.DIAGONAL.addDesign(EnumDesign.Saltire);
		Category.DIAGONAL.addDesign(EnumDesign.ThickDiagonal);
		Category.DIAGONAL.addDesign(EnumDesign.ThickSaltire);
		Category.DIAGONAL.addDesign(EnumDesign.DiagonalHalved);
		Category.DIAGONAL.addDesign(EnumDesign.Diagonal1Edged);
		Category.DIAGONAL.addDesign(EnumDesign.Diagonal2Edged);
		Category.DIAGONAL.addDesign(EnumDesign.ThickDiagonal1Edged);
		EnumDesign.ThinStriped.setupStriped(EnumPattern.ThinStriped.layout());
		EnumDesign.Striped.setupStriped(EnumPattern.Striped.layout());
		EnumDesign.Halved.setupStriped(EnumPattern.Halved.layout());
		EnumDesign.Chequered.setChequered(EnumPattern.Chequered.layout());
		EnumDesign.Tiled.setChequered(EnumPattern.Tiled.layout());
		EnumDesign.ChequeredB.setStripedChequered(EnumPattern.Halved.layout());
		EnumDesign.TiledB.setStripedChequered(EnumPattern.Striped.layout());
		EnumDesign.VeryThinCorner.setCornered(EnumPattern.VeryThinCorner.layout(), EnumPattern.ThinEdged.layout());
		EnumDesign.ThinCorner.setCornered(EnumPattern.ThinCorner.layout(), EnumPattern.Edged.layout());
		EnumDesign.Corner.setCornered(EnumPattern.Corner.layout(), EnumPattern.Halved.layout());
		EnumDesign.ThickCorner.setCornered(EnumPattern.ThickCorner.layout(), EnumPattern.Edged.layout(true).flipHorizontal());
		EnumDesign.ThinCurvedCorner.setCornered(EnumPattern.ThinCurvedCorner.layout(), EnumPattern.Edged.layout());
		EnumDesign.CurvedCorner.setCornered(EnumPattern.CurvedCorner.layout(), EnumPattern.Halved.layout());
		EnumDesign.Edged.setEdged();
		EnumDesign.ThinEdged.setEdged();
		EnumDesign.ThinBarred.setBarred();
		EnumDesign.Barred.setBarred();
		EnumDesign.ThickBarred.setBarred();
		EnumDesign.Diagonal.setDiagonal(EnumPattern.Diagonal.layout());
		EnumDesign.ThickDiagonal.setDiagonal(EnumPattern.ThickDiagonal.layout());
		EnumDesign.ThinSaltire.setSaltire(EnumPattern.ThinSaltire.layout(), EnumPattern.ThickBarred.layout());
		EnumDesign.Saltire.setSaltire(EnumPattern.Saltire.layout(), EnumPattern.Barred.layout());
		EnumDesign.ThickSaltire.setSaltire(EnumPattern.ThickSaltire.layout(), EnumPattern.ThinBarred.layout());
		EnumDesign.ThinCrossed.setCross(EnumPattern.ThinCrossed.layout(), EnumPattern.ThinBarred.layout());
		EnumDesign.Crossed.setCross(EnumPattern.Crossed.layout(), EnumPattern.Barred.layout());
		EnumDesign.ThickCrossed.setCross(EnumPattern.ThickCrossed.layout(), EnumPattern.ThickBarred.layout());
		EnumDesign.ThinCurvedCrossed.setCross(EnumPattern.ThinCurvedCrossed.layout(), EnumPattern.ThinBarred.layout());
		EnumDesign.ThinTSection.setTSection(EnumPattern.ThinTSection.layout(), EnumPattern.ThinBarred.layout());
		EnumDesign.TSection.setTSection(EnumPattern.TSection.layout(), EnumPattern.Barred.layout());
		EnumDesign.ThickTSection.setTSection(EnumPattern.ThickTSection.layout(), EnumPattern.ThickBarred.layout());
		EnumDesign.ThinCurvedTSection.setTSection(EnumPattern.ThinCurvedTSection.layout(), EnumPattern.ThinBarred.layout());
		EnumDesign.CurvedTSection.setTSection(EnumPattern.CurvedTSection.layout(), EnumPattern.Barred.layout());
		EnumDesign.ThinBarredCorner.setBarredCorner(EnumPattern.ThinBarredCorner.layout(), EnumPattern.ThinBarred.layout());
		EnumDesign.BarredCorner.setBarredCorner(EnumPattern.BarredCorner.layout(), EnumPattern.Barred.layout());
		EnumDesign.ThickBarredCorner.setBarredCorner(EnumPattern.ThickBarredCorner.layout(), EnumPattern.ThickBarred.layout());
		EnumDesign.ThinCurvedBarredCorner.setBarredCorner(EnumPattern.ThinCurvedBarredCorner.layout(), EnumPattern.ThinBarred.layout());
		EnumDesign.CurvedBarredCorner.setBarredCorner(EnumPattern.BarredCurvedCorner.layout(), EnumPattern.Barred.layout());
		EnumDesign.ThinStripedCorner.setStripedCorner(EnumPattern.ThinStripedCorner.layout(), EnumPattern.ThinStriped.layout());
		EnumDesign.StripedCorner.setStripedCorner(EnumPattern.StripedCorner.layout(), EnumPattern.Striped.layout());
		EnumDesign.OrnateStripedCorner.setStripedCorner(EnumPattern.OrnateStripedCorner.layout(), EnumPattern.ThinStriped.layout());
		EnumDesign.LetterA.setLetterPattern(EnumPattern.LetterA.layout());
		EnumDesign.LetterB.setLetterPattern(EnumPattern.LetterB.layout());
		EnumDesign.LetterF.setLetterPattern(EnumPattern.LetterF.layout());
		EnumDesign.LetterS.setLetterPattern(EnumPattern.LetterS.layout());
		EnumDesign.LetterT.setLetterPattern(EnumPattern.LetterT.layout());
		EnumDesign.BarredEnd.setBarredEndPattern(EnumPattern.BarredEnd.layout(), EnumPattern.Barred.layout());
		EnumDesign.DiagonalCorner.setDiagonalCorner(EnumPattern.DiagonalCorner.layout(), EnumPattern.Barred.layout(), EnumPattern.Edged.layout());
		EnumDesign.DiagonalTSection.setDiagonalTSection(EnumPattern.DiagonalTSection.layout(), EnumPattern.Barred.layout(), EnumPattern.Edged.layout());
		EnumDesign.DiagonalCurvedCorner.setDiagonalCorner(EnumPattern.DiagonalCurvedCorner.layout(), EnumPattern.Barred.layout(), EnumPattern.Edged.layout());
		EnumDesign.DiagonalCurvedTSection.setDiagonalTSection(EnumPattern.DiagonalCurvedTSection.layout(), EnumPattern.Barred.layout(), EnumPattern.Edged.layout());
		EnumDesign.OrnateBarred.setBarred();
		EnumDesign.OrnateThinBarred.setBarred();
		EnumDesign.SplitBarred.setBarred();
		EnumDesign.SplitBarredCorner.setBarredCorner(EnumPattern.SplitBarredCorner.layout(), EnumPattern.SplitBarred.layout());
		EnumDesign.SplitBarredTSection.setTSection(EnumPattern.SplitBarredTSection.layout(), EnumPattern.SplitBarred.layout());
		EnumDesign.SplitCrossed.setCross(EnumPattern.SplitCrossed.layout(), EnumPattern.SplitBarred.layout());
		EnumDesign.SplitBarredEnd.setBarredEndPattern(EnumPattern.SplitBarredEnd.layout(), EnumPattern.SplitBarred.layout());
		EnumDesign.Creeper.setTopAndBottomPattern(EnumPattern.Creeper.layout());
		EnumDesign.DiagonalHalved.setTopAndBottomPattern(EnumPattern.DiagonalHalved.layout());
		EnumDesign.DiagonalHalved.setEdgePatterns(EnumPattern.Blank.layout(), EnumPattern.Blank.layout(true), EnumPattern.Blank.layout(true), EnumPattern.Blank.layout());
		EnumDesign.Diagonal1Edged.setTopAndBottomPattern(EnumPattern.Diagonal1Edged.layout());
		EnumDesign.Diagonal1Edged.setEdgePatterns(EnumPattern.Edged.layout().flipHorizontal(), EnumPattern.Blank.layout(true), EnumPattern.Blank.layout(true), EnumPattern.Edged.layout());
		EnumDesign.Diagonal2Edged.setTopAndBottomPattern(EnumPattern.Diagonal2Edged.layout());
		EnumDesign.Diagonal2Edged.setEdgePatterns(EnumPattern.Edged.layout(), EnumPattern.Edged.layout().flipHorizontal(), EnumPattern.Edged.layout(), EnumPattern.Edged.layout().flipHorizontal());
		EnumDesign.ThickDiagonal1Edged.setTopAndBottomPattern(EnumPattern.ThickDiagonal1Edged.layout());
		EnumDesign.ThickDiagonal1Edged.setEdgePatterns(EnumPattern.Halved.layout().flipHorizontal(), EnumPattern.Blank.layout(true), EnumPattern.Blank.layout(true), EnumPattern.Halved.layout());
		EnumDesign.ThickBarredEnd.setBarredEndPattern(EnumPattern.ThickBarredEnd.layout(), EnumPattern.ThickBarred.layout());
		EnumDesign.ThinBarredEnd.setBarredEndPattern(EnumPattern.ThinBarredEnd.layout(), EnumPattern.ThinBarred.layout());
		EnumDesign.OverlappedSplitBarred.setTopAndBottomPattern(EnumPattern.OverlappedSplitBarred.layout());
		EnumDesign.OverlappedBarred.setTopAndBottomPattern(EnumPattern.OverlappedBarred.layout());
	}

	private String name;
	private ILayout topPattern;
	private ILayout bottomPattern;
	private ILayout northPattern;
	private ILayout southPattern;
	private ILayout eastPattern;
	private ILayout westPattern;

	EnumDesign(final String name) {
		this(name, false);
	}

	EnumDesign(final String name, final boolean inverted) {
		this(name, Layout.get(EnumPattern.Blank, inverted));
	}

	EnumDesign(final String name, final ILayout fillLayout) {
		this.topPattern = fillLayout;
		this.bottomPattern = fillLayout;
		this.northPattern = fillLayout;
		this.eastPattern = fillLayout;
		this.southPattern = fillLayout;
		this.westPattern = fillLayout;
		this.name = name;
	}

	void setEdgePatterns(final ILayout north, final ILayout east, final ILayout south, final ILayout west) {
		this.setNorthPattern(north);
		this.setEastPattern(east);
		this.setSouthPattern(south);
		this.setWestPattern(west);
	}

	void setEdgePatterns(final ILayout face) {
		this.setEdgePatterns(face, face, face, face);
	}

	void setupStriped(final ILayout vert) {
		this.setTopAndBottomPattern(vert);
		this.setEdgePatterns(vert.invert(), EnumPattern.Blank.layout(true), vert, EnumPattern.Blank.layout());
	}

	void setChequered(final ILayout cheq) {
		this.setNorthPattern(cheq.invert());
		this.setSouthPattern(cheq.invert());
		this.setBottomPattern(cheq.invert());
	}

	void setStripedChequered(final ILayout stripe) {
		this.setNorthPattern(stripe.invert());
		this.setSouthPattern(stripe.invert());
		this.setEastPattern(stripe);
		this.setWestPattern(stripe);
	}

	void setCornered(final ILayout corner, final ILayout edge) {
		this.setTopAndBottomPattern(corner);
		this.setNorthPattern(EnumPattern.Blank.layout());
		this.setEastPattern(edge.flipHorizontal());
		this.setSouthPattern(edge);
		this.setWestPattern(EnumPattern.Blank.layout());
	}

	void setEdged() {
		this.setWestPattern(EnumPattern.Blank.layout());
		this.setEastPattern(EnumPattern.Blank.layout(true));
		this.northPattern = this.northPattern.flipHorizontal();
	}

	void setBarred() {
		this.setEastPattern(EnumPattern.Blank.layout(true));
		this.setWestPattern(EnumPattern.Blank.layout(true));
	}

	void setDiagonal(final ILayout diagonal) {
		this.setTopAndBottomPattern(diagonal);
		this.northPattern = this.northPattern.flipHorizontal();
		this.southPattern = this.southPattern.flipHorizontal();
	}

	void setSaltire(final ILayout saltire, final ILayout bar) {
		this.setTopAndBottomPattern(saltire);
		this.setEdgePatterns(bar.invert());
	}

	void setCross(final ILayout saltire, final ILayout bar) {
		this.setTopAndBottomPattern(saltire);
		this.setEdgePatterns(bar);
	}

	void setTSection(final ILayout tsection, final ILayout bar) {
		this.setTopAndBottomPattern(tsection);
		this.setEdgePatterns(bar, bar, bar, EnumPattern.Blank.layout(true));
	}

	void setBarredCorner(final ILayout corner, final ILayout bar) {
		this.setTSection(corner, bar);
		this.setNorthPattern(EnumPattern.Blank.layout(true));
	}

	void setStripedCorner(final ILayout corner, final ILayout striped) {
		this.setCornered(corner, striped);
	}

	void setLetterPattern(final ILayout letter) {
		this.setTopPattern(letter);
		this.setBottomPattern(EnumPattern.Blank.layout(true));
	}

	void setBarredEndPattern(final ILayout end, final ILayout bar) {
		this.setTopAndBottomPattern(end);
		this.setWestPattern(bar);
	}

	void setDiagonalCorner(final ILayout diagonal, final ILayout bar, final ILayout edged) {
		this.setTopAndBottomPattern(diagonal);
		this.setWestPattern(bar.invert());
		this.setNorthPattern(edged.flipHorizontal());
		this.setSouthPattern(edged);
	}

	void setDiagonalTSection(final ILayout diagonal, final ILayout bar, final ILayout edged) {
		this.setTopAndBottomPattern(diagonal);
		this.setWestPattern(bar.invert());
		this.setNorthPattern(bar.invert());
		this.setEastPattern(edged.flipHorizontal());
		this.setSouthPattern(edged);
	}

	@Override
	public String getName() {
		return I18N.localise(new ResourceLocation(Constants.DESIGN_MOD_ID, "pattern." + this.name));
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public ILayout getTopPattern() {
		return this.topPattern;
	}

	public void setTopPattern(final  ILayout layout) { this.topPattern = layout; }

	public void setTopAndBottomPattern(final ILayout layout) {
		this.topPattern = layout;
		this.setBottomPattern(layout.flipVertical());
	}

	@Override
	public ILayout getBottomPattern() {
		return this.bottomPattern;
	}

	public void setBottomPattern(final ILayout layout) {
		this.bottomPattern = layout;
	}

	@Override
	public ILayout getNorthPattern() {
		return this.northPattern;
	}

	public void setNorthPattern(final ILayout layout) {
		this.northPattern = layout;
	}

	@Override
	public ILayout getSouthPattern() {
		return this.southPattern;
	}

	public void setSouthPattern(final ILayout layout) {
		this.southPattern = layout;
	}

	@Override
	public ILayout getEastPattern() {
		return this.eastPattern;
	}

	public void setEastPattern(final ILayout layout) {
		this.eastPattern = layout;
	}

	@Override
	public ILayout getWestPattern() {
		return this.westPattern;
	}

	public void setWestPattern(final ILayout layout) {
		this.westPattern = layout;
	}

	enum Category implements IDesignCategory {
		DESIGN("Designs & Emblems"),
		STRIPES("Squares & Stripes"),
		EDGES("Edges"),
		BARRED("Bars"),
		LETTERS("Letters"),
		DIAGONAL("Diagonals");

		private final String name;
		private final List<IDesign> designs;

		Category(final String name) {
			this.designs = new ArrayList<>();
			this.name = name;
			Design.getDesignManager().registerDesignCategory(this);
		}

		@Override
		public String getName() {
			return this.name;
		}

		@Override
		public List<IDesign> getDesigns() {
			return this.designs;
		}

		@Override
		public void addDesign(final IDesign design) {
			this.designs.add(design);
		}

		@Override
		public String getId() {
			return this.toString().toLowerCase();
		}
	}
}
