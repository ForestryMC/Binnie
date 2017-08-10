package binnie.extratrees.carpentry;

import java.util.ArrayList;
import java.util.List;

import binnie.core.util.I18N;
import binnie.extratrees.api.CarpentryManager;
import binnie.extratrees.api.IDesign;
import binnie.extratrees.api.IDesignCategory;
import binnie.extratrees.api.ILayout;

public enum EnumDesign implements IDesign {
	Blank("blank"),
	Octagon("octagon"),
	Diamond("diamond"),
	Ringed("ringed"),
	Squared("squared"),
	Multiply("multiply"),
	Halved("halved"),
	Striped("striped"),
	ThinStriped("thin.striped"),
	Chequered("full.chequered"),
	Tiled("full.tiled"),
	ChequeredB("chequered"),
	TiledB("tiled"),
	VeryThinCorner("very.thin.cornered"),
	ThinCorner("thin.cornered"),
	Corner("cornered"),
	ThickCorner("thick.cornered"),
	Edged("edged"),
	ThinEdged("thin.edged"),
	ThinBarred("thin.barred"),
	Barred("barred"),
	ThickBarred("thick.barred"),
	Diagonal("diagonal"),
	ThickDiagonal("thick.diagonal"),
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
	Emblem1("emblem.1"),
	Emblem2("emblem.2"),
	Emblem3("emblem.3"),
	Emblem4("emblem.4"),
	Emblem5("emblem.5"),
	LetterA("letter.a"),
	LetterB("letter.b"),
	LetterC("letter.c"),
	LetterD("letter.d"),
	LetterE("letter.e"),
	LetterF("letter.f"),
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
	LetterS("letter.s"),
	LetterT("letter.t"),
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
	BarredEnd("barred.end"),
	DiagonalCorner("diagonal.corner"),
	DiagonalTSection("diagonal.t.section"),
	DiagonalCurvedCorner("diagonal.curved.corner"),
	DiagonalCurvedTSection("diagonal.curved.t.section"),
	OrnateBarred("ornate.barred"),
	SplitBarred("split.barred"),
	SplitBarredCorner("split.barred.corner"),
	SplitBarredTSection("split.barred.t.section"),
	SplitCrossed("split.crossed"),
	SplitBarredEnd("split.barred.end"),
	OrnateThinBarred("ornate.thin.barred"),
	Circle("circle"),
	Plus("plus"),
	Creeper("creeper"),
	OrnateStripedCorner("ornate.striped.corner"),
	Test("testing.block"),
	DiagonalHalved("diagonal.halved"),
	Diagonal1Edged("cornered.diagonal"),
	Diagonal2Edged("opposite.cornered.diagonal"),
	ThickDiagonal1Edged("thick.cornered.diagonal"),
	ThinBarredEnd("thin.barred.end"),
	ThickBarredEnd("thick.barred.end"),
	OverlappedBarred("overlapped.barred"),
	OverlappedSplitBarred("overlapped.split.barred");

