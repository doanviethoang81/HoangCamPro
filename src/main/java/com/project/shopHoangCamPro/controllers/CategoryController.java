package com.project.shopHoangCamPro.controllers;

import com.project.shopHoangCamPro.dtos.CategoryDTO;
import com.project.shopHoangCamPro.models.Category;
import com.project.shopHoangCamPro.models.Product;
import com.project.shopHoangCamPro.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
//@RequestMapping("/categories")
//@Validated
//dependency Injection
@RequiredArgsConstructor
public class CategoryController {

    @RequestMapping("/category_security")
    public String categorySecurity(){
        return "category_security";
    }

    @RequestMapping("/category_smart_home")
    public String categorySmartHome(){
        return "category_smart_home";
    }



//    private final CategoryService categoryService;

//    @PostMapping("")
//    public ResponseEntity<?> createCategory(
//            @Valid @RequestBody CategoryDTO categoryDTO,
//            BindingResult result){
//        if (result.hasErrors()){
//            List<String> errorMessages = result.getFieldErrors()
//                    .stream()
//                    .map(FieldError::getDefaultMessage)
//                    .toList();
//            return ResponseEntity.badRequest().body(errorMessages);
//        }
//        categoryService.createCategory(categoryDTO);
//        return ResponseEntity.ok("Insert category successfully");
//    }

    //http://localhost:8088/login/category_security
//    @GetMapping("/category_security")
//    public ResponseEntity<List<Category>> getAllCategorys(
//            @RequestParam("page") int page,
//            @RequestParam("limit") int limit
//    ){
//        List<Category> categories = categoryService.getAllCategorys();
//        return ResponseEntity.ok(categories);
//    }


//    @PutMapping ("/{id}")
//    public ResponseEntity<String> updateCategory(
//            @PathVariable Long id,
//            @Valid @RequestBody CategoryDTO categoryDTO
//    ){
//        categoryService.updateCategory(id, categoryDTO);
//        return ResponseEntity.ok("Update category succesfully");
//    }
//
//    @DeleteMapping ("/{id}")
//    public ResponseEntity<String> deleteCategory(@PathVariable Long id){
//        categoryService.deleteCategory(id);
//        return ResponseEntity.ok("delete category with id = " + id + " succesfully");
//    }
}
