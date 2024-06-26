package com.toolsToHome.PI.Controller;

import com.toolsToHome.PI.Exceptions.ResourceNotFoundException;
import com.toolsToHome.PI.Model.Herramienta;
import com.toolsToHome.PI.Model.Reserva;
import com.toolsToHome.PI.Model.Reseña;
import com.toolsToHome.PI.Service.HerramientaService;
import com.toolsToHome.PI.Service.ReservaService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/Reservas")
public class ReservaController {
    private static final Logger logger = Logger.getLogger(ReservaController.class);
    private ReservaService reservaService;
    private HerramientaService herramientaService;
    @Autowired
    public ReservaController(ReservaService reservaService, HerramientaService herramientaService) {
        this.reservaService = reservaService;
        this.herramientaService = herramientaService;
    }


    @PostMapping("/create")
    public ResponseEntity<Reserva> guardarReserva(@RequestBody Reserva reserva)throws ResourceNotFoundException{


            Reserva guardarReserva = reservaService.guardarReserva(reserva);
            guardarReserva.setHerramientaId(reserva.getHerramientaId());
        logger.info("Usuario Id : "+reserva.getUsuarioId()+"  Herramienta:  "  + reserva.getHerramientaId()+"  Herramienta Id "+"Procede informacion a Service Reserva");
            return ResponseEntity.ok(guardarReserva);

    }


    @GetMapping("/list/{id}")
    public ResponseEntity<Optional<Reserva>>buscarReserva(@PathVariable Long id) throws ResourceNotFoundException{
        Optional<Reserva>buscarReserva= reservaService.BuscarPorId(id);
        if(buscarReserva.isPresent()){
            return ResponseEntity.ok(buscarReserva);
        }else throw new ResourceNotFoundException("No se encontro la reserva con ID :  "+ id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String>eliminarReserva(@PathVariable Long id)throws ResourceNotFoundException{
        Optional<Reserva>buscarReserva = reservaService.BuscarPorId(id);
        if(buscarReserva.isPresent()){
            reservaService.eliminarReserva(id);
            return ResponseEntity.ok("Reserva con id: "+ id +" eliminada");
        }else throw  new ResourceNotFoundException("No se encontro Reserva con id: "+id);







    }
    @GetMapping("/list")
    public ResponseEntity<List<Reserva>>listarReservas(){
        return ResponseEntity.ok( reservaService.listarReservas());
    }



}
