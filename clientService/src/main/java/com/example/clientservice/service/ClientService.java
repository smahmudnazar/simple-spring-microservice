package com.example.clientservice.service;

import com.example.clientservice.dto.ApiResponse;
import com.example.clientservice.dto.ClientDTO;
import com.example.clientservice.entity.Client;
import com.example.clientservice.entity.Department;
import com.example.clientservice.feign.DepartmentFeign;
import com.example.clientservice.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final DepartmentFeign departmentFeign;
    private final ClientRepository clientRepository;

    public ApiResponse<?> add(ClientDTO clientDTO) {

        Client client = new Client();
        client.setName(clientDTO.getName());

        Department response = departmentFeign.getOneDepartment(clientDTO.getDepartmentId());
        System.out.println(response);

//        List<Department> all = departmentFeign.getAll();
//        System.out.println(all);

        client.setDepartment(response);
        clientRepository.save(client);
        return new ApiResponse<Client>("Added", true, client);
    }

    public ApiResponse<?> edit(Integer id, ClientDTO clientsDTO) {
        Optional<Client> clientOptional = clientRepository.findById(id);
        if (clientOptional.isEmpty()) return ApiResponse.builder().message("Client not Found").success(false).build();

        Department department = departmentFeign.getOneDepartment(clientsDTO.getDepartmentId());
        if (department == null) return ApiResponse.builder().message("Department not Found").success(false).build();

        Client client = clientOptional.get();
        client.setName(clientsDTO.getName());
        client.setDepartment(department);
        clientRepository.save(client);

        return ApiResponse.builder().message("Edited").success(true).build();
    }


    public ApiResponse<?> getAll() {
        List<Client> clients = clientRepository.findAll();
        return ApiResponse.builder().message("Here").success(true).data(clients).build();
    }

    public ApiResponse<?> getOne(Integer id) {
        Optional<Client> clientOptional = clientRepository.findById(id);
        if (clientOptional.isEmpty()) return ApiResponse.builder().message("Client not Found").success(false).build();

        return ApiResponse.builder().message("Here").success(true).data(clientOptional.get()).build();
    }


    public ApiResponse<?> delete(Integer id) {
        Optional<Client> clientOptional = clientRepository.findById(id);
        if (clientOptional.isEmpty()) return ApiResponse.builder().message("Client not Found").success(false).build();

        clientRepository.deleteById(id);

        return ApiResponse.builder().message("Deleted").success(true).build();
    }
}
