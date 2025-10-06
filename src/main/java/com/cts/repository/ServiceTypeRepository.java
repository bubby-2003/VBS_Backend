package com.cts.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cts.entity.ServiceType;

public interface ServiceTypeRepository extends JpaRepository<ServiceType, Integer> {

    // Fetch all service types for a given service center
	@Query("SELECT s FROM ServiceType s WHERE s.serviceCenter.servicecenterId = :centerId")
	List<ServiceType> findAllByServiceCenterId(@Param("centerId") Integer centerId);

	 @Query("SELECT s FROM ServiceType s " +
		       "WHERE s.serviceTypeId = :serviceTypeId " +
		       "AND s.serviceCenter.servicecenterId = :centerId")
		Optional<ServiceType> findByServiceTypeIdAndServiceCenterId(
		        @Param("serviceTypeId") Integer serviceTypeId,
		        @Param("centerId") Integer centerId
		);

}
