package com.oxeschool.api.services;

import com.oxeschool.api.domain.UsuarioDomain;
import com.oxeschool.api.dtos.usuario.request.EditarEmailUsuarioRequest;
import com.oxeschool.api.dtos.usuario.request.EditarNomeUsuarioRequest;
import com.oxeschool.api.dtos.usuario.request.EditarSenhaUsuarioRequest;
import com.oxeschool.api.dtos.usuario.response.UsuarioResponse;
import com.oxeschool.api.entity.UsuarioEntity;
import com.oxeschool.api.exceptions.customs.usuario.UsuarioNaoEncontradoException;
import com.oxeschool.api.mappers.UsuarioMapper;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuariosServiceTest {

    @Mock
    private UsuariosRepository usuariosRepository;
    @Mock
    private UsuarioMapper usuarioMapper;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuariosService usuariosService;

    @Test
    void editarNomeUsuario_comUsuarioExistente_deveAtualizarNomeSalvarEMapear() {
        var request = new EditarNomeUsuarioRequest("Novo Nome");

        var entidade = UsuarioEntity.builder()
                .id(1L).nome("Nome Antigo").email("ana@email.com").senha("hashSenha").build();
        var entidadeSalva = UsuarioEntity.builder()
                .id(1L).nome("Novo Nome").email("ana@email.com").senha("hashSenha").build();
        var domain = new UsuarioDomain(1L, "Novo Nome", "ana@email.com", "hashSenha");
        var response = new UsuarioResponse(1L, "Novo Nome", "ana@email.com");

        when(usuariosRepository.findById(1L)).thenReturn(Optional.of(entidade));
        when(usuariosRepository.save(any(UsuarioEntity.class))).thenReturn(entidadeSalva);
        when(usuarioMapper.toUsuarioDomain(any(UsuarioEntity.class))).thenReturn(domain);
        when(usuarioMapper.toUsuarioResponse(domain)).thenReturn(response);

        var resultado = usuariosService.editarNomeUsuario(request, 1L);

        assertEquals("Novo Nome", resultado.getNome());
        assertEquals("ana@email.com", resultado.getEmail());
        verify(usuariosRepository).save(any(UsuarioEntity.class));
    }

    @Test
    void editarNomeUsuario_comUsuarioInexistente_deveLancarUsuarioNaoEncontradoException() {
        var request = new EditarNomeUsuarioRequest("Novo Nome");

        when(usuariosRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UsuarioNaoEncontradoException.class,
                () -> usuariosService.editarNomeUsuario(request, 99L));

        verify(usuariosRepository, never()).save(any());
    }

    @Test
    void editarEmailUsuario_comUsuarioExistente_deveAtualizarEmailSalvarEMapear() {
        var request = new EditarEmailUsuarioRequest("novo@email.com");

        var entidade = UsuarioEntity.builder()
                .id(1L).nome("Ana").email("ana@email.com").senha("hashSenha").build();
        var entidadeSalva = UsuarioEntity.builder()
                .id(1L).nome("Ana").email("novo@email.com").senha("hashSenha").build();
        var domain = new UsuarioDomain(1L, "Ana", "novo@email.com", "hashSenha");
        var response = new UsuarioResponse(1L, "Ana", "novo@email.com");

        when(usuariosRepository.findById(1L)).thenReturn(Optional.of(entidade));
        when(usuariosRepository.save(any(UsuarioEntity.class))).thenReturn(entidadeSalva);
        when(usuarioMapper.toUsuarioDomain(any(UsuarioEntity.class))).thenReturn(domain);
        when(usuarioMapper.toUsuarioResponse(domain)).thenReturn(response);

        var resultado = usuariosService.editarEmailUsuario(request, 1L);

        assertEquals("novo@email.com", resultado.getEmail());
        verify(usuariosRepository).save(any(UsuarioEntity.class));
    }

    @Test
    void editarEmailUsuario_comUsuarioInexistente_deveLancarUsuarioNaoEncontradoException() {
        var request = new EditarEmailUsuarioRequest("novo@email.com");

        when(usuariosRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UsuarioNaoEncontradoException.class,
                () -> usuariosService.editarEmailUsuario(request, 99L));

        verify(usuariosRepository, never()).save(any());
    }

    @Test
    void editarSenhaUsuario_comSenhaAtualCorreta_deveCriptografarSalvarEMapear() {
        var request = new EditarSenhaUsuarioRequest("novaSenha123", "senhaAtual123");

        var entidade = UsuarioEntity.builder()
                .id(1L).nome("Ana").email("ana@email.com").senha("hashSenha").build();
        var entidadeSalva = UsuarioEntity.builder()
                .id(1L).nome("Ana").email("ana@email.com").senha("novoHash").build();
        var domain = new UsuarioDomain(1L, "Ana", "ana@email.com", "novoHash");
        var response = new UsuarioResponse(1L, "Ana", "ana@email.com");

        when(usuariosRepository.findById(1L)).thenReturn(Optional.of(entidade));
        when(passwordEncoder.matches("senhaAtual123", "hashSenha")).thenReturn(true);
        when(passwordEncoder.encode("novaSenha123")).thenReturn("novoHash");
        when(usuariosRepository.save(any(UsuarioEntity.class))).thenReturn(entidadeSalva);
        when(usuarioMapper.toUsuarioDomain(any(UsuarioEntity.class))).thenReturn(domain);
        when(usuarioMapper.toUsuarioResponse(domain)).thenReturn(response);

        var resultado = usuariosService.editarSenhaUsuario(request, 1L);

        assertEquals("ana@email.com", resultado.getEmail());
        verify(passwordEncoder).encode("novaSenha123");
        verify(usuariosRepository).save(any(UsuarioEntity.class));
    }

    @Test
    void editarSenhaUsuario_comSenhaAtualIncorreta_deveLancarUsuarioNaoEncontradoException() {
        var request = new EditarSenhaUsuarioRequest("novaSenha123", "senhaErrada");

        var entidade = UsuarioEntity.builder()
                .id(1L).nome("Ana").email("ana@email.com").senha("hashSenha").build();

        when(usuariosRepository.findById(1L)).thenReturn(Optional.of(entidade));
        when(passwordEncoder.matches("senhaErrada", "hashSenha")).thenReturn(false);

        assertThrows(UsuarioNaoEncontradoException.class,
                () -> usuariosService.editarSenhaUsuario(request, 1L));

        verify(passwordEncoder, never()).encode(anyString());
        verify(usuariosRepository, never()).save(any());
    }

    @Test
    void editarSenhaUsuario_comUsuarioInexistente_deveLancarUsuarioNaoEncontradoException() {
        var request = new EditarSenhaUsuarioRequest("novaSenha123", "senhaAtual123");

        when(usuariosRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UsuarioNaoEncontradoException.class,
                () -> usuariosService.editarSenhaUsuario(request, 99L));

        verify(usuariosRepository, never()).save(any());
    }
}