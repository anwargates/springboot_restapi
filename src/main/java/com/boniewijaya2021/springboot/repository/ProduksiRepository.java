package com.boniewijaya2021.springboot.repository;

import com.boniewijaya2021.springboot.entity.TblProduksi;
import com.boniewijaya2021.springboot.entity.TblSales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface ProduksiRepository extends JpaRepository<TblProduksi, UUID> {
    @Query(value = "SELECT cast (id_produksi as varchar) id_produksi, nama_barang, tipe_barang, asal_barang, tanggal_produksi, biaya_produksi\n" +
            "FROM sample.tbl_produksi where id_produksi =:idProduksi", nativeQuery = true)
    TblProduksi getProduksiByid(@Param("idProduksi") UUID idProduksi);

}
