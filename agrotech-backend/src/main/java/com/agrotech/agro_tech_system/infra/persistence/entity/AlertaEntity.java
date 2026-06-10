package com.agrotech.agro_tech_system.infra.persistence.entity;


import com.agrotech.agro_tech_system.domain.enums.StatusAlerta;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "alertas")
@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class AlertaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "leituras_id", nullable = false)
    private LeituraEntity leitura;

    @ManyToOne(optional = false)
    @JoinColumn(name = "regras_id", nullable = false)
    private RegraEntity regra;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAlerta status;

    @Column(nullable = false)
    private LocalDateTime dataHora;
}
