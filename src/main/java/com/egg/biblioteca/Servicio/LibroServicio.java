/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.biblioteca.Servicio;

import com.egg.biblioteca.Entidades.Autor;
import com.egg.biblioteca.Entidades.Editorial;
import com.egg.biblioteca.Entidades.Libro;
import com.egg.biblioteca.MiException.MiException;
import com.egg.biblioteca.Repositorio.AutorRepositorio;
import com.egg.biblioteca.Repositorio.EditorialRepositorio;
import com.egg.biblioteca.Repositorio.LibroRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *
 * @author Chicho
 */
@Service
public class LibroServicio {
    @Autowired
   private LibroRepositorio libroRepositorio;
      @Autowired
   private EditorialRepositorio editorialRepositorio;
      @Autowired
   private AutorRepositorio autorRepositorio;
      
      @Transactional
    public void crearLibro(Long isbn,String titulo, Integer ejemplares, String idAutor,String idEditorial) throws MiException{
        
          validar(isbn, titulo, ejemplares, idAutor, idEditorial);
        
        Editorial editorial = editorialRepositorio.findById(idEditorial).get();
        Autor autor = autorRepositorio.findById(idAutor).get();        
        
        Libro libro = new Libro();
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);
        libro.setAlta(new Date());
        
        libro.setAutor(autor);
        libro.setEditorial(editorial);
        
        libroRepositorio.save(libro);
        
        
        
    }
    
    public List<Libro> listaLibros(){
        
        List<Libro> libros = new ArrayList();
        
        libros = libroRepositorio.findAll(); // metodo para agregar todo los libros a la lista, trae todo los libros que estan en el repo
        
        return libros;
    }
    
    
    public void ModificarLibro(Long isbn, String titulo, String idAutor,String idEditorial, Integer ejemplares ) throws MiException{
        
        validar(isbn, titulo, ejemplares, idAutor, idEditorial);
        
        Optional<Libro> respuestaLibro = libroRepositorio.findById(isbn);
        Optional<Autor> respuestaAutor = autorRepositorio.findById(idAutor);
        Optional<Editorial> respuestaEditorial = editorialRepositorio.findById(idEditorial);
        
        Autor autor = new Autor();
        Editorial editorial = new Editorial();
        
        if(respuestaAutor.isPresent()){
            
            autor = respuestaAutor.get();
        }
        if(respuestaEditorial.isPresent()){
            
            editorial = respuestaEditorial.get();
        }
        
        if(respuestaLibro.isPresent()){
           
            Libro libro = respuestaLibro.get();
            libro.setTitulo(titulo);
            libro.setAutor(autor);
            libro.setEditorial(editorial);
            libro.setEjemplares(ejemplares);
            libroRepositorio.save(libro);
        }
              
    }
    
    public Libro getOne(Long isbn){
        
        return libroRepositorio.getOne(isbn);
    }
    
    
    private void validar(Long isbn,String titulo,Integer ejemplares,String idAutor,String idEditorial) throws MiException{
        
        if(isbn == null){
            throw new MiException("El isbn no puede ser nulo");
        }
        if(ejemplares == null){
            throw new MiException("Los ejemplares no pueden ser nulo");
        }
        if(titulo.isEmpty() || titulo == null){
            throw new MiException("El titulo no puede estar vacio o ser nulo");
        }
        if(idAutor.isEmpty() || idAutor == null){
            throw new MiException("El autor no puede estar vacio o ser nulo");
        }
        if(idEditorial.isEmpty() || idEditorial == null){
            throw new MiException("La editorial no puede estar vacio o ser nulo");
        }
                

        
        
    }
}
