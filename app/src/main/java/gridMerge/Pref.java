package gridMerge;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class Pref {

    // Class
    private static final Pref instance = new Pref();
    public static Pref getInstance() { return instance; }
    private Pref() {}



    // Fields
    public static String PATH = Paths.get("").toAbsolutePath().toString();
    private int x, y;
    private List<String> list = new ArrayList<>();
    private boolean isH; // Horizontal = true, Vertical = false
    private String resultInfo; // Result info as String
    private String outputExt;



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
    public List<String> getList() {
        return list;
    }
    public void setList(List<String> list) {
        list.sort(Comparator.naturalOrder()); // Asc ordering
        this.list = list;
    }
    public boolean getIsH() {
        return isH;
    }
    public void setIsH(boolean h) {
        isH = h;
    }
    public String getOutputExt() {
        return outputExt;
    }
    public void setOutputExt(String setOutputExt) {
        this.outputExt = setOutputExt;
    }
    public String getResultInfo() {
        return resultInfo;
    }
    public void setResultInfo(String resultInfo) {
        this.resultInfo = resultInfo;
    }

}
