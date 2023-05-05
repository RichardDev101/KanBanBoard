package de.neuefische.backend.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GenerateUUID {

    public String getUUID(){
        return UUID.randomUUID().toString();
    }
}
