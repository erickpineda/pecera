package pecera;

public class Helper {
    public Helper() {

    }

    public static int rand(int desde, int hasta) {
        return (int) (Math.random() * (hasta - desde + 1) + desde);
    }

    public static String split(String text) {
        String[] array = text.split(text);
        return new String(array[0]);
    }
}
