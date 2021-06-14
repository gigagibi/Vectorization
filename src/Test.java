class Item {
    private int i;

    public int getI() {
        return i;
    }

    public Item(int i) {
        this.i = i;
    }
}

public class Test {
    public static void main(String[] args){
//        Item t1 = new Item(1);
//        Item t2 = new Item(2);
//        Item t3 = new Item(3);
//        t2 = t1;
//        System.out.println(t2.getI());
//        t2 = t3;
//        System.out.println(t1.getI());
//        System.out.println(t2.getI());
        int counter = 1;
        int[][] field = new int[2][2];
//        for(int x = 0; x < 2; x++) {
//            for(int y = 0; y < 2; y++) {
//                field[y][x] = counter;
//                counter++;
//            }
//        }
        field[0][0] = 1;
        field[0][1] = 2;
        field[1][0] = 3;
        field[1][1] = 4;
        for(int y = 0; y < 2; y++) {
            for(int x = 0; x < 2; x++) {
                System.out.print(field[y][x] + " ");
            }
            System.out.println();
        }
    }
}
