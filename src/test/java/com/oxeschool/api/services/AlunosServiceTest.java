package com.oxeschool.api.services;

import com.oxeschool.api.domain.AlunoDomain;
import com.oxeschool.api.dtos.tokens.TokensResponse;
import com.oxeschool.api.dtos.usuario.request.RegistrarAlunoRequest;
import com.oxeschool.api.dtos.usuario.response.AlunoResponse;
import com.oxeschool.api.entity.AlunoEntity;
import com.oxeschool.api.exceptions.customs.usuario.UsuarioJaExisteException;
import com.oxeschool.api.jwt.JwtService;
import com.oxeschool.api.mappers.AlunoMapper;
import com.oxeschool.api.repository.UsuariosRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlunosServiceTest {

    @Mock
    private AlunoMapper alunoMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UsuariosRepository usuariosRepository;
    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AlunosService alunosService;

    @Test
    void registrar_comEmailUnico_deveCriptografarSenhaSalvarGerarTokensEMapear() {
        var request = new RegistrarAlunoRequest();
        request.setNome("Ana");
        request.setEmail("ana@email.com");
        request.setSenha("senha123");

        var entidadeSalva = AlunoEntity.builder()
                .id(1L).nome("Ana").email("ana@email.com").senha("hashSenha").build();
        var domain = new AlunoDomain();
        domain.setId(1L); domain.setNome("Ana"); domain.setEmail("ana@email.com"); domain.setSenha("hashSenha");

        var response = new AlunoResponse();
        response.setId(1L);
        response.setEmail("ana@email.com");
        response.setNome("Ana");

        var tokens = new TokensResponse("accessToken", "refreshToken");

        when(usuariosRepository.existsByEmail("ana@email.com")).thenReturn(false);
        when(passwordEncoder.encode("senha123")).thenReturn("hashSenha");
        when(usuariosRepository.save(any(AlunoEntity.class))).thenReturn(entidadeSalva);
        when(jwtService.criarTokens(1L, "Aluno")).thenReturn(tokens);
        when(alunoMapper.toAlunoDomain(any(AlunoEntity.class))).thenReturn(domain);
        when(alunoMapper.toAlunoResponse(domain)).thenReturn(response);

        var resultado = alunosService.registrar(request);

        assertEquals(tokens, resultado.getTokens());
        assertEquals(1L, resultado.getUsuario().getId());
        assertEquals("ana@email.com", resultado.getUsuario().getEmail());
        verify(passwordEncoder).encode("senha123");
        verify(usuariosRepository).save(any(AlunoEntity.class));
        verify(jwtService).criarTokens(1L, "Aluno");
    }

    // Regra RN01: e-mail já usado -> exceção, nada é salvo
    @Test
    void registrar_comEmailJaExistente_deveLancarUsuarioJaExisteException() {
        var request = new RegistrarAlunoRequest();
        request.setNome("Ana");
        request.setEmail("ana@email.com");
        request.setSenha("senha123");

        when(usuariosRepository.existsByEmail("ana@email.com")).thenReturn(true);

        assertThrows(UsuarioJaExisteException.class, () -> alunosService.registrar(request));

        verify(usuariosRepository, never()).save(any());
        verify(passwordEncoder, never()).encode(anyString());
        verify(jwtService, never()).criarTokens(any(), anyString());
    }
}