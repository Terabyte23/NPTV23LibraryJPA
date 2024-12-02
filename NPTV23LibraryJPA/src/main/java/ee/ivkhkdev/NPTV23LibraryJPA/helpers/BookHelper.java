package ee.ivkhkdev.nptv23libraryjpa.helpers;

import ee.ivkhkdev.nptv23libraryjpa.entity.Book;
import ee.ivkhkdev.nptv23libraryjpa.interfaces.AppHelper;
import ee.ivkhkdev.nptv23libraryjpa.interfaces.Input;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class BookHelper implements AppHelper<Book> {

    @Autowired private Input input;

    @Override
    public Optional<Book> create() {
        Book book = new Book();
        System.out.print("Название книги: ");
        book.setTitle(input.getString());
        System.out.print("Год издания книги: ");
        book.setPublishedYear(input.getInt());
        return Optional.of(book);
    }

    @Override
    public Optional<Book> edit(Book book) {
        return Optional.empty();
    }

    @Override
    public boolean printList() {
        return false;
    }
}
