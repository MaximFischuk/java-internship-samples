package com.example.demo.features;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SealedAndRecord {
    //** Sealed classes **//
    public static abstract sealed class Base permits InheritedA, InheritedB/*, InheritedC*/ { }

    public static final class InheritedA extends Base {}
    public static final class InheritedB extends Base {}
//    public static class InheritedC extends Base {}
//    public static class Another extends Base {}

    //** Record type **//
    public record Pojo (Integer id, String name) {}

    public record PojoWithMethods (Integer id, String name) {
        String getNickname() {
            return name + '-' + id;
        }
    }

    public record PojoWithConstructors (Integer id, String name) {
        // NoArgsConstructor
        public PojoWithConstructors() {
            this(123, "Some");
        }

        // CustomAllArgsConstructor validator
        public PojoWithConstructors {

            // Assign not allowed here
//            this.id = id;
//            this.name = name;
        }
    }

    class Merchant {}
    double computeSales(Merchant merchant, int month) {
        return 0.0;
    }
    List<Merchant> findTopMerchants(List<Merchant> merchants, int month) {
        // Local record
        record MerchantSales(Merchant merchant, double sales) {}

        return merchants.stream()
                .map(merchant -> new MerchantSales(merchant, computeSales(merchant, month)))
                .sorted((m1, m2) -> Double.compare(m2.sales(), m1.sales()))
                .map(MerchantSales::merchant)
                .collect(Collectors.toList());
    }

    class PojoWithMethodsAsRecord {
        private final Integer id;
        private final String name;

        public PojoWithMethodsAsRecord(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        public Integer id() {
            return id;
        }

        public String name() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof PojoWithMethodsAsRecord)) return false;
            PojoWithMethodsAsRecord that = (PojoWithMethodsAsRecord) o;
            return id.equals(that.id) && name.equals(that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name);
        }

        @Override
        public String toString() {
            return "PojoWithMethodsAsRecord{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
