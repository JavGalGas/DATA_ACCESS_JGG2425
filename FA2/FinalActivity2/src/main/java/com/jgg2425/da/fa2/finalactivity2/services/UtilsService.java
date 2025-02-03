package com.jgg2425.da.fa2.finalactivity2.services;

import com.jgg2425.da.fa2.finalactivity2.models.dao.ISellerDAO;
import com.jgg2425.da.fa2.finalactivity2.models.dto.SellerDTO;
import com.jgg2425.da.fa2.finalactivity2.models.entities.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.Optional;

@Service
public class UtilsService {

    @Autowired
    private ISellerDAO sellerDAO;

    public String encode(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] hashedBytes = md.digest(password.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes)
            {
                sb.append(String.format("%02x",  b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException exception) {
            return exception.getMessage();
        }
    }

    public boolean checkSDTODtUpdt(int sellerId, SellerDTO sellerDTO) {
        if (sellerDTO == null) {
            return false;
        }

        if (sellerDTO.getName() == null || sellerDTO.getName().isBlank() ||
                sellerDTO.getBusinessName() == null || sellerDTO.getBusinessName().isBlank() ||
                sellerDTO.getPhone() == null || sellerDTO.getPhone().isBlank() ||
                sellerDTO.getEmail() == null || sellerDTO.getEmail().isBlank()) {
            return false;
        }

        Optional<Seller> optionalSeller = sellerDAO.findById(sellerId);
        if (optionalSeller.isEmpty()) {
            return false;
        }

        Seller seller = optionalSeller.get();

        return !Objects.equals(sellerDTO.getName(), seller.getName()) ||
                !Objects.equals(sellerDTO.getBusinessName(), seller.getBusinessName()) ||
                !Objects.equals(sellerDTO.getPhone(), seller.getPhone()) ||
                !Objects.equals(sellerDTO.getEmail(), seller.getEmail()) ||
                !Objects.equals(sellerDTO.getPlainPassword(), seller.getPlainPassword());
    }
}
