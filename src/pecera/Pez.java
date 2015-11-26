package pecera;

/**
 * 
 * @author Erick Pineda.
 *
 */
public class Pez extends Animal {
    public Pez(String nombre, String gen, int dir) {
        super(nombre, gen, dir);
    }

    public Pez(String nombre, String gen) {
        super(nombre, gen);
    }

    /**
     * Velocidad del pez.
     */
    private final int VELOCIDAD_PEZ = Helper.rand(2, 4);

    /**
     * Mueve el pez segun los ejes X e Y.
     * 
     * @param x
     *            eje X.
     * @param y
     *            eje Y.
     */
    @Override
    public void mover(final double x, final double y) {
        this.imagen.move(x * VELOCIDAD_PEZ, y * VELOCIDAD_PEZ);
    }
}