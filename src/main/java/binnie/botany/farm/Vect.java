package binnie.botany.farm;

public class Vect {
    public int x;
    public int y;
    public int z;

    public Vect(final int[] dim) {
        if (dim.length != 3) {
            throw new RuntimeException("Cannot instantiate a vector with less or more than 3 points.");
        }
        this.x = dim[0];
        this.y = dim[1];
        this.z = dim[2];
    }

    public Vect(final int x, final int y, final int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vect add(final Vect other) {
        final Vect vect;
        final Vect result = vect = new Vect(this.x, this.y, this.z);
        vect.x += other.x;
        final Vect vect2 = result;
        vect2.y += other.y;
        final Vect vect3 = result;
        vect3.z += other.z;
        return result;
    }

    public Vect multiply(final float factor) {
        final Vect vect;
        final Vect result = vect = new Vect(this.x, this.y, this.z);
        vect.x *= (int) factor;
        final Vect vect2 = result;
        vect2.y *= (int) factor;
        final Vect vect3 = result;
        vect3.z *= (int) factor;
        return result;
    }

    @Override
    public String toString() {
        return String.format("%sx%sx%s;", this.x, this.y, this.z);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = 31 * result + this.x;
        result = 31 * result + this.y;
        result = 31 * result + this.z;
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final Vect other = (Vect) obj;
        return this.x == other.x && this.y == other.y && this.z == other.z;
    }
}
