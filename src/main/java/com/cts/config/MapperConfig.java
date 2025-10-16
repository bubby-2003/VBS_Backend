package com.cts.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cts.dto.UsersResponseDTO;
import com.cts.dto.VehicleResponseDTO;
import com.cts.entity.Customer;
import com.cts.entity.Vehicles;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(new PropertyMap<Customer, UsersResponseDTO>() {
            @Override
            protected void configure() {
                map().setEmail(source.getAuth().getEmail());
            }
        });
        
        mapper.addMappings(new PropertyMap<Vehicles, VehicleResponseDTO>() {
            @Override
            protected void configure() {
                map().setEmail(source.getCustomer().getAuth().getEmail());
            }
        });

        return mapper;
    }
}
