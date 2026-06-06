package itau.teste.cesta.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "itens_cesta",
    uniqueConstraints = @UniqueConstraint(
            name = "uk_itens_cesta_cesta_id_ticker",
            columnNames = {"cesta_id", "ticker"}
    )
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemCestaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String ticker;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal percentual;

    @ManyToOne
    @JoinColumn(name = "cesta_id")
    private CestaRecomendacaoEntity cesta;
}
