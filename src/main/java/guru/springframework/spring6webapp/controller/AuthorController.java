package guru.springframework.spring6webapp.controller;

import guru.springframework.spring6webapp.domain.Author;
import guru.springframework.spring6webapp.domain.Book;
import guru.springframework.spring6webapp.services.AuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) { this.authorService = authorService; }

    @RequestMapping("/authors")
    public String getAuthors(Model model) {
        List<Author> authors = (List<Author>) authorService.findAll();

        Map<Long, String> authorBookTitles = authors.stream()
                .collect(Collectors.toMap(
                        Author::getId,
                        (author) -> author.getBooks().stream()
                                .map(Book::getTitle)
                                .collect(Collectors.joining(", "))
                ));

        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("authorBookTitles", authorBookTitles);
        return "authors";
    }
}
