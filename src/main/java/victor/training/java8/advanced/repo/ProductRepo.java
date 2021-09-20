package victor.training.java8.advanced.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import victor.training.java8.advanced.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface ProductRepo extends JpaRepository<Product, Long> {
   List<Product> findByHiddenTrue();

   Optional<Product> findByNameContaining(String namePart);

   Stream<Product> streamAllByDeletedTrue();// 1MLN // pe sub agata un ResultSert din care va trage date.
   // daca tu nu ai Tx deschisa, conn se elivereaza si nu are CUM sa-ti dea un Stream<>(ResultSet)

   Product findExactlyOneById(long l);
}
