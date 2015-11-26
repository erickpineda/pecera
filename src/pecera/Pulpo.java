package pecera;

public class Pulpo extends Animal {

    private final double VELOCIDAD_PULPO = Helper.rand(3, 5);

    public Pulpo(String nombre, String gen) {
        super(nombre, gen);
    }

    @Override
    public void mover(double x, double y) {
        this.imagen.move(x * VELOCIDAD_PULPO, y * VELOCIDAD_PULPO);
    }

    @Override
    public void posicionarAleatoriamente(double w, double h) {
        // Dependiendo la direccion del pulpo
        if (getDireccion() == 1 || getDireccion() == 3) {
            // Puede escoger si se posiciona en la parte superior de la pantalla
            if (Helper.rand(0, 1) == 0) {
                posicionar(Helper.rand((int) getWidth(), (int) (w - getWidth())), 0);
            } else {
                // Sino, se posiciona en la parte inferior de la misma
                posicionar(Helper.rand((int) getWidth(), (int) (w - getWidth())), h - getHeight());
            }
        }
        // Dependiendo la direccion del pulpo
        if (getDireccion() == 2 || getDireccion() == 4) {
            // Puede escoger si se posiciona a la izquierda de la pantalla
            if (Helper.rand(0, 1) == 0) {
                posicionar(0, Helper.rand((int) getHeight(), (int) (h - getWidth())));
            } else {
                // Sino, se posiciona a la derecha de la misma
                posicionar(w - getWidth(), Helper.rand((int) getHeight(), (int) (h - getHeight())));
            }
        }
    }
}
