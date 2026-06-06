package itau.teste.compra.infrastructure.entity;

import itau.teste.cliente.infrastructure.entity.ContaGraficaEntity;
import itau.teste.compra.domain.enums.TipoMercadoEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "ordens_compras")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrdemCompraEntity {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String ticker;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(name = "preco_unitario", nullable = false, precision = 18, scale = 2)
    private BigDecimal precoUnitario;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_mercado", nullable = false, length = 20)
    private TipoMercadoEnum tipoMercado;

    @Column(name = "data_execucao", nullable = false)
    private OffsetDateTime dataUlimaAtualizacao = OffsetDateTime.now();

    @OneToMany(mappedBy = "ordem", fetch = FetchType.LAZY)
    private List<DistribuicaoEntity> distribuicoes;

    @ManyToOne
    @JoinColumn(name = "conta_master_id")
    private ContaGraficaEntity contaMaster;

}
