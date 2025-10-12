package com.cts.service;

import java.util.List;

import com.cts.dto.ServiceCenterRequestDTO;
import com.cts.entity.ServiceCenter;

public interface ServiceCenterService {
    public ServiceCenter createServiceCenter(ServiceCenterRequestDTO centerDto);
    public List<ServiceCenter> getAllServiceCenters();
    public ServiceCenter getServiceCenterById(Integer id);
    public void deleteServiceCenterById(Integer id);
    public ServiceCenter updateServiceCenterById(ServiceCenterRequestDTO centerDto, Integer id);
}