package binnie.extratrees.carpentry;

import binnie.extratrees.api.CarpentryManager;
import binnie.extratrees.api.IDesign;
import binnie.extratrees.api.IDesignCategory;
import binnie.extratrees.api.ILayout;

import java.util.ArrayList;
import java.util.List;

public enum EnumDesign implements IDesign {
	Blank("Blank"),
	Octagon("Octagon"),
	Diamond("Diamond"),
	Ringed("Ringed"),
	Squared("Squared"),
	Multiply("Multiply"),
	Halved("Halved"),
	Striped("Striped"),
	ThinStriped("Thin Striped"),
	Chequered("Full Chequered"),
	Tiled("Full Tiled"),
	ChequeredB("Chequered"),
	TiledB("Tiled"),
	VeryThinCorner("Very Thin Cornered"),
	ThinCorner("Thin Cornered"),
	Corner("Cornered"),
	ThickCorner("Thick Cornered"),
	Edged("Edged"),
	ThinEdged("Thin Edged"),
	ThinBarred("Thin Barred"),
	Barred("Barred"),
	ThickBarred("Thick Barred"),
	Diagonal("Diagonal"),
	ThickDiagonal("Thick Diagonal"),
	ThinSaltire("Thin Saltire"),
	Saltire("Saltire"),
	ThickSaltire("Thick Saltire"),
	ThinCrossed("Thin Crossed"),
	Crossed("Crossed"),
	ThickCrossed("Thick Crossed"),
	ThinTSection("Thin T Section"),
	TSection("T Section"),
	ThickTSection("Thick T Section"),
	ThinBarredCorner("Thin Barred Corner"),
	BarredCorner("Barred Corner"),
	ThickBarredCorner("Thick Barred Corner"),
	ThinStripedCorner("Thin Striped Corner"),
	StripedCorner("Striped Corner"),
	Emblem1("Emblem 1"),
	Emblem2("Emblem 2"),
	Emblem3("Emblem 3"),
	Emblem4("Emblem 4"),
	Emblem5("Emblem 5"),
	LetterA("Letter A"),
	LetterB("Letter B"),
	LetterC("Letter C"),
	LetterD("Letter D"),
	LetterE("Letter E"),
	LetterF("Letter F"),
	LetterG("Letter G"),
	LetterH("Letter H"),
	LetterI("Letter I"),
	LetterJ("Letter J"),
	LetterK("Letter K"),
	LetterL("Letter L"),
	LetterM("Letter M"),
	LetterN("Letter N"),
	LetterO("Letter O"),
	LetterP("Letter P"),
	LetterQ("Letter Q"),
	LetterR("Letter R"),
	LetterS("Letter S"),
	LetterT("Letter T"),
	LetterU("Letter U"),
	LetterV("Letter V"),
	LetterW("Letter W"),
	LetterX("Letter X"),
	LetterY("Letter Y"),
	LetterZ("Letter Z"),
	ThinCurvedCrossed("Thin Curved Crossed"),
	ThinCurvedBarredCorner("Thin Curved Barred Corner"),
	CurvedBarredCorner("Curved Barred Corner"),
	ThinCurvedCorner("Thin Curved Corner"),
	CurvedCorner("Curved Corner"),
	ThinCurvedTSection("Thin Curved T Section"),
	CurvedTSection("Curved T Section"),
	BarredEnd("Barred End"),
	DiagonalCorner("Diagonal Corner"),
	DiagonalTSection("Diagonal T Section"),
	DiagonalCurvedCorner("Diagonal Curved Corner"),
	DiagonalCurvedTSection("Diagonal Curved T Section"),
	OrnateBarred("Ornate Barred"),
	SplitBarred("Split Barred"),
	SplitBarredCorner("Split Barred Corner"),
	SplitBarredTSection("Split Barred T Section"),
	SplitCrossed("Split Crossed"),
	SplitBarredEnd("Split Barred End"),
	OrnateThinBarred("Ornate Thin Barred"),
	Circle("Circle"),
	Plus("Plus"),
	Creeper("Creeper"),
	OrnateStripedCorner("Ornate Striped Corner"),
	Test("Testing Block"),
	DiagonalHalved("Diagonal Halved"),
	Diagonal1Edged("Cornered Diagonal"),
	Diagonal2Edged("Opposite Cornered Diagonal"),
	ThickDiagonal1Edged("Thick Cornered Diagonal"),
	ThinBarredEnd("Thin Barred End"),
	ThickBarredEnd("Thick Barred End"),
	OverlappedBarred("Overlapped Barred"),
	OverlappedSplitBarred("Overlapped Split Barred");

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

	protected String name;
	protected ILayout topPattern;
	protected ILayout bottomPattern;
	protected ILayout northPattern;
	protected ILayout southPattern;
	protected ILayout eastPattern;
	protected ILayout westPattern;

	EnumDesign(String name) {
		this.name = name;
		topPattern = Layout.get(EnumPattern.Blank, false);
		bottomPattern = Layout.get(EnumPattern.Blank, false);
		northPattern = Layout.get(EnumPattern.Blank, false);
		eastPattern = Layout.get(EnumPattern.Blank, false);
		southPattern = Layout.get(EnumPattern.Blank, false);
		westPattern = Layout.get(EnumPattern.Blank, false);
	}

	void setEdgePatterns(ILayout north, ILayout east, ILayout south, ILayout west) {
		setNorthPattern(north);
		setEastPattern(east);
		setSouthPattern(south);
		setWestPattern(west);
	}

