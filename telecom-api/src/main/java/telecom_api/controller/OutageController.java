package telecom_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import telecom_api.entity.Outage;
import telecom_api.service.OutageService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/outages")
@RequiredArgsConstructor
public class OutageController {

    private final OutageService outageService;

    @PostMapping
    public Outage createOutage(@RequestBody Outage outage) {
        return outageService.saveOutage(outage);
    }

    @GetMapping
    public List<Outage> getAllOutages() {
        return outageService.getAllOutages();
    }

    @GetMapping("/{id}")
    public Optional<Outage> getOutageById(@PathVariable Long id) {
        return outageService.getOutageById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteOutage(@PathVariable Long id) {
        outageService.deleteOutage(id);
        return "Outage deleted successfully";
    }
}