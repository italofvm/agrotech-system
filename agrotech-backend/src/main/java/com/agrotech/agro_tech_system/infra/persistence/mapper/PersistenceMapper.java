package com.agrotech.agro_tech_system.infra.persistence.mapper;

import com.agrotech.agro_tech_system.domain.models.Alerta;
import com.agrotech.agro_tech_system.domain.models.Area;
import com.agrotech.agro_tech_system.domain.models.Leitura;
import com.agrotech.agro_tech_system.domain.models.Regra;
import com.agrotech.agro_tech_system.domain.models.Sensor;
import com.agrotech.agro_tech_system.domain.models.Usuario;
import com.agrotech.agro_tech_system.infra.persistence.entity.AlertaEntity;
import com.agrotech.agro_tech_system.infra.persistence.entity.AreaEntity;
import com.agrotech.agro_tech_system.infra.persistence.entity.LeituraEntity;
import com.agrotech.agro_tech_system.infra.persistence.entity.RegraEntity;
import com.agrotech.agro_tech_system.infra.persistence.entity.SensorEntity;
import com.agrotech.agro_tech_system.infra.persistence.entity.UsuarioEntity;

public class PersistenceMapper {

    private PersistenceMapper() {}

    public static AreaEntity toEntity(Area area) {
        return new AreaEntity(area.getId(), area.getNome(), area.getDescricao(), null);
    }

    public static Area toDomain(AreaEntity entity) {
        return new Area(entity.getId(), entity.getNome(), entity.getDescricao());
    }

    public static RegraEntity toEntity(Regra regra) {
        return new RegraEntity(
                regra.getId(),
                regra.getTipoSensor(),
                regra.getOperador(),
                regra.getValor(),
                regra.getMensagem(),
                regra.isAtivo()
        );
    }

    public static Regra toDomain(RegraEntity entity) {
        return new Regra(
                entity.getId(),
                entity.getTipoSensor(),
                entity.getOperador(),
                entity.getValor(),
                entity.getMensagem(),
                entity.isAtivo()
        );
    }

    public static Sensor toDomain(SensorEntity entity) {
        return new Sensor(
                entity.getId(),
                entity.getNome(),
                entity.getLocalizacao(),
                true,
                entity.getTipo()
        );
    }

    public static Leitura toDomain(LeituraEntity entity) {
        return new Leitura(
                entity.getId(),
                toDomain(entity.getSensor()),
                entity.getValor(),
                entity.getDataHora(),
                entity.getLocalizacao()
        );
    }

    public static AlertaEntity toEntity(Alerta alerta) {
        RegraEntity regraRef = new RegraEntity(
                alerta.getRegra().getId(), null, null, 0, null, false);
        LeituraEntity leituraRef = new LeituraEntity(
                alerta.getLeitura().getId(), null, null, null, null);
        return new AlertaEntity(
                alerta.getId(),
                leituraRef,
                regraRef,
                alerta.getStatus(),
                alerta.getDataHora()
        );
    }

    public static Alerta toDomain(AlertaEntity entity) {
        return new Alerta(
                entity.getId(),
                toDomain(entity.getLeitura()),
                toDomain(entity.getRegra()),
                entity.getStatus(),
                entity.getDataHora()
        );
    }

    public static UsuarioEntity toEntity(Usuario usuario) {
        return new UsuarioEntity(
                usuario.getId(),
                usuario.getLogin(),
                usuario.getSenha(),
                usuario.getRole()
        );
    }

    public static Usuario toDomain(UsuarioEntity entity) {
        return new Usuario(
                entity.getId(),
                entity.getLogin(),
                entity.getSenha(),
                entity.getRole()
        );
    }
}
