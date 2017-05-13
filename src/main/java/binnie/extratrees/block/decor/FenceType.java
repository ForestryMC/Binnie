package binnie.extratrees.block.decor;

import binnie.extratrees.ExtraTrees;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FenceType {
	public static List<FenceType> VALUES;
	
	public int size;
	public boolean solid;
	public boolean embossed;

	public FenceType(final int size, final boolean solid, final boolean embedded) {
		this.size = size;
		this.solid = solid;
		this.embossed = embedded;
	}

	public String getPrefix() {
		String prefix = "";
		if(size == 1){
			prefix+=ExtraTrees.proxy.localise("multifence.low.type");
		}else if(size == 2){
			prefix+=ExtraTrees.proxy.localise("multifence.full.type");
		}
		if(solid){
			if(!prefix.isEmpty()){
				prefix+=" ";
			}
			prefix+=ExtraTrees.proxy.localise("multifence.solid.type");
		}
		if(embossed){
			if(!prefix.isEmpty()){
				prefix+=" ";
			}
			prefix+=ExtraTrees.proxy.localise("multifence.embedded.type");
		}
		if(!prefix.isEmpty()){
			prefix+=" ";
		}
		return prefix;
	}

	public int ordinal() {
		return this.size + ((this.solid ? 1 : 0) << 2) + ((this.embossed ? 1 : 0) << 3);
	}

	public FenceType(final int meta) {
		this.size = (meta & 0x3);
		this.solid = ((meta >> 2 & 0x1) > 0);
		this.embossed = ((meta >> 3 & 0x1) > 0);
	}

	public static Collection<FenceType> values() {
		if(VALUES == null){
			VALUES = new ArrayList<>();
			for (int size = 0; size < 3; ++size) {
				for (final boolean solid : new boolean[]{false, true}) {
					for (final boolean embedded : new boolean[]{false, true}) {
						VALUES.add(new FenceType(size, solid, embedded));
					}
				}
			}
		}
		return VALUES;
	}

	public boolean isPlain() {
		return this.size == 0 && !this.embossed && !this.solid;
	}
	
	@Override
	public String toString() {
		return size + (embossed ? ":embossed" : "") + (solid ? ":solid" : "");
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof FenceType) {
			final FenceType o = (FenceType) obj;
			return o.size == this.size && o.embossed == this.embossed && o.solid == this.solid;
		}
		return super.equals(obj);
	}
}
