package itau.teste.cesta.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.Set;

@Entity
@Table(name = "cestas_recomendacao")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CestaRecomendacaoEntity {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false)
    private boolean ativa = true;

    @Column(name = "data_criacao", nullable = false)
    private OffsetDateTime dataCriacao = OffsetDateTime.now();

    @Column(name = "data_desativacao")
    private OffsetDateTime dataDesativacao;

    @OneToMany(mappedBy = "cesta", fetch = FetchType.LAZY)
    private Set<ItemCestaEntity> itens;
}
