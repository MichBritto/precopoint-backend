package br.com.precopoint.PrecoPoint.service.impl;


import br.com.precopoint.PrecoPoint.controller.AuthenticationController;
import br.com.precopoint.PrecoPoint.dto.filtro.FornecedorFilterByDistanceResponseDto;
import br.com.precopoint.PrecoPoint.dto.usuario.StatusResponseDto;
import br.com.precopoint.PrecoPoint.dto.usuario.fornecedor.FornecedorRequestDto;
import br.com.precopoint.PrecoPoint.dto.usuario.fornecedor.FornecedorResponseDto;
import br.com.precopoint.PrecoPoint.dto.usuario.fornecedor.UpdateFornecedorRequestDto;
import br.com.precopoint.PrecoPoint.exception.AlreadyExistsException;
import br.com.precopoint.PrecoPoint.exception.DefaultException;
import br.com.precopoint.PrecoPoint.exception.NotFoundException;
import br.com.precopoint.PrecoPoint.model.Usuario;
import br.com.precopoint.PrecoPoint.repository.RoleRepository;
import br.com.precopoint.PrecoPoint.repository.UsuarioRepository;
import br.com.precopoint.PrecoPoint.service.FornecedorService;
import br.com.precopoint.PrecoPoint.service.StatusService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class FornecedorServiceImpl implements FornecedorService {
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    StatusService statusService;
    @Autowired
    AuthenticationController authenticationController;
    @Autowired
    RoleRepository roleRepository;

    @Override
    public ResponseEntity<StatusResponseDto> addFornecedor(FornecedorRequestDto fornecedor) {
        ThreadContext.put("user",authenticationController.getUser());
        try {
            usuarioRepository.findByEmail(fornecedor.getEmail()).ifPresent(consumidorAux ->{
                throw new AlreadyExistsException("E-mail '"+ fornecedor.getEmail() +"' já registrado no sistema.");
            });
            usuarioRepository.findByEmail(fornecedor.getEmail()).ifPresent(fornecedorAux -> {
                throw new AlreadyExistsException("E-mail '"+ fornecedor.getEmail() +"' já registrado no sistema.");
            });
            usuarioRepository.save(fornecedor.toFornecedor(roleRepository));
            log.info("Fornecedor '"+ fornecedor.getEmail() +"' cadastrado com sucesso.");
            return ResponseEntity.status(HttpStatus.CREATED).body(statusService.usuarioStatusTrue());
        }catch(NotFoundException e){
            throw new NotFoundException(e.getMessage());
        }catch(Exception e){
            throw new DefaultException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> updateFornecedor(int idFornecedor, UpdateFornecedorRequestDto updateFornecedorRequestDto) {
        try{
            Usuario fornecedor = usuarioRepository.findById(idFornecedor).orElseThrow(
                    () -> new NotFoundException("Erro: usuário com id '"+ idFornecedor +"' não encontrado."));
            if(updateFornecedorRequestDto.getCep() != null && !updateFornecedorRequestDto.getCep().trim().isEmpty() ){
                fornecedor.setCep(updateFornecedorRequestDto.getCep());
            }
            if(updateFornecedorRequestDto.getNome() != null && !updateFornecedorRequestDto.getNome().trim().isEmpty()){
                fornecedor.setNome(updateFornecedorRequestDto.getNome());
            }
            if(updateFornecedorRequestDto.getLogotipo() != null && !updateFornecedorRequestDto.getLogotipo().trim().isEmpty()){
                fornecedor.setLogotipo(updateFornecedorRequestDto.getLogotipo());
            }
            if(updateFornecedorRequestDto.getSenha() != null && !updateFornecedorRequestDto.getSenha().trim().isEmpty()){
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String senhaCrypt = passwordEncoder.encode(updateFornecedorRequestDto.getSenha());
                fornecedor.setSenha(senhaCrypt);
            }
            fornecedor.setAtualizadoEm(LocalDateTime.now());
            usuarioRepository.save(fornecedor);
            ModelMapper modelMapper = new ModelMapper();
            return ResponseEntity.ok(modelMapper.map(fornecedor, FornecedorResponseDto.class));
        }catch(NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }catch(Exception e) {
            throw new DefaultException("Erro ao atualizar usuário: "+ e.getMessage());
        }
    }



    @Override
    public ResponseEntity<?> getAllFornecedor() {
        try {
            ModelMapper modelMapper = new ModelMapper();
            List<Usuario> fornecedorList = usuarioRepository.findAll();
            List<FornecedorResponseDto> finalList = fornecedorList.stream()
                    .map(fornecedor -> modelMapper.map(fornecedor, FornecedorResponseDto.class)).toList();
            return ResponseEntity.ok(finalList);
        }catch (Exception e){
            throw new DefaultException("Erro ao pegar usuários: "+ e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> deleteFornecedor(int idForncedor) {
        ThreadContext.put("user", authenticationController.getUser());
        try{
            Usuario fornecedor = usuarioRepository.findById(idForncedor).orElseThrow(
                    () -> new NotFoundException("Erro: usuário de id '"+ idForncedor +"' não encontrado"));
            usuarioRepository.delete(fornecedor);
            log.info("Fornecedor '{}' removido com sucesso",fornecedor.getEmail());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch(NotFoundException e){
            throw new NotFoundException(e.getMessage());
        }catch(Exception e){
            throw new DefaultException("Erro ao remover produto: "+ e.getMessage());
        }
    }

    private static final String OSM_API_BASE_URL = "https://nominatim.openstreetmap.org";
    @Override
    public ResponseEntity<List<FornecedorFilterByDistanceResponseDto>> getFornecedoresMaisProximos(String cep) {
        try{
            // Use the OpenStreetMap Nominatim API to get the latitude and longitude for the consumer's CEP
            double[] consumerCoordinates = getCoordinatesFromCep(cep);

            ModelMapper modelMapper = new ModelMapper();
            // Get all Fornecedors from the repository
            List<Usuario> fornecedores = usuarioRepository.findAll();

            List<FornecedorFilterByDistanceResponseDto> fornecedoresFilter = new java.util.ArrayList<>(fornecedores.stream()
                    .map(fornecedor -> modelMapper.map(fornecedor, FornecedorFilterByDistanceResponseDto.class))
                    .toList());

            // Calculate the distance between each Fornecedor and the consumer
            for (FornecedorFilterByDistanceResponseDto fornecedor : fornecedoresFilter) {
                double[] fornecedorCoordinates = getCoordinatesFromCep(fornecedor.getEndereco());
                double distance = haversine(consumerCoordinates[0], consumerCoordinates[1], fornecedorCoordinates[0], fornecedorCoordinates[1]);
                fornecedor.setDistancia(distance);
            }
            //TODO:DISTANCIA ESTA ERRADA
            // Sort the list of Fornecedors by distance
            Collections.sort(fornecedoresFilter, Comparator.comparingDouble(FornecedorFilterByDistanceResponseDto::getDistancia));

            // Return the sorted list of Fornecedors
            return ResponseEntity.ok(fornecedoresFilter);
        }catch(Exception e){
            throw new DefaultException("Erro ao filtrar supermercado por localização: "+ e.getMessage());
        }
    }

    // Helper method to calculate the distance between two points using the Haversine formula
    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c;

        return distance;
    }

    // Helper method to get the latitude and longitude coordinates for a given CEP using OpenStreetMap Nominatim API
    private double[] getCoordinatesFromCep(String cep) {
        RestTemplate restTemplate = new RestTemplate();

        // Build the API URL with the given CEP and format the response as JSON
        String url = OSM_API_BASE_URL + "/search?q=" + cep + ",Brazil&format=json";

        // Send a GET request to the API and parse the response as a JSON array
        ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity(url, Object[].class);
        Object[] responseArray = responseEntity.getBody();

        // Extract the latitude and longitude from the first result in the array
        Map<String, Object> firstResult = (Map<String, Object>) responseArray[0];
        double lat = Double.parseDouble(firstResult.get("lat").toString());
        double lon = Double.parseDouble(firstResult.get("lon").toString());

        return new double[] {lat, lon};
    }
}
