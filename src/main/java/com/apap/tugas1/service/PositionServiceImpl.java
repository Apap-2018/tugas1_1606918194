package com.apap.tugas1.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.PositionModel;
import com.apap.tugas1.model.EmployeeModel;
import com.apap.tugas1.repository.PositionDb;

@Service
@Transactional
public class PositionServiceImpl implements PositionService{
	
	@Autowired
	PositionDb positionDb;
	
	@Override
	public PositionDb getPositionDb() {
		return positionDb;
	}
	
	@Override
	public PositionModel getPositionDetailById(long id) {
		return positionDb.findById(id);
	}
	
	@Override
	public PositionModel getPositionDetailByNama(String nama) {
		return positionDb.findByNama(nama);
	}

	@Override
	public List<PositionModel> allPositions() {
		return positionDb.findAll();
	}
	
	@Override
	public void addJabatan(PositionModel position) {
		positionDb.save(position);
	}	
	
	@Override
	public 	void deleteJabatan(PositionModel jabatan) {
		positionDb.delete(jabatan);
	}
	
	@Override
	public void updateJabatan(PositionModel position) {
		for (int i = 0; i < positionDb.findAll().size(); i++) {
			if (positionDb.findAll().get(i).getId() == (position.getId())) {
				PositionModel thePosition = positionDb.findAll().get(i);
				int index = positionDb.findAll().indexOf(thePosition);
				
				positionDb.findAll().get(index).setNama(position.getNama());
				positionDb.findAll().get(index).setDeskripsi(position.getDeskripsi());
				positionDb.findAll().get(index).setGajiPokok(position.getGajiPokok());				
			}
		}
	}
	
	@Override
	public boolean checkEmployeeByPosition(PositionModel jabatan, List<EmployeeModel> listEmployee) {
		boolean theEmployee = false;
		
		for (int i = 0; i < listEmployee.size(); i++) {
			if (listEmployee.get(i).getJabatanPegawai().size() > 1) {
				
				for (int j = 0; j < listEmployee.get(i).getJabatanPegawai().size(); j++) {
					if (listEmployee.get(i).getJabatanPegawai().get(j).getNama().equalsIgnoreCase(jabatan.getNama())) {
						theEmployee = true;
						return theEmployee;
					}
				}
				
			}
			
			else {
				if (listEmployee.get(i).getJabatanPegawai().get(0).getNama().equalsIgnoreCase(jabatan.getNama())) {
					theEmployee = true;
					return theEmployee;
				}
			}
		}
		
		return theEmployee;
	}

	@Override
	public int totalPegawai(PositionModel jabatan, List<EmployeeModel> listPegawai) {
		int totalPegawai = 0;
		
		for (int i = 0; i < listPegawai.size(); i++) {
			if (listPegawai.get(i).getJabatanPegawai().size() > 1) {
				for (int j = 0; j < listPegawai.get(i).getJabatanPegawai().size(); j++) {
					if (listPegawai.get(i).getJabatanPegawai().get(j).getNama().equalsIgnoreCase(jabatan.getNama())) {
						totalPegawai += 1;
					}
				}
				
			}
			
			else {
				if (listPegawai.get(i).getJabatanPegawai().get(0).getNama().equalsIgnoreCase(jabatan.getNama())) {
					totalPegawai += 1;
				}
			}
			
		}
		
		return totalPegawai;
	}
}
