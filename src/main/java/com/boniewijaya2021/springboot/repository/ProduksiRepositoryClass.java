package com.boniewijaya2021.springboot.repository;

import com.boniewijaya2021.springboot.entity.TblSales;
import com.boniewijaya2021.springboot.pojo.PenjualanPojo;
import com.boniewijaya2021.springboot.pojo.ProduksiPojo;
import com.boniewijaya2021.springboot.utility.AppUtil;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.lang.reflect.Field;
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

        String queryText = "SELECT cast (id_produksi as varchar) id_produksi, nama_barang, tipe_barang, asal_barang, tanggal_produksi, biaya_produksi\n" +
                "FROM sample.tbl_produksi  WHERE 1=1 \n" + sisipan;

        Query query = entityManager.createNativeQuery(queryText);
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
        UUID generatedUUID = UUID.randomUUID();
        String uuid = generatedUUID.toString();
        String namaBarang = produksiPojo.getNamaBarang();
        String tipeBarang = produksiPojo.getTipeBarang();
        String asalBarang = produksiPojo.getAsalBarang();
        LocalDateTime tanggalProduksi = produksiPojo.getTanggalProduksi();
        Integer biayaProduksi = produksiPojo.getBiayaProduksi();

        String queryText = "INSERT INTO sample.tbl_produksi " +
                "(id_produksi, nama_barang, tipe_barang, asal_barang, tanggal_produksi, biaya_produksi)\n" +
                "VALUES " +
                "(CAST(:idProduksi AS UUID),:namaBarang,:tipeBarang,:asalBarang,:tanggalProduksi,:biayaProduksi)";

//        Query query =
        entityManager.createNativeQuery(queryText)
                .setParameter("idProduksi", uuid)
                .setParameter("namaBarang", namaBarang)
                .setParameter("tipeBarang", tipeBarang)
                .setParameter("asalBarang", asalBarang)
                .setParameter("tanggalProduksi", tanggalProduksi)
                .setParameter("biayaProduksi", biayaProduksi).executeUpdate();
    }

    @Transactional
    public void editData(ProduksiPojo produksiPojo) {
        StringBuilder queryString = new StringBuilder("UPDATE sample.tbl_produksi SET");

        // Iterate over the attributes of the provided POJO
        for (Field field : ProduksiPojo.class.getDeclaredFields()) {
            field.setAccessible(true);
            Object value;
            try {
                if (field.getName().equals("idProduksi")) {
                    continue;
                } else {
                    value = field.get(produksiPojo);
                }
            } catch (IllegalAccessException e) {
                // Handle the exception as needed
                continue;
            }

            if (value != null) {
                String snakeCaseField = AppUtil.camelCaseToUnderscore(field.getName());
                queryString.append(" ").append(snakeCaseField).append(" = :").append(field.getName()).append(",");
            }
        }

        // Remove the trailing comma
        queryString.setLength(queryString.length() - 1);

        queryString.append(" WHERE id_produksi = CAST(:idProduksi AS UUID)");

        Query query = entityManager.createNativeQuery(queryString.toString());

        // Set the parameter values for the non-null attributes
        for (Field field : ProduksiPojo.class.getDeclaredFields()) {
            field.setAccessible(true);
            Object value;
            try {
                if (field.getName().equals("idProduksi")) {
                    continue;
                } else {
                    value = field.get(produksiPojo);
                }
            } catch (IllegalAccessException e) {
                // Handle the exception as needed
                continue;
            }

            if (value != null) {
                query.setParameter(field.getName(), value);
            }
        }

        query.setParameter("idProduksi", produksiPojo.getIdProduksi());
        query.executeUpdate();

//        // Retrieve the updated object using a SELECT query
//        String selectQueryString = "SELECT * FROM sample.tbl_produksi WHERE id_produksi = CAST(:idProduksi AS UUID)";
//        Query selectQuery = entityManager.createNativeQuery(selectQueryString);
//        selectQuery.setParameter("idProduksi", produksiPojo.getIdProduksi());
//
//        // Execute the SELECT query and return the updated object
//        ProduksiPojo result = new ProduksiPojo();
//        Object[] obj = (Object[]) selectQuery.getSingleResult();
//        String idProduksi = String.valueOf(obj[0]);
//        String namaBarangS = String.valueOf(obj[1]);
//        String tipeBarangS = String.valueOf(obj[2]);
//        String asalBarang = String.valueOf(obj[3]);
////            Timestamp tanggalProduksiTimestamp = (Timestamp) obj[4];
//        LocalDateTime tanggalProduksi = ((Timestamp) obj[4]).toLocalDateTime();
//        Integer biayaProduksi = (Integer) obj[5];
//
//        result.setIdProduksi(idProduksi);
//        result.setNamaBarang(namaBarangS);
//        result.setTipeBarang(tipeBarangS);
//        result.setAsalBarang(asalBarang);
//        result.setTanggalProduksi(tanggalProduksi);
//        result.setBiayaProduksi(biayaProduksi);
//        return result;
    }

    @Transactional
    public void deleteData(UUID idProduksi){

        String queryText = "DELETE FROM sample.tbl_produksi " +
                "WHERE " +
                "id_produksi = CAST(:idProduksi AS UUID)";
        entityManager.createNativeQuery(queryText)
                .setParameter("idProduksi", idProduksi)
                .executeUpdate();

    }
}
