package expertostech.autenticacao.jwt.configuracoes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Injetaveis {

    @Value("${app.tempo}")
    private int TOKEN_EXPIRACAO;

    @Value("${app.senha}")
    private String TOKEN_SENHA;

    @Value("${app.atributo.prefixo}")
    private String ATRIBUTO_PREFIXO;

    @Value("${app.header.atributo}")
    private String HEADER_ATRIBUTO;

    private static String senha;
    private static String header;
    private static String atributo;
    private static int tempo;

    @PostConstruct
    public void iniciarConfiguracao() {
        this.senha = TOKEN_SENHA;
        this.atributo = ATRIBUTO_PREFIXO;
        this.tempo = TOKEN_EXPIRACAO;
        this.header = HEADER_ATRIBUTO;
    }

    public String getSenha() {
        return senha;
    }

    public String getAtributo() {
        return atributo;
    }

    public int getTempo() {
        return tempo;
    }

    public String getHeader() {
        return header;
    }

}
