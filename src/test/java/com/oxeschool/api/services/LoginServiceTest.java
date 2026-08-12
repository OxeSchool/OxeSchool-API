package com.oxeschool.api.services;

import com.oxeschool.api.domain.UsuarioDomain;
import com.oxeschool.api.dtos.tokens.TokensResponse;
import com.oxeschool.api.dtos.usuario.request.LoginRequest;
import com.oxeschool.api.dtos.usuario.response.AuthResponse;
import com.oxeschool.api.dtos.usuario.response.UsuarioResponse;
import com.oxeschool.api.entity.UsuarioEntity;
import com.oxeschool.api.enums.TiposDeUsuarios;
import com.oxeschool.api.exceptions.customs.aluno.AlunoNaoEncontradoException;
import com.oxeschool.api.exceptions.customs.professor.ProfessorNaoEncontradoException;
import com.oxeschool.api.jwt.JwtService;
import com.oxeschool.api.mappers.UsuarioMapper;
import com.oxeschool.api.repository.AlunosRepository;
import com.oxeschool.api.repository.ProfessoresRepository;
import com.oxeschool.api.repository.UsuariosRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @Mock
    private AlunosRepository alunosRepository;
    @Mock
    private ProfessoresRepository professorRepository;
    @Mock
    private UsuariosRepository usuariosRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private UsuarioMapper usuarioMapper;

    @InjectMocks
    private LoginService loginService;

    @Test
    void loginAluno_comCredenciaisValidas_deveGerarTokensERetornarAuthResponse() {
        var loginRequest = new LoginRequest("ana@email.com", "senha123");
        var usuario = UsuarioEntity.builder()
                .id(1L).nome("Ana").email("ana@email.com").senha("hashSenha").build();
        var domain = new UsuarioDomain(1L, "Ana", "ana@email.com", "hashSenha");
        var usuarioResponse = new UsuarioResponse(1L, "Ana", "ana@email.com");
        var tokens = new TokensResponse("accessToken", "refreshToken");

        when(usuariosRepository.findByEmail("ana@email.com")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("senha123", "hashSenha")).thenReturn(true);
        when(alunosRepository.existsById(1L)).thenReturn(true);
        when(jwtService.criarTokens(1L, TiposDeUsuarios.Aluno.name())).thenReturn(tokens);
        when(usuarioMapper.toUsuarioDomain(usuario)).thenReturn(domain);
        when(usuarioMapper.toUsuarioResponse(domain)).thenReturn(usuarioResponse);

        AuthResponse resultado = loginService.loginAluno(loginRequest);

        assertEquals(tokens, resultado.getTokens());
        assertEquals("Ana", resultado.getUsuario().getNome());
        assertEquals("ana@email.com", resultado.getUsuario().getEmail());
        verify(jwtService).criarTokens(1L, "Aluno");
    }

    @Test
    void loginAluno_comEmailInexistente_deveLancarAlunoNaoEncontradoException() {
        var loginRequest = new LoginRequest("inexistente@email.com", "senha123");

        when(usuariosRepository.findByEmail("inexistente@email.com")).thenReturn(Optional.empty());

        assertThrows(AlunoNaoEncontradoException.class, () -> loginService.loginAluno(loginRequest));

        verify(jwtService, never()).criarTokens(any(), any());
    }

    @Test
    void loginAluno_comSenhaIncorreta_deveLancarAlunoNaoEncontradoException() {
        var loginRequest = new LoginRequest("ana@email.com", "senhaErrada");
        var usuario = UsuarioEntity.builder()
                .id(1L).nome("Ana").email("ana@email.com").senha("hashSenha").build();

        when(usuariosRepository.findByEmail("ana@email.com")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("senhaErrada", "hashSenha")).thenReturn(false);

        assertThrows(AlunoNaoEncontradoException.class, () -> loginService.loginAluno(loginRequest));

        verify(jwtService, never()).criarTokens(any(), any());
    }

    @Test
    void loginAluno_comUsuarioQueNaoEhAluno_deveLancarAlunoNaoEncontradoException() {
        var loginRequest = new LoginRequest("ana@email.com", "senha123");
        var usuario = UsuarioEntity.builder()
                .id(1L).nome("Ana").email("ana@email.com").senha("hashSenha").build();

        when(usuariosRepository.findByEmail("ana@email.com")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("senha123", "hashSenha")).thenReturn(true);
        when(alunosRepository.existsById(1L)).thenReturn(false);

        assertThrows(AlunoNaoEncontradoException.class, () -> loginService.loginAluno(loginRequest));

        verify(jwtService, never()).criarTokens(any(), any());
    }

    @Test
    void loginProfessor_comCredenciaisValidas_deveGerarTokensERetornarAuthResponse() {
        var loginRequest = new LoginRequest("carlos@email.com", "senha123");
        var usuario = UsuarioEntity.builder()
                .id(2L).nome("Carlos").email("carlos@email.com").senha("hashSenha").build();
        var domain = new UsuarioDomain(2L, "Carlos", "carlos@email.com", "hashSenha");
        var usuarioResponse = new UsuarioResponse(2L, "Carlos", "carlos@email.com");
        var tokens = new TokensResponse("accessToken", "refreshToken");

        when(usuariosRepository.findByEmail("carlos@email.com")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("senha123", "hashSenha")).thenReturn(true);
        when(professorRepository.existsById(2L)).thenReturn(true);
        when(jwtService.criarTokens(2L, TiposDeUsuarios.Professor.name())).thenReturn(tokens);
        when(usuarioMapper.toUsuarioDomain(usuario)).thenReturn(domain);
        when(usuarioMapper.toUsuarioResponse(domain)).thenReturn(usuarioResponse);

        AuthResponse resultado = loginService.loginProfessor(loginRequest);

        assertEquals(tokens, resultado.getTokens());
        assertEquals("Carlos", resultado.getUsuario().getNome());
        verify(jwtService).criarTokens(2L, "Professor");
    }

    @Test
    void loginProfessor_comEmailInexistente_deveLancarProfessorNaoEncontradoException() {
        var loginRequest = new LoginRequest("inexistente@email.com", "senha123");

        when(usuariosRepository.findByEmail("inexistente@email.com")).thenReturn(Optional.empty());

        assertThrows(ProfessorNaoEncontradoException.class, () -> loginService.loginProfessor(loginRequest));

        verify(jwtService, never()).criarTokens(any(), any());
    }
}