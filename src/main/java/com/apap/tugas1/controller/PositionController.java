package com.apap.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.PositionModel;
import com.apap.tugas1.model.EmployeeModel;
import com.apap.tugas1.service.InstanceService;
import com.apap.tugas1.service.PositionService;
import com.apap.tugas1.service.EmployeeService;

@Controller
public class PositionController {
	private long tempId;

	@Autowired
	PositionService positionService;
	
	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	InstanceService instanceService;
	
	@RequestMapping(value= "/jabatan/tambah", method = RequestMethod.GET)
	private String addJabatan(Model model) {
		model.addAttribute("jabatan", new PositionModel());
		model.addAttribute("title", "Tambah Jabatan");
		
		return "add-jabatan";	
	}

	@RequestMapping(value= "/jabatan/tambah", method = RequestMethod.POST)
	private String addJabatanSubmit(@ModelAttribute PositionModel jabatan, Model model) {
		positionService.addJabatan(jabatan);
		model.addAttribute("title", "Tambah Jabatan");
		
		return "add";
	}
	
	@RequestMapping(value= "/jabatan/view", method = RequestMethod.GET)
	private String viewJabatan(@RequestParam ("idJabatan") String idJabatan,  Model model) {
		Long id = Long.parseLong(idJabatan);
		PositionModel jabatan = positionService.getPositionDetailById(id);
		
		List<EmployeeModel> allEmployee = employeeService.allEmployees();
		int totalPegawai = positionService.totalPegawai(jabatan, allEmployee);
		
		model.addAttribute("jabatan", jabatan);
		model.addAttribute("totalPegawai", totalPegawai);
		model.addAttribute("title", "Detail Jabatan");

		return "view-jabatan";
	}
	
	@RequestMapping(value= "/jabatan/ubah/{idJabatan}", method = RequestMethod.GET)
	private String update(@PathVariable ("idJabatan") String idJabatan, Model model) {
		Long id = Long.parseLong(idJabatan);
		PositionModel jabatan = positionService.getPositionDetailById(id);

		this.tempId = Long.parseLong(idJabatan);
		jabatan.setId(tempId);
		
		model.addAttribute("jabatanArchived", jabatan);
		model.addAttribute("jabatan", new PositionModel());
		model.addAttribute("title", "Ubah Data Jabatan");
		
		return "update-jabatan";
	}
	
	@RequestMapping(value= "/jabatan/ubah", method = RequestMethod.POST)
	private String updateJabatanSubmit(@ModelAttribute PositionModel jabatan, Model model) {
		jabatan.setId(tempId);
		positionService.updateJabatan(jabatan);
		model.addAttribute("title", "Ubah Data Jabatan");
		
		return "update";
	}
	
	@RequestMapping(value= "/jabatan/hapus/{idJabatan}", method = RequestMethod.POST)
	private String deleteJabatan(@PathVariable(value = "idJabatan") String idJabatan, Model model) {
		List<EmployeeModel> allEmployee = employeeService.allEmployees();
		
		Long id = Long.parseLong(idJabatan);
		PositionModel jabatan = positionService.getPositionDetailById(id);
		
		if (positionService.checkEmployeeByPosition(jabatan, allEmployee)) {
			return "fail-delete";
		}
		
		positionService.deleteJabatan(jabatan);
		model.addAttribute("title", "Hapus Jabatan");
		
		return "delete-jabatan";		
	}
	
	@RequestMapping("/jabatan/viewall")
	public String viewAll (Model model) {
		List<PositionModel> listJabatan = positionService.allPositions();
		
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("title", "Lihat Semua Jabatan");
		return "view-all";	
	}
}
