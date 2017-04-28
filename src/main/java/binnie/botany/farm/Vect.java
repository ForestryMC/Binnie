package binnie.botany.farm;

public class Vect {
	public int x;
	public int y;
	public int z;

	public Vect(int[] dim) {
		if (dim.length != 3) {
			throw new RuntimeException("Cannot instantiate a vector with less or more than 3 points.");
		}
		x = dim[0];
		y = dim[1];
		z = dim[2];
	}

	public Vect(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vect add(Vect other) {
		Vect vect;
		Vect result = vect = new Vect(x, y, z);
		vect.x += other.x;
		Vect vect2 = result;
		vect2.y += other.y;
		Vect vect3 = result;
		vect3.z += other.z;
		return result;
	}

	public Vect multiply(float factor) {
		Vect vect;
		Vect result = vect = new Vect(x, y, z);
		vect.x *= (int) factor;
		Vect vect2 = result;
		vect2.y *= (int) factor;
		Vect vect3 = result;
		vect3.z *= (int) factor;
		return result;
	}

	@Override
	public String toString() {
		return String.format("%sx%sx%s;", x, y, z);
	}

	@Override
	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		result = prime * result + z;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Vect other = (Vect) obj;
		return x == other.x
			&& y == other.y
			&& z == other.z;
	}
}
