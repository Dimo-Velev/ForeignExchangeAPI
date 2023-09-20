package exchange.foreignexchangeapi.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String errorMessage;
    @Lob
    private String stackTrace;
    @Column
    private LocalDateTime timeStamp;
}
