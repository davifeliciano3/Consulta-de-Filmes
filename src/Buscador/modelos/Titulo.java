package Buscador.modelos;

import Buscador.Converter.ConverterOmdb;
import Buscador.exc.ErroFormatoInvalido;

public class Titulo implements Comparable<Titulo> {

    private String nome;

    private int anoDeLancamento;

    private int duracaoEmMinutos;

    public Titulo(String nome, int anoDeLancamento) {
        this.nome = nome;
        this.anoDeLancamento = anoDeLancamento;
    }

    public Titulo(ConverterOmdb meuTitulo) {
        this.nome = meuTitulo.title();

        if (meuTitulo.year().length() > 4 ) {
            throw new ErroFormatoInvalido("Não foi possível ler o ano de lançamento de maneira correta");
        }

        this.anoDeLancamento = Integer.valueOf(meuTitulo.year());
        this.duracaoEmMinutos = Integer.valueOf(meuTitulo.runtime().substring(0 , 3).trim());

    }

    public String getNome() {
        return nome;
    }

    public int getAnoDeLancamento() {
        return anoDeLancamento;
    }

    public int getDuracaoEmMinutos() {
        return duracaoEmMinutos;
    }

    public void setDuracaoEmMinutos(int duracaoEmMinutos) {
        this.duracaoEmMinutos = duracaoEmMinutos;
    }


    @Override
    public String toString() {
        return "(Filme: " + getNome() + "\n" +
                "Ano de lançamento: " + getAnoDeLancamento() +"\n"
                +"Duração: " + duracaoEmMinutos +")" +"\n";
    }

    @Override
    public int compareTo(Titulo titulos) {
        return this.getNome().compareTo(titulos.getNome());
    }
}
