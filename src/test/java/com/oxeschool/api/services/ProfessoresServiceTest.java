package com.oxeschool.api.services;

import com.oxeschool.api.domain.ProfessorDomain;
import com.oxeschool.api.dtos.tokens.TokensResponse;
import com.oxeschool.api.dtos.usuario.request.RegistrarProfessorRequest;
import com.oxeschool.api.dtos.usuario.response.ProfessorResponse;
import com.oxeschool.api.entity.ProfessorEntity;
import com.oxeschool.api.exceptions.customs.usuario.UsuarioJaExisteException;
import com.oxeschool.api.jwt.JwtService;
import com.oxeschool.api.mappers.ProfessorMapper;
import com.oxeschool.api.repository.UsuariosRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProfessoresServiceTest {

    @Mock
    private ProfessorMapper professorMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UsuariosRepository usuariosRepository;
    @Mock
    private JwtService jwtService;

    @InjectMocks
    private ProfessoresService professoresService;

    @Test
    void registrar_comEmailUnico_deveCriptografarSenhaSalvarGerarTokensEMapear() {
        var request = new RegistrarProfessorRequest();
        request.setNome("Prof. Carlos");
        request.setEmail("carlos@email.com");
        request.setSenha("senha123");

        var entidadeSalva = ProfessorEntity.builder()
                .id(1L).nome("Prof. Carlos").email("carlos@email.com").senha("hashSenha").build();
        var domain = new ProfessorDomain();
        domain.setId(1L);
        domain.setNome("Prof. Carlos");
        domain.setEmail("carlos@email.com");
        domain.setSenha("hashSenha");
        var response = new ProfessorResponse(1L, "Prof. Carlos", "carlos@email.com", List.of());

        var tokens = new TokensResponse("accessToken", "refreshToken");

        when(usuariosRepository.existsByEmail("carlos@email.com")).thenReturn(false);
        when(passwordEncoder.encode("senha123")).thenReturn("hashSenha");
        when(usuariosRepository.save(any(ProfessorEntity.class))).thenReturn(entidadeSalva);
        when(jwtService.criarTokens(1L, "Professor")).thenReturn(tokens);
        when(professorMapper.toProfessorDomain(any(ProfessorEntity.class))).thenReturn(domain);
        when(professorMapper.toProfessorResponse(domain)).thenReturn(response);

        var resultado = professoresService.registrar(request);

        assertEquals(tokens, resultado.getTokens());
        assertEquals(1L, resultado.getUsuario().getId());
        assertEquals("carlos@email.com", resultado.getUsuario().getEmail());
        assertEquals("Prof. Carlos", resultado.getUsuario().getNome());
        verify(passwordEncoder).encode("senha123");
        verify(usuariosRepository).save(any(ProfessorEntity.class));
        verify(jwtService).criarTokens(1L, "Professor");
    }

    @Test
    void registrar_comEmailJaExistente_deveLancarUsuarioJaExisteException() {
        var request = new RegistrarProfessorRequest();
        request.setNome("Prof. Carlos");
        request.setEmail("carlos@email.com");
        request.setSenha("senha123");

        when(usuariosRepository.existsByEmail("carlos@email.com")).thenReturn(true);

        assertThrows(UsuarioJaExisteException.class, () -> professoresService.registrar(request));

        verify(usuariosRepository, never()).save(any());
        verify(passwordEncoder, never()).encode(anyString());
        verify(jwtService, never()).criarTokens(any(), anyString());
    }
}