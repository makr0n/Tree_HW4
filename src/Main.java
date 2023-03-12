
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        RBTree rbTree = new RBTree();

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                int value = scanner.nextInt();
                rbTree.add(value);
                System.out.printf("Added -> %d\n", value);
                System.out.printf("Tree size: %d\n", rbTree.getSize());
                System.out.printf("Black height: %d\n", rbTree.getBlackHeight());
                System.out.printf("Total height: %d\n", rbTree.getTotalHeight());
            }
        }
    }
}