	static {
		Category.Design.addDesign(EnumDesign.Blank);
		Category.Design.addDesign(EnumDesign.Octagon);
		Category.Design.addDesign(EnumDesign.Diamond);
		Category.Design.addDesign(EnumDesign.Ringed);
		Category.Design.addDesign(EnumDesign.Squared);
		Category.Design.addDesign(EnumDesign.Multiply);
		Category.Design.addDesign(EnumDesign.Plus);
		Category.Design.addDesign(EnumDesign.Circle);
		Category.Design.addDesign(EnumDesign.Emblem1);
		Category.Design.addDesign(EnumDesign.Emblem2);
		Category.Design.addDesign(EnumDesign.Emblem3);
		Category.Design.addDesign(EnumDesign.Emblem4);
		Category.Design.addDesign(EnumDesign.Emblem5);
		Category.Design.addDesign(EnumDesign.Creeper);
		Category.Stripes.addDesign(EnumDesign.Chequered);
		Category.Stripes.addDesign(EnumDesign.ChequeredB);
		Category.Stripes.addDesign(EnumDesign.Tiled);
		Category.Stripes.addDesign(EnumDesign.TiledB);
		Category.Stripes.addDesign(EnumDesign.Striped);
		Category.Stripes.addDesign(EnumDesign.ThinStriped);
		Category.Stripes.addDesign(EnumDesign.ThinStripedCorner);
		Category.Stripes.addDesign(EnumDesign.StripedCorner);
		Category.Stripes.addDesign(EnumDesign.OrnateStripedCorner);
		Category.Edges.addDesign(EnumDesign.Halved);
		Category.Edges.addDesign(EnumDesign.Corner);
		Category.Edges.addDesign(EnumDesign.ThickCorner);
		Category.Edges.addDesign(EnumDesign.Edged);
		Category.Edges.addDesign(EnumDesign.ThinCorner);
		Category.Edges.addDesign(EnumDesign.ThinEdged);
		Category.Edges.addDesign(EnumDesign.VeryThinCorner);
		Category.Edges.addDesign(EnumDesign.ThinCurvedCorner);
		Category.Edges.addDesign(EnumDesign.CurvedCorner);
		Category.Barred.addDesign(EnumDesign.ThinBarred);
		Category.Barred.addDesign(EnumDesign.ThinBarredCorner);
		Category.Barred.addDesign(EnumDesign.ThinTSection);
		Category.Barred.addDesign(EnumDesign.ThinCrossed);
		Category.Barred.addDesign(EnumDesign.ThinBarredEnd);
		Category.Barred.addDesign(EnumDesign.OrnateThinBarred);
		Category.Barred.addDesign(EnumDesign.Barred);
		Category.Barred.addDesign(EnumDesign.BarredCorner);
		Category.Barred.addDesign(EnumDesign.TSection);
		Category.Barred.addDesign(EnumDesign.Crossed);
		Category.Barred.addDesign(EnumDesign.BarredEnd);
		Category.Barred.addDesign(EnumDesign.OverlappedBarred);
		Category.Barred.addDesign(EnumDesign.OrnateBarred);
		Category.Barred.addDesign(EnumDesign.ThickBarred);
		Category.Barred.addDesign(EnumDesign.ThickBarredCorner);
		Category.Barred.addDesign(EnumDesign.ThickTSection);
		Category.Barred.addDesign(EnumDesign.ThickCrossed);
		Category.Barred.addDesign(EnumDesign.ThickBarredEnd);
		Category.Barred.addDesign(EnumDesign.ThinCurvedBarredCorner);
		Category.Barred.addDesign(EnumDesign.CurvedBarredCorner);
		Category.Barred.addDesign(EnumDesign.ThinCurvedTSection);
		Category.Barred.addDesign(EnumDesign.CurvedTSection);
		Category.Barred.addDesign(EnumDesign.ThinCurvedCrossed);
		Category.Barred.addDesign(EnumDesign.SplitBarred);
		Category.Barred.addDesign(EnumDesign.SplitBarredCorner);
		Category.Barred.addDesign(EnumDesign.SplitBarredTSection);
		Category.Barred.addDesign(EnumDesign.SplitCrossed);
		Category.Barred.addDesign(EnumDesign.SplitBarredEnd);
		Category.Barred.addDesign(EnumDesign.OverlappedSplitBarred);
		Category.Diagonal.addDesign(EnumDesign.ThinSaltire);
		Category.Diagonal.addDesign(EnumDesign.Diagonal);
		Category.Diagonal.addDesign(EnumDesign.DiagonalCorner);
		Category.Diagonal.addDesign(EnumDesign.DiagonalTSection);
		Category.Diagonal.addDesign(EnumDesign.DiagonalCurvedCorner);
		Category.Diagonal.addDesign(EnumDesign.DiagonalCurvedTSection);
		Category.Diagonal.addDesign(EnumDesign.Saltire);
		Category.Diagonal.addDesign(EnumDesign.ThickDiagonal);
		Category.Diagonal.addDesign(EnumDesign.ThickSaltire);
		Category.Diagonal.addDesign(EnumDesign.DiagonalHalved);
		Category.Diagonal.addDesign(EnumDesign.Diagonal1Edged);
		Category.Diagonal.addDesign(EnumDesign.Diagonal2Edged);
		Category.Diagonal.addDesign(EnumDesign.ThickDiagonal1Edged);
		EnumDesign.Octagon.setAllPatterns(EnumPattern.Octagon.layout());
		EnumDesign.Diamond.setAllPatterns(EnumPattern.Diamond.layout());
		EnumDesign.Ringed.setAllPatterns(EnumPattern.Ringed.layout());
		EnumDesign.Squared.setAllPatterns(EnumPattern.Squared.layout());
		EnumDesign.Multiply.setAllPatterns(EnumPattern.Multiply.layout());
		EnumDesign.ThinStriped.setupStriped(EnumPattern.ThinStriped.layout());
		EnumDesign.Striped.setupStriped(EnumPattern.Striped.layout());
		EnumDesign.Halved.setupStriped(EnumPattern.Halved.layout());
		EnumDesign.Chequered.setChequered(EnumPattern.Chequered.layout());
		EnumDesign.Tiled.setChequered(EnumPattern.Tiled.layout());
		EnumDesign.ChequeredB.setStripedChequered(EnumPattern.Chequered.layout(), EnumPattern.Halved.layout());
		EnumDesign.TiledB.setStripedChequered(EnumPattern.Tiled.layout(), EnumPattern.Striped.layout());
		EnumDesign.VeryThinCorner.setCornered(EnumPattern.VeryThinCorner.layout(), EnumPattern.ThinEdged.layout());
		EnumDesign.ThinCorner.setCornered(EnumPattern.ThinCorner.layout(), EnumPattern.Edged.layout());
		EnumDesign.Corner.setCornered(EnumPattern.Corner.layout(), EnumPattern.Halved.layout());
		EnumDesign.ThickCorner.setCornered(EnumPattern.ThickCorner.layout(), EnumPattern.Edged.layout(true).flipHorizontal());
		EnumDesign.ThinCurvedCorner.setCornered(EnumPattern.ThinCurvedCorner.layout(), EnumPattern.Edged.layout());
		EnumDesign.CurvedCorner.setCornered(EnumPattern.CurvedCorner.layout(), EnumPattern.Halved.layout());
		EnumDesign.Edged.setEdged(EnumPattern.Edged.layout());
		EnumDesign.ThinEdged.setEdged(EnumPattern.ThinEdged.layout());
		EnumDesign.ThinBarred.setBarred(EnumPattern.ThinBarred.layout());
		EnumDesign.Barred.setBarred(EnumPattern.Barred.layout());
		EnumDesign.ThickBarred.setBarred(EnumPattern.ThickBarred.layout());
		EnumDesign.Diagonal.setDiagonal(EnumPattern.Diagonal.layout(), EnumPattern.Edged.layout());
		EnumDesign.ThickDiagonal.setDiagonal(EnumPattern.ThickDiagonal.layout(), EnumPattern.Halved.layout());
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
		EnumDesign.Emblem1.setAllPatterns(EnumPattern.Emblem1.layout());
		EnumDesign.Emblem2.setAllPatterns(EnumPattern.Emblem2.layout());
		EnumDesign.Emblem3.setAllPatterns(EnumPattern.Emblem3.layout());
		EnumDesign.Emblem4.setAllPatterns(EnumPattern.Emblem4.layout());
		EnumDesign.Emblem5.setAllPatterns(EnumPattern.Emblem5.layout());
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
		EnumDesign.OrnateBarred.setBarred(EnumPattern.OrnateBarred.layout());
		EnumDesign.OrnateThinBarred.setBarred(EnumPattern.OrnateThinBarred.layout());
		EnumDesign.SplitBarred.setBarred(EnumPattern.SplitBarred.layout());
		EnumDesign.SplitBarredCorner.setBarredCorner(EnumPattern.SplitBarredCorner.layout(), EnumPattern.SplitBarred.layout());
		EnumDesign.SplitBarredTSection.setTSection(EnumPattern.SplitBarredTSection.layout(), EnumPattern.SplitBarred.layout());
		EnumDesign.SplitCrossed.setCross(EnumPattern.SplitCrossed.layout(), EnumPattern.SplitBarred.layout());
		EnumDesign.SplitBarredEnd.setBarredEndPattern(EnumPattern.SplitBarredEnd.layout(), EnumPattern.SplitBarred.layout());
		EnumDesign.Circle.setAllPatterns(EnumPattern.Circle.layout());
		EnumDesign.Plus.setAllPatterns(EnumPattern.Plus.layout());
		EnumDesign.Creeper.setAllPatterns(EnumPattern.Blank.layout(true));
		EnumDesign.Creeper.setTopPattern(EnumPattern.Creeper.layout());
		EnumDesign.DiagonalHalved.setTopPattern(EnumPattern.DiagonalHalved.layout());
		EnumDesign.DiagonalHalved.setEdgePatterns(EnumPattern.Blank.layout(), EnumPattern.Blank.layout(true), EnumPattern.Blank.layout(true), EnumPattern.Blank.layout());
		EnumDesign.Diagonal1Edged.setTopPattern(EnumPattern.Diagonal1Edged.layout());
		EnumDesign.Diagonal1Edged.setEdgePatterns(EnumPattern.Edged.layout().flipHorizontal(), EnumPattern.Blank.layout(true), EnumPattern.Blank.layout(true), EnumPattern.Edged.layout());
		EnumDesign.Diagonal2Edged.setTopPattern(EnumPattern.Diagonal2Edged.layout());
		EnumDesign.Diagonal2Edged.setEdgePatterns(EnumPattern.Edged.layout(), EnumPattern.Edged.layout().flipHorizontal(), EnumPattern.Edged.layout(), EnumPattern.Edged.layout().flipHorizontal());
		EnumDesign.ThickDiagonal1Edged.setTopPattern(EnumPattern.ThickDiagonal1Edged.layout());
		EnumDesign.ThickDiagonal1Edged.setEdgePatterns(EnumPattern.Halved.layout().flipHorizontal(), EnumPattern.Blank.layout(true), EnumPattern.Blank.layout(true), EnumPattern.Halved.layout());
		EnumDesign.ThickBarredEnd.setBarredEndPattern(EnumPattern.ThickBarredEnd.layout(), EnumPattern.ThickBarred.layout());
		EnumDesign.ThinBarredEnd.setBarredEndPattern(EnumPattern.ThinBarredEnd.layout(), EnumPattern.ThinBarred.layout());
		EnumDesign.OverlappedSplitBarred.setAllPatterns(EnumPattern.SplitBarred.layout());
		EnumDesign.OverlappedSplitBarred.setTopPattern(EnumPattern.OverlappedSplitBarred.layout());
		EnumDesign.OverlappedBarred.setAllPatterns(EnumPattern.Barred.layout());
		EnumDesign.OverlappedBarred.setTopPattern(EnumPattern.OverlappedBarred.layout());
	}

