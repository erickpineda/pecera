package pecera;

public class Tortuga extends Animal {

    private final double VELOCIDAD_TORTUGA = Helper.rand(1, 6);

    public Tortuga(String nombre, String gen) {
        super(nombre, gen);
    }

    @Override
    public void mover(double x, double y) {
        this.imagen.move(x * VELOCIDAD_TORTUGA, y * VELOCIDAD_TORTUGA);
    }

}
