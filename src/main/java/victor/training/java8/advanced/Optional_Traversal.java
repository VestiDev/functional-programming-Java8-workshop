package victor.training.java8.advanced;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

import static java.util.Optional.ofNullable;

public class Optional_Traversal {
    public static void main(String[] args) {
        System.out.println(convertToName(new A(new B(new C("Gigel")))));
        System.out.println(convertToName(new A(new B())));
        System.out.println(convertToName(new A()));
    }
    public static String convertToName(A a) {
        return a.getBOpt()
            .flatMap(B::getCOpt)
            .map(C::getName)
            .orElse("");
    }

}


class A {
    private B b;
    public A(B b) {
        this.b = b;
    }
    public A() {
        this(null);
    }
    public B getB() {
        return b;
    }
    public Optional<B> getBOpt() {
        return ofNullable(b);
    }
}
class B {
    private final C c;

    public B(C c) {
        this.c = c;
    }
    public B() {
        this(null);
    }

    public C getC() {
        return c;
    }
    public Optional<C> getCOpt() {
        return ofNullable(c);
    }
}
class C {
    private final String name;

    public C(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
