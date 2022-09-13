package pl.projectarea.project0.pricealert;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.Optional;

@Controller
public class PriceAlertController {

    private final PriceAlertRepository priceAlertRepository;

    @Autowired
    public PriceAlertController(PriceAlertRepository priceAlertRepository) {
        this.priceAlertRepository = priceAlertRepository;
    }

    @GetMapping(value = {"/alerts"})
    public String showAllAlerts(Model model){
        model.addAttribute("alerts", priceAlertRepository.findAll());
        return "alerts";
    }


    @GetMapping (path = "/alerts/{id}")
    public String getAlertById(@PathVariable("id") int id, Model model) throws IOException {
        Optional<PriceAlert> pa = priceAlertRepository.findById(id);
        PriceAlert object = pa.stream().findFirst().orElse(null);
        model.addAttribute("alert", object);
        return "alert";
    }


/*    @GetMapping("/search/done")
    ResponseEntity<List<Task>> readDoneTasks(@RequestParam(defaultValue="true") boolean state){
        return ResponseEntity.ok(repository.findByDone(state));
    }

    @RequestMapping("/searchByCategory")
    public String homePost(@RequestParam("categoryId") long categoryId, Model model){
        model.addAttribute("books", productService.findAllByCategoryId(categoryId));
        model.addAttribute("booksCount", productService.count());
        model.addAttribute("categories", categoryService.findAll());
        return "home";
    }*/
}
