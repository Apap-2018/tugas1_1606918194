package com.apap.tugas1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.apap.tugas1.model.InstanceModel;

public interface InstanceDb extends JpaRepository<InstanceModel, Long>{
	InstanceModel findById(long id);
}
