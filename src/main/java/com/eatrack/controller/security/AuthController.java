package com.eatrack.controller.security;

import com.eatrack.helper.DataHelper;
import com.eatrack.helper.UserHelper;
import com.eatrack.model.Cliente;
import com.eatrack.model.Role;
import com.eatrack.model.Usuario;
import com.eatrack.model.records.DadosAuth;
import com.eatrack.model.records.Infos;
import com.eatrack.model.records.UserData;
import com.eatrack.model.records.UserRecord;
import com.eatrack.model.type.Status;
import com.eatrack.repository.ClienteRepository;
import com.eatrack.repository.RoleRepository;
import com.eatrack.repository.UsuarioRepository;
import com.eatrack.security.UserPrincipal;
import com.eatrack.security.jwt.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private DataHelper dataHelper;

    @Autowired
    private UserHelper userHelper;

    @Autowired
    private ClienteRepository clienteRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid DadosAuth dados) {

        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.password());

        var authentication = manager.authenticate(authenticationToken);

        if (!userHelper.userAtivo(authentication)){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if (authentication.isAuthenticated()) {
            var tokenJWT = tokenService.gerarToken(authentication);

            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

            Optional<Usuario> usuario = usuarioRepository.findByLogin(userPrincipal.getUsername());

            UserData user = new UserData(
                    tokenJWT,
                    usuario.get().getId(),
                    usuario.get().getLogin()
            );

            return new ResponseEntity<>(user, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PostMapping("/cadastrar-user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity adicionarUser(@RequestBody UserRecord user) {

        Optional<Role> userRole = roleRepository.findByName(user.roleName());
        List<Role> roles = new ArrayList<>();
        roles.add(userRole.get());

        Usuario usuario = new Usuario();

        usuario.setRoles(roles);
        usuario.setLogin(user.login());
        usuario.setPassword(encoder.encode(user.password()));
        usuario.setStatus(Status.ATIVO);
        usuario.setDataIni(dataHelper.getDataHora());

        usuarioRepository.save(usuario);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/cadastrar-role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> cadastrarRole(@RequestBody Role role) {

        Optional<Role> userRole = roleRepository.findByName(role.getName());

        if (userRole.isPresent()){
            throw new RuntimeException("Role já exixtente!");
        }

        Role retorno = roleRepository.save(role);

        return new ResponseEntity<>(retorno, HttpStatus.OK);
    }

    @PostMapping("infos")
    public ResponseEntity<?> infos(HttpRequest request){
        String id = request.getHeaders().getFirst("id");

        Optional<Usuario> usuario = usuarioRepository.findById(Long.valueOf(id));

        if (usuario.isPresent()){
            switch (usuario.get().getFuncao()){
                case CLIENTE -> {

                    Cliente cliente = clienteRepository.findByUsuario(usuario.get().getId());

                    return new ResponseEntity<>(new Infos(usuario.get().getId(), cliente.getNome(), cliente.getEmail()), HttpStatus.OK);
                }
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
