package expertostech.autenticacao.jwt.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import expertostech.autenticacao.jwt.configuracoes.Injetaveis;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
public class JWTValidarFilter extends BasicAuthenticationFilter {

    private Injetaveis injetaveis;

    public JWTValidarFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.injetaveis = new Injetaveis();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String atributo = request.getHeader(injetaveis.getHeader());

        if(atributo == null || !atributo.startsWith(injetaveis.getAtributo())){
            chain.doFilter(request, response);
            return;
        }

        String token = atributo.replace(injetaveis.getAtributo(), "");

        UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(token);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);

    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(String token){
        String usuario = JWT.require(Algorithm.HMAC512(injetaveis.getSenha()))
                .build()
                .verify(token)
                .getSubject();

        if(usuario == null){
            return null;
        }

        return new UsernamePasswordAuthenticationToken(usuario,null, new ArrayList<>());
    }


}