	String name;
	ILayout topPattern;
	ILayout bottomPattern;
	ILayout northPattern;
	ILayout southPattern;
	ILayout eastPattern;
	ILayout westPattern;

	EnumDesign(final String name) {
		this.topPattern = Layout.get(EnumPattern.Blank, false);
		this.bottomPattern = Layout.get(EnumPattern.Blank, false);
		this.northPattern = Layout.get(EnumPattern.Blank, false);
		this.eastPattern = Layout.get(EnumPattern.Blank, false);
		this.southPattern = Layout.get(EnumPattern.Blank, false);
		this.westPattern = Layout.get(EnumPattern.Blank, false);
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
		this.setTopPattern(vert);
		this.setEdgePatterns(vert.invert(), EnumPattern.Blank.layout(true), vert, EnumPattern.Blank.layout());
	}

	void setChequered(final ILayout cheq) {
		this.setAllPatterns(cheq);
		this.setNorthPattern(cheq.invert());
		this.setSouthPattern(cheq.invert());
		this.setBottomPattern(cheq.invert());
	}

	void setStripedChequered(final ILayout cheq, final ILayout stripe) {
		this.setAllPatterns(cheq);
		this.setNorthPattern(stripe.invert());
		this.setSouthPattern(stripe.invert());
		this.setEastPattern(stripe);
		this.setWestPattern(stripe);
	}

