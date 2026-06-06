package itau.teste.cotacao.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(
        name = "cotacoes",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_cotacoes_data_pregao_ticker",
                columnNames = {"data_pregao", "ticker"}
        )
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CotacaoEntity {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_pregao", nullable = false)
    private LocalDate dataPregao;

    @Column(nullable = false, length = 10)
    private String ticker;

    @Column(name = "preco_abertura", precision = 18, scale = 2)
    private BigDecimal precoAbertura;

    @Column(name = "preco_fechamento", precision = 18, scale = 2)
    private BigDecimal precoFechamento;

    @Column(name = "preco_maximo", precision = 18, scale = 2)
    private BigDecimal precoMaximo;

    @Column(name = "preco_minimo", precision = 18, scale = 2)
    private BigDecimal precoMinimo;
}
