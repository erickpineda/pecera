package pecera;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Erick Pineda
 *
 */
public class Pecera {
    /**
     * Altura de la pantalla.
     */
    private double altura;
    /**
     * Anchura de la pantalla.
     */
    private double anchura;
    /**
     * Lista de animales.
     */
    private List<Animal> animales;
    /**
     * Lista de animales nacidos a partir de las colisiones.
     */
    private List<Animal> recienNacidos = new ArrayList<Animal>();
    /**
     * Variable de animal macho.
     */
    private static final String MACHO = "macho";
    /**
     * ss.
     */
    private static final int TRES = 3, CUATRO = 4;

    /**
     * Crea una pecera respecto al rectangulo de la pantalla.
     * 
     * @param r
     *            el rectangulo de la pantalla.
     */
    public Pecera(final Rectangle r) {
        this.animales = new ArrayList<>();
        this.anchura = r.getWidth();
        this.altura = r.getHeight();
    }

    /**
     * 
     * @param nPeces
     *            animales que se agregaran a la pecera.
     */
    public final void agregarAnimales(final List<Animal> nPeces) {
        this.animales.addAll(nPeces);
    }

    /**
     * Metodo para definir el destino de cada animal y girar la imagen segun su
     * direccion.
     */
    public final void definirDestino() {
        for (Animal p : animales) {
            darDireccion(p);
            p.girarImagenSegunDireccion(true);
        }
    }

    /**
     * Metodo para agregar un animal a la lista.
     * 
     * @param p
     *            animal que se agregara a la lista.
     */
    public final void addToList(final Animal p) {
        if (p != null) {
            animales.add(p);
        }
    }

    /**
     * 
     * @return retorna la cantidad de peces en la pecera.
     */
    public final int cantidadPeces() {
        return animales.size();
    }

    /**
     * Metodo que posiciona todos los animales en la pecera, al inicio del juego
     * de forma aleatoria en la pantalla..
     */
    public final void posicionInicial() {
        for (int i = 0; i < cantidadPeces(); i++) {
            posicionarAleatoriamente(getAnimal(i));
        }
    }

    /**
     * 
     * @param p
     *            animal como parametro para posicionarlo de manera aleatoria en
     *            la pantalla.
     */
    private void posicionarAleatoriamente(final Animal p) {
        p.posicionarAleatoriamente(anchura, altura);
    }

    /**
     * Metodo que posiciona un animal a un costado de la pantalla.
     * 
     * @param p
     *            animal que se posicionara al costado segun su direccion.
     */
    private void posicionarAlCostado(final Animal p) {
        if (p.getDireccion() == 1) {
            p.posicionar(anchura - p.getWidth(), Helper.rand(0, (int) altura));
        }
        if (p.getDireccion() == 2) {
            p.getImagen().setLocation(Helper.rand(0, (int) anchura), altura - p.getHeight());
        }
        if (p.getDireccion() == TRES) {
            p.posicionar(0, Helper.rand(0, (int) altura));
        }
        if (p.getDireccion() == CUATRO) {
            p.posicionar(Helper.rand(0, (int) anchura), 0);
        }
    }

    /**
     * 
     * @return retorna cierto o falso, si en la pecera hay mas de un animal.
     */
    public final boolean hayPeces() {

        if (cantidadPeces() > 1) {
            return true;
        }
        return false;
    }

    /**
     * Metodo que va comprobando si un animal se sale de la pantalla.
     * 
     * @param a
     *            parametro del animal a comprobar.
     */
    public final void noExcedeLimitesDePantalla(final Animal a) {
        if (a.getX() < 0) {
            a.setDireccion(TRES);
            a.girarImagenSegunDireccion(false);
        }
        if (a.getPosFinalX() > anchura) {
            a.setDireccion(1);
            a.girarImagenSegunDireccion(false);
        }
        if (a.getPosFinalY() > altura) {
            a.setDireccion(2);
            a.girarImagenSegunDireccion(false);
        }
        if (a.getY() < 0) {
            a.setDireccion(CUATRO);
            a.girarImagenSegunDireccion(false);
        }
    }

    /**
     * Metodo creado con el fin de matar animales una vez salen de la pantalla.
     * 
     * @param a
     *            animal que se sale de pantalla.
     */
    public final void excedeLimitesDePantalla(final Animal a) {
        if (a.getPosFinalX() < 0 || a.getX() > anchura || a.getY() > altura
                || a.getPosFinalY() < 0) {

            a.setHaMuerto(true);
        }
    }

    /**
     * Metodo que recibe un animal por parametro y lo cmpara con otro, para
     * saber si colisiona con otro. Animales con genero igual se matan y con
     * genero opuesto crian uno nuevo.
     * 
     * @param a
     *            animal a comparar.
     */
    public final void comprobarColisiones(final Animal a) {
        final Animal b = pezQueColisiona(a);

        if (b != null) {
            siHayQueEliminar(a, b);
            if (hayQueProcrear(a, b)) {
                a.setPuedeReproducir(true);
                b.setPuedeReproducir(false);
            }
        } else {
            crearPezBebe(a, b);
        }
    }

