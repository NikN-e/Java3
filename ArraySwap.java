public class ArraySwap {
    public static void arraySwap(Object[] arr, int index1, int index2){
        Object tmp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = tmp;
    }
}
