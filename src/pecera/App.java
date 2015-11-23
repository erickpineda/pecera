package pecera;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import java.util.Iterator;

import acm.program.GraphicsProgram;

/**
 * 
 * @author Erick Pineda
 *
 */
public class App extends GraphicsProgram {
    /**
     * ID de la clase.
     */
    private static final long serialVersionUID = -6604079658293550567L;
    /**
     * Anchura por defecto de la pantalla.
     */
    private static final int WIDTH = 1100;
    /**
     * Altura por defecto de la pantalla.
     */
    private static final int HEIGHT = 600;
    /**
     * Imagenes a usar.
     */
    private String[] array = { "macho_pez.png", "hembra_pez.png" };
    /**
     * Pecera.
     */
    private Pecera pecera;
    /**
     * Cantidad fija de peces a crear.
     */
    private static final int CANTIDAD = 25;
    /**
     * Cuantos milisegundos pausara el juego.
     */
    private static final int PAUSA = 50;

    /**
     * @param args args
     */
    public static void main(String[] args) {
        new App().start(args);
    }

    /**
     * Metodo principal del programa.
     */
    public final void run() {

        pintarImagenes();
        comprobarPecera();
    }

    /**
     * Metodo inicial del programa.
     */
    public final void init() {
        pantalla();
        pecera = new Pecera(array, CANTIDAD, getBounds()).crear();
        pecera.posicionInicial();
    }

    /**
     * Comprueba que hayan peces en la pecera.
     */
    private void comprobarPecera() {

        while (pecera.hayPeces()) {
            eliminarPeces();
            crearPecesBebes();
            pause(PAUSA);
        }

    }

    /**
     * Metodo que itera una lista de peces y va eliminando los que van muriendo.
     */
    private void eliminarPeces() {

        for (Iterator<Pez> it = pecera.getPeces().iterator(); it.hasNext();) {
            Pez a = it.next();

            if (pecera.cantidadPeces() == 2) {
                pecera.noExcedeLimitesDePantalla(a);
            }
            // Siempre que el pez no este declarado muerto
            if (!a.estaMuerto()) {
                pecera.moverPeces(a, 1);
                pecera.excedeLimitesDePantalla(a);
                pecera.comprobarColisiones(a);
            } else {
                it.remove();
                remove(a.getImagen());
            }
        }
    }

    /**
     * Metodo para agregar al canvas los peces bebes y a la lista de peces.
     */
    private void crearPecesBebes() {
        pecera.getBebes().forEach(b -> add(b.getImagen()));
        pecera.getPeces().addAll(pecera.getBebes());
        pecera.getBebes().clear();
    }

    /**
     * Metodo que recorre las imagenes creadas de los peces y las pinta por
     * pantalla.
     */
    private void pintarImagenes() {
        pecera.getPeces().forEach(p -> add(p.getImagen()));
    }

    /**
     * Imagen de fondo en la pantalla.
     */
    // private void fondo() {
    // GImage f = new GImage("");
    // f.sendToBack();
    // f.setBounds(0, 0, getWidth(), getHeight());
    // add(f);
    // }
    /**
     * Poner la pantalla completa, al iniciar el juego.
     */
    private void pantalla() {
        try {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

            // height of the task bar
            Insets scnMax = Toolkit.getDefaultToolkit()
                    .getScreenInsets(getGraphicsConfiguration());
            int taskBarSize = scnMax.bottom;

            // available size of the screen
            setSize(screenSize.width, screenSize.height - taskBarSize);
            setLocation(screenSize.width - getWidth(),
                    screenSize.height - taskBarSize - getHeight());
        } catch (Exception e) {
            setSize(WIDTH, HEIGHT);
        }
    }
}
