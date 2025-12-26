@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService service;
    public ProductController(ProductService p){ this.service=p; }

    @PostMapping
    public Product create(@RequestBody Product p){ return service.createProduct(p); }

    @GetMapping
    public List<Product> all(){ return service.findAll(); }

    @DeleteMapping("/{id}")
    public void deactivate(@PathVariable Long id){ service.deactivateProduct(id); }
}
