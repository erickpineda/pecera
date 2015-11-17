package pecera;

public class Tiburon extends Pez {
    private static final int VELOCIDAD_TIBURON = Helper.rand(4, 7);

    public Tiburon(String nombre, String gen, int dir) {
        super(nombre, gen, dir);
    }

    @Override
    public final void mover(final double x, final double y) {
        this.imagen.move(x * VELOCIDAD_TIBURON, y * VELOCIDAD_TIBURON);
    }
}
