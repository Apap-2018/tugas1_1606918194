package com.apap.tugas1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tugas1.model.EmployeeModel;

@Repository
public interface EmployeeDb extends JpaRepository<EmployeeModel, Long> {
	EmployeeModel findByNip(String nip);
}