	void setEdgePatterns(ILayout face) {
		setEdgePatterns(face, face, face, face);
	}

	void setupStriped(ILayout vert) {
		setTopPattern(vert);
		setEdgePatterns(vert.invert(), EnumPattern.Blank.layout(true), vert, EnumPattern.Blank.layout());
	}

	void setChequered(ILayout cheq) {
		setAllPatterns(cheq);
		setNorthPattern(cheq.invert());
		setSouthPattern(cheq.invert());
		setBottomPattern(cheq.invert());
	}

	void setStripedChequered(ILayout cheq, ILayout stripe) {
		setAllPatterns(cheq);
		setNorthPattern(stripe.invert());
		setSouthPattern(stripe.invert());
		setEastPattern(stripe);
		setWestPattern(stripe);
	}

	void setCornered(ILayout corner, ILayout edge) {
		setTopPattern(corner);
		setNorthPattern(EnumPattern.Blank.layout());
		setEastPattern(edge.flipHorizontal());
		setSouthPattern(edge);
		setWestPattern(EnumPattern.Blank.layout());
	}

	void setEdged(ILayout edge) {
		setAllPatterns(edge);
		setWestPattern(EnumPattern.Blank.layout());
		setEastPattern(EnumPattern.Blank.layout(true));
		northPattern = northPattern.flipHorizontal();
	}

	void setBarred(ILayout bar) {
		setAllPatterns(bar);
		setEastPattern(EnumPattern.Blank.layout(true));
		setWestPattern(EnumPattern.Blank.layout(true));
	}

	void setDiagonal(ILayout diagonal, ILayout edged) {
		setAllPatterns(edged);
		setTopPattern(diagonal);
		northPattern = northPattern.flipHorizontal();
		southPattern = southPattern.flipHorizontal();
	}

	void setSaltire(ILayout saltire, ILayout bar) {
		setTopPattern(saltire);
		setEdgePatterns(bar.invert());
	}

	void setCross(ILayout saltire, ILayout bar) {
		setTopPattern(saltire);
		setEdgePatterns(bar);
	}

	void setTSection(ILayout tsection, ILayout bar) {
		setTopPattern(tsection);
		setEdgePatterns(bar);
		setWestPattern(EnumPattern.Blank.layout(true));
	}

	void setBarredCorner(ILayout corner, ILayout bar) {
		setTSection(corner, bar);
		setNorthPattern(EnumPattern.Blank.layout(true));
	}

	void setStripedCorner(ILayout corner, ILayout striped) {
		setCornered(corner, striped);
	}

	void setLetterPattern(ILayout letter) {
		setAllPatterns(EnumPattern.Blank.layout(true));
		setTopPattern(letter);
		setBottomPattern(EnumPattern.Blank.layout(true));
	}

	void setBarredEndPattern(ILayout end, ILayout bar) {
		setAllPatterns(EnumPattern.Blank.layout(true));
		setTopPattern(end);
		setWestPattern(bar);
	}

	void setDiagonalCorner(ILayout diagonal, ILayout bar, ILayout edged) {
		setAllPatterns(EnumPattern.Blank.layout(true));
		setTopPattern(diagonal);
		setWestPattern(bar.invert());
		setNorthPattern(edged.flipHorizontal());
		setSouthPattern(edged);
	}

	void setDiagonalTSection(ILayout diagonal, ILayout bar, ILayout edged) {
		setAllPatterns(EnumPattern.Blank.layout(true));
		setTopPattern(diagonal);
		setWestPattern(bar.invert());
		setNorthPattern(bar.invert());
		setEastPattern(edged.flipHorizontal());
		setSouthPattern(edged);
	}

	private void setAllPatterns(ILayout layout) {
		setTopPattern(layout);
		setBottomPattern(layout);
		setNorthPattern(layout);
		setEastPattern(layout);
		setSouthPattern(layout);
		setWestPattern(layout);
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public ILayout getTopPattern() {
		return topPattern;
	}

	public void setTopPattern(ILayout layout) {
		setBottomPattern(topPattern = layout);
	}

	@Override
	public ILayout getBottomPattern() {
		return bottomPattern;
	}

	public void setBottomPattern(ILayout layout) {
		bottomPattern = layout;
	}

	@Override
	public ILayout getNorthPattern() {
		return northPattern;
	}

	public void setNorthPattern(ILayout layout) {
		northPattern = layout;
	}

	@Override
	public ILayout getSouthPattern() {
		return southPattern;
	}

	public void setSouthPattern(ILayout layout) {
		southPattern = layout;
	}

	@Override
	public ILayout getEastPattern() {
		return eastPattern;
	}

	public void setEastPattern(ILayout layout) {
		eastPattern = layout;
	}

	@Override
	public ILayout getWestPattern() {
		return westPattern;
	}

	public void setWestPattern(ILayout layout) {
		westPattern = layout;
	}

	enum Category implements IDesignCategory {
		Design("Designs & Emblems"),
		Stripes("Squares & Stripes"),
		Edges("Edges"),
		Barred("Bars"),
		Letters("Letters"),
		Diagonal("Diagonals");

		protected String name;
		protected List<IDesign> designs;

		Category(String name) {
			this.name = name;
			designs = new ArrayList<>();
			CarpentryManager.carpentryInterface.registerDesignCategory(this);
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public List<IDesign> getDesigns() {
			return designs;
		}

		@Override
		public void addDesign(IDesign design) {
			designs.add(design);
		}

		@Override
		public String getId() {
			return toString().toLowerCase();
		}
	}
}
