package com.cts.service;

import java.util.List;

import com.cts.entity.ServiceCenter;

public interface ServiceCenterService {
	
	
	public ServiceCenter createServiceCenter(ServiceCenter center);
	public List<ServiceCenter> getAllServiceCenters();
	public ServiceCenter getServiceCenterById(Integer id) ;
	public void deleteServiceCenterById(Integer id) ;
	public ServiceCenter updateServiceCenterById(ServiceCenter center, Integer id);
}