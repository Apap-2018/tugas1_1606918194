package com.apap.tugas1.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apap.tugas1.model.EmployeeModel;
import com.apap.tugas1.model.InstanceModel;
import com.apap.tugas1.model.PositionModel;
import com.apap.tugas1.model.ProvinceModel;
import com.apap.tugas1.service.EmployeeService;
import com.apap.tugas1.service.InstanceService;
import com.apap.tugas1.service.PositionService;
import com.apap.tugas1.service.ProvinceService;


@Controller
public class EmployeeController {
	private String tempNip;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private InstanceService instanceService;
	
	@Autowired
	private PositionService positionService;
	
	@Autowired
	private ProvinceService provinceService;
	
	@RequestMapping("/")
	private String home(Model model) {
		List<PositionModel> listJabatan = positionService.allPositions();
		List<InstanceModel> listInstansi = instanceService.allInstances();
		
		model.addAttribute("title", "Home");
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listInstansi", listInstansi);

		return "home";
	}
	
	@RequestMapping(value= "/pegawai", method = RequestMethod.GET)
	private String viewPegawai(@RequestParam("nip") String nip,  Model model) {
		EmployeeModel pegawai = employeeService.getEmployeeDetailByNip(nip);
		
		if (pegawai == null) {
			model.addAttribute("title", "Data Pegawai");
			model.addAttribute("nip", nip);
			
			return "error-page";
		}
		
		else {
			int gaji = (int) employeeService.getGaji(pegawai);
			
			model.addAttribute("pegawai", pegawai);
			model.addAttribute("gaji", gaji);
			model.addAttribute("title", "Data Pegawai");
			
			return "view-pegawai";
		}
	}
	
	@RequestMapping(value= "/pegawai/termuda-tertua", method = RequestMethod.GET)
	private String viewTermudaTertua(@RequestParam("idInstansi") String id, Model model) {
		Long idInstance = Long.parseLong(id);
		InstanceModel instance = instanceService.getInstanceDetailById(idInstance);
		
		List<EmployeeModel> listEmployee = instance.getListPegawai();
		Collections.sort(listEmployee);
		
		EmployeeModel youngest = listEmployee.get(listEmployee.size() - 1);
		EmployeeModel oldest = listEmployee.get(0);
		
		model.addAttribute("title", "Lihat Pegawai Tertua dan Termuda");
		model.addAttribute("termuda", youngest);
		model.addAttribute("tertua", oldest);
		model.addAttribute("instansi", instance);
		
		return "view-termuda-tertua";
	}
	
	@RequestMapping(value= "/pegawai/tambah", method = RequestMethod.GET)
	private String add(Model model) {
		EmployeeModel pegawai = new EmployeeModel();
		if (pegawai.getJabatanPegawai() == null) {
			pegawai.setJabatanPegawai(new ArrayList());
		}
		
		pegawai.getJabatanPegawai().add(new PositionModel());
		
		List<ProvinceModel> listProvinsi = provinceService.allProvinces();
		List<PositionModel> listJabatan = positionService.allPositions();
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("title", "Tambah Pegawai");

		return "add-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/tambah/instansi",method = RequestMethod.GET)
	private @ResponseBody List<InstanceModel> checkInstansiTambah(@RequestParam(value="provinsiId") long provinsiId) {
		ProvinceModel chosenProv = provinceService.getProvinceDetailById(provinsiId);
		
		return chosenProv.getListInstansi();
	}
	
