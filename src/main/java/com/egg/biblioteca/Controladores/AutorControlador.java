/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.biblioteca.Controladores;

import com.egg.biblioteca.Entidades.Autor;
import com.egg.biblioteca.MiException.MiException;
import com.egg.biblioteca.Servicio.AutorServicio;
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
@RequestMapping("/autor") //localhost:8080/autor
public class AutorControlador {
    @Autowired
    AutorServicio autorServicio;
    
    
    @GetMapping("/registrar") //localhost:8080/autor/registrar
    public String Registrar(){
        return "autor_form.html";
    }
    
    @PostMapping("/registro")
    public String Registro(@RequestParam String nombre, ModelMap modelo) throws MiException{
        
        try{
        autorServicio.crearAutor(nombre);
        modelo.put("exito", "el autor se cargo correctamente");
        
        } catch (MiException ex ) {
          
            modelo.put("error", ex.getMessage());
            
            return "autor_form.html";
        }
        return "index.html";
    }
    
    @GetMapping("/lista")
    public String listar(ModelMap modelo){
        
        List<Autor> autores = autorServicio.listaAutores();
        modelo.addAttribute("autores", autores);
        return "autor_list.html";
    }
    
    
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo){
        
        modelo.put("autor", autorServicio.getOne(id));
        
        return "autor_modificar.html";
    }
    
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String nombre, ModelMap modelo)throws MiException{
        try {
            
            autorServicio.modificarAutor(id, nombre);
            
            return "redirect:../lista";
            
        } catch (MiException ex) {
            
            modelo.put("error", ex.getMessage());
            return "autor_modificar.html";
        }
    }
}
