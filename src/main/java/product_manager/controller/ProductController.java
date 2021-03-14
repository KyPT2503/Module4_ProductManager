package product_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import product_manager.model.Category;
import product_manager.model.Product;
import product_manager.service.category.ICategoryService;
import product_manager.service.product.IProductService;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private IProductService productService;

    @ModelAttribute("categories")
    public List<Category> getAllCategory() {
        return categoryService.findAll();
    }

    @ModelAttribute("total_money")
    public double getTotalMoney() {
        return productService.totalMoney();
    }

    @GetMapping("create")
    public ModelAndView showCreatePage() {
        return new ModelAndView("product/create", "product", new Product());
    }

    @PostMapping("create-product")
    public ModelAndView create(@ModelAttribute("product") Product product) {
        productService.add(product);
        return new ModelAndView("redirect:/product/create");
    }

    @GetMapping("/all")
    public ModelAndView all() {
        return new ModelAndView("product/index", "products", productService.findAll());
    }

    @GetMapping("/update/{id}")
    public ModelAndView showUpdatePage(@PathVariable("id") Product product) {
        return new ModelAndView("product/create", "product", product);
    }

    @PostMapping("/update-product")
    public ModelAndView update(@ModelAttribute("product") Product product) {
        productService.update(product.getId(), product);
        return new ModelAndView("redirect:/product/all");
    }

    @GetMapping("/remove/{id}")
    public ModelAndView showDetailPage(@PathVariable("id") Product product) {
        return new ModelAndView("product/remove", "product", product);
    }

    @PostMapping("/remove-product")
    public ModelAndView remove(@ModelAttribute("product") Product product) {
        productService.remove(product.getId());
        return new ModelAndView("redirect:/product/all");
    }

    @PostMapping("/category-search")
    public ModelAndView searchByCategory(@ModelAttribute("id") Category category) {
        return new ModelAndView("product/index", "products", productService.findByCategory(category));
    }

    @GetMapping("/top5newest")
    public ModelAndView top5Newest() {
        return new ModelAndView("product/index", "products", productService.find5Newest());
    }

    @GetMapping("top5much-expensive")
    public ModelAndView top5MuchExpensive() {
        return new ModelAndView("product/index", "products", productService.find5MuchExpensive());
    }
}
