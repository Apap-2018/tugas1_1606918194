package com.apap.tugas1.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "pegawai")
public class EmployeeModel implements Serializable, Comparable<EmployeeModel> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotNull
	@Size(max = 255)
	@Column(name = "nip", nullable = false, unique= true)
	private String nip;

	@NotNull
	@Size(max = 255)
	@Column(name = "nama", nullable = false)
	private String nama;

	@NotNull
	@Size(max = 255)
	@Column(name = "tempat_lahir", nullable = false)
	private String tempatLahir;
	
	@NotNull
	@Column(name = "tanggal_lahir", nullable = false)
	@DateTimeFormat(iso=ISO.DATE)
	private Date tanggalLahir;

	@NotNull
	@Size(max = 255)
	@Column(name = "tahun_masuk", nullable = false)
	private String tahunMasuk;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_instansi", referencedColumnName = "id", nullable = false)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	@JsonIgnore
	private InstanceModel instansi;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "jabatan_pegawai", joinColumns = { @JoinColumn(name = "id_pegawai") }, inverseJoinColumns = { @JoinColumn(name = "id_jabatan") })
	private List<PositionModel> jabatanPegawai = new ArrayList<PositionModel>();

	private String[] jabatan;
	
	public String[] getJabatan() {
		return jabatan;
	}

	public void setJabatan(String[] jabatan) {
		this.jabatan = jabatan;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNip() {
		return nip;
	}

	public void setNip(String nip) {
		this.nip = nip;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getTempatLahir() {
		return tempatLahir;
	}

	public void setTempatLahir(String tempatLahir) {
		this.tempatLahir = tempatLahir;
	}

	public Date getTanggalLahir() {
		return tanggalLahir;
	}

	public void setTanggalLahir(Date tanggalLahir) {
		this.tanggalLahir = tanggalLahir;
	}

	public String getTahunMasuk() {
		return tahunMasuk;
	}

	public void setTahunMasuk(String tahunMasuk) {
		this.tahunMasuk = tahunMasuk;
	}

	public InstanceModel getInstansi() {
		return instansi;
	}

	public void setInstansi(InstanceModel instansi) {
		this.instansi = instansi;
	}

	public List<PositionModel> getJabatanPegawai() {
		return jabatanPegawai;
	}

	public void setJabatanPegawai(List<PositionModel> jabatanPegawai) {
		this.jabatanPegawai = jabatanPegawai;
	}
	
	@Override
	public int compareTo(EmployeeModel other) { 
		return this.tanggalLahir.compareTo(other.getTanggalLahir()); 
	} 
}