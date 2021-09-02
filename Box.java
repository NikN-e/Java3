import java.util.ArrayList;
import java.util.Arrays;

public class Box<T extends Fruit> {
    private ArrayList<T> items;

    public Box(T... items) {
        this.items = new ArrayList<>(Arrays.asList(items));
    }

    public void add(T... items){
        this.items.addAll(Arrays.asList(items));
    }

    public void remove(T... items){
        for(T item:items) this.items.remove(item);
    }

    public ArrayList<T> getItems(){
        return new ArrayList<T>(items);
    }

    public double getWeigth(){
        if(items.size() == 0) return 0;
        double weigth = 0;
        for(T item: items) weigth += item.getWeigth();
        return weigth;
    }

    public boolean compare(Box box){
        return this.getWeigth() == box.getWeigth();
    }

    public void transfer(Box<? super T> box){
        box.items.addAll(this.items);
        items.clear();
    }
}
