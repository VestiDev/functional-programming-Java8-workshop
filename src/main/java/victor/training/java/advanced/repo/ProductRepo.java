package victor.training.java.advanced.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import victor.training.java.advanced.model.Product;

import java.util.List;
import java.util.stream.Stream;

public interface ProductRepo extends JpaRepository<Product, Long> {
   List<Product> findByHiddenTrue();

   Product findByNameContaining(String namePart);

   Stream<Product> streamAllByDeletedTrue();

   Product findExactlyOneById(long l);
}