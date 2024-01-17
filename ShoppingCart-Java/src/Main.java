import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {

    public static float calculateDiscount(float amount, float discount) {
        return (amount * discount) / 100;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String prod_A = "Product A";
        String prod_B = "Product B";
        String prod_C = "Product C";

        float price_A = 20, price_B = 40, price_C = 50, shippingFee = 0, giftFee = 0;

        int quant_A, quant_B, quant_C, total_quant, gift;

        System.out.println("Enter quantity of " + prod_A);
        quant_A = scanner.nextInt();
        System.out.println("Enter quantity of " + prod_B);
        quant_B = scanner.nextInt();
        System.out.println("Enter quantity of " + prod_C);
        quant_C = scanner.nextInt();

        System.out.println("Enter 1 for Gift Wrap or 0 for normal delivery!");
        gift = scanner.nextInt();
        boolean isGiftWrap = gift == 1;

        total_quant = quant_A + quant_B + quant_C;
        float total_amount = (price_A * quant_A) + (price_B * quant_B) + (price_C * quant_C);

        giftFee = isGiftWrap ? total_quant : 0;
        shippingFee = total_quant % 10 == 0 ? (total_quant / 10) * 5 : ((total_quant / 5) + 1) * 5;

        float flat_10 = 0, bulk_5 = 0, bulk_10 = 0, tiered_50 = 0;

        PriorityQueue<Pair<String, Float>> pq = new PriorityQueue<>((a, b) -> Float.compare(b.getValue(), a.getValue()));

        // calculating "flat_10_discount"
        if (total_amount > 200) {
            flat_10 = calculateDiscount(total_amount, 20);
            pq.add(new Pair<>("flat_10_discount", flat_10));
        }

        // calculating "bulk_5_discount"
        if (quant_A > 10 || quant_B > 10 || quant_C > 10) {
            if (quant_A > 10) {
                bulk_5 = calculateDiscount(quant_A * price_A, 5);
            } else if (quant_B > 10) {
                bulk_5 = calculateDiscount(quant_B * price_B, 5);
            } else {
                bulk_5 = calculateDiscount(quant_C * price_C, 5);
            }
            pq.add(new Pair<>("bulk_5_discount", bulk_5));
        }

        // calculating "bulk_10_discount"
        if (total_quant > 20) {
            bulk_10 = calculateDiscount(total_amount, 10);
            pq.add(new Pair<>("bulk_10_discount", bulk_10));
        }

        // calculating "tiered_50_discount"
        if (total_quant > 30 && (quant_C > 15 || quant_B > 15 || quant_A > 15)) {
            if (quant_A > 15) {
                int amt = (int) ((price_A * quant_A) - (price_A * (quant_A - 15)));
                tiered_50 = calculateDiscount(amt, 50);
            } else if (quant_B > 15) {
                int amt = (int) ((price_B * quant_B) - (price_B * (quant_B - 15)));
                tiered_50 = calculateDiscount(amt, 50);
            } else {
                int amt = (int) ((price_C * quant_C) - (price_C * (quant_C - 15)));
                tiered_50 = calculateDiscount(amt, 50);
            }
            pq.add(new Pair<>("tiered_50_discount", tiered_50));
        }

        float finalAmount = total_amount - pq.peek().getValue() + giftFee + shippingFee;

        System.out.println(prod_A + "            " + quant_A + "           $" + (price_A * quant_A));
        System.out.println(prod_B + "            " + quant_B + "           $" + (price_B * quant_B));
        System.out.println(prod_C + "            " + quant_C + "           $" + (price_C * quant_C));

        System.out.println("SubTotal : $" + total_amount);
        System.out.println("Discount Applied : " + pq.peek().getKey());
        System.out.println("Discount amount : $" + pq.peek().getValue());
        System.out.println("Shipping Fees : $" + shippingFee);
        System.out.println("Gift Fee : $" + giftFee);

        System.out.println("Total Amount : $" + finalAmount);

        scanner.close();
    }

    static class Pair<K, V> {
        private final K key;
        private final V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }
}
