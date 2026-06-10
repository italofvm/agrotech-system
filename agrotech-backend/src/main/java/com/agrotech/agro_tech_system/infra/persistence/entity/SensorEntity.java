package com.agrotech.agro_tech_system.infra.persistence.entity;

import com.agrotech.agro_tech_system.domain.enums.TipoSensor;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "sensores")
@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class SensorEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String nome;

    private String localizacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoSensor tipo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "area_id", nullable = false)
    private AreaEntity area;

    @OneToMany(
            mappedBy = "sensor",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<LeituraEntity> leituras;
}
