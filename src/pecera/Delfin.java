package pecera;

public class Delfin extends Animal {

    private final double VELOCIDAD_DELFIN = Helper.rand(5, 8);

    public Delfin(String nombre, String gen) {
        super(nombre, gen);
    }

    public Delfin(String nombre, String gen, int dir) {
        super(nombre, gen, dir);
    }

    @Override
    public void mover(double x, double y) {
        this.imagen.move(x * VELOCIDAD_DELFIN, y * VELOCIDAD_DELFIN);
    }

    @Override
    public void moverSegunDireccion(int pos) {
        if (getDireccion() == 1) {
            mover(-pos, -pos);
        }
        if (getDireccion() == 2) {
            mover(pos, -pos);
        }
        if (getDireccion() == 3) {
            mover(pos, pos);
        }
        if (getDireccion() == 4) {
            mover(-pos, pos);
        }
    }
}
