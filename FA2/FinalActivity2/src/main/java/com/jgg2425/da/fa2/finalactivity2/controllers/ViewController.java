package com.jgg2425.da.fa2.finalactivity2.controllers;

import com.jgg2425.da.fa2.finalactivity2.models.dao.ICategoryDAO;
import com.jgg2425.da.fa2.finalactivity2.models.dao.IProductDAO;
import com.jgg2425.da.fa2.finalactivity2.models.dao.ISellerDAO;
import com.jgg2425.da.fa2.finalactivity2.models.dto.SellerDTO;
import com.jgg2425.da.fa2.finalactivity2.models.entities.Category;
import com.jgg2425.da.fa2.finalactivity2.models.entities.Product;
import com.jgg2425.da.fa2.finalactivity2.models.entities.Seller;
import com.jgg2425.da.fa2.finalactivity2.models.entities.SellerProduct;
import com.jgg2425.da.fa2.finalactivity2.services.UtilsService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", error);
        }
        model.addAttribute("user", new Seller());
        return "login";
    }

    @PostMapping("/login")
    public String checkLogin(@RequestParam(name = "cif") String cif,
                             @RequestParam(name = "password") String password,
                             Model model,
                             HttpSession session) {
        if (cif.isEmpty()) {
            model.addAttribute("error", "Username cannot be empty");
        }
        if (password.isEmpty()) {
            model.addAttribute("error", "Password cannot be empty");
        }
        if (model.containsAttribute("error")) {
            return "login";
        }

        Optional<Seller> optionalSeller = sellerDAO.findByCifAndPassword(cif, utilsService.encode(password));

        if (optionalSeller.isPresent()) {
            // Save seller in a Spring Security session
            session.setAttribute("seller", optionalSeller.get());
            return "redirect:/seller_data";
        } else {
            model.addAttribute("error", "User not found");
            return "login";
        }
    }

    @GetMapping({"/seller_data", "/seller_data.html"})
    public String showSellerData(@AuthenticationPrincipal UserDetails user, Model model) {
        if (user.isCredentialsNonExpired()) {
            Optional<Seller> optionalSeller = sellerDAO.findByCif(user.getUsername());
            if (optionalSeller.isPresent()) {
                model.addAttribute("seller", optionalSeller.get());
            } else {
                model.addAttribute("error", "Seller not found. Please check your Username and password.");
                return "redirect:/login";
            }
            return "seller_data";
        } else {
            model.addAttribute("error", "Credentials are expired");
            return "redirect:/login";
        }
    }

    @PutMapping("/seller_data")
    public String updateSeller(
            @AuthenticationPrincipal UserDetails user,
            Model model,
            @Valid @ModelAttribute("seller") SellerDTO sellerDTO,
            BindingResult binding,
            @RequestParam("id") int sellerId,
            @RequestParam("confirm") String confirmPasswd) {
        if (user.isCredentialsNonExpired()) {
            if (binding.hasErrors()) {
                System.out.println(binding.getAllErrors());
                model.addAttribute("message", "Error: Seller with Id " +sellerId + " does not exist");
                model.addAttribute("theme", "error");
                return "/seller_data";
            }

            if (utilsService.checkSDTODtUpdt(sellerId, sellerDTO)) {
                sellerController.updateSeller(sellerDTO, sellerId);
            } else {
                model.addAttribute("message", "Error: Seller with Id " +sellerId + " does not exist");
                model.addAttribute("theme", "error");
            }
            return "seller_data";
        } else {
            model.addAttribute("error", "Credentials are expired");
        }
        return "redirect:/login";
    }

    @GetMapping("/products")
    public String showProducts(@AuthenticationPrincipal UserDetails user, Model model, @RequestParam(name = "category", required = false) Integer category) {
        if (user.isCredentialsNonExpired()) {
            model.addAttribute("sellerProduct", new SellerProduct());
            List<Category> categories = (List<Category>) categoryDAO.findAll();
            model.addAttribute("categories", categories);
            Category selectedCategory = null;
            List<Product> products = null;
            int userId = sellerDAO.findByCif(user.getUsername()).get().getId();
            if (category == null) {
                products = productDAO.findProductsNotInSellerProducts(userId);
            } else {
                selectedCategory = categoryDAO.findById(category).get();
                model.addAttribute("selectedCategory", selectedCategory);
                products = productDAO.findProductsNotInSellerProducts(userId, selectedCategory.getId());
            }
            model.addAttribute("products", products);
            return "products";
        } else {
            model.addAttribute("title", "Error");
            model.addAttribute("message", "Credentials are expired");
        }
        return "error";
    }

    /*@PostMapping("/products")
    public String saveSellerProducts(@AuthenticationPrincipal UserDetails user, Model model, @RequestParam SellerProduct sellerProduct) {
        if (user.isCredentialsNonExpired()) {

        } else {
            model.addAttribute("title", "Error");
            model.addAttribute("message", "Credentials are expired");
        }
        return "redirect:/seller-api/products";
    }*/
}
