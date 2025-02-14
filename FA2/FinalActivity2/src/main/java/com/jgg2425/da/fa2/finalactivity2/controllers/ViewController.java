package com.jgg2425.da.fa2.finalactivity2.controllers;

import com.jgg2425.da.fa2.finalactivity2.models.dao.ICategoryDAO;
import com.jgg2425.da.fa2.finalactivity2.models.dao.IProductDAO;
import com.jgg2425.da.fa2.finalactivity2.models.dao.ISellerDAO;
import com.jgg2425.da.fa2.finalactivity2.models.dto.SellerDTO;
import com.jgg2425.da.fa2.finalactivity2.models.dto.SellerProductDTO;
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
import org.springframework.validation.ObjectError;
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
    public String login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model) {
        if (error != null) {
            if (error.equals("true"))
                model.addAttribute("error", "Wrong credentials. Please try again.");
            model.addAttribute("error", error);
        }
        if (logout != null) {
            model.addAttribute("logout", logout);
        }
        return "login";
    }

//    @PostMapping("/login")
//    public String checkLogin(@RequestParam(name = "cif") String cif,
//                             @RequestParam(name = "password") String password,
//                             Model model,
//                             HttpSession session) {
//        String errorMessage = "";
//        if (cif.isEmpty()) {
//            errorMessage += "Username";
//        }
//        if (password.isEmpty() && !errorMessage.isEmpty()) {
//            errorMessage += " and Password";
//        } else if (password.isEmpty()) {
//            errorMessage += "Password";
//        }
//        if (!errorMessage.isEmpty()) {
//            errorMessage += " cannot be empty";
//            model.addAttribute("errorMessage", errorMessage);
//        }
//        if (model.containsAttribute("errorMessage")) {
//            return "login";
//        }
//
//        Optional<Seller> optionalSeller = sellerDAO.findByCifAndPassword(cif, utilsService.encode(password));
//
//        if (optionalSeller.isPresent()) {
//            // Save seller in a Spring Security session
//            session.setAttribute("seller", optionalSeller.get());
//            return "redirect:/seller_data?";
//        } else {
//            model.addAttribute("errorMessage", "User not found. Please try again");
//            return "login";
//        }
//    }

    @GetMapping({"/seller_data", "/seller_data.html"})
    public String showSellerData(@AuthenticationPrincipal UserDetails user, Model model) {
        if (user.isCredentialsNonExpired()) {
            Optional<Seller> optionalSeller = sellerDAO.findByCif(user.getUsername());
            if (optionalSeller.isPresent()) {
                SellerDTO sellerDTO = new SellerDTO();
                sellerDTO.setCif(optionalSeller.get().getCif());
                sellerDTO.setName(optionalSeller.get().getName());
                sellerDTO.setBusinessName(optionalSeller.get().getBusinessName());
                sellerDTO.setEmail(optionalSeller.get().getEmail());
                sellerDTO.setPhone(optionalSeller.get().getPhone());
                sellerDTO.setPlainPassword(optionalSeller.get().getPlainPassword());

                model.addAttribute("sellerDTO", sellerDTO);
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
            @Valid @ModelAttribute("sellerDTO") SellerDTO sellerDTO,
            BindingResult binding,
            @RequestParam("password-confirm") String confirmPasswd) {
        if (user.isCredentialsNonExpired()) {
            int sellerId = Integer.MIN_VALUE;
            Optional<Seller> optionalSeller = sellerDAO.findByCif(user.getUsername());

            if (optionalSeller.isPresent()) {
                sellerId = optionalSeller.get().getId();
            }

            if (binding.hasErrors()) {
                StringBuilder errorMessage = new StringBuilder("\n");
                for (ObjectError error : binding.getAllErrors()) {
                    errorMessage.append("\n").append(error.getDefaultMessage());
                }
                model.addAttribute("message", "Binding Error: " + errorMessage);
                model.addAttribute("theme", "error");
                return "seller_data";
            }

            if (utilsService.checkSDTODtUpdt(sellerId, sellerDTO)) {
                sellerController.updateSeller(sellerDTO, sellerId, confirmPasswd);
                model.addAttribute("message", "Seller updated successfully");
                model.addAttribute("theme", "success");
            } else {
                model.addAttribute("message", "Error: The given seller data is not valid for the update.");
                model.addAttribute("theme", "error");
            }

            return "seller_data";
        } else {
            model.addAttribute("error", "Credentials are expired");
            return "redirect:/login";
        }
    }

    @GetMapping("/products")
    public String showProducts(
            @AuthenticationPrincipal UserDetails user,
            Model model,
            @RequestParam(name = "selectedCategory", required = false) Integer category
    ) {
        if (user.isCredentialsNonExpired()) {
            model.addAttribute("sellerProduct", new SellerProductDTO());
            Category selectedCategory = null;
            List<Product> products = null;
            int userId = 0;
            Optional<Seller> optionalSeller = sellerDAO.findByCif(user.getUsername());
            if (optionalSeller.isPresent()) {
                userId = optionalSeller.get().getId();
            }
            List<Category> categories = (userId != 0) ? categoryDAO.findAllCategWithProducts(userId) : null;
            model.addAttribute("categories", categories);
            if (category == null) {
                products = productDAO.getNonAddedProducts(userId);
            } else {
                Optional<Category> optionalCategory = categoryDAO.findById(category);
                if (optionalCategory.isPresent()) {
                    selectedCategory = optionalCategory.get();
                    model.addAttribute("selectedCategory", selectedCategory);
                    products = productDAO.getNonAddedProductsByCategory(userId, selectedCategory.getId());
                } else {
                    products = productDAO.getNonAddedProducts(userId);
                }
            }
            model.addAttribute("products", products);
            return "products";
        } else {
            model.addAttribute("error", "Credentials are expired");
        }
        return "redirect:/login";
    }

    @PostMapping("/products")
    public String saveSellerProducts(
            @AuthenticationPrincipal UserDetails user,
            Model model,
            @ModelAttribute SellerProductDTO sellerProduct,
            BindingResult binding
    ) {
        if (user.isCredentialsNonExpired()) {
            if (binding.hasErrors()) {
                StringBuilder errorMessage = new StringBuilder("\n");
                for (ObjectError error : binding.getAllErrors()) {
                    errorMessage.append("\n").append(error.getDefaultMessage());
                }
                model.addAttribute("message", "Binding Error: " + errorMessage);
                model.addAttribute("theme", "error");
                return "products";
            }
        } else {
            model.addAttribute("error", "Credentials are expired");
        }
        return "redirect:/login";
    }
}
