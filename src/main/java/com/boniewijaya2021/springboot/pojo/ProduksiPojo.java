package com.boniewijaya2021.springboot.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
public class ProduksiPojo {
    private String idProduksi;
    private String namaBarang;
    private String tipeBarang;
    private String asalBarang;
    private LocalDateTime tanggalProduksi;
    private Integer biayaProduksi;

//    public ProduksiPojo(String namaBarang, String tipeBarang, String asalBarang, LocalDateTime tanggalProduksi, Integer biayaProduksi) {
//        this.namaBarang = namaBarang;
//        this.tipeBarang = tipeBarang;
//        this.asalBarang = asalBarang;
//        this.tanggalProduksi = tanggalProduksi;
//        this.biayaProduksi = biayaProduksi;
//    }
}
