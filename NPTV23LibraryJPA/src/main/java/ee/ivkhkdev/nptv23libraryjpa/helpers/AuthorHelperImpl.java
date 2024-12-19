package ee.ivkhkdev.nptv23libraryjpa.helpers;
import ee.ivkhkdev.nptv23libraryjpa.entity.Author;
import ee.ivkhkdev.nptv23libraryjpa.interfaces.AuthorHelper;
import ee.ivkhkdev.nptv23libraryjpa.interfaces.Input;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class AuthorHelperImpl implements AuthorHelper {

    private final Input input;

    public AuthorHelperImpl(Input input) {
        this.input = input;
    }

    @Override
    public Optional<Author> create() {
        try {
            Author author = new Author();
            System.out.print("Имя автора: ");
            author.setFirstname(input.getString());
            System.out.print("Фамилия автора: ");
            author.setLastname(input.getString());
            return Optional.of(author);
        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Author> edit(Author author) {
        return Optional.empty();
    }

    @Override
    public boolean printList(List<Author> authors) {
        try {
            if(authors.isEmpty()){
                System.out.println("Список авторов пуст");
                return false;
            }
            System.out.println("---------- Список авторов --------");
            for(int i=0;i<authors.size();i++) {
                StringBuilder sbAvailableInfo = new StringBuilder();
                Author author = authors.get(i);
                if(!author.isAvailable()){
                    sbAvailableInfo.append("(выключен)");
                };
                System.out.printf("%d. %s %s %s%n",
                        author.getId(),
                        author.getFirstname(),
                        author.getLastname(),
                        sbAvailableInfo.toString()
                );
            }
            return true;
        }catch (Exception e){
            System.out.println("Error authorAppHelper.printList(authors): "+e.getMessage());
        }
        return false;
    }

    @Override
    public Long findIdEntityForChangeAvailability(List<Author> authors) {
        this.printList(authors);
        System.out.println("Выберите номер автора для удаления: ");
        return (long) input.getInt();
    }

    public List<Long> listAuthorsId(List<Author> authors) {
        this.printList(authors);
        System.out.print("Сколько авторов у книги: ");
        int countAuthorsForBook = input.getInt();
        List<Long>authorsId = new ArrayList<>();
        for(int i=0;i<countAuthorsForBook;i++) {
            System.out.printf("Выберите %d-го автора из списка: ",i+1);
            int numberAuthor = input.getInt();
            Author author = authors.get(numberAuthor-1);
            if(!author.isAvailable()){
                System.out.printf("Книгу добавить невозможно!%nВыбран выключенный автор: %s %s%n", author.getFirstname(), author.getLastname());
                return new ArrayList<>();
            }
            authorsId.add(author.getId());
        }
        return authorsId;
    }
}
