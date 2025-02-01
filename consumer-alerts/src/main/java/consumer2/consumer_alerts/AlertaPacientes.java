package consumer2.consumer_alerts;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
@Table(name = "alertsPatients")
public class AlertaPacientes {
     
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "messagge")
    private String messagge;
    
    @Column(name = "received_at")
    private LocalDateTime received_at;

    
    @PrePersist
    protected void onCreate() {
        received_at = LocalDateTime.now();
    }

     // Constructor vacío para JPA
     public AlertaPacientes() {
    }

    public AlertaPacientes(Long id, String messagge, LocalDateTime received_at) {
        this.id = id;
        this.messagge = messagge;
        this.received_at = received_at;
    }

    public AlertaPacientes(String messagge) {
        this.messagge = messagge;
    }

    
    

}
