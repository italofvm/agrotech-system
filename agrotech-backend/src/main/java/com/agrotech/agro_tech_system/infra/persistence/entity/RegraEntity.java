package com.agrotech.agro_tech_system.infra.persistence.entity;


import com.agrotech.agro_tech_system.domain.enums.OperadorRegra;
import com.agrotech.agro_tech_system.domain.enums.TipoSensor;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "regras")
@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class RegraEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoSensor tipoSensor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OperadorRegra operador;

    @Column(nullable = false)
    private double valor;

    @Column(nullable = false)
    private String mensagem;

    @Column(nullable = false)
    private boolean ativo;
}
