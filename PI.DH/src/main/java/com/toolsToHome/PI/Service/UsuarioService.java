package com.toolsToHome.PI.Service;



import com.toolsToHome.PI.DTO.UserDTO;
import com.toolsToHome.PI.Exceptions.ResourceNotFoundException;
import com.toolsToHome.PI.Model.Herramienta;
import com.toolsToHome.PI.Model.Usuario;
import com.toolsToHome.PI.Model.UsuarioRole;
import com.toolsToHome.PI.Repository.HerramientaRepository;
import com.toolsToHome.PI.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final HerramientaRepository herramientaRepository;
    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, HerramientaRepository herramientaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.herramientaRepository = herramientaRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> buscarUsuario = usuarioRepository.findByEmail(username);
        if(buscarUsuario.isPresent()){
            return buscarUsuario.get();
        }else throw new UsernameNotFoundException("No se encontro el usuario");
    }

    public Optional<UserDTO> buscarPorId(Long id){
        Optional<Usuario>buscarUsuario = usuarioRepository.findById(id);
        if(buscarUsuario.isPresent()){
            return Optional.of(UserDTO.fromUser(buscarUsuario.get()));
        }else return Optional.empty();
    }
    public void eliminarUsuario(Long id){
        usuarioRepository.deleteById(id);
    }
    public void actualizarUsuario(Usuario usuario){
        usuarioRepository.save(usuario);
    }
    public List<Usuario> listarTodos(){
        return usuarioRepository.findAll();
    }

    public void updateRole(Long id, Usuario usuarioRole) throws ResourceNotFoundException {
        Optional<UserDTO> usuarioRequest = buscarPorId(id);
        if(usuarioRequest.isPresent()){
            Usuario user = usuarioRepository.findByEmail(usuarioRequest.get().getUsername())
                    .orElseThrow(() -> new ResourceNotFoundException("No se encontró al usuario con el id: " + id));
            user.setUsuarioRole(usuarioRole.getUsuarioRole());
            usuarioRepository.save(user);
        } else {
            throw new ResourceNotFoundException("No se encontró al usuario con el id: " + id);
        }
    }

    public Optional<UserDTO> findUserByEmail(String email) {
        Optional<Usuario> userOptional = usuarioRepository.findByEmail(email);
        return userOptional.map(UserDTO::fromUser);
    }

    /* FAVS */
    public boolean isUsuarioCorreo(String email, Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.isPresent() && usuario.get().getUsername().equals(email);
    }

    public void agregarHerramientaFavorita(Long usuarioId, Long herramientaId) throws ResourceNotFoundException {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró al usuario con el ID proporcionado"));

        Herramienta herramienta = herramientaRepository.findById(herramientaId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la herramienta con el ID proporcionado"));

        if (!usuario.getHerramientasFavoritas().contains(herramienta)) {
            usuario.getHerramientasFavoritas().add(herramienta);
            usuarioRepository.save(usuario);
        }
    }

    public void eliminarHerramientaFavorita(Long usuarioId, Long herramientaId) throws ResourceNotFoundException {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró al usuario con el ID proporcionado"));

        Herramienta herramienta = herramientaRepository.findById(herramientaId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la herramienta con el ID proporcionado"));

        usuario.getHerramientasFavoritas().removeIf(h -> h.getId().equals(herramientaId));
        usuarioRepository.save(usuario);
    }

    public List<Herramienta> getHerramientasFavoritas(Long usuarioId) throws ResourceNotFoundException {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró al usuario con el ID proporcionado"));
        return usuario.getHerramientasFavoritas();
    }

}