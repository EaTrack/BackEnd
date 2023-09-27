package com.eatrack.controller.system;

import com.eatrack.model.records.ClienteRecord;
import com.eatrack.service.client.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/cadastro")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<?> adicionar(@RequestBody ClienteRecord clienteRecord){
        return clienteService.adicionar(clienteRecord);
    }


}
