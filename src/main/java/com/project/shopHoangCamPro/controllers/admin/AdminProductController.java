package com.project.shopHoangCamPro.controllers.admin;

import com.project.shopHoangCamPro.models.Category;
import com.project.shopHoangCamPro.models.Product;
import com.project.shopHoangCamPro.models.ProductVariant;
import com.project.shopHoangCamPro.services.CategoryService;
import com.project.shopHoangCamPro.services.ProductService;
import com.project.shopHoangCamPro.services.ProductVariantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("admin/")
@RequiredArgsConstructor
public class AdminProductController {


    private final ProductService productService;
    private final ProductVariantService productVariantService;
    private  final CategoryService categoryService;

    @RequestMapping("/product/add")
    public String add( Model model){
        try {
            model.addAttribute("PRODUCT", new Product());
            List<Category> categorys = categoryService.getAllCategorys();
            model.addAttribute("listCategories", categorys);
            return "admin/product/add";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error"; // Tên file HTML để hiển thị lỗi
        }
    }

    @RequestMapping("/product-detail/{id}")
    public String getProductsDetailByProductName(@PathVariable("id") Integer id, Model model) {
        try {
            List<Category> categorys = categoryService.getAllCategorys();
            Product product = productService.getProductById(id);
            if (product == null) {
                throw new RuntimeException("Sản phẩm không tồn tại");
            }
            List<ProductVariant> variants = productVariantService.getProductVariantsByProductId(product.getId());
            model.addAttribute("listCategories", categorys);
            model.addAttribute("listProducts", product);
            model.addAttribute("listVariants", variants);

            return "admin/product/product_detail"; // Tên file HTML để hiển thị danh sách sản phẩm
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error"; // Tên file HTML để hiển thị lỗi
        }
    }

    @RequestMapping("/product-Edit/{id}")
    public String getProductEditAdmin(@PathVariable("id") Integer id, Model model) {
        try {
            model.addAttribute("PRODUCT", new Product());
            Product product = productService.getProductById(id);
            List<Category> categorys = categoryService.getAllCategorys();
            if (product == null) {
                throw new RuntimeException("Sản phẩm không tồn tại");
            }
            List<ProductVariant> variants = productVariantService.getProductVariantsByProductId(product.getId());
            model.addAttribute("listCategories", categorys);
            model.addAttribute("listProducts", product);
            model.addAttribute("listVariants", variants);

            return "admin/product/edit";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error"; // Tên file HTML để hiển thị lỗi
        }
    }

