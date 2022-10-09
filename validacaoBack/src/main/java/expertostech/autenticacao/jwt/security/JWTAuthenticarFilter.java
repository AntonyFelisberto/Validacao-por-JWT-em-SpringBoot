package expertostech.autenticacao.jwt.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import expertostech.autenticacao.jwt.configuracoes.Injetaveis;
import expertostech.autenticacao.jwt.data.DetalheUsuarioData;
import expertostech.autenticacao.jwt.model.UsuarioModel;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JWTAuthenticarFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final Injetaveis injetaveis;

    public JWTAuthenticarFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.injetaveis = new Injetaveis();
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UsuarioModel usuarioModel= new ObjectMapper().readValue(request.getInputStream(),UsuarioModel.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
               usuarioModel.getUserName(), usuarioModel.getPassword(),new ArrayList<>()
            ));
        } catch (IOException e) {
            throw new RuntimeException("Falha ao autenticar usuario: " + e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        DetalheUsuarioData usuarioData = (DetalheUsuarioData) authResult.getPrincipal();
        String token = JWT.create()
                .withSubject(usuarioData.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+injetaveis.getTempo()))
                .sign(Algorithm.HMAC512(injetaveis.getSenha()));
        response.getWriter().write(token);
        response.getWriter().flush();
    }
}
