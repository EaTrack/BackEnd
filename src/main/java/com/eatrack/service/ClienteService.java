package com.eatrack.service;

import com.eatrack.helper.DataHelper;
import com.eatrack.helper.FormatadorHelper;
import com.eatrack.model.Cliente;
import com.eatrack.model.Role;
import com.eatrack.model.Usuario;
import com.eatrack.model.records.ClienteRecord;
import com.eatrack.model.records.UserRecord;
import com.eatrack.model.type.Status;
import com.eatrack.repository.ClienteRepository;
import com.eatrack.repository.RoleRepository;
import com.eatrack.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private DataHelper dataHelper;

    @Autowired
    private FormatadorHelper formatadorHelper;

    public ResponseEntity<?> adicionar(ClienteRecord clienteRecord){

        Usuario user = criarUser(clienteRecord.userRecord());

        Cliente cliente = criarCliente(clienteRecord, user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Usuario criarUser(UserRecord userRecord){

        Optional<Role> userRole = roleRepository.findByName("CLIENTE");
        List<Role> roles = new ArrayList<>();
        roles.add(userRole.get());

        Usuario usuario = new Usuario();

        usuario.setRoles(roles);
        usuario.setLogin(userRecord.login());
        usuario.setPassword(encoder.encode(userRecord.password()));
        usuario.setStatus(Status.ATIVO);
        usuario.setDataIni(dataHelper.getDataHora());

        Usuario retorno = usuarioRepository.save(usuario);

        return retorno;
    }

    private Cliente criarCliente(ClienteRecord clienteRecord, Usuario user){

        Cliente objCliente = clienteRepository.findByUsuario(user.getId());

        if (objCliente != null){
            this.throwStatusException(HttpStatus.UNAUTHORIZED, "Não permitido");
        }

        Cliente cliente = new Cliente();

        cliente.setNome(clienteRecord.nome());
        cliente.setDocumento(clienteRecord.documento() != null ? formatadorHelper.formatarDocumentoBanco(clienteRecord.documento()) : null);
        cliente.setTipoDocumento(clienteRecord.tipoDocumento() != null ? clienteRecord.tipoDocumento() : null);
        cliente.setCelular(clienteRecord.celular() != null ? formatadorHelper.formatarCelularBanco(clienteRecord.celular()) : null);
        cliente.setEmail(clienteRecord.email());
        cliente.setTipoPessoa(clienteRecord.tipoPessoa());
        cliente.setUsuario(user.getId());
        cliente.setDataIni(dataHelper.getDataHora());
        cliente.setStatus(Status.ATIVO);

        Cliente retorno = clienteRepository.save(cliente);

        return retorno;
    }

    private void throwStatusException(HttpStatus status, String message){

        ResponseStatusException exception = new ResponseStatusException(status, message);
        exception.setStackTrace(new StackTraceElement[]{});
        throw exception;
    }
}
