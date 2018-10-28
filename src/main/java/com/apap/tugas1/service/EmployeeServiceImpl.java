package com.apap.tugas1.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.EmployeeModel;
import com.apap.tugas1.model.PositionModel;
import com.apap.tugas1.repository.EmployeeDb;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService{
	@Autowired
	private EmployeeDb employeeDb;
	
	@Override
	public EmployeeDb getEmployeeDb() {
		return employeeDb;
	}
	
	@Override
	public Optional<EmployeeModel> getEmployeeDetailById(Long id) {
		return employeeDb.findById(id);
	}
	
	@Override
	public EmployeeModel getEmployeeDetailByNip(String nip) {
		return employeeDb.findByNip(nip);
	}
	
	@Override
	public List<EmployeeModel> allEmployees() {
		return employeeDb.findAll();
	}
	
	@Override
	public void addPegawai(EmployeeModel pegawai) {
		employeeDb.save(pegawai);
	}
	
	@Override
	public void deletePegawai(EmployeeModel pegawai) {
		employeeDb.delete(pegawai);
	}

	@Override
	public void updatePegawai(EmployeeModel pegawai, String oldNip) {
		EmployeeModel archived = employeeDb.findByNip(oldNip);
		int index = employeeDb.findAll().indexOf(archived);
		String nipBaru = "";
		
		String lastCode = "0";
		String firstCode = "";
		int urutanPegawai = 0;
		
		DateFormat df = new SimpleDateFormat("ddMMYY");
		Date tglLahir = pegawai.getTanggalLahir();
		String formatted = df.format(tglLahir);
		System.out.println("date :" + formatted);
		
		Long idInstansi = pegawai.getInstansi().getId();
		System.out.println("kode instansi :"+ idInstansi);
		
		List<EmployeeModel> filter = this.allEmployees();

		  filter = filter.stream()
			.filter(peg -> peg.getInstansi().getId() == archived.getInstansi().getId())
			.filter(peg -> peg.getTanggalLahir().equals(archived.getTanggalLahir()))
			.filter(peg -> peg.getTahunMasuk().equalsIgnoreCase(archived.getTahunMasuk()))
			.collect(Collectors.toList());
	
		  System.out.println(filter.size());
		  
		if (filter.isEmpty()) {
			  urutanPegawai += 1;
		}
		  
		else {
			urutanPegawai = filter.size() + 1;
		}
		
		if (Integer.toString(urutanPegawai).length() == 1) {
			lastCode += Integer.toString(urutanPegawai);
			nipBaru = idInstansi + formatted + pegawai.getTahunMasuk() + lastCode;
		}
		
		else {
			firstCode += Integer.toString(urutanPegawai);
			nipBaru = idInstansi + formatted + pegawai.getTahunMasuk() + firstCode;
		}
		
		employeeDb.findAll().get(index).setNama(pegawai.getNama());
		employeeDb.findAll().get(index).setTempatLahir(pegawai.getTempatLahir());
		employeeDb.findAll().get(index).setTanggalLahir(pegawai.getTanggalLahir());
		employeeDb.findAll().get(index).setTahunMasuk(pegawai.getTahunMasuk());
		employeeDb.findAll().get(index).setInstansi(pegawai.getInstansi());
		employeeDb.findAll().get(index).setJabatanPegawai(pegawai.getJabatanPegawai());
		employeeDb.findAll().get(index).setNip(nipBaru);
	}
	
	@Override
	public double getGaji(EmployeeModel pegawai) {
		double gajiPokok = 0;
		double totalGaji = 0;
		
		for (PositionModel jabatan : pegawai.getJabatanPegawai()) {
			double temp = jabatan.getGajiPokok();
			
			if (temp > gajiPokok) {
				gajiPokok = temp;
			}
		}
		
		double tunjangan = ((pegawai.getInstansi().getProvinsi().getPresentaseTunjangan() / 100) * gajiPokok);
		totalGaji = gajiPokok + tunjangan;
		
		return totalGaji;
	}

	@Override
	public int totalPegawai() {
		return employeeDb.findAll().size();
	}

	@Override
	public String generateNip(EmployeeModel pegawai) {		
		String nipBaru = "";
		
		String lastCode = "0";
		String firstCode = "";
		int urutanPegawai = 0;
		
		DateFormat df = new SimpleDateFormat("ddMMYY");
		Date tglLahir = pegawai.getTanggalLahir();
		String formatted = df.format(tglLahir);
		System.out.println("date :" + formatted);
		
		Long idInstansi = pegawai.getInstansi().getId();
		System.out.println("kode instansi :"+ idInstansi);
		
		List<EmployeeModel> filter = this.allEmployees();

		  filter = filter.stream()
			.filter(peg -> peg.getInstansi().getId() == pegawai.getInstansi().getId())
			.filter(peg -> peg.getTanggalLahir().equals(pegawai.getTanggalLahir()))
			.filter(peg -> peg.getTahunMasuk().equalsIgnoreCase(pegawai.getTahunMasuk()))
			.collect(Collectors.toList());
	
		  System.out.println(filter.size());
		  
		if (filter.isEmpty()) {
			  urutanPegawai += 1;
		}
		  
		else {
			urutanPegawai = filter.size() + 1;
		}
		
		if (Integer.toString(urutanPegawai).length() == 1) {
			lastCode += Integer.toString(urutanPegawai);
			nipBaru = idInstansi + formatted + pegawai.getTahunMasuk() + lastCode;
		}
		
		else {
			firstCode += Integer.toString(urutanPegawai);
			nipBaru = idInstansi + formatted + pegawai.getTahunMasuk() + firstCode;
		}
		
		return nipBaru;
	}
}