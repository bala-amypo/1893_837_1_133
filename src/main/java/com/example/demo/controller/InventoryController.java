@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    private final InventoryLevelService service;
    public InventoryController(InventoryLevelService s){ this.service=s; }

    @PostMapping
    public InventoryLevel createOrUpdate(@RequestBody InventoryLevel i){ return service.createOrUpdateInventory(i); }

    @GetMapping("/store/{storeId}")
    public List<InventoryLevel> byStore(@PathVariable Long storeId){ return service.getInventoryForStore(storeId); }

    @GetMapping("/product/{productId}")
    public List<InventoryLevel> byProduct(@PathVariable Long productId){ return service.getInventoryForProduct(productId); }
}
