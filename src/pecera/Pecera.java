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
     * Lista de peces.
     */
    private List<Pez> peces;
    /**
     * Lista de peces nacidos a partir de las colisiones.
     */
    private List<Pez> recienNacidos = new ArrayList<Pez>();
    /**
     * Variable de pez macho.
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
        this.peces = new ArrayList<>();
        this.anchura = r.getWidth();
        this.altura = r.getHeight();
    }

    /**
     * 
     * @param nPeces
     *            peces que se agregaran a la pecera.
     */
    public final void agregarPeces(final List<Pez> nPeces) {
        this.peces.addAll(nPeces);
    }

    /**
     * Metodo para definir el destino de cada pez y girar la imagen segun su
     * direccion.
     */
    public final void definirDestino() {
        for (Pez p : peces) {
            darDireccion(p);
            girarImagenSegunDireccion(p);
        }
    }

    /**
     * Metodo para agregar un pez a la lista.
     * 
     * @param p
     *            pez que se agregara a la lista.
     */
    public final void addToList(final Pez p) {
        if (p != null) {
            peces.add(p);
        }
    }

    /**
     * 
     * @return retorna la cantidad de peces en la pecera.
     */
    public final int cantidadPeces() {
        return peces.size();
    }

    /**
     * Metodo que posiciona todos los peces en la pecera, al inicio del juego de
     * forma aleatoria en la pantalla..
     */
    public final void posicionInicial() {
        for (int i = 0; i < cantidadPeces(); i++) {
            posicionarAleatoriamente(getPez(i));
        }
    }

    /**
     * 
     * @param p
     *            pez como parametro para posicionarlo de manera aleatoria en la
     *            pantalla.
     */
    private void posicionarAleatoriamente(final Pez p) {
        p.posicionar(Helper.rand(0, (int) (anchura - p.getWidth())),
                Helper.rand(0, (int) (altura - p.getHeight())));
    }

    /**
     * Metodo que posiciona un pez a un costado de la pantalla.
     * 
     * @param p
     *            pez que se posicionara al costado segun su direccion.
     */
    private void posicionarAlCostado(final Pez p) {
        if (p.getDireccion() == 1) {
            p.posicionar(anchura - p.getWidth(), Helper.rand(0, (int) altura));
        }
        if (p.getDireccion() == 2) {
            p.posicionar(Helper.rand(0, (int) anchura), altura - p.getHeight());
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
     * @return retorna cierto o falso, si en la pecera hay mas de un pez.
     */
    public final boolean hayPeces() {

        if (cantidadPeces() > 1) {
            return true;
        }
        return false;
    }

    /**
     * Metodo que va comprobando si un pez se sale de la pantalla.
     * 
     * @param a
     *            parametro del pez a comprobar.
     */
    public final void noExcedeLimitesDePantalla(final Pez a) {
        if (a.getX() <= 0) {
            a.setDireccion(TRES);
            a.flipX();
        }
        if (a.getPosFinalX() >= anchura) {
            a.setDireccion(1);
            a.flipX();
        }
        if (a.getPosFinalY() >= altura) {
            a.setDireccion(2);
            a.flipY();
            a.flipY();
        }
        if (a.getY() <= 0) {
            a.setDireccion(CUATRO);
            a.flipY2();
            a.flipY2();
        }
    }

    /**
     * Metodo creado con el fin de matar peces una vez salen de la pantalla.
     * 
     * @param a
     *            pez que se sale de pantalla.
     */
    public final void excedeLimitesDePantalla(final Pez a) {
        if (a.getPosFinalX() < 0 || a.getX() > anchura || a.getY() > altura
                || a.getPosFinalY() < 0) {

            a.setHaMuerto(true);
        }
    }

    /**
     * Metodo que recibe un pez por parametro y lo cmpara con otro, para saber
     * si colisiona con otro. Peces con genero igual se matan y con genero
     * opuesto crian uno nuevo.
     * 
     * @param a
     *            pez a comparar.
     */
    public final void comprobarColisiones(final Pez a) {
        final Pez b = pezQueColisiona(a);

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
     *            pez como referencia para crear el nuevo pez.
     * @return retorna un nuevo pez creado a partir de uno existente.
     */
    private Pez crearPezAPartirDeOtro(final Pez a) {
        Pez b = null;
        if (a instanceof Pez) {
            b = new Pez(a.getNombreImg(), a.getGenero(), a.getDireccion());
        }
        if (a instanceof Tiburon) {
            b = new Tiburon(a.getNombreImg(), a.getGenero(), a.getDireccion());
        }
        return b;
    }

    /**
     * Metodo que crea una cria de pez, aleatoriamente macho o hembra.
     * 
     * @param a
     *            es el pez que colisiona.
     * @param b
     *            es el pez colisionado.
     */
    private void crearPezBebe(final Pez a, Pez b) {
        if (a.isPuedeReproducir()) {
            b = crearPezAPartirDeOtro(a);
            girarImagenSegunDireccion(b);
            posicionarAlCostado(b);
            recienNacidos.add(b);
            a.setPuedeReproducir(false);
            b.setPuedeReproducir(false);
        }
    }

    /**
     * 
     * @param a
     *            pez que colisiona.
     * @return retorna null cuando el pez no colisiona.
     */
    private Pez pezQueColisiona(final Pez a) {
        for (Pez b : getPeces()) {
            if (b != a && a.getPosicion().intersects(b.getPosicion())) {
                return b;
            }
        }
        return null;
    }

    /**
     * 
     * @return retorna la lista de peces recien nacidos.
     */
    public final List<Pez> getBebes() {
        return recienNacidos;
    }

    /**
     * @param a
     *            pez a.
     * @param b
     *            pez b.
     */
    private void siHayQueEliminar(final Pez a, final Pez b) {

        if ((a instanceof Tiburon && b instanceof Tiburon)
                || (a.getClass() == Pez.class && b.getClass() == Pez.class)) {

            if ((esMacho(a) && esMacho(b)) || (!esMacho(a) && !esMacho(b))) {
                a.setHaMuerto(true);
                b.setHaMuerto(true);
            }
        } else {
            if (a instanceof Tiburon && b instanceof Pez) {
                b.setHaMuerto(true);
            }
            if (a instanceof Pez && b instanceof Tiburon) {
                a.setHaMuerto(true);
            }
        }
    }

    /**
     * 
     * @param a
     *            pez a.
     * @param b
     *            pez b.
     * @return retorna true si hay qe crear un nuevo pez.
     */
    private boolean hayQueProcrear(final Pez a, final Pez b) {

        if ((a instanceof Tiburon && b instanceof Tiburon)
                || (a.getClass() == Pez.class && b.getClass() == Pez.class)) {

            return ((esMacho(a) && !esMacho(b)) || (!esMacho(a) && esMacho(b)));
        }
        return false;
    }

    /**
     * 
     * @param a
     *            pez a.
     * @param b
     *            pez b.
     * @return retorna true si los peces colisionan.
     */
    public final boolean colisionan(final Pez a, final Pez b) {
        return (!a.getPosicion().equals(b.getPosicion())
                && a.getPosicion().intersects(b.getPosicion()));
    }

    /**
     * 
     * @param p
     *            pez quien se le comprobara su genero.
     * @return retorna true si un pez es macho.
     */
    public final boolean esMacho(final Pez p) {
        return (p.getGenero().equals(MACHO));
    }

    /**
     * Gira las imagenes de los peces segun la direccion obtenida.
     * 
     * @param p
     *            pez a girar.
     */
    public final void girarImagenSegunDireccion(final Pez p) {
        if (p.getDireccion() == 1) {
            p.flipX();
            p.flipX();
        }
        if (p.getDireccion() == 2) {
            p.flipY();
        }
        if (p.getDireccion() == TRES) {
            p.flipX();
        }
        if (p.getDireccion() == CUATRO) {
            p.flipY2();
        }
    }

    /**
     * Metodo para darle direccion a un pez de forma aleatoria.
     * 
     * @param p
     *            pez como parametro para dar direccion.
     */
    public final void darDireccion(final Pez p) {
        p.setDireccion(Helper.rand(1, CUATRO));
    }

    /**
     * Metodo para mover los peces segun su direccion y cuantas posiciones
     * movera.
     * 
     * @param p
     *            pez a mover.
     * @param pos
     *            cuantos pixeles movera.
     */
    public final void moverPeces(final Pez p, final double pos) {
        if (p.getDireccion() == 1) {
            p.mover(-pos, 0);
        }
        if (p.getDireccion() == 2) {
            p.mover(0, -pos);
        }
        if (p.getDireccion() == TRES) {
            p.mover(pos, 0);
        }
        if (p.getDireccion() == CUATRO) {
            p.mover(0, pos);
        }

    }

    /**
     * 
     * @param i
     *            posicion en la lista de peces.
     * @return retorna un pez a partir de su posicion en la lista.
     */
    public final Pez getPez(final int i) {
        return peces.get(i);
    }

    /**
     * 
     * @param p
     *            pez a remover de la lista.
     */
    public final void remover(final Pez p) {
        peces.remove(p);
    }

    /**
     * @return the peces
     */
    public final List<Pez> getPeces() {
        return peces;
    }

    /**
     * @param peces
     *            the peces to set
     */
    public final void setPeces(final List<Pez> peces) {
        this.peces = peces;
    }
}
