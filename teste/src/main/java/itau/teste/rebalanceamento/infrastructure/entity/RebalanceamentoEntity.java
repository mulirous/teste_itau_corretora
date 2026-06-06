package itau.teste.rebalanceamento.infrastructure.entity;

import itau.teste.cliente.infrastructure.entity.ClienteEntity;
import itau.teste.rebalanceamento.domain.enums.TipoRebalanceamentoEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "rebalanceamentos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RebalanceamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private TipoRebalanceamentoEnum tipo;

    @Column(name = "ticker_vendido", length = 10)
    private String tickerVendido;

    @Column(name = "ticker_comprado", length = 10)
    private String tickerComprado;

    @Column(name = "valor_venda", precision = 18, scale = 2)
    private BigDecimal valorVenda;

    @Column(name = "data_rebalanceamento", nullable = false)
    private OffsetDateTime dataRebalanceamento = OffsetDateTime.now();

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private ClienteEntity cliente;
}
