public class Test {
    public static void main(String[] args) {
        System.out.println(indexFor(40,16));
    }
    // 计算位置
    static int indexFor(int h , int length){
        return h & (length -1);
    }
}
