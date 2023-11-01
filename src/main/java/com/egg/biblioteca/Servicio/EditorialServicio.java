/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.biblioteca.Servicio;

import com.egg.biblioteca.Entidades.Editorial;
import com.egg.biblioteca.MiException.MiException;
import com.egg.biblioteca.Repositorio.EditorialRepositorio;
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
public class EditorialServicio {
    @Autowired
    EditorialRepositorio editorialRepositorio;
    
    @Transactional
    public void crearEditorial(String nombre) throws MiException{
        
        ValidarAutor(nombre);
        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        
        editorialRepositorio.save(editorial);
    }
    
    public List<Editorial> ListaEditoriales(){
        
        List<Editorial> editoriales = new ArrayList();
        
        editoriales = editorialRepositorio.findAll();
        
        return editoriales;
    }
    
    public void modificarEditorial(String id,String nombre) throws MiException{
        ValidarAutor(nombre);
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        
        if(respuesta.isPresent()){
            
            Editorial editorial = respuesta.get();
            editorial.setNombre(nombre);
            editorialRepositorio.save(editorial);
                    
            
        }
        
    }
    
    
    public Editorial getOne(String id){
        return editorialRepositorio.getOne(id);
    }
    private void ValidarAutor( String nombre) throws MiException{
        
      
         if(nombre.isEmpty() || nombre == null){
             
             throw new MiException("El nombre de la editorial no puede estar vacio o ser nulo");
         }
             
    }
}
