package victor.training.java8.advanced;


import java.util.Objects;
import java.util.function.BiFunction;


//@Component
class PriceCalculator {
//   @Value("${new.release.factor:2}")
   private int factor = 2;

   public int computeRegularPrice(int days) {
      return days + 1;
   }
   public int computeNewReleasePrice(int days) {
      return days * factor;
   }
   public int computeChildrenPrice(int days) {
      return 5;
   }
}

// 1. Problema cand e multa logica OK - cu referinte la metode statice din enum
// 2. daca factorul "2" trebuie citit dintr-un fisier de proprietati ~ @Value -- DONE
enum MovieType {
   REGULAR(PriceCalculator::computeRegularPrice),
   NEW_RELEASE(PriceCalculator::computeNewReleasePrice) ,
   CHILDREN(PriceCalculator::computeChildrenPrice),
   BABACI(null)
   ;

   private final BiFunction<PriceCalculator, Integer, Integer> priceAlgo;

   MovieType(BiFunction<PriceCalculator, Integer, Integer> priceAlgo) {
      this.priceAlgo = /*Objects.requireNonNull(*/priceAlgo;
   }

   public BiFunction<PriceCalculator, Integer, Integer> getPriceAlgo() {
      return priceAlgo;
   }
}

public class FunctionsOnEnums {

   public static void main(String[] args) {

//      new SimpleDateFormat().parse("aa");
      System.out.println(computePrice(MovieType.REGULAR, 2));
      System.out.println(computePrice(MovieType.NEW_RELEASE, 2));
      System.out.println(computePrice(MovieType.CHILDREN, 2));
   }

   public static int computePrice(MovieType type, int days) {

      PriceCalculator priceCalculator = new PriceCalculator(); // ma prefac c-o iau din spring @Autowired
      return type.getPriceAlgo().apply(priceCalculator, days);
   }
   public static int computePrice_java15_in2023(MovieType type, int days) {
      return switch (type) {
         case REGULAR -> days + 1;
         case NEW_RELEASE -> days * 2;
         case CHILDREN -> 5;
         case BABACI -> 0;
      };
   }


}
