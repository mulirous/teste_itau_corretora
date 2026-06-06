package itau.teste.cliente.infrastructure.entity;

import itau.teste.fiscal.infrastructure.entity.EventoIrEntity;
import itau.teste.rebalanceamento.infrastructure.entity.RebalanceamentoEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;

@Entity
@Table(name = "clientes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String nome;

    @Column(nullable = false, length = 11, unique = true)
    private String cpf;

    @Column(nullable = false, length = 200)
    private String email;

    @Column(name = "valor_mensal", nullable = false, precision = 18, scale = 2)
    private BigDecimal valorMensal;

    @Column(nullable = false)
    private boolean ativo = true;

    @Column(name = "data_adesao", nullable = false)
    private OffsetDateTime dataAdesao = OffsetDateTime.now();

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private Set<ContaGraficaEntity> contas;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private Set<EventoIrEntity> eventosIr;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private Set<RebalanceamentoEntity> rebalanceamentos;
}
