package com.boniewijaya2021.springboot.service;

import com.boniewijaya2021.springboot.entity.TblProduksi;
import com.boniewijaya2021.springboot.pojo.PenjualanPojo;
import com.boniewijaya2021.springboot.pojo.ProduksiPojo;
import com.boniewijaya2021.springboot.repository.ProduksiRepository;
import com.boniewijaya2021.springboot.repository.ProduksiRepositoryClass;
import com.boniewijaya2021.springboot.utility.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ProduksiService {
    @Autowired
    private ProduksiRepository produksiRepository;

    @Autowired
    private ProduksiRepositoryClass produksiRepositoryClass;

    public ResponseEntity getDataProduksi(UUID idProduksi){
        Map<String, Object> result = new HashMap<>();
        MessageModel msg = new MessageModel();
        try {
            TblProduksi data = produksiRepository.getProduksiByid(idProduksi);
            if(data.getIdProduksi() ==null) {
                msg.setStatus(true);
                msg.setMessage("data tidak ditemukan");
                msg.setData(null);
                return ResponseEntity.ok().body(msg);
            }else {
                msg.setStatus(true);
                msg.setMessage("Success");
                result.put("data", data);
                msg.setData(result);
                return ResponseEntity.ok().body(msg);
            }

        }catch (Exception e){
            msg.setStatus(false);
            msg.setMessage(e.getMessage());
            msg.setData(result);
            return ResponseEntity.ok().body(msg);

        }


    }

    public ResponseEntity deleteDataProduksi(UUID idProduksi){
        MessageModel msg = new MessageModel();
        try{
            produksiRepository.deleteById(idProduksi);
            msg.setStatus(true);
            msg.setMessage("Data Deleted");
            return ResponseEntity.ok().body(msg);
        }catch (Exception e){
            e.printStackTrace();
            msg.setStatus(false);
            msg.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
    }

    public ResponseEntity getProduksiClassrepo(String tipeBarang, String namaBarang){
        Map<String, Object> result = new HashMap<>();
        MessageModel msg = new MessageModel();
        try {
            List<ProduksiPojo> data = produksiRepositoryClass.getDataDinamic(tipeBarang, namaBarang);
            if(data.isEmpty()) {
                msg.setStatus(true);
                msg.setMessage("data tidak ditemukan");
                msg.setData(null);
                return ResponseEntity.ok().body(msg);

            }else {
                msg.setStatus(true);
                msg.setMessage("Success");
                result.put("data", data);
                msg.setData(result);
                return ResponseEntity.ok().body(msg);
            }

        }catch (Exception e){
            msg.setStatus(false);
            msg.setMessage(e.getMessage());
            return ResponseEntity.ok().body(msg);

        }


    }

    public ResponseEntity deleteDataProduksiClassRepo(UUID idProduksi){
        MessageModel msg = new MessageModel();
        try{
            produksiRepositoryClass.deleteData(idProduksi);
            msg.setStatus(true);
            msg.setMessage("Data Deleted");
            return ResponseEntity.ok().body(msg);
        }catch (Exception e){
            e.printStackTrace();
            msg.setStatus(false);
            msg.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
    }
}