    @PostMapping("/product-edit/{id}")
    public String editProduct(
            @PathVariable("id") Integer id,
            @ModelAttribute("PRODUCT") Product updatedProduct,
            @RequestParam("image") MultipartFile productImage,
            @RequestParam("variantSku[]") List<String> variantSkus,
            @RequestParam("variantStorage[]") List<String> variantStorages,
            @RequestParam("variantDiscount[]") List<String> variantDiscounts,
            @RequestParam("variantPrice[]") List<String> variantPrices,
            Model model,
            RedirectAttributes redirectAttributes)
    {
        if (updatedProduct.getCategory() == null) {
            redirectAttributes.addFlashAttribute("error", "Vui lòng chọn danh mục sản phẩm.");
            List<Category> categories = categoryService.getAllCategorys();
            model.addAttribute("listCategories", categories);
            return "redirect:/admin/product/add";
        }
        try {
            // Xử lý ảnh
            if (!productImage.isEmpty()) {
                String uploadDir = "uploads"; //đường dẫn
                String fileName = UUID.randomUUID().toString() + "_" + productImage.getOriginalFilename().replaceAll("[^a-zA-Z0-9.-]", "_");
                Path filePath = Paths.get(uploadDir, fileName);
                Files.copy(productImage.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                updatedProduct.setImageUrl(fileName);
            } else {
                updatedProduct.setImageUrl("default.png");
            }

            // Xử lý cập nhật các biến thể sản phẩm
            List<ProductVariant> updatedVariants = new ArrayList<>();
            for (int i = 0; i < variantSkus.size(); i++) {
                double price = Double.parseDouble(variantPrices.get(i));
                double discount = Double.parseDouble(variantDiscounts.get(i));

                // Kiểm tra điều kiện discount < price
                if (discount >= price) {
                    redirectAttributes.addFlashAttribute("error", "Giá giảm phải nhỏ hơn giá gốc!");
                    return "redirect:/admin/product-Edit/" + updatedProduct.getName();
                }

                ProductVariant variant = new ProductVariant();
                variant.setSku(variantSkus.get(i));
                variant.setStorage(variantStorages.get(i));
                variant.setDiscount(variantDiscounts.get(i));
                variant.setPrice(variantPrices.get(i));
                variant.setProduct(updatedProduct); // Gắn biến thể với sản phẩm
                updatedVariants.add(variant);
            }
            productService.updateProduct(id, updatedProduct);
            //cập nhật biến thể trong cơ sở dữ liệu
            productVariantService.updateVariantsForProduct(id, updatedVariants);

            redirectAttributes.addFlashAttribute("message", "Sửa sản phẩm thành công!");
            return "redirect:/admin/product-Edit/" + updatedProduct.getId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Sửa sản phẩm thất bại!");
            return "redirect:/admin/product-Edit/" + updatedProduct.getId();
        }
    }


    // thêm sản phẩm
    @PostMapping("/add-product")
    public String addProduct(
            @Valid
            @ModelAttribute("PRODUCT") Product product,
            BindingResult result,
            @RequestParam("image") MultipartFile image,
            @RequestParam("variantSku[]") List<String> variantSkus,
            @RequestParam("variantStorage[]") List<String> variantStorages,
            @RequestParam("variantDiscount[]") List<String> variantDiscounts,
            @RequestParam("variantPrice[]") List<String> variantPrices,
            Model model,
            RedirectAttributes redirectAttributes) {
        if(result.hasErrors()){
            List<Category> categories = categoryService.getAllCategorys();
            model.addAttribute("listCategories", categories);
            return "admin/product/add";
        }
        //kiểm tra danh mục sản phẩm
        if (product.getCategory() == null) {
            redirectAttributes.addFlashAttribute("error", "Vui lòng chọn danh mục sản phẩm!");
            List<Category> categories = categoryService.getAllCategorys();
            model.addAttribute("listCategories", categories);
            return "redirect:/admin/product/add";
        }
        try {
            // Xử lý ảnh
            if (!image.isEmpty()) {
                String uploadDir = "uploads";
//                String uploadDir = "src/main/resources/static/uploads"; //đường dẫn
                File uploadPath = new File(uploadDir);
                if (!uploadPath.exists()) {
                    uploadPath.mkdirs();
                }

                String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename().replaceAll("[^a-zA-Z0-9.-]", "_");
                Path filePath = Paths.get(uploadDir, fileName);
                Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                product.setImageUrl(fileName);
            } else {
                product.setImageUrl("default.png");
            }
            //các biến thể sản phẩm
            List<ProductVariant> variants = new ArrayList<>();
            for (int i = 0; i < variantSkus.size(); i++) {
                double price = Double.parseDouble(variantPrices.get(i));
                double discount = Double.parseDouble(variantDiscounts.get(i));

                //kiểm tra điều kiện discount < price
                if (discount >= price) {
                    model.addAttribute("error","Giá giảm phải nhỏ hơn giá gốc!");
                    List<Category> categories = categoryService.getAllCategorys();
                    model.addAttribute("listCategories", categories);
                    return "/admin/product/add";
                }
                ProductVariant variant = new ProductVariant();
                variant.setSku(variantSkus.get(i));
                variant.setStorage(variantStorages.get(i));
                variant.setDiscount(variantDiscounts.get(i));
                variant.setPrice(variantPrices.get(i));
                variant.setProduct(product); // Gắn biến thể vào sản phẩm
                variants.add(variant);
            }
            productService.save(product);
            productVariantService.saveAll(variants);
            redirectAttributes.addFlashAttribute("message", "Thêm sản phẩm thành công!");
            return "redirect:/admin/product/add";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Có lỗi xảy ra khi thêm sản phẩm.");
            List<Category> categories = categoryService.getAllCategorys();
            model.addAttribute("listCategories", categories);
            return "admin/product/add";
        }
    }

    @GetMapping("/product-remove/{id}")
    public String deleteProduct(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            productVariantService.deleteByProductId(id);
            productService.deleteProductById(id);
            redirectAttributes.addFlashAttribute("message", "Xóa sản phẩm thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Không thể xóa sản phẩm. Vui lòng thử lại!");
        }
        return "redirect:/admin/product"; // Đường dẫn quay về danh sách sản phẩm
    }


}