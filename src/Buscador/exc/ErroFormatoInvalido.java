package Buscador.exc;

public class ErroFormatoInvalido extends RuntimeException {

    private String mansagemErro;
    public ErroFormatoInvalido(String mansagemErro) {
        this.mansagemErro = mansagemErro;
    }
    @Override
    public String getMessage() {
        return this.mansagemErro;
    }
}
