package itau.teste.fiscal.infrastructure.entity;

import itau.teste.cliente.infrastructure.entity.ClienteEntity;
import itau.teste.fiscal.domain.enums.TipoEventoIrEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "eventos_ir")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EventoIrEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private TipoEventoIrEnum tipo;

    @Column(name = "valor_base", nullable = false, precision = 18, scale = 2)
    private BigDecimal valorBase;

    @Column(name = "valor_ir", nullable = false, precision = 18, scale = 2)
    private BigDecimal valorIr;

    @Column(name = "publicado_kafka", nullable = false)
    private boolean publicadoKafka = false;

    @Column(name = "data_evento", nullable = false)
    private OffsetDateTime dataEvento = OffsetDateTime.now();

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private ClienteEntity cliente;
}
