package itau.teste.rebalanceamento.domain.enums;

public enum TipoRebalanceamentoEnum {
    MUDANCA_CESTA("MUDANCA_CESTA"),
    DESVIO("DESVIO");

    private String tipoRebalanceamento;

    TipoRebalanceamentoEnum(String tipoRebalanceamento) {
        this.tipoRebalanceamento = tipoRebalanceamento;
    }

    public String getTipoRebalanceamento() {
        return this.tipoRebalanceamento;
    }
}
