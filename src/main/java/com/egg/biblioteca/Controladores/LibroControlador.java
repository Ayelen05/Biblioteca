/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.biblioteca.Controladores;

import com.egg.biblioteca.Entidades.Autor;
import com.egg.biblioteca.Entidades.Editorial;
import com.egg.biblioteca.Entidades.Libro;
import com.egg.biblioteca.MiException.MiException;
import com.egg.biblioteca.Servicio.AutorServicio;
import com.egg.biblioteca.Servicio.EditorialServicio;
import com.egg.biblioteca.Servicio.LibroServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Chicho
 */
@Controller
@RequestMapping("/libro")
public class LibroControlador {

    @Autowired
    private LibroServicio libroServicio;

    @Autowired
    private EditorialServicio editorialServicio;

    @Autowired
    private AutorServicio autorServicio;

    @GetMapping("/registrar")//localhost:8080/libro/registrar
    public String registrar(ModelMap modelo) {

        List<Autor> autores = autorServicio.listaAutores();
        List<Editorial> editoriales = editorialServicio.ListaEditoriales();
        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);

        return "libro_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam(required = false) Long isbn, @RequestParam String titulo,
            @RequestParam(required = false) Integer ejemplares, @RequestParam String idAutor,
            @RequestParam String idEditorial, ModelMap modelo) {

        try {
            libroServicio.crearLibro(isbn, titulo, ejemplares, idAutor, idEditorial);
            modelo.put("exito", "El libro fue cxargado correctamente");

        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());

            List<Autor> autores = autorServicio.listaAutores();
            List<Editorial> editoriales = editorialServicio.ListaEditoriales();
            modelo.addAttribute("autores", autores);
            modelo.addAttribute("editoriales", editoriales);
            return "libro_form.html";
        }

        return "index.html";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {

        List<Libro> libros = libroServicio.listaLibros();
        modelo.addAttribute("libros", libros);
        return "libro_list.html";
    }

    @GetMapping("/modificar/{isbn}")
    public String modificar(@PathVariable Long isbn, ModelMap modelo) {

        modelo.put("libro", libroServicio.getOne(isbn));
        List<Autor> autores = autorServicio.listaAutores();
        List<Editorial> editoriales = editorialServicio.ListaEditoriales();
        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);

        return "libro_modificar.html";
    }

    @PostMapping("/modificar/{isbn}")
    public String modificar(@PathVariable Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial, ModelMap modelo) throws MiException {
        try {

            libroServicio.ModificarLibro(isbn, titulo, idAutor, idEditorial, ejemplares);

            return "redirect:../lista";

        } catch (MiException ex) {
            List<Autor> autores = autorServicio.listaAutores();
            List<Editorial> editoriales = editorialServicio.ListaEditoriales();
            modelo.addAttribute("autores", autores);
            modelo.addAttribute("editoriales", editoriales);
            modelo.put("error", ex.getMessage());
            return "libro_modificar.html";
        }
    }

}
