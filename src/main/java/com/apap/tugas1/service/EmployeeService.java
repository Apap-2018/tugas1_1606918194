package com.apap.tugas1.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.apap.tugas1.model.EmployeeModel;
import com.apap.tugas1.repository.EmployeeDb;


public interface EmployeeService {
	EmployeeDb getEmployeeDb();
	
	Optional<EmployeeModel> getEmployeeDetailById(Long id);
	EmployeeModel getEmployeeDetailByNip(String nip);
	
	List<EmployeeModel> allEmployees();
	void addPegawai(EmployeeModel pegawai);
	void deletePegawai(EmployeeModel pegawai);
	void updatePegawai(EmployeeModel pegawai, String oldNip);
	
	double getGaji(EmployeeModel pegawai);
	String generateNip(long idInstansi, Date tanggalLahir, String tahunMasuk);
	int totalPegawai();
}
