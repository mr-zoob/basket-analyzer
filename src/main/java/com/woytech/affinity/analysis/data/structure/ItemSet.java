package com.woytech.affinity.analysis.data.structure;

import java.util.ArrayList;
import java.util.List;

public class ItemSet {

    private int level;
    private List<Item> items;

    public ItemSet(int level, List<Item> items) {
        this.level = level;
        this.items = items != null ? items : new ArrayList<>();
    }

    public ItemSet(int level) {
        this.level = level;
        this.items = new ArrayList<>();
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    @Override
    public String toString() {
        return "ItemSet{" +
                "level=" + level +
                ", items=" + items +
                '}';
    }

}