	void setCornered(final ILayout corner, final ILayout edge) {
		this.setTopPattern(corner);
		this.setNorthPattern(EnumPattern.Blank.layout());
		this.setEastPattern(edge.flipHorizontal());
		this.setSouthPattern(edge);
		this.setWestPattern(EnumPattern.Blank.layout());
	}

	void setEdged(final ILayout edge) {
		this.setAllPatterns(edge);
		this.setWestPattern(EnumPattern.Blank.layout());
		this.setEastPattern(EnumPattern.Blank.layout(true));
		this.northPattern = this.northPattern.flipHorizontal();
	}

	void setBarred(final ILayout bar) {
		this.setAllPatterns(bar);
		this.setEastPattern(EnumPattern.Blank.layout(true));
		this.setWestPattern(EnumPattern.Blank.layout(true));
	}

	void setDiagonal(final ILayout diagonal, final ILayout edged) {
		this.setAllPatterns(edged);
		this.setTopPattern(diagonal);
		this.northPattern = this.northPattern.flipHorizontal();
		this.southPattern = this.southPattern.flipHorizontal();
	}

	void setSaltire(final ILayout saltire, final ILayout bar) {
		this.setTopPattern(saltire);
		this.setEdgePatterns(bar.invert());
	}

	void setCross(final ILayout saltire, final ILayout bar) {
		this.setTopPattern(saltire);
		this.setEdgePatterns(bar);
	}

