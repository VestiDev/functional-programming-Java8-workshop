package victor.training.java8.advanced;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.lambda.Unchecked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import victor.training.java8.advanced.repo.OrderRepo;


interface CheckedConsumer<T> {

   void accept(T t) throws Exception;
}

class CsvExporter {
   @Value("${out.folder}")
   private File folder;// = new File("/apps/export"); // injected eg @Value

   {
     ContentWriterFunction writer = w -> w.write("test");
   }

   @FunctionalInterface
   interface ContentWriterFunction {
      void writeContent(Writer destination) throws Exception;
   }

   @Transactional(readOnly = true)
   public File exportFile(String fileName, ContentWriterFunction contentWriter) {
      File file = new File(folder, fileName);
      try (Writer writer = new FileWriter(file)) {
         contentWriter.writeContent(writer);

         System.out.println("File export completed: " + file.getAbsolutePath());
         return file;
      } catch (Exception e) {
         // TODO send email notification
         throw new RuntimeException("Error exporting data", e);
      } finally {
         if (!file.delete()) {
            System.err.println("Could not delete export file: " + file.getAbsolutePath());
         }
      }
   }
}



@Slf4j
@Service
@RequiredArgsConstructor
class OrderContentWriter {
   private final OrderRepo orderRepo;

   public void writeContent(Writer writer) throws IOException {
      writer.write("OrderID;Date\n"); // header
      log.debug("Frate!");
      orderRepo.findByActiveTrue()
         .map(o -> o.getId() + ";" + o.getCreationDate()+"\n")
         .forEach(Unchecked.consumer(writer::write)); // jool
   }
}




















@NoRepositoryBean // dummy , not supposed to work fir this demo
interface UserRepo extends JpaRepository<User,Long> {}

@Slf4j
@Service
@RequiredArgsConstructor
class UserContentWriter{
   private final UserRepo userRepo;

//   @SneakyThrows
   public void writeContent(Writer writer) throws IOException {
      writer.write("User Name;Roles\n"); // header
      userRepo.findAll().stream()
         .map(user -> user.getName() + ";" + user.getRoles()+"\n")
         .forEach(Unchecked.consumer(writer::write)); // jool
   }
}

public class LoanPattern {
   public static void main(String[] args) throws Exception {
      OrderContentWriter orderContent = new OrderContentWriter(null);
      new CsvExporter().exportFile("orders.csv", orderContent::writeContent);

      UserContentWriter userContentWriter = new UserContentWriter(null);
      new CsvExporter().exportFile("users.csv", userContentWriter::writeContent);

      // TODO export users la fel cum exporti si orderurile: la fel cu err handling,
      //  notificari daca ok, cu tot ce am discutat pentru exportl de oderi
   }
}

