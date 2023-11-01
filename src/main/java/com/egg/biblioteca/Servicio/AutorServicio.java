/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.biblioteca.Servicio;

import com.egg.biblioteca.Entidades.Autor;
import com.egg.biblioteca.MiException.MiException;
import com.egg.biblioteca.Repositorio.AutorRepositorio;
import java.util.ArrayList;
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
public class AutorServicio {
    @Autowired
    AutorRepositorio autorRepositorio;
    
    @Transactional
    public void crearAutor(String nombre) throws MiException{
         ValidarAutor(nombre); // valida o tira expepcion
       
        Autor autor = new Autor();
      
        autor.setNombre(nombre);
        
        autorRepositorio.save(autor);
        
    }
    
    public List<Autor> listaAutores(){
        
        List<Autor> autores = new ArrayList();
        
        autores = autorRepositorio.findAll();
        
        return autores;
        
    }
    
    public void modificarAutor(String id,String nombre) throws MiException{
        ValidarAutor(nombre);
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        
        if(respuesta.isPresent()){
            
            Autor autor = respuesta.get();
            
            autor.setNombre(nombre);
            
            autorRepositorio.save(autor);
        }
    }
    
    public Autor getOne( String id){
        return autorRepositorio.getOne(id);
    }
    
    private void ValidarAutor( String nombre) throws MiException{ // valida que el nombre no este vacio o tira expecion
        
        
         if(nombre.isEmpty() || nombre == null){
             
             throw new MiException("El nombre del autor no puede estar vacio o ser nulo");
         }
             
    }
}