	void setTSection(final ILayout tsection, final ILayout bar) {
		this.setTopPattern(tsection);
		this.setEdgePatterns(bar);
		this.setWestPattern(EnumPattern.Blank.layout(true));
	}

	void setBarredCorner(final ILayout corner, final ILayout bar) {
		this.setTSection(corner, bar);
		this.setNorthPattern(EnumPattern.Blank.layout(true));
	}

	void setStripedCorner(final ILayout corner, final ILayout striped) {
		this.setCornered(corner, striped);
	}

	void setLetterPattern(final ILayout letter) {
		this.setAllPatterns(EnumPattern.Blank.layout(true));
		this.setTopPattern(letter);
		this.setBottomPattern(EnumPattern.Blank.layout(true));
	}

	void setBarredEndPattern(final ILayout end, final ILayout bar) {
		this.setAllPatterns(EnumPattern.Blank.layout(true));
		this.setTopPattern(end);
		this.setWestPattern(bar);
	}

	void setDiagonalCorner(final ILayout diagonal, final ILayout bar, final ILayout edged) {
		this.setAllPatterns(EnumPattern.Blank.layout(true));
		this.setTopPattern(diagonal);
		this.setWestPattern(bar.invert());
		this.setNorthPattern(edged.flipHorizontal());
		this.setSouthPattern(edged);
	}

	void setDiagonalTSection(final ILayout diagonal, final ILayout bar, final ILayout edged) {
		this.setAllPatterns(EnumPattern.Blank.layout(true));
		this.setTopPattern(diagonal);
		this.setWestPattern(bar.invert());
		this.setNorthPattern(bar.invert());
		this.setEastPattern(edged.flipHorizontal());
		this.setSouthPattern(edged);
	}

	private void setAllPatterns(final ILayout layout) {
		this.setTopPattern(layout);
		this.setBottomPattern(layout);
		this.setNorthPattern(layout);
		this.setEastPattern(layout);
		this.setSouthPattern(layout);
		this.setWestPattern(layout);
	}

	@Override
	public String getName() {
		return I18N.localise("extratrees.carpentry.design." + this.name);
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public ILayout getTopPattern() {
		return this.topPattern;
	}

	public void setTopPattern(final ILayout layout) {
		this.setBottomPattern(this.topPattern = layout);
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
		Design("Designs & Emblems"),
		Stripes("Squares & Stripes"),
		Edges("Edges"),
		Barred("Bars"),
		Letters("Letters"),
		Diagonal("Diagonals");

		String name;
		List<IDesign> designs;

		Category(final String name) {
			this.designs = new ArrayList<>();
			this.name = name;
			CarpentryManager.carpentryInterface.registerDesignCategory(this);
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
