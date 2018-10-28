package com.apap.tugas1.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.InstanceModel;
import com.apap.tugas1.repository.InstanceDb;

@Service
@Transactional
public class InstanceServiceImpl implements InstanceService {
	
	@Autowired
	InstanceDb instanceDb;
	
	@Override
	public InstanceDb getInstanceDb() {
		return instanceDb;
	}
	
	@Override
	public InstanceModel getInstanceDetailById(long id) {
		return instanceDb.findById(id);
	}

	@Override
	public List<InstanceModel> allInstances() {
		return instanceDb.findAll();
	}
}
