package pecera;

public class Helper {
    private Helper() {

    }

    public static int rand(int desde, int hasta) {
        return (int) (Math.random() * (hasta - desde + 1) + desde);
    }
}