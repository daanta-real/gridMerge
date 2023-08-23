package imageMerge;

import java.nio.file.Paths;
import java.util.Set;
import java.util.TreeSet;

public final class Pref {

    // Class
    private static final Pref instance = new Pref();
    public static Pref getInstance() { return instance; }
    private Pref() {}



    // Fields
    public static String PATH = Paths.get("").toAbsolutePath().toString();
    private int x, y;
    private Set<String> list = new TreeSet<>();
    private boolean isH; // Horizontal = true, Vertical = false
    private String resultInfo; // Result info as String



    // Getters, Setters

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
    public Set<String> getList() {
        return list;
    }
    public void setList(Set<String> list) {
        this.list = list;
    }
    public boolean getIsH() {
        return isH;
    }
    public void setIsH(boolean h) {
        isH = h;
    }
    public String getResultInfo() {
        return resultInfo;
    }
    public void setResultInfo(String resultInfo) {
        this.resultInfo = resultInfo;
    }

}
