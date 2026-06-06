package itau.teste.cliente.domain.enums;

public enum TipoContaGraficaEnum {
    MASTER("MASTER"),
    FILHOTE("FILHOTE");

    private String tipo;

    TipoContaGraficaEnum(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return this.tipo;
    }

}