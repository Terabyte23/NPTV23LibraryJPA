package ee.ivkhkdev.nptv23libraryjpa.services;

import ee.ivkhkdev.nptv23libraryjpa.entity.Author;
import ee.ivkhkdev.nptv23libraryjpa.helpers.AuthorHelperImpl;
import ee.ivkhkdev.nptv23libraryjpa.interfaces.AppHelper;
import ee.ivkhkdev.nptv23libraryjpa.interfaces.AppService;
import ee.ivkhkdev.nptv23libraryjpa.repository.AuthorRepository;
import ee.ivkhkdev.nptv23libraryjpa.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorServiceImpl implements AppService<Author> {

    private final AppHelper<Author> authorAppHelper;
    private final AuthorRepository authorRepository;
    private final AuthorHelperImpl authorHelperImpl;
    private final BookRepository bookRepository;

    public AuthorServiceImpl(AuthorHelperImpl authorAppHelper, AuthorRepository authorRepository, AuthorHelperImpl authorHelperImpl, BookRepository bookRepository) {
        this.authorAppHelper = authorAppHelper;
        this.authorRepository = authorRepository;
        this.authorHelperImpl = authorHelperImpl;
        this.bookRepository = bookRepository;
    }

    @Override
    public boolean add() {
        Optional<Author> optionalAuthor = authorAppHelper.create();
        if (optionalAuthor.isEmpty()) {
            return false;
        }
        Author author = optionalAuthor.get();
        authorRepository.save(author);
        return true;
    }

    @Override
    public boolean update(Author author) {return false;}

    @Override
    public boolean changeAvailability() {
        try{
            Long authorId = authorHelperImpl.findIdEntityForChangeAvailability(authorRepository.findAll());
            Optional<Author> optionalAuthor = authorRepository.findById(authorId);
            if (optionalAuthor.isEmpty()) {
                return false;
            }
            Author author = optionalAuthor.get();
            if(author.isAvailable()){
                author.setAvailable(false);
            }else{
                author.setAvailable(true);
            }
            authorRepository.save(author);
            return true;
        }catch(Exception e){
            return false;
        }

    }

    @Override
    public boolean print() {
        return authorAppHelper.printList(authorRepository.findAll());
    }


}
