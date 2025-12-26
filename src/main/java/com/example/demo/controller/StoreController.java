@RestController
@RequestMapping("/api/stores")
public class StoreController {
    private final StoreService service;
    public StoreController(StoreService s){ this.service=s; }

    @PostMapping
    public Store create(@RequestBody Store s){ return service.createStore(s); }

    @GetMapping
    public List<Store> all(){ return service.findAll(); }

    @PutMapping("/{id}")
    public Store update(@PathVariable Long id, @RequestBody Store s){ return service.updateStore(id,s); }

    @DeleteMapping("/{id}")
    public void deactivate(@PathVariable Long id){ service.deactivateStore(id); }
}
