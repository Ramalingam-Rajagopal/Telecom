package telecom_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import telecom_api.entity.Outage;
import telecom_api.repository.OutageRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OutageService {

    private final OutageRepository outageRepository;

    public Outage saveOutage(Outage outage) {
        return outageRepository.save(outage);
    }

    public List<Outage> getAllOutages() {
        return outageRepository.findAll();
    }

    public Optional<Outage> getOutageById(Long id) {
        return outageRepository.findById(id);
    }

    public void deleteOutage(Long id) {
        outageRepository.deleteById(id);
    }
}