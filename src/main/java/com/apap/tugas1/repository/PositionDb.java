package com.apap.tugas1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apap.tugas1.model.PositionModel;

public interface PositionDb extends JpaRepository<PositionModel, Long> {

	PositionModel findById(long id);
	PositionModel findByNama(String nama);
}
