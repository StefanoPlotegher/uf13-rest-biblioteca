package it.marconi.biblioteca.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.Operation;
import it.marconi.biblioteca.domain.LibroDTO;
import it.marconi.biblioteca.domain.response.APIResponse;
import it.marconi.biblioteca.services.LibroService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/libri")
public class LibroController {
    
    @Autowired
    LibroService libroService;

    @GetMapping
    @Operation(summary = "Recupera la lista di tutti i libri")
    public APIResponse<List<LibroDTO>> getAll() {

        return APIResponse.success(libroService.findAll());
    }

    @GetMapping("/{isbn}")
    @Operation(summary = "Cerca un libro dal sui ISBN")
    public APIResponse<LibroDTO> getLibroByIsbn(@PathVariable String isbn) {

        return libroService.getByIsbn(isbn)
            .map(APIResponse::success)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Libro non trovato"));
    }

    @GetMapping("/libro")
    @Operation(summary = "Cerca un libro per titolo esatto")
    public APIResponse<LibroDTO> getLibroByTitolo(@RequestParam("titolo") String titolo) {

        return libroService.getByTitolo(titolo)
            .map(APIResponse::success)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Libro non trovato"));
    }

    @PostMapping("/add")
    @Operation(summary = "Aggiunge un nuovo libro, dato l'autore")
    public APIResponse<LibroDTO> addLibro(@Valid @RequestBody LibroDTO libro) {
        
        return libroService.save(libro)
            .map(APIResponse::success)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Autore non trovato"));
    }

    @DeleteMapping("/{isbn}")
    @Operation(summary = "Elimina un libro dato il suo ISBN")
    public APIResponse<Void> deleteLibro(@PathVariable String isbn) {

        boolean deleted = libroService.deleteByIsbn(isbn);

        if (deleted)
            return APIResponse.success(null);
        else
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Libro non trovato");
    }
}
