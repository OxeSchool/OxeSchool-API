package com.oxeschool.api.configurations;

import com.oxeschool.api.enums.TiposDeUsuarios;
import com.oxeschool.api.exceptions.customs.aluno.AlunoNaoEncontradoException;
import com.oxeschool.api.exceptions.customs.professor.ProfessorNaoEncontradoException;
import com.oxeschool.api.exceptions.customs.token.TokenExpiradoException;
import com.oxeschool.api.exceptions.customs.token.TokenNaBlackListException;
import com.oxeschool.api.exceptions.customs.token.TokenTipoInvalidoException;
import com.oxeschool.api.jwt.JwtService;
import com.oxeschool.api.repository.AlunosRepository;
import com.oxeschool.api.repository.ProfessoresRepository;
import com.oxeschool.api.services.RedisBlackListService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final RedisBlackListService redisBlackListService;
    private final AlunosRepository alunosRepository;
    private final ProfessoresRepository professorRepository;

    public SecurityFilter(JwtService jwtService,
                          RedisBlackListService redisBlackListService,
                          AlunosRepository alunosRepository,
                          ProfessoresRepository professorRepository) {
        this.jwtService = jwtService;
        this.redisBlackListService = redisBlackListService;
        this.alunosRepository = alunosRepository;
        this.professorRepository = professorRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {

            var token = request.getHeader("Authorization");

            if (token != null && token.startsWith("Bearer ")) {

                token = token.replace("Bearer ", "");

                redisBlackListService.verificarSeEstaBlacklisted(jwtService.pegarTokenId(token));

                var tokenDecoded = jwtService.decodificarAccessToken(token);

                if (tokenDecoded.getRole().equals(TiposDeUsuarios.Aluno.name())) {

                    var aluno = alunosRepository.findById(tokenDecoded.getUserId())
                            .orElseThrow(AlunoNaoEncontradoException::new);

                    var authentication = new UsernamePasswordAuthenticationToken(aluno, null, List.of(new SimpleGrantedAuthority("ROLE_" + TiposDeUsuarios.Aluno.name())));

                    SecurityContextHolder.getContext().setAuthentication(authentication);

                }


                if (tokenDecoded.getRole().equals(TiposDeUsuarios.Professor.name())) {

                    var professor = professorRepository.findById(tokenDecoded.getUserId())
                            .orElseThrow(ProfessorNaoEncontradoException::new);

                    var authentication = new UsernamePasswordAuthenticationToken(professor, null, List.of(new SimpleGrantedAuthority("ROLE_" + TiposDeUsuarios.Professor.name())));

                    SecurityContextHolder.getContext().setAuthentication(authentication);

                }


            }

            filterChain.doFilter(request, response);

        } catch (TokenExpiradoException e){

            SecurityContextHolder.clearContext();

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write("""
                    {\s
                        "status": 401,
                        "message": "Token esta expirado"
                     }\s
                   \s""");

        } catch (TokenNaBlackListException e){

            SecurityContextHolder.clearContext();

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write("""
                    {\s
                        "status": 401,
                        "message": "Token está na blacklisted"
                     }\s
                   \s""");

        } catch (AlunoNaoEncontradoException e){

            SecurityContextHolder.clearContext();

            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setContentType("application/json");
            response.getWriter().write("""
                    {\s
                        "status": 404,
                        "message": "aluno não encontrado no filtro"
                     }\s
                   \s""");

        } catch (ProfessorNaoEncontradoException e){

            SecurityContextHolder.clearContext();

            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setContentType("application/json");
            response.getWriter().write("""
                    {\s
                        "status": 404,
                        "message": "professor não encontrado no filtro"
                     }\s
                   \s""");

        } catch (TokenTipoInvalidoException e){

            SecurityContextHolder.clearContext();

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write("""
                    {\s
                        "status": 401,
                        "message": "o tipo do token é invalido"
                     }\s
                   \s""");
        }

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {

        String path = request.getServletPath();

        return path.equals("/refresh");
    }

}
