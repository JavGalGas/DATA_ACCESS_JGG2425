package com.jgg2425.da.fa2.finalactivity2.controllers;

import com.jgg2425.da.fa2.finalactivity2.models.dao.ICategoryDAO;
import com.jgg2425.da.fa2.finalactivity2.models.dao.IProductDAO;
import com.jgg2425.da.fa2.finalactivity2.models.dao.ISellerDAO;
import com.jgg2425.da.fa2.finalactivity2.models.dto.SellerDTO;
import com.jgg2425.da.fa2.finalactivity2.models.entities.Category;
import com.jgg2425.da.fa2.finalactivity2.models.entities.Product;
import com.jgg2425.da.fa2.finalactivity2.models.entities.SellerProduct;
import com.jgg2425.da.fa2.finalactivity2.services.UtilsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ViewController {

    @Autowired
    private SellerController sellerController;
    @Autowired
    private ISellerDAO sellerDAO;
    @Autowired
    private IProductDAO productDAO;
    @Autowired
    private ICategoryDAO categoryDAO;

    @Autowired
    private UtilsService utilsService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/seller_data")
    public String showSellerData(@AuthenticationPrincipal UserDetails user, Model model, @RequestParam("id") int sellerId) {
        if (user.isCredentialsNonExpired()) {
            if (sellerDAO.findById(sellerId).isPresent()) {
                model.addAttribute("seller", sellerDAO.findById(sellerId));
                return "seller_data";
            } else {
                model.addAttribute("title", "Error");
                model.addAttribute("message", "Seller with id " + sellerId + " was not found");
            }
        } else {
            model.addAttribute("title", "Error");
            model.addAttribute("message", "Credentials are expired");
        }
        return "error";
    }

    @PutMapping("/seller_data")
    public String updateSeller(@AuthenticationPrincipal UserDetails user, Model model, @Valid @ModelAttribute("seller") SellerDTO sellerDTO, BindingResult binding, @RequestParam("id") int sellerId) {
        if (user.isCredentialsNonExpired()) {
            if (binding.hasErrors()) {
                System.out.println(binding.getAllErrors());
                model.addAttribute("message", "Error: Seller with Id " +sellerId + " does not exist");
                model.addAttribute("theme", "error");
            }

            if (utilsService.checkSDTODtUpdt(sellerId, sellerDTO)) {
                sellerController.updateSeller(sellerDTO, sellerId);
            } else {
                model.addAttribute("message", "Error: Seller with Id " +sellerId + " does not exist");
                model.addAttribute("theme", "error");
            }
            return "seller_data";
        } else {
            model.addAttribute("title", "Error");
            model.addAttribute("message", "Credentials are expired");
        }
        return "error";
    }

    @GetMapping("/products")
    public String showProducts(@AuthenticationPrincipal UserDetails user, Model model) {
        if (user.isCredentialsNonExpired()) {
            model.addAttribute("sellerProduct", new SellerProduct());
            List<Category> categories = (List<Category>) categoryDAO.findAll();
            model.addAttribute("categories", categories);
            List<Product> products = (List<Product>) productDAO.findAll();
            model.addAttribute("products", products);
            return "products";
        } else {
            model.addAttribute("title", "Error");
            model.addAttribute("message", "Credentials are expired");
        }
        return "error";
    }
}
