package com.boniewijaya2021.springboot.service;

import com.boniewijaya2021.springboot.entity.TblProduksi;
import com.boniewijaya2021.springboot.entity.TblSales;
import com.boniewijaya2021.springboot.pojo.ProduksiPojo;
import com.boniewijaya2021.springboot.repository.ProduksiRepository;
import com.boniewijaya2021.springboot.repository.ProduksiRepositoryClass;
import com.boniewijaya2021.springboot.utility.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class ProduksiPostService {

    @Autowired
    private ProduksiRepository produksiRepository;

    @Autowired
    private ProduksiRepositoryClass produksiRepositoryClass;

    public ResponseEntity<MessageModel> addDataProduksi(TblProduksi tblProduksi)
    {
        Map<String, Object> result = new HashMap<>();
        MessageModel msg = new MessageModel();

        try{
            tblProduksi.setTanggalProduksi(LocalDateTime.now());
            produksiRepository.save(tblProduksi);
            msg.setStatus(true);
            msg.setMessage("Success");
            result.put("data", tblProduksi);
            msg.setData(result);
            return ResponseEntity.status(HttpStatus.OK).body(msg);

        }catch (Exception e){
            e.printStackTrace();
            msg.setStatus(false);
            msg.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
    }

    public ResponseEntity<MessageModel> addDataProduksiClassrepo(ProduksiPojo produksiPojo)
    {
        Map<String, Object> result = new HashMap<>();
        MessageModel msg = new MessageModel();

        try{
            produksiPojo.setTanggalProduksi(LocalDateTime.now());
            produksiRepositoryClass.postData(produksiPojo);
            msg.setStatus(true);
            msg.setMessage("Success");
            result.put("data", produksiPojo);
            msg.setData(result);
            return ResponseEntity.status(HttpStatus.OK).body(msg);

        }catch (Exception e){
            e.printStackTrace();
            msg.setStatus(false);
            msg.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
    }
}
