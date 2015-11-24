package pecera;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import acm.program.GraphicsProgram;

/**
 * @author Erick Pineda
 */
public class App extends GraphicsProgram {
    /**
     * ID de la clase.
     */
    private static final long serialVersionUID = -6604079658293550567L;
    /**
     * Nombres de imagenes a usar.
     */
    /**
     * Variable de pez macho.
     */
    private static final String MACHO = "macho";
    /**
     * Variable de pez hembra.
     */
    private static final String HEMBRA = "hembra";
    /**
     * Anchura por defecto de la pantalla.
     */
    private static final int WIDTH = 1100;
    /**
     * Altura por defecto de la pantalla.
     */
    private static final int HEIGHT = 600;
    /**
     * Imagenes para los tiburones.
     */
    private String[] images = { "macho_pez.png", "hembra_pez.png",
            "macho_tiburon.png", "hembra_tiburon.png", "macho_delfin.png",
            "hembra_delfin.png", "macho_pulpo.png", "hembra_pulpo.png",
            "macho_tortuga.png", "hembra_tortuga.png" };
    /**
     * Pecera.
     */
    private Pecera pecera;
    /**
     * Numero de tiburones a crear.
     */
    private static final int N_TIBURONES = 3;
    /**
     * Numero de peces a crear.
     */
    private static final int N_PECES = 40;
    /**
     * Numero de delfines a crear.
     */
    private static final int N_DELFINES = 4;
    /**
     * Numero de pulpos a crear.
     */
    private static final int N_PULPOS = 3;
    /**
     * Numero de tortugas a crear.
     */
    private static final int N_TORTUGAS = 5;
    /**
     * Cuantos milisegundos pausara el juego.
     */
    private static final int PAUSA = 50;

    /**
     * @param args
     *            args
     */
    public static void main(String[] args) {
        new App().start(args);
    }

    /**
     * Metodo principal del programa.
     */
    public final void run() {

        comprobarPecera();
    }

    /**
     * Metodo inicial del programa.
     */
    public final void init() {
        pantalla();
        crearPecera();
    }

    /**
     * Reunira todos los metodos necesarios para poner en funcionamiento la
     * pecera.
     */
    private void crearPecera() {
        pecera = new Pecera(getBounds());
        pecera.agregarPeces(tiburones());
        pecera.agregarPeces(peces());
        pecera.agregarPeces(delfines());
        pecera.agregarPeces(pulpos());
        pecera.agregarPeces(tortugas());
        pecera.definirDestino();
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
            // Siempre que el pez no este declarado muerto
            if (!a.estaMuerto()) {
                // (pecera.cantidadPeces() == 2) {
                pecera.noExcedeLimitesDePantalla(a);
                // } else {
                // pecera.excedeLimitesDePantalla(a);
                // }
                pecera.moverPeces(a, 1);
                pecera.comprobarColisiones(a);
            } else {
                it.remove();
                remove(a.getImagen());
            }
        }
    }

    /**
     * @return retorna una lista de peces tiburones.
     */
    private List<Pez> tiburones() {
        List<Pez> tiburones = new ArrayList<Pez>();

        for (int i = 0; i < N_TIBURONES; i++) {
            Tiburon t = null;

            if (i % 2 == 0) {
                t = new Tiburon(images[2], MACHO, Helper.rand(1, 4));
            } else {
                t = new Tiburon(images[3], HEMBRA, Helper.rand(1, 4));
            }
            tiburones.add(t);
            add(t.getImagen());
        }

        return tiburones;
    }

    /**
     * @return retorna una lista de peces normales.
     */
    private List<Pez> peces() {
        List<Pez> peces = new ArrayList<Pez>();

        for (int i = 0; i < N_PECES; i++) {
            Pez p = null;
            if (i % 2 == 0) {
                p = new Pez(images[0], MACHO, Helper.rand(1, 4));
            } else {
                p = new Pez(images[1], HEMBRA, Helper.rand(1, 4));
            }
            peces.add(p);
            add(p.getImagen());
        }
        return peces;
    }

    /**
     * @return retorna una lista de delfines.
     */
    private List<Pez> delfines() {
        List<Pez> delfines = new ArrayList<Pez>();

        for (int i = 0; i < N_DELFINES; i++) {
            Pez p = null;
            if (i % 2 == 0) {
                p = new Delfin(images[4], MACHO, Helper.rand(1, 4));
            } else {
                p = new Delfin(images[5], HEMBRA, Helper.rand(1, 4));
            }
            delfines.add(p);
            add(p.getImagen());
        }
        return delfines;
    }

    /**
     * @return retorna una lista de pulpos.
     */
    private List<Pez> pulpos() {
        List<Pez> pulpos = new ArrayList<Pez>();

        for (int i = 0; i < N_DELFINES; i++) {
            Pez p = null;
            if (i % 2 == 0) {
                p = new Pulpo(images[6], MACHO, Helper.rand(1, 4));
            } else {
                p = new Pulpo(images[7], HEMBRA, Helper.rand(1, 4));
            }
            pulpos.add(p);
            add(p.getImagen());
        }
        return pulpos;
    }

    /**
     * @return retorna una lista de tortugas.
     */
    private List<Pez> tortugas() {
        List<Pez> tortugas = new ArrayList<Pez>();

        for (int i = 0; i < N_DELFINES; i++) {
            Pez p = null;
            if (i % 2 == 0) {
                p = new Tortuga(images[8], MACHO, Helper.rand(1, 4));
            } else {
                p = new Tortuga(images[9], HEMBRA, Helper.rand(1, 4));
            }
            tortugas.add(p);
            add(p.getImagen());
        }
        return tortugas;
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
