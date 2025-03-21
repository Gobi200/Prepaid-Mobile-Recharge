package com.example.demo.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Recharge;

@Repository
public interface RechargeRepository extends JpaRepository<Recharge, Long> {
    // Custom query methods can be added here if needed
	
	@Query("SELECT r FROM Recharge r " +
		       "JOIN r.plan p " +
		       "WHERE FUNCTION('DATE_ADD', r.rechargeDate, FUNCTION('INTERVAL', p.validity, 'DAY')) <= :threeDaysLater")
		List<Recharge> findExpiringRecharges(@Param("threeDaysLater") LocalDateTime threeDaysLater);

	 List<Recharge> findByExpiryDateBetween(LocalDate startDate, LocalDate endDate);

	List<Recharge> findByUserUserId(String userId);

}
