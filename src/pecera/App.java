package pecera;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import acm.graphics.GImage;
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
    private String[] images = { "macho_pez.png", "hembra_pez.png", "macho_tiburon.png",
            "hembra_tiburon.png", "macho_delfin.png", "hembra_delfin.png", "macho_pulpo.png",
            "macho_tortuga.png" };
    /**
     * Imagen para el fondo de la pantalla.
     */
    private static final String IMG_FONDO = "fondo2.jpg";
    /**
     * Pecera.
     */
    private Pecera pecera;
    /**
     * Lista de animales.
     */
    private List<Animal> animales = new ArrayList<Animal>();
    /**
     * Numero de tiburones a crear.
     */
    private static final int N_TIBURONES = 3;
    /**
     * Numero de peces a crear.
     */
    private static final int N_PECES = 60;
    /**
     * Numero de delfines a crear.
     */
    private static final int N_DELFINES = 10;
    /**
     * Numero de pulpos a crear.
     */
    private static final int N_PULPOS = 5;
    /**
     * Numero de tortugas a crear.
     */
    private static final int N_TORTUGAS = 10;
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
        fondoPantalla(IMG_FONDO);
        crearPecera();
    }

    /**
     * Reunira todos los metodos necesarios para poner en funcionamiento la
     * pecera.
     */
    private void crearPecera() {
        pecera = new Pecera(getBounds());
        pecera.agregarAnimales(crearListaAnimales());
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

        for (Iterator<Animal> it = pecera.getAnimales().iterator(); it.hasNext();) {
            Animal a = it.next();
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
     * Metodo para agregar al canvas los peces bebes y a la lista de peces.
     */
    private void crearPecesBebes() {
        pecera.getBebes().forEach(b -> add(b.getImagen()));
        pecera.getAnimales().addAll(pecera.getBebes());
        pecera.getBebes().clear();
    }

    /**
     * Crea una lista de animales a partir de su clase y una cantidad.
     * 
     * @param clase
     *            clase del animal a crear.
     * @param total
     *            cantidad de animales a crear.
     */
    private void crearAnimales(Class<? extends Animal> clase, int total) {

        for (int i = 0; i < total; i++) {
            Animal an = null;
            an = creaUnAnimal(clase);
            animales.add(an);
            add(an.getImagen());
        }
    }

    /**
     * Crea un animal segun la clase que pasa por parametro.
     * 
     * @param clase
     *            clase del animal a crear.
     * @return retorna un animal segun su clase.
     */
    private Animal creaUnAnimal(Class<? extends Animal> clase) {
        String imagen = "";

        if (clase.isAssignableFrom(Pez.class)) {
            imagen = images[Helper.rand(0, 1)];
            return new Pez(imagen, defineGen(imagen));
        }
        if (clase.isAssignableFrom(Tiburon.class)) {
            imagen = images[Helper.rand(2, 3)];
            return new Tiburon(imagen, defineGen(imagen));
        }
        if (clase.isAssignableFrom(Delfin.class)) {
            imagen = images[Helper.rand(4, 5)];
            return new Delfin(imagen, defineGen(imagen));
        }
        if (clase.isAssignableFrom(Pulpo.class)) {
            imagen = images[6];
            return new Pulpo(imagen, defineGen(imagen));
        }
        if (clase.isAssignableFrom(Tortuga.class)) {
            imagen = images[7];
            return new Tortuga(imagen, defineGen(imagen));
        }
        return null;
    }

    /**
     * Define el gen del animal a partir del nombre de la imagen,
     * obligatoriamente debe llevar 'macho_' o 'hembra_' al inicio del nombre.
     * 
     * @param gen
     *            String que define el gen del animal.
     * @return retorna un String con el gen del animal.
     */
    private String defineGen(String gen) {
        if (gen.contains(MACHO)) {
            gen = MACHO;
        }
        if (gen.contains(HEMBRA)) {
            gen = HEMBRA;
        }
        return gen;
    }

    /**
     * 
     * @return retorna una lista de peces creados a partir de sus clases.
     */
    public List<Animal> crearListaAnimales() {
        crearAnimales(Pez.class, N_PECES);
        crearAnimales(Tiburon.class, N_TIBURONES);
        crearAnimales(Delfin.class, N_DELFINES);
        crearAnimales(Pulpo.class, N_PULPOS);
        crearAnimales(Tortuga.class, N_TORTUGAS);
        return animales;
    }

    /**
     * @param imagenFondo
     *            sera la imagen de fondo que llevara la pantalla.
     */
    public void fondoPantalla(String imagenFondo) {
        GImage fondo = new GImage(imagenFondo);
        fondo.setBounds(0, 0, getWidth(), getHeight());
        add(fondo);
    }

    /**
     * Poner la pantalla completa, al iniciar el juego.
     */
    private void pantalla() {
        try {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

            // height of the task bar
            Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
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
