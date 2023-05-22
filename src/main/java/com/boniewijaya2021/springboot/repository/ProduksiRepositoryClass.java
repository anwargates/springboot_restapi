package com.boniewijaya2021.springboot.repository;

import com.boniewijaya2021.springboot.entity.TblSales;
import com.boniewijaya2021.springboot.pojo.PenjualanPojo;
import com.boniewijaya2021.springboot.pojo.ProduksiPojo;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Repository
public class ProduksiRepositoryClass {

    @PersistenceContext
    private EntityManager entityManager;

    public List<ProduksiPojo> getDataDinamic(String tipeBarang, String namaBarang) {
        StringBuilder qb = new StringBuilder();

        if (namaBarang != null) {
            qb.append(" and nama_barang = :namaBarang \n");
        }
        if (tipeBarang != null) {
            qb.append("and tipe_barang = :tipeBarang \n");
        }

        String sisipan = qb.toString();

        //untuk sisipan yang ditengah

        String QueryText = "SELECT cast (id_produksi as varchar) id_produksi, nama_barang, tipe_barang, asal_barang, tanggal_produksi, biaya_produksi\n" +
                "FROM sample.tbl_produksi  WHERE 1=1 \n" + sisipan;

        Query query = entityManager.createNativeQuery(QueryText);
        if (namaBarang != null) {
            query.setParameter("namaBarang", namaBarang);
        }
        if (tipeBarang != null) {
            query.setParameter("tipeBarang", tipeBarang);
        }


        List hasil = query.getResultList();
        List<ProduksiPojo> result = new ArrayList<>();

        for (Object o : hasil) {
            ProduksiPojo browse = new ProduksiPojo();
            Object[] obj = (Object[]) o;
            String idProduksi = String.valueOf(obj[0]);
            String namaBarangS = String.valueOf(obj[1]);
            String tipeBarangS = String.valueOf(obj[2]);
            String asalBarang = String.valueOf(obj[3]);
//            Timestamp tanggalProduksiTimestamp = (Timestamp) obj[4];
            LocalDateTime tanggalProduksi = ((Timestamp) obj[4]).toLocalDateTime();
            Integer biayaProduksi = (Integer) obj[5];

            browse.setIdProduksi(idProduksi);
            browse.setNamaBarang(namaBarangS);
            browse.setTipeBarang(tipeBarangS);
            browse.setAsalBarang(asalBarang);
            browse.setTanggalProduksi(tanggalProduksi);
            browse.setBiayaProduksi(biayaProduksi);
            result.add(browse);
        }
        return result;
    }

    @Transactional
    public void postData(ProduksiPojo produksiPojo) {
//        StringBuilder qb = new StringBuilder();

        UUID generatedUUID = UUID.randomUUID();
        String uuid = generatedUUID.toString();
        String namaBarang = produksiPojo.getNamaBarang();
        String tipeBarang = produksiPojo.getTipeBarang();
        String asalBarang = produksiPojo.getAsalBarang();
        LocalDateTime tanggalProduksi = produksiPojo.getTanggalProduksi();
        Integer biayaProduksi = produksiPojo.getBiayaProduksi();

        String QueryText = "INSERT INTO sample.tbl_produksi " +
                "(id_produksi, nama_barang, tipe_barang, asal_barang, tanggal_produksi, biaya_produksi)\n" +
                "VALUES " +
                "(CAST(:idProduksi AS UUID),:namaBarang,:tipeBarang,:asalBarang,:tanggalProduksi,:biayaProduksi)";

//        Query query =
        entityManager.createNativeQuery(QueryText)
                .setParameter("idProduksi", uuid)
                .setParameter("namaBarang", namaBarang)
                .setParameter("tipeBarang", tipeBarang)
                .setParameter("asalBarang", asalBarang)
                .setParameter("tanggalProduksi", tanggalProduksi)
                .setParameter("biayaProduksi", biayaProduksi).executeUpdate();

//        query.executeUpdate();
//        Stream hasil = query.getResultStream();
//        System.out.println(query.getResultStream());
    }


}
