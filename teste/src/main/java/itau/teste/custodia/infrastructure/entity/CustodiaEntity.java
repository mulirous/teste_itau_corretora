package itau.teste.custodia.infrastructure.entity;

import itau.teste.cliente.infrastructure.entity.ContaGraficaEntity;
import itau.teste.compra.infrastructure.entity.DistribuicaoEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;

@Entity
@Table(
        name = "custodias",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_custodias_conta_grafica_ticker",
                columnNames = {"conta_grafica_id", "ticker"}
        )
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustodiaEntity {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String ticker;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(name = "preco_medio", nullable = false, precision = 18, scale = 2)
    private BigDecimal precoMedio;

    @Column(name = "data_ultima_atualizacao", nullable = false)
    private OffsetDateTime dataUltimaAtualizacao = OffsetDateTime.now();

    @ManyToOne
    @JoinColumn(name = "conta_grafica_id")
    private ContaGraficaEntity conta;

    @OneToMany(mappedBy = "custodiaFilhote", fetch = FetchType.LAZY)
    private Set<DistribuicaoEntity> distribuicoes;
}
