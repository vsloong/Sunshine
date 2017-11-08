package cn.edu.zstu.sunshine.entity;

/**
 * Created by CooLoongWu on 2017-11-8 15:35.
 */

public class Ordinal {

    private int id;
    private boolean isSelected;

    public Ordinal(int id, boolean isSelected) {
        this.id = id;
        this.isSelected = isSelected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
