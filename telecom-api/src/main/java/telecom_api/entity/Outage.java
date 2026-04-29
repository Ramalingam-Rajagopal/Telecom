package telecom_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import telecom_api.enums.OutageStatus;
import telecom_api.enums.SeverityLevel;
import java.time.LocalDateTime;

@Entity
@Table(name = "outages")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Outage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String area;

    private String description;

    @Enumerated(EnumType.STRING)
    private SeverityLevel severity;

    @Enumerated(EnumType.STRING)
    private OutageStatus status;

    private LocalDateTime reportedAt;
    private LocalDateTime resolvedAt;

    @PrePersist
    public void prePersist(){
        this.reportedAt = LocalDateTime.now();
    }
}