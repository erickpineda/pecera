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
     * Cantidad minima y maxima de peces, para crear de forma aleatoria.
     */
    private static final int MIN_P = 20, MAX_P = 25;
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
     * Cantidad de peces que se crearan de forma aleatoria.
     */
    private int cantidad;
    /**
     * Variable de pez macho.
     */
    private static final String MACHO = "macho";
    /**
     * Variable de pez hembra.
     */
    private static final String HEMBRA = "hembra";
    /**
     * Variable de un pez, cuyo genero es desconocido.
     */
    private static final String DESCONOCIDO = "desconocido";
    /**
     * ss.
     */
    private static final int TRES = 3, CUATRO = 4;

    /**
     * Arrat con los nombres de las imagenes.
     */
    private String[] imagenes;

    /**
     * Constructor a partir de un array de nombres de imagenes, cantidad de
     * peces a crear y el rectangulo de la pantalla, para saber las dimensiones
     * de esta.
     * 
     * @param noms
     *            array de nombres de imagenes.
     * @param cant
     *            cantidad de peces que se crearan.
     * @param r
     *            rectangulo con las dimensiones de la pantala.
     */
    public Pecera(final String[] noms, final int cant, final Rectangle r) {
        this.peces = new ArrayList<Pez>();
        this.imagenes = noms;
        setCantidad(cant);
        this.anchura = r.getWidth();
        this.altura = r.getHeight();
    }

    /**
     * Constructor a partir de un array de nombres de imagenes y el rectangulo
     * de la pantalla, para saber las dimensiones de esta. La cantidad de peces
     * se crearan de manera aleatoria a partir de {@code MAX_P} y {@code MIN_P}.
     * 
     * @param noms
     *            array de nombres de imagenes.
     * @param r
     *            rectangulo con las dimensiones de la pantala.
     */
    public Pecera(final String[] noms, final Rectangle r) {
        this.peces = new ArrayList<Pez>();
        this.imagenes = noms;
        this.setCantidad(getAleatorio(MIN_P, MAX_P));
        this.anchura = r.getWidth();
        this.altura = r.getHeight();
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
     * @return retorna la clase Pecera que se crea en ese momento.
     */
    public final Pecera crear() {
        for (int i = 0; i < cantidad; i++) {
            addToList(crearUnPezCompleto());
        }
        return this;
    }

    /**
     * 
     * @return retorna un pez creado aleatoriamente.
     */

    public final Pez crearUnPez() {
        String nombre = imagenes[getAleatorio(1, imagenes.length) - 1];
        return definirGenero(nombre);
    }

    /**
     * 
     * @param nombre
     *            nombre de la imagen para crear el pez.
     * @return retorna un pez creado aleatoriamente a partir de un nombre de
     *         imagen.
     */
    public final Pez crearUnPez(final String nombre) {
        return definirGenero(nombre);
    }

    /**
     * 
     * @return retorna un pez con genero, direccion, posicion etc.
     */
    public final Pez crearUnPezCompleto() {
        Pez nemo = crearUnPez();
        darDireccion(nemo);
        girarImagenSegunDireccion(nemo);
        return nemo;
    }

    /**
     * Crea un pez completo a partir de un nombre de imagen dada.
     * 
     * @param nombre
     *            nombre de la imagen del pez a crear.
     * 
     * @return retorna un pez con genero, direccion, posicion etc.
     */
    public final Pez crearUnPezCompleto(final String nombre) {
        Pez nemo = crearUnPez(nombre);
        darDireccion(nemo);
        girarImagenSegunDireccion(nemo);
        return nemo;
    }

    /**
     * 
     * @param nombreImg
     *            nombre de la imagen.
     * @return retorna un pez con un genero definido.
     */
    private Pez definirGenero(final String nombreImg) {
        Pez p = null;

        if (nombreImg.contains(MACHO)) {
            p = new Pez(nombreImg, MACHO);
        }
        if (nombreImg.contains(HEMBRA)) {
            p = new Pez(nombreImg, HEMBRA);
        }
        if (!nombreImg.contains(MACHO) && !nombreImg.contains(HEMBRA)) {
            p = new Pez(nombreImg, DESCONOCIDO);
        }

        return p;
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
        p.posicionar(getAleatorio(0, anchura - p.getWidth()),
                getAleatorio(0, altura - p.getHeight()));
    }

    /**
     * Metodo que posiciona un pez a un costado de la pantalla.
     * 
     * @param p
     *            pez que se posicionara al costado segun su direccion.
     */
    private void posicionarAlCostado(final Pez p) {
        if (p.getDireccion() == 1) {
            p.posicionar(anchura - p.getWidth(), getAleatorio(0, altura));
        }
        if (p.getDireccion() == 2) {
            p.posicionar(getAleatorio(0, anchura), altura - p.getHeight());
        }
        if (p.getDireccion() == TRES) {
            p.posicionar(0 - p.getWidth(), getAleatorio(0, altura));
        }
        if (p.getDireccion() == CUATRO) {
            p.posicionar(getAleatorio(0, anchura), 0 - p.getHeight());
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
            if (hayQueEliminar(a, b)) {
                a.setHaMuerto(true);
                b.setHaMuerto(true);
            }
            if (hayQueProcrear(a, b)) {
                a.setPuedeReproducir(true);
                b.setPuedeReproducir(false);
            }
        } else {
            crearPezBebe(a, b);
        }
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
            b = crearUnPezCompleto();
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
     * @return retorna true si se debe destruir el pez.
     */
    private boolean hayQueEliminar(final Pez a, final Pez b) {
        return (esMacho(a) && esMacho(b) || !esMacho(a) && !esMacho(b));
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
        return (esMacho(a) && !esMacho(b) || !esMacho(a) && esMacho(b));
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
        p.setDireccion(getAleatorio(1, CUATRO));
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
     * 
     * @param desde
     *            numero minimo.
     * @param hasta
     *            numero maximo.
     * @return retorna un numero aleatorio apartir de un minimo y un maximo.
     */
    private int getAleatorio(final double desde, final double hasta) {
        return (int) (Math.random() * (hasta - desde + 1) + desde);
    }

    /**
     * @param cant
     *            the cantidad to set
     */
    public final void setCantidad(final int cant) {
        this.cantidad = cant;
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
        // peces this
        this.peces = peces;
    }
}
