package pecera;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Pecera {
	/**
	 * Cantidad minima y maxima de peces, para crear de forma aleatoria.
	 */
	private static final int MIN_P = 20, MAX_P = 25;
	/**
	 * Altura de la pantalla.
	 */
	private double ALTURA;
	/**
	 * Anchura de la pantalla.
	 */
	private double ANCHURA;
	/**
	 * Lista de peces.
	 */
	private List<Pez> peces;
	/**
	 * Lista de peces nacidos a partir de las colisiones.
	 */
	private List<Pez> recienNacido = new ArrayList<Pez>();
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
	 * @param cantidad
	 *            cantidad de peces que se crearan.
	 * @param r
	 *            rectangulo con las dimensiones de la pantala.
	 */
	public Pecera(String[] noms, int cantidad, Rectangle r) {
		this.peces = new ArrayList<Pez>();
		this.imagenes = noms;
		this.setCantidad(cantidad);
		this.ANCHURA = r.getWidth();
		this.ALTURA = r.getHeight();
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
	public Pecera(String[] noms, Rectangle r) {
		this.peces = new ArrayList<Pez>();
		this.imagenes = noms;
		this.setCantidad(getAleatorio(MIN_P, MAX_P));
		this.ANCHURA = r.getWidth();
		this.ALTURA = r.getHeight();
	}

	/**
	 * Metodo para agregar un pez a la lista.
	 * 
	 * @param p
	 *            pez que se agregara a la lista.
	 */
	public void addToList(Pez p) {
		if (p != null)
			peces.add(p);
	}

	/**
	 * 
	 * @return retorna la clase Pecera que se crea en ese momento.
	 */
	public Pecera crear() {
		for (int i = 0; i < cantidad; i++) {
			addToList(crearUnPez());
			darDireccion(peces.get(i));
			girarImagenSegunDireccion(peces.get(i));
		}
		return this;
	}

	/**
	 * 
	 * @return retorna un pez creado aleatoriamente.
	 */

	public Pez crearUnPez() {
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
	public Pez crearUnPez(String nombre) {
		return definirGenero(nombre);
	}

	/**
	 * 
	 * @param nombreImg
	 *            nombre de la imagen.
	 * @return retorna un pez con un genero definido.
	 */
	private Pez definirGenero(String nombreImg) {
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
	public int cantidadPeces() {
		return peces.size();
	}

	/**
	 * Metodo que posiciona todos los peces en la pecera, al inicio del juego de
	 * forma aleatoria en la pantalla..
	 */
	public void posicionInicial() {
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
	private void posicionarAleatoriamente(Pez p) {
		p.posicionar(getAleatorio(0, ANCHURA - p.getWidth()),
				getAleatorio(0, ALTURA - p.getHeight()));
	}

	/**
	 * 
	 * @return retorna cierto o falso, si en la pecera hay mas de un pez.
	 */
	public boolean hayPeces() {

		if (cantidadPeces() > 1) {
			return false;
		}
		return true;
	}

	/**
	 * Metodo que va comprobando si un pez se sale de la pantalla.
	 * 
	 * @param a
	 *            parametro del pez a comprobar.
	 */
	public void noExcedeLimitesDePantalla(Pez a) {
		if (a.getX() <= 0) {
			a.setDireccion(3);
			a.flipX();
		}
		if (a.getPosFinalX() >= ANCHURA) {
			a.setDireccion(1);
			a.flipX();
		}
		if (a.getPosFinalY() >= ALTURA) {
			a.setDireccion(2);
			a.flipY();
			a.flipY();
		}
		if (a.getY() <= 0) {
			a.setDireccion(4);
			a.flipY2();
			a.flipY2();
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
	public void comprobarColisiones(Pez a) {

		for (Pez b : getPeces()) {
			if (colisionan(a, b)) {
				if (hayQueEliminar(a, b)) {
					a.setHaMuerto(true);
					b.setHaMuerto(true);
				}
				if (hayQueProcrear(a, b)) {
					// recienNacido.add(crearUnPez());
				}
			}
		}
	}

	/**
	 * FALTA IMPLENTAR CORRECTAMENTE ESTE METODO**
	 */
	public void posicionarRecienNacido() {
		for (Pez rn : recienNacidos()) {
			for (Pez b : getPeces()) {
				if (!colisionan(rn, b)) {
					rn.setDireccion(1);
					posicionarAleatoriamente(rn);
				}
			}
			addToList(rn);
		}
	}

	/**
	 * 
	 * @return retorna la lista de peces recien nacidos.
	 */
	public List<Pez> recienNacidos() {
		return recienNacido;
	}

	/**
	 * 
	 * @return retorna true si se debe destruir el pez.
	 */
	private boolean hayQueEliminar(Pez a, Pez b) {
		return (esMacho(a) && esMacho(b) || !esMacho(a) && !esMacho(b));
	}

	/**
	 * 
	 * @return retorna true si hay qe crear un nuevo pez.
	 */
	private boolean hayQueProcrear(Pez a, Pez b) {
		return (esMacho(a) && !esMacho(b) || !esMacho(a) && esMacho(b));
	}

	/**
	 * 
	 * @return retorna true si los peces colisionan.
	 */
	public boolean colisionan(Pez a, Pez b) {
		return (!a.getPosicion().equals(b.getPosicion())
				&& a.getPosicion().intersects(b.getPosicion()));
	}

	/**
	 * 
	 * @param p
	 *            pez quien se le comprobara su genero.
	 * @return retorna true si un pez es macho.
	 */
	public boolean esMacho(Pez p) {
		return (p.getGenero().equals(MACHO));
	}

	/**
	 * Gira las imagenes de los peces segun la direccion obtenida.
	 * 
	 * @param p
	 *            pez a girar.
	 */
	public void girarImagenSegunDireccion(Pez p) {
		if (p.getDireccion() == 1) {
			p.flipX();
			p.flipX();
		}
		if (p.getDireccion() == 2) {
			p.flipY();
		}
		if (p.getDireccion() == 3) {
			p.flipX();
		}
		if (p.getDireccion() == 4) {
			p.flipY2();
		}
	}

	/**
	 * 
	 * Metodo para darle direccion a un pez de forma aleatoria.
	 */
	public void darDireccion(Pez p) {
		p.setDireccion(getAleatorio(1, 4));
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
	public void moverPeces(Pez p, double pos) {
		if (p.getDireccion() == 1) {
			p.mover(-pos, 0);
		}
		if (p.getDireccion() == 2) {
			p.mover(0, -pos);
		}
		if (p.getDireccion() == 3) {
			p.mover(pos, 0);
		}
		if (p.getDireccion() == 4) {
			p.mover(0, pos);
		}

	}

	/**
	 * 
	 * @param i
	 *            posicion en la lista de peces.
	 * @return retorna un pez a partir de su posicion en la lista.
	 */
	public Pez getPez(int i) {
		return peces.get(i);
	}

	/**
	 * 
	 * @param p
	 *            pez a remover de la lista.
	 */
	public void remover(Pez p) {
		peces.remove(p);
	}

	/**
	 * 
	 * @return retorna un numero aleatorio apartir de un minimo y un maximo.
	 */
	private int getAleatorio(double desde, double hasta) {
		return (int) (Math.random() * (hasta - desde + 1) + desde);
	}

	/**
	 * @param cantidad
	 *            the cantidad to set
	 */
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * @return the peces
	 */
	public List<Pez> getPeces() {
		return peces;
	}

	/**
	 * @param peces
	 *            the peces to set
	 */
	public void setPeces(List<Pez> peces) {
		this.peces = peces;
	}
}
