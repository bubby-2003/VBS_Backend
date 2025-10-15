package com.cts.service;

import java.util.List;

import com.cts.dto.ServiceCenterRequestDTO;
import com.cts.dto.ServiceCenterResponseDTO;

public interface ServiceCenterService {
    public ServiceCenterResponseDTO createServiceCenter(ServiceCenterRequestDTO centerDto);
    public List<ServiceCenterResponseDTO> getAllServiceCenters();
    public ServiceCenterResponseDTO getServiceCenterById(Integer id);
    public void deleteServiceCenterById(Integer id);
    public ServiceCenterResponseDTO updateServiceCenterById(ServiceCenterRequestDTO centerDto, Integer id);
}