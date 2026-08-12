package com.oxeschool.api.services;

import com.oxeschool.api.domain.ProfessorDomain;
import com.oxeschool.api.dtos.usuario.request.RegistrarProfessorRequest;
import com.oxeschool.api.dtos.usuario.response.ProfessorResponse;
import com.oxeschool.api.entity.ProfessorEntity;
import com.oxeschool.api.exceptions.customs.professor.ProfessorJaExisteException;
import com.oxeschool.api.mappers.ProfessorMapper;
import com.oxeschool.api.repository.ProfessoresRepository;
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
    private ProfessoresRepository professoresRepository;
    @Mock
    private ProfessorMapper professorMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UsuariosRepository usuariosRepository;

    @InjectMocks
    private ProfessoresService professoresService;

    @Test
    void registrar_comEmailUnico_deveCriptografarSenhaSalvarEMapear() {
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

        when(usuariosRepository.existsByEmail("carlos@email.com")).thenReturn(false);
        when(passwordEncoder.encode("senha123")).thenReturn("hashSenha");
        when(professoresRepository.save(any(ProfessorEntity.class))).thenReturn(entidadeSalva);
        when(professorMapper.toProfessorDomain(any(ProfessorEntity.class))).thenReturn(domain);
        when(professorMapper.toProfessorResponse(domain)).thenReturn(response);

        var resultado = professoresService.registrar(request);

        assertEquals(1L, resultado.getUsuario().getId());
        assertEquals("carlos@email.com", resultado.getUsuario().getEmail());
        assertEquals("Prof. Carlos", resultado.getUsuario().getNome());
        verify(passwordEncoder).encode("senha123");
        verify(professoresRepository).save(any(ProfessorEntity.class));
    }

    @Test
    void registrar_comEmailJaExistente_deveLancarProfessorJaExisteException() {
        var request = new RegistrarProfessorRequest();
        request.setNome("Prof. Carlos");
        request.setEmail("carlos@email.com");
        request.setSenha("senha123");

        when(usuariosRepository.existsByEmail("carlos@email.com")).thenReturn(true);

        assertThrows(ProfessorJaExisteException.class, () -> professoresService.registrar(request));

        verify(professoresRepository, never()).save(any());
        verify(passwordEncoder, never()).encode(anyString());
    }
}