package com.apap.tugas1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apap.tugas1.model.ProvinceModel;

public interface ProvinceDb extends JpaRepository<ProvinceModel, Long> {
	ProvinceModel findById(long id);
}
