package victor.training.java8.advanced;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import victor.training.java8.advanced.model.Order;
import victor.training.java8.advanced.repo.OrderRepo;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Stream;

import static java.time.LocalDate.parse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderContentWriterTest {

   @Mock
   private OrderRepo orderRepo;
   @InjectMocks
   private OrderContentWriter contentWriter;

   @Test
   public void writeContent() throws IOException {
      Order order = new Order()
          .setId(13L)
          .setCreationDate(parse("2021-01-01"));
      when(orderRepo.findByActiveTrue()).thenReturn(Stream.of(order));

      Writer writer = new StringWriter();
      contentWriter.writeContent(writer);
      String content = writer.toString();

      assertThat(content)
          .contains("OrderID;Date\n")
          .contains("13;2021-01-01\n");
   }
}