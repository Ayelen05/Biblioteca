/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.biblioteca.Controladores;

import com.egg.biblioteca.Entidades.Autor;
import com.egg.biblioteca.Entidades.Editorial;
import com.egg.biblioteca.MiException.MiException;
import com.egg.biblioteca.Servicio.EditorialServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@RequestMapping("/editorial") //localhost:8080/editorial
public class EditorialControlador {
    
    @Autowired
    EditorialServicio editorialServicio;
    
    @GetMapping("/registrar") //localhost:8080/editorial/registrar
    public String Registrar(){
        return "editorial_form.html";
    }
    
    @PostMapping("/registro")
    public String Registro(@RequestParam String nombre, ModelMap modelo){
          try{
        editorialServicio.crearEditorial(nombre);
        modelo.put("exito", "La editorial fue cargada correctamente");
        } catch (MiException ex ) {
            
            modelo.put("error", ex.getMessage());
            
            return "autor_form.html";
        }
        return "index.html";
        
    }
    
     @GetMapping("/lista")
    public String listar(ModelMap modelo)throws MiException{
        
       
        List<Editorial> editoriales = editorialServicio.ListaEditoriales();
         if (editoriales == null) {
             
             throw new MiException("editorial vacia");
         }
        modelo.addAttribute("editoriales", editoriales);
        return "editorial_list.html";
    }
    
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo){
        
        modelo.put("editorial", editorialServicio.getOne(id));
        
        return "editorial_modificar.html";
    }
    
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String nombre, ModelMap modelo)throws MiException{
        try {
            
            editorialServicio.modificarEditorial(id, nombre);
            
            return "redirect:../lista";
            
        } catch (MiException ex) {
            
            modelo.put("error", ex.getMessage());
            return "editorial_modificar.html";
        }
    }
}
