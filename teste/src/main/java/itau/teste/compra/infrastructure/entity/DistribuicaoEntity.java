package itau.teste.compra.infrastructure.entity;

import itau.teste.custodia.infrastructure.entity.CustodiaEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "distribuicoes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DistribuicaoEntity {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String ticker;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(name = "preco_unitario", nullable = false, precision = 18, scale = 2)
    private BigDecimal precoUnitario;

    @Column(name = "data_distribuicao", nullable = false)
    private OffsetDateTime dataDistribuicao = OffsetDateTime.now();

    @ManyToOne
    @JoinColumn(name = "ordem_compra_id")
    private OrdemCompraEntity ordem;

    @ManyToOne
    @JoinColumn(name = "custodia_filhote_id")
    private CustodiaEntity custodiaFilhote;
}
