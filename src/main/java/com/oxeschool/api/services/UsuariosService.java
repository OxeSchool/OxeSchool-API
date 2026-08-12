package com.oxeschool.api.services;

import com.oxeschool.api.dtos.usuario.request.EditarEmailUsuarioRequest;
import com.oxeschool.api.dtos.usuario.request.EditarNomeUsuarioRequest;
import com.oxeschool.api.dtos.usuario.request.EditarSenhaUsuarioRequest;
import com.oxeschool.api.dtos.usuario.response.UsuarioResponse;
import com.oxeschool.api.exceptions.customs.usuario.UsuarioNaoEncontradoException;
import com.oxeschool.api.mappers.UsuarioMapper;
import com.oxeschool.api.repository.UsuariosRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuariosService {

    final private UsuariosRepository usuariosRepository;;
    final private UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    public UsuariosService(UsuariosRepository usuariosRepository,
                           UsuarioMapper usuarioMapper, PasswordEncoder passwordEncoder) {
        this.usuariosRepository = usuariosRepository;
        this.usuarioMapper = usuarioMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioResponse editarNomeUsuario(EditarNomeUsuarioRequest editarNomeUsuarioRequest, Long id){

        var usuario = usuariosRepository.findById(id)
                .orElseThrow(UsuarioNaoEncontradoException::new);

        usuario.setNome(editarNomeUsuarioRequest.getNovoNome());

        var usuarioSalvo =usuariosRepository.save(usuario);

        return usuarioMapper.toUsuarioResponse(usuarioMapper.toUsuarioDomain(usuarioSalvo));
    }

    public UsuarioResponse editarEmailUsuario(EditarEmailUsuarioRequest editarEmailUsuarioRequest, Long id){

        var usuario = usuariosRepository.findById(id)
                .orElseThrow(UsuarioNaoEncontradoException::new);

        usuario.setEmail(editarEmailUsuarioRequest.getNovoEmail());

        var usuarioSalvo = usuariosRepository.save(usuario);

        return usuarioMapper.toUsuarioResponse(usuarioMapper.toUsuarioDomain(usuarioSalvo));
    }

    public UsuarioResponse editarSenhaUsuario(EditarSenhaUsuarioRequest editarSenhaUsuarioRequest, Long id){

        var usuario = usuariosRepository.findById(id)
                .orElseThrow(UsuarioNaoEncontradoException::new);

        var senhaBate = passwordEncoder.matches(editarSenhaUsuarioRequest.getSenhaAtual(), usuario.getSenha());

        if (!senhaBate){
            throw new UsuarioNaoEncontradoException();
        }

        var senhaCriptografada = passwordEncoder.encode(editarSenhaUsuarioRequest.getNovaSenha());

        usuario.setSenha(senhaCriptografada);

        var usuarioSalvo = usuariosRepository.save(usuario);

        return usuarioMapper.toUsuarioResponse(usuarioMapper.toUsuarioDomain(usuarioSalvo));
    }

}
