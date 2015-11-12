package pecera;

import acm.graphics.GImage;
import acm.graphics.GRectangle;

/**
 * 
 * @author Erick Pineda.
 *
 */
public class Pez {
    /**
     * Velocidad del pez.
     */
    private static final int VELOCIDAD_PEZ = getAleatorio(3, 6);
    /**
     * Sera el nombre de la imagen.
     */
    private String nombreImg;
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
    private int direccion = 1;
    /**
     * Variable para saber si un pez muere o no.
     */
    private boolean haMuerto = false;
    /**
     * Variable para saber si un pez puede reproducirse con otro o no.
     */
    private boolean puedeReproducir = false;

    /**
     * Constructor de un pez a partir de un nombre de imagen.
     * 
     * @param nombre
     *            nombre de la imagen.
     */
    public Pez(final String nombre) {
        this.setNombreImg(nombre);
        this.setImagen(new GImage(nombre));
    }

    /**
     * 
     * @param img
     *            GImage del pez a crear.
     */
    public Pez(final GImage img) {
        this.setImagen(img);
    }

    /**
     * Constructor de un pez a partir de un nombre de imagen y su genero.
     * 
     * @param nombre
     *            nombre de la imagen.
     * @param gen
     *            genero del pez.
     */
    public Pez(final String nombre, final String gen) {
        this.setNombreImg(nombre);
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
    public Pez(final String nombre, final String gen, final int dir) {
        this.setNombreImg(nombre);
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
    public final void posicionar(final double x, final double y) {
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
    public final void mover(final double x, final double y) {
        this.imagen.move(x * VELOCIDAD_PEZ, y * VELOCIDAD_PEZ);
    }

    /**
     * Girar la imagen del pez en forma horizontal.
     */
    public final void flipX() {
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
    public final void flipY() {
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
    public final void flipX2() {
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
    public final void flipY2() {
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
     * @param desde
     *            numero minimo.
     * @param hasta
     *            numero maximo.
     * @return retorna un numero aleatorio.
     */
    private static int getAleatorio(final double desde, final double hasta) {
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
    public final double getWidth() {
        return imagen.getWidth();
    }

    /**
     * 
     * @return retorna la altura de la imagen del pez.
     */
    public final double getHeight() {
        return imagen.getHeight();
    }

    /**
     * 
     * @return retorna la posicion inicial del eje X de la imagen.
     */
    public final double getX() {
        return getPosicion().getX();
    }

    /**
     * 
     * @return retorna la posicion inicial del eje Y de la imagen.
     */
    public final double getY() {
        return getPosicion().getY();
    }

    /**
     * 
     * @return retorna la posicion final del eje X de la imagen.
     */
    public final double getPosFinalX() {
        return getX() + getWidth();
    }

    /**
     * 
     * @return retorna la posicion final del eje Y de la imagen.
     */
    public final double getPosFinalY() {
        return getY() + getHeight();
    }

    /**
     * @return the nombreImg
     */
    public final String getNombreImg() {
        return nombreImg;
    }

    /**
     * @param nombre
     *            the nombreImg to set
     */
    public final void setNombreImg(final String nombre) {
        this.nombreImg = nombre;
    }

    /**
     * @return the imagen
     */
    public final GImage getImagen() {
        return imagen;
    }

    /**
     * @param imagen
     *            the imagen to set
     */
    public final void setImagen(final GImage imagen) {
        this.imagen = imagen;
    }

    /**
     * @return the genero
     */
    public final String getGenero() {
        return genero;
    }

    /**
     * @param genero
     *            the genero to set
     */
    public final void setGenero(final String genero) {
        this.genero = genero;
    }

    /**
     * @return the direccion
     */
    public final int getDireccion() {
        return direccion;
    }

    /**
     * @param direccion
     *            the direccion to set
     */
    public final void setDireccion(final int direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the haMuerto
     */
    public final boolean estaMuerto() {
        return haMuerto;
    }

    /**
     * @param haMuerto
     *            the haMuerto to set
     */
    public final void setHaMuerto(final boolean haMuerto) {
        this.haMuerto = haMuerto;
    }

    /**
     * @return the puedeReproducir
     */
    public final boolean isPuedeReproducir() {
        return puedeReproducir;
    }

    /**
     * @param puede
     *            the puedeReproducir to set
     */
    public final void setPuedeReproducir(final boolean puede) {
        this.puedeReproducir = puede;
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
                + getX() + ", getY()=" + getY() + ", getPosFinalX()="
                + getPosFinalX() + ", getPosFinalY()=" + getPosFinalY()
                + ", getImagen()=" + getImagen() + ", getGenero()="
                + getGenero() + ", getDireccion()=" + getDireccion()
                + ", estaMuerto()=" + estaMuerto() + "]";
    }
}
