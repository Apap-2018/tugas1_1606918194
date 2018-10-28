package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.PositionModel;
import com.apap.tugas1.model.EmployeeModel;
import com.apap.tugas1.repository.PositionDb;

public interface PositionService {
	PositionDb getPositionDb();
	PositionModel getPositionDetailById(long id);
	PositionModel getPositionDetailByNama(String nama);
	
	List<PositionModel> allPositions();
	void addJabatan(PositionModel jabatan);
	void deleteJabatan(PositionModel jabatan);
	void updateJabatan(PositionModel dealer);
	
	boolean checkEmployeeByPosition(PositionModel jabatan, List<EmployeeModel> listPegawai);
	int totalPegawai(PositionModel jabatan, List<EmployeeModel> listPegawai);
}
