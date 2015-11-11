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
	 * 
	 */
	private static final long serialVersionUID = -6604079658293550567L;
	/**
	 * Imagenes a usar.
	 */
	private String[] array = { "macho_pez.png", "hembra_pez.png" };
	/**
	 * Pecera.
	 */
	private Pecera pecera;

	public void run() {

		pintarImagenes();
		comprobarPecera();
	}

	public void init() {
		// this.setSize(ANCHURA, ALTURA);
		fullScreen();
		pecera = new Pecera(array, 25, getBounds()).crear();
		pecera.posicionInicial();
	}

	/**
	 * Comprueba que hayan peces en la pecera.
	 */
	private void comprobarPecera() {

		while (!pecera.hayPeces()) {
			colisiones();
			pause(20);
		}

	}

	/**
	 * Metodo que itera una lista de peces y va eliminando los que van muriendo.
	 */
	private void colisiones() {
		for (Iterator<Pez> it = pecera.getPeces().iterator(); it.hasNext();) {
			Pez a = it.next();
			// Siempre que el pez no este declarado muerto
			if (!a.estaMuerto()) {
				pecera.moverPeces(a, 1);
				pecera.noExcedeLimitesDePantalla(a);
				pecera.comprobarColisiones(a);
			} else {
				it.remove();
				remove(a.getImagen());
			}
		}
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
	private void fullScreen() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		// height of the task bar

		Insets scnMax = Toolkit.getDefaultToolkit()
				.getScreenInsets(getGraphicsConfiguration());
		int taskBarSize = scnMax.bottom;

		// available size of the screen

		setSize(screenSize.width, screenSize.height - taskBarSize);
		setLocation(screenSize.width - getWidth(),
				screenSize.height - taskBarSize - getHeight());
	}
}