    /**
     * 
     * @param a
     *            animal como referencia para crear el nuevo animal.
     * @return retorna un nuevo animal creado a partir de uno existente.
     */
    private Animal crearPezAPartirDeOtro(final Animal a) {
        Animal b = null;
        if (a instanceof Pez) {
            b = new Pez(a.getNombreImg(), a.getGenero(), Helper.rand(1, CUATRO));
        }
        if (a instanceof Tiburon) {
            b = new Tiburon(a.getNombreImg(), a.getGenero(), Helper.rand(1, CUATRO));
        }
        if (a instanceof Delfin) {
            b = new Delfin(a.getNombreImg(), a.getGenero(), Helper.rand(1, CUATRO));
        }
        return b;
    }

    /**
     * Metodo que crea una cria de animal, aleatoriamente macho o hembra.
     * 
     * @param a
     *            es el animal que colisiona.
     * @param b
     *            es el animal colisionado.
     */
    private void crearPezBebe(final Animal a, Animal b) {
        if (a.isPuedeReproducir()) {
            b = crearPezAPartirDeOtro(a);
            b.girarImagenSegunDireccion(true);
            posicionarAlCostado(b);
            recienNacidos.add(b);
            a.setPuedeReproducir(false);
            b.setPuedeReproducir(false);
        }
    }

    /**
     * 
     * @param a
     *            animal que colisiona.
     * @return retorna null cuando el animal no colisiona.
     */
    private Animal pezQueColisiona(final Animal a) {
        for (Animal b : getAnimales()) {
            if (b != a && a.getPosicion().intersects(b.getPosicion())) {
                return b;
            }
        }
        return null;
    }

    /**
     * 
     * @return retorna la lista de animales recien nacidos.
     */
    public final List<Animal> getBebes() {
        return recienNacidos;
    }

    /**
     * @param a
     *            animal a.
     * @param b
     *            animal b.
     */
    private void siHayQueEliminar(final Animal a, final Animal b) {

        if ((a instanceof Tiburon && b instanceof Tiburon) || (a instanceof Pez && b instanceof Pez)
                || (a instanceof Delfin && b instanceof Delfin)
                || (a instanceof Pulpo && b instanceof Pulpo)
                || (a instanceof Tortuga && b instanceof Tortuga)) {

            if ((esMacho(a) && esMacho(b)) || (!esMacho(a) && !esMacho(b))) {
                a.setHaMuerto(true);
                b.setHaMuerto(true);
            }
        } else {
            if (a instanceof Tiburon && b instanceof Pez) {
                b.setHaMuerto(true);
            }
            if (a instanceof Tiburon && b instanceof Delfin) {
                a.setHaMuerto(true);
            }
            if (a instanceof Pez && b instanceof Tiburon) {
                a.setHaMuerto(true);
            }
            if (a instanceof Delfin && b instanceof Tiburon) {
                b.setHaMuerto(true);
            }
        }
    }

    /**
     * 
     * @param a
     *            animal a.
     * @param b
     *            animal b.
     * @return retorna true si hay qe crear un nuevo animal.
     */
    private boolean hayQueProcrear(final Animal a, final Animal b) {

        if ((a instanceof Tiburon && b instanceof Tiburon) || (a instanceof Pez && b instanceof Pez)
                || (a instanceof Delfin && b instanceof Delfin)) {

            return ((esMacho(a) && !esMacho(b)) || (!esMacho(a) && esMacho(b)));
        }
        return false;
    }

    /**
     * 
     * @param a
     *            animal a.
     * @param b
     *            animal b.
     * @return retorna true si los animales colisionan.
     */
    public final boolean colisionan(final Animal a, final Animal b) {
        return (!a.getPosicion().equals(b.getPosicion())
                && a.getPosicion().intersects(b.getPosicion()));
    }

    /**
     * 
     * @param p
     *            animal quien se le comprobara su genero.
     * @return retorna true si un animal es macho.
     */
    public final boolean esMacho(final Animal p) {
        return (p.getGenero().equals(MACHO));
    }

    /**
     * Metodo para darle direccion a un animal de forma aleatoria.
     * 
     * @param p
     *            animal como parametro para dar direccion.
     */
    public final void darDireccion(final Animal p) {
        p.setDireccion(Helper.rand(1, CUATRO));
    }

    /**
     * Metodo para mover los animales segun su direccion y cuantas posiciones
     * movera.
     * 
     * @param p
     *            animal a mover.
     * @param pos
     *            cuantos pixeles movera.
     */
    public final void moverPeces(final Animal p, final int pos) {
        p.moverSegunDireccion(pos);

    }

    /**
     * 
     * @param i
     *            posicion en la lista de animales.
     * @return retorna un animal a partir de su posicion en la lista.
     */
    public final Animal getAnimal(final int i) {
        return animales.get(i);
    }

    /**
     * 
     * @param p
     *            animal a remover de la lista.
     */
    public final void remover(final Animal p) {
        animales.remove(p);
    }

    /**
     * @return the animal
     */
    public final List<Animal> getAnimales() {
        return animales;
    }

    /**
     * @param peces
     *            the animal to set
     */
    public final void setPeces(final List<Animal> peces) {
        this.animales = peces;
    }
}