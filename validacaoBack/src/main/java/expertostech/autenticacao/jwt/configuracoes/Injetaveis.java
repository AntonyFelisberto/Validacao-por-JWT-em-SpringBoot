package expertostech.autenticacao.jwt.configuracoes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Injetaveis {
    @Value("${app.tempo}")
    private Integer TOKEN_EXPIRACAO;
    @Value("${app.senha}")
    private String TOKEN_SENHA;

    @Value("app.header.atributo")
    private String HEADER_ATRIBUTO;

    @Value("app.atributo.prefixo")
    private String ATRIBUTO_PREFIXO;

    public Integer TOKEN_EXPIRACAO(){
        return TOKEN_EXPIRACAO;
    }

    public String TOKEN_SENHA(){
        return TOKEN_SENHA;
    }

    public String HEADER_ATRIBUTO(){
        return HEADER_ATRIBUTO;
    }

    public String ATRIBUTO_PREFIXO(){
        return ATRIBUTO_PREFIXO;
    }

}
