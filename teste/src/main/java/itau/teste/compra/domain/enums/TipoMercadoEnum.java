package itau.teste.compra.domain.enums;

public enum TipoMercadoEnum {
    LOTE("LOTE"),
    FRACIONARIO("FRACIONARIO");

    private String tipoMercado;

    TipoMercadoEnum(String tipoMercado) {
        this.tipoMercado = tipoMercado;
    }

    public String getTipoMercado() {
        return this.tipoMercado;
    }

}
