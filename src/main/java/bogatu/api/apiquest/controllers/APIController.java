package bogatu.api.apiquest.controllers;

import bogatu.api.apiquest.dtos.API.APIDto;
import bogatu.api.apiquest.services.API.APIService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/apis")
@RequiredArgsConstructor
public class APIController {

    private final APIService apiService;


    @PostMapping
    public ResponseEntity<APIDto> registerAPI(@RequestBody APIDto request){
        return new ResponseEntity<>(apiService.registerAPI(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<APIDto>> getAllAPIs(){
        return new ResponseEntity<>(apiService.getAllAPIs(), HttpStatus.OK);
    }

}
