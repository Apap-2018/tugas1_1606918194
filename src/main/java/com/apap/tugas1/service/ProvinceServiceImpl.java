package com.apap.tugas1.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.ProvinceModel;
import com.apap.tugas1.repository.ProvinceDb;

@Service
@Transactional
public class ProvinceServiceImpl implements ProvinceService{ 
	@Autowired
	ProvinceDb provinceDb;
	
	@Override
	public ProvinceDb getProvinceDb() {
		return provinceDb;
	}

	@Override
	public ProvinceModel getProvinceDetailById(long id) {
		// TODO Auto-generated method stub
		return provinceDb.findById(id);
	}

	@Override
	public List<ProvinceModel> allProvinces() {
		// TODO Auto-generated method stub
		return provinceDb.findAll();
	}
}
