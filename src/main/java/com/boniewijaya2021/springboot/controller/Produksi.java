package com.boniewijaya2021.springboot.controller;

import com.boniewijaya2021.springboot.entity.TblProduksi;
import com.boniewijaya2021.springboot.pojo.ProduksiPojo;
import com.boniewijaya2021.springboot.service.ProduksiPostService;
import com.boniewijaya2021.springboot.service.ProduksiService;
import com.boniewijaya2021.springboot.utility.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/sample")
public class Produksi {

    @Autowired
    private ProduksiService produksiService;

    @Autowired
    private ProduksiPostService produksiPostService;

    @GetMapping("/get/dataProduksi")
    public ResponseEntity getDataProduksi(@RequestParam UUID idProduksi) {
        ResponseEntity responseEntity = produksiService.getDataProduksi(idProduksi);
        return responseEntity;
    }

    @GetMapping("/get/dataProduksiClass")
    public ResponseEntity getDataProduksiClass(String tipeBarang, String namaBarang) {
        ResponseEntity responseEntity = produksiService.getProduksiClassrepo(tipeBarang, namaBarang);
        return responseEntity;
    }

    @PostMapping("/post/dataProduksi")
    public ResponseEntity<MessageModel> postDataProduksi(@RequestBody TblProduksi tblProduksi) {
        ResponseEntity responseEntity = produksiPostService.addDataProduksi(tblProduksi);
        return responseEntity;
    }

    @PostMapping("/post/dataProduksiClass")
    public ResponseEntity<MessageModel> postData(@RequestBody ProduksiPojo produksiPojo) {
        ResponseEntity responseEntity = produksiPostService.addDataProduksiClassrepo(produksiPojo);
        return responseEntity;
    }

    @PutMapping("/put/dataProduksiClass")
    public ResponseEntity<MessageModel> putData(@RequestBody ProduksiPojo produksiPojo) {
        ResponseEntity responseEntity = produksiPostService.editDataProduksiClassrepo(produksiPojo);
        return responseEntity;
    }
    @DeleteMapping("/delete/dataProduksi")
    public ResponseEntity<MessageModel> deleteDataProduksi(@RequestParam UUID idProduksi) {
        ResponseEntity responseEntity = produksiService.deleteDataProduksi(idProduksi);
        return responseEntity;
    }

    @DeleteMapping("/delete/dataProduksiClass")
    public ResponseEntity<MessageModel> deleteData(@RequestParam UUID idProduksi) {
        ResponseEntity responseEntity = produksiService.deleteDataProduksiClassRepo(idProduksi);
        return responseEntity;
    }


}
