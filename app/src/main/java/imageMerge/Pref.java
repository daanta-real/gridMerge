package imageMerge;

public final class Pref {

    private static final Pref instance = new Pref();
    public static Pref getInstance() { return instance; }
    private Pref() {}
    public int x, y;
    boolean isH; // is Horizontal?

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isH() {
        return isH;
    }

    public void setIsH(boolean h) {
        isH = h;
    }

}
