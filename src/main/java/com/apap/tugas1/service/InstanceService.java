package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.InstanceModel;
import com.apap.tugas1.repository.InstanceDb;

public interface InstanceService {
	InstanceDb getInstanceDb();
	InstanceModel getInstanceDetailById(long id);
	List<InstanceModel> allInstances();
}
