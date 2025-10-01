package com.cts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.entity.ServiceCenter;

public interface ServiceCenterRepository extends JpaRepository<ServiceCenter, Integer> {

}
