package pecera;

import acm.graphics.GImage;
import acm.graphics.GRectangle;

public class Pez {
	/**
	 * Velocidad del pez.
	 */
	private static final int VELOCIDAD_PEZ = getAleatorio(3, 6);
	/**
	 * Imagen del pez.
	 */
	private GImage imagen;
	/**
	 * Genero.
	 */
	private String genero;
	/**
	 * Direccion que tendra el pez.
	 */
	private int direccion;
	/**
	 * Variable para saber si un pez muere o no.
	 */
	private boolean haMuerto;

	/**
	 * Constructor de un pez a partir de un nombre de imagen.
	 * 
	 * @param nombre
	 *            nombre de la imagen.
	 */
	public Pez(String nombre) {
		this.setImagen(new GImage(nombre));
	}

	/**
	 * Constructor de un pez a partir de un nombre de imagen y su genero.
	 * 
	 * @param nombre
	 *            nombre de la imagen.
	 * @param gen
	 *            genero del pez.
	 */
	public Pez(String nombre, String gen) {
		this.setImagen(new GImage(nombre));
		this.setGenero(gen);
	}

	/**
	 * Constructor de un pez a partir de un nombre de imagen, su genero y en que
	 * direccion va a ir.
	 * 
	 * @param nombre
	 *            nombre de la imagen del pez.
	 * @param gen
	 *            genero del pez.
	 * @param dir
	 *            direccion.
	 */
	public Pez(String nombre, String gen, int dir) {
		this.setImagen(new GImage(nombre));
		this.setGenero(gen);
		this.setDireccion(dir);
	}

	/**
	 * Posiciona el pez segun los ejes X e Y.
	 * 
	 * @param x
	 *            eje X.
	 * @param y
	 *            eje Y.
	 */
	public void posicionar(double x, double y) {
		this.imagen.setLocation(x, y);
	}

	/**
	 * Mueve el pez segun los ejes X e Y.
	 * 
	 * @param x
	 *            eje X.
	 * @param y
	 *            eje Y.
	 */
	public void mover(double x, double y) {
		this.imagen.move(x * VELOCIDAD_PEZ, y * VELOCIDAD_PEZ);
	}

	/**
	 * Girar la imagen del pez en forma horizontal.
	 */
	public void flipX() {
		int[][] array = imagen.getPixelArray();
		int height = array.length;
		int width = array[0].length;

		int[][] newpixels = new int[height][width];

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// int x = width - j - 1;
				// int temp = array[i][j];
				// array[i][j] = array[i][x];
				// array[i][x] = temp;
				newpixels[i][width - j - 1] = array[i][j];
			}
		}
		imagen.setImage(new GImage(newpixels).getImage());
	}

	/**
	 * Gira la imagen 90 grados.
	 */
	public void flipY() {
		int[][] array = imagen.getPixelArray();
		int height = array.length;
		int width = array[0].length;

		// Intercambiar el width por el height
		int[][] newpixels = new int[width][height];

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				newpixels[j][height - 1 - i] = array[i][j];
			}
		}
		imagen.setImage(new GImage(newpixels).getImage());
	}

	/**
	 * Gira la imagen de arriba abajo.
	 */
	public void flipX2() {
		int[][] array = imagen.getPixelArray();
		int height = array.length;
		int width = array[0].length;

		int[][] newpixels = new int[height][width];

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				newpixels[height - 1 - i][j] = array[i][j];
			}
		}
		imagen.setImage(new GImage(newpixels).getImage());
	}

	/**
	 * Gira la imagen verticalmente.
	 */
	public void flipY2() {
		int[][] array = imagen.getPixelArray();
		int height = array.length;
		int width = array[0].length;

		// Intercambiar el width por el height
		int[][] newpixels = new int[width][height];

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				newpixels[width - j - 1][i] = array[i][j];
			}
		}
		imagen.setImage(new GImage(newpixels).getImage());
	}

	/**
	 * 
	 * @return retorna un numero aleatorio.
	 */
	private static int getAleatorio(double desde, double hasta) {
		return (int) (Math.random() * (hasta - desde + 1) + desde);
	}

	/**
	 * 
	 * @return retorna el rectangula de la imagen del pez.
	 */
	public final GRectangle getPosicion() {
		return imagen.getBounds();
	}

	/**
	 * 
	 * @return retorna la anchura de la imagen del pez.
	 */
	public double getWidth() {
		return imagen.getWidth();
	}

	/**
	 * 
	 * @return retorna la altura de la imagen del pez.
	 */
	public double getHeight() {
		return imagen.getHeight();
	}

	/**
	 * 
	 * @return retorna la posicion inicial del eje X de la imagen.
	 */
	public double getX() {
		return getPosicion().getX();
	}

	/**
	 * 
	 * @return retorna la posicion inicial del eje Y de la imagen.
	 */
	public double getY() {
		return getPosicion().getY();
	}

	/**
	 * 
	 * @return retorna la posicion final del eje X de la imagen.
	 */
	public double getPosFinalX() {
		return getX() + getWidth();
	}

	/**
	 * 
	 * @return retorna la posicion final del eje Y de la imagen.
	 */
	public double getPosFinalY() {
		return getY() + getHeight();
	}

	/**
	 * @return the imagen
	 */
	public GImage getImagen() {
		return imagen;
	}

	/**
	 * @param imagen
	 *            the imagen to set
	 */
	public void setImagen(GImage imagen) {
		this.imagen = imagen;
	}

	/**
	 * @return the genero
	 */
	public String getGenero() {
		return genero;
	}

	/**
	 * @param genero
	 *            the genero to set
	 */
	public void setGenero(String genero) {
		this.genero = genero;
	}

	/**
	 * @return the direccion
	 */
	public int getDireccion() {
		return direccion;
	}

	/**
	 * @param direccion
	 *            the direccion to set
	 */
	public void setDireccion(int direccion) {
		this.direccion = direccion;
	}

	/**
	 * @return the haMuerto
	 */
	public boolean estaMuerto() {
		return haMuerto;
	}

	/**
	 * @param haMuerto
	 *            the haMuerto to set
	 */
	public void setHaMuerto(boolean haMuerto) {
		this.haMuerto = haMuerto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Pez [getPosicion()=" + getPosicion() + ", getWidth()="
				+ getWidth() + ", getHeight()=" + getHeight() + ", getX()="
				+ getX() + ", getPosFinalX()=" + getPosFinalX()
				+ ", getImagen()=" + getImagen() + ", getGenero()="
				+ getGenero() + ", getDireccion()=" + getDireccion() + "]";
	}

}
