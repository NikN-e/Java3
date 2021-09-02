import java.util.ArrayList;
import java.util.Arrays;

public class App {
    public static void main(String[] args) {
        String[] stringArr = new String[2];
        stringArr[0] = "a";
        stringArr[1] = "b";

        //System.out.println(Arrays.toString(stringArr));
        //ArraySwap.arraySwap(stringArr, 0, 1);
        // System.out.println(Arrays.toString(stringArr));

        ArrayList<String> arrayList = toArrayList(stringArr);

        Apple apple1 = new Apple();
        Apple apple2 = new Apple();
        Apple apple3 = new Apple();

        Orange orange = new Orange();
        Orange orange1 = new Orange();

        Box<Apple> box1 = new Box<>(apple1, apple2, apple3);
        Box<Orange> box2 = new Box<>(orange,orange1);

        System.out.println(box1.compare(box2));

        Box<Orange> box3 = new Box<>();
        box2.transfer(box3);



    }

    public static <T> ArrayList<T> toArrayList(T[] arr){
        return new ArrayList<T>(Arrays.asList(arr));
    }
}
