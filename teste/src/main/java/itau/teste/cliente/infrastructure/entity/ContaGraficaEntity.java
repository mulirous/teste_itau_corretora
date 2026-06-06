package itau.teste.cliente.infrastructure.entity;

import itau.teste.cliente.domain.enums.TipoContaGraficaEnum;
import itau.teste.custodia.infrastructure.entity.CustodiaEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.Set;

@Entity
@Table(name = "contas_graficas")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ContaGraficaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_conta", nullable = false, length = 20, unique = true)
    private String numeroConta;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private TipoContaGraficaEnum tipo;

    @Column(name = "data_criacao", nullable = false)
    private OffsetDateTime dataCriacao = OffsetDateTime.now();

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private ClienteEntity cliente;

    @OneToMany(mappedBy = "conta", fetch = FetchType.LAZY)
    private Set<CustodiaEntity> custodias;
}
