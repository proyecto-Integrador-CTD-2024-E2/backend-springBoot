package com.toolsToHome.PI.Controller;

import com.toolsToHome.PI.Exceptions.ResourceNotFoundException;
import com.toolsToHome.PI.Model.Caracteristicas;
import com.toolsToHome.PI.Model.Categoria;
import com.toolsToHome.PI.Service.CaracteristicaService;
import com.toolsToHome.PI.Service.CategoriaService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/Caracteristicas")
public class CaracteristicaController {


    private CaracteristicaService caracteristicaService;
    private static final Logger logger = Logger.getLogger(HerramientaController.class);
    @Autowired
    public CaracteristicaController(CaracteristicaService categoriaService) {
        this.caracteristicaService = categoriaService;
    }


    @GetMapping("/list/{id}")
    public ResponseEntity<Optional<Caracteristicas>> buscarCaracteristicas(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Caracteristicas> buscarCaracteristicas = caracteristicaService.buscarPorId(id);
        if (buscarCaracteristicas.isPresent()) {
            return ResponseEntity.ok(buscarCaracteristicas);
        } else {
            throw new ResourceNotFoundException("No se encontró la categoria con el ID: " + id);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<Caracteristicas>>listarHerramientas(){
        return ResponseEntity.ok(caracteristicaService.listarTodos());
    }






    @PostMapping("/create")
    public ResponseEntity<Caracteristicas>guardarCaracteristicas(@RequestBody Caracteristicas caracteristicas)throws ResourceNotFoundException{
        Optional<Caracteristicas>buscarCaracteristicas= caracteristicaService.buscarPorCaracteristicas(caracteristicas.getTitulo());
        if(buscarCaracteristicas.isEmpty()){
            Caracteristicas categoriaGuardada = caracteristicaService.guardarCaracteristica(caracteristicas);

            logger.info("Nueva Caracteristica, Controller");
            return ResponseEntity.ok(categoriaGuardada);

        }else throw new ResourceNotFoundException("La Caracteristica ya existe");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> eliminarCaracteristicas(@PathVariable Long id) throws ResourceNotFoundException{
        Optional<Caracteristicas>buscarCategoria = caracteristicaService.buscarPorId(id);
        if(buscarCategoria.isPresent()){
            caracteristicaService.eliminarCaracteristicas(id);
            return ResponseEntity.ok("Caracteristica Eliminada");
        } else {
            throw new ResourceNotFoundException("No se encontró la caracteristica con el ID: " + id);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String>actualizarCaracteristicas(@RequestBody Caracteristicas caracteristicas) throws ResourceNotFoundException{
        Optional<Caracteristicas> categoriaRequest = caracteristicaService.buscarPorId(caracteristicas.getId());

        if(categoriaRequest.isPresent()){
           caracteristicaService.actualizarCaracteristicas(caracteristicas);

            return ResponseEntity.ok("La Caracteristica: " + caracteristicas.getTitulo() + " ha sido actualizada correctamente");
        }

        else {
            throw new ResourceNotFoundException("No se encontró la Caracteristica con el ID: " + caracteristicas.getId());
        }
    }









}
