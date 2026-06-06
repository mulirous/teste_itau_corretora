package itau.teste.fiscal.domain.enums;

public enum TipoEventoIrEnum {
    DEDO_DURO("DEDO_DURO"),
    IR_VENDA("IR_VENDA");

    private String tipoEvento;

    TipoEventoIrEnum(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public String getTipoEvento() {
        return this.tipoEvento;
    }
}