	@RequestMapping(value="/pegawai/tambah",method = RequestMethod.POST, params= {"addRow"})
	private String addRowTambah (@ModelAttribute EmployeeModel pegawai, Model model, BindingResult bindingResult) {
		pegawai.getJabatanPegawai().add(new PositionModel());
		if (pegawai.getJabatanPegawai() == null) {
			pegawai.setJabatanPegawai(new ArrayList());
		}
		
		List<PositionModel> listJabatan = positionService.allPositions();
		List<ProvinceModel> listProvinsi = provinceService.allProvinces();
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("title", "Tambah Pegawai");
		
		return "add-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST, params= {"submit"})
	private String tambahPegawaiSubmit(@ModelAttribute EmployeeModel pegawai, Model model) {		
		String nip = employeeService.generateNip(pegawai);
		pegawai.setNip(nip);
		employeeService.addPegawai(pegawai);
		
		model.addAttribute("nip", nip);
		model.addAttribute("title", "Tambah Pegawai");
		
		return "add-success";
	}
	
	@RequestMapping(value= "/pegawai/ubah", method=RequestMethod.GET)
	private String ubahDataPegawai(@RequestParam("nip") String nip,Model model) {
		EmployeeModel pegawai = employeeService.getEmployeeDetailByNip(nip);
		this.tempNip = nip;
		
		List<PositionModel> listJabatan = positionService.allPositions();
		List<ProvinceModel> listProvinsi = provinceService.allProvinces();
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("title", "Ubah Data Pegawai");
		
		return "update-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/ubah/instansi", method = RequestMethod.GET)
	private @ResponseBody List<InstanceModel> checkInstansiUpdate(@RequestParam(value="provinsiId") long provinsiId) {
		ProvinceModel chosenProv = provinceService.getProvinceDetailById(provinsiId);
		
		return chosenProv.getListInstansi();
	}
	
	@RequestMapping(value="/pegawai/ubah",method = RequestMethod.POST, params= {"tambahBaris"})
	private String addRowUbah (@ModelAttribute EmployeeModel pegawai, Model model, BindingResult bindingResult) {
		pegawai.getJabatanPegawai().add(new PositionModel());
		if (pegawai.getJabatanPegawai() == null) {
			pegawai.setJabatanPegawai(new ArrayList());
		}
		
		List<PositionModel> listJabatan = positionService.allPositions();
		List<ProvinceModel> listProvinsi = provinceService.allProvinces();
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("title", "Ubah Data Pegawai");
		
		return "update-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.POST, params= {"submit"})
	private String updatePegawaiSubmit(@ModelAttribute EmployeeModel pegawai, Model model) {
		pegawai.setNip(tempNip);
		employeeService.updatePegawai(pegawai, tempNip);
		
		model.addAttribute("title", "Ubah Data Pegawai");
		model.addAttribute("nip", tempNip);
		
		return "update-success";
	}
	
	@RequestMapping(value= "/get/instansi/{provinsi}", method=RequestMethod.GET, produces = "application/json")
	public ResponseEntity<String> bar(@PathVariable(value = "provinsi") String provinsi) {
	    final HttpHeaders httpHeaders= new HttpHeaders();
	    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
	    ProvinceModel province = provinceService.getProvinceDetailById(Long.parseLong(provinsi));
	    
	    List<InstanceModel> instances = province.getListInstansi();
	    
	    StringBuilder sb = new StringBuilder();
	    for (InstanceModel s : instances) {
	    	sb.append(s.getId());
	    	sb.append(" // ");
	        sb.append(s.getNama());
	        sb.append(" // ");
	    }

	    return new ResponseEntity<String>("{\"instansi\": \""+ sb.toString() + "\"}", httpHeaders, HttpStatus.OK);
	}
	
	@RequestMapping(value= "/pegawai/carii", method=RequestMethod.GET)
	private String cariPegawai(@RequestParam("idProvinsi") int idProvinsi,@RequestParam("idInstansi") int idInstansi,@RequestParam("idJabatan") int idJabatan, Model model) {
		List<EmployeeModel> filter = employeeService.allEmployees();
		
		filter = filter.stream()
			.filter(pegawai -> pegawai.getInstansi().getProvinsi().getId() == idProvinsi)				
			.filter(pegawai -> pegawai.getInstansi().getId() == idInstansi)
			.filter(pegawai -> pegawai.getJabatanPegawai().stream().anyMatch(jabatans -> jabatans.getId() == idJabatan)).collect(Collectors.toList());
		
		List<PositionModel> jabatan = positionService.allPositions();
		List<ProvinceModel> provinsi = provinceService.allProvinces();
		
		model.addAttribute("provinsi", provinsi);
		model.addAttribute("jabatan", jabatan);
		model.addAttribute("pegawai", filter);
		model.addAttribute("title", "Cari Pegawai");
		
		return "find-pegawai";
	}
	
	@RequestMapping(value= "/pegawai/cari", method=RequestMethod.GET)
	private String filterPegawai(Model model) {
		List<PositionModel> jabatan = positionService.allPositions();
		List<ProvinceModel> provinsi = provinceService.allProvinces();
		
		model.addAttribute("provinsi", provinsi);
		model.addAttribute("jabatan", jabatan);
		model.addAttribute("title", "Cari Pegawai");
		
		return "find-pegawai";
	}
}


class Pegawai {   
   EmployeeModel pegawai;
   

   public EmployeeModel getPegawai() {
       return pegawai;
   }   
}