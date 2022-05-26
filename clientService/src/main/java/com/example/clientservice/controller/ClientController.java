package com.example.clientservice.controller;

import com.example.clientservice.dto.ApiResponse;
import com.example.clientservice.dto.ClientDTO;
import com.example.clientservice.repository.ClientRepository;
import com.example.clientservice.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ClientController {
    final ClientRepository clientRepository;
    final ClientService clientService;

    //getAll
    @GetMapping
    public ResponseEntity<?> getAll() {
        ApiResponse<?> response = clientService.getAll();
        return ResponseEntity.ok().body(response);
    }

    //getOne
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id) {
        ApiResponse<?> response = clientService.getOne(id);
        return ResponseEntity.ok().body(response);
    }

    //add
    @PostMapping
    public ResponseEntity<?> add(@RequestBody ClientDTO dto) {
        ApiResponse<?> response = clientService.add(dto);
        return ResponseEntity.ok().body(response);
    }

    //edit
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Integer id, @RequestBody ClientDTO clientdto) {
        ApiResponse<?> response = clientService.edit(id, clientdto);
        return ResponseEntity.ok().body(response);
    }

    //delete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        ApiResponse<?> response = clientService.delete(id);
        return ResponseEntity.ok().body(response);
    }
}
