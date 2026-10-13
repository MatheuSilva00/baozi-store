package baozi_store.controller;

import baozi_store.model.Cliente;
import baozi_store.repository.ClienteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @PostMapping
    public Cliente cadastrar(@RequestBody Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @PutMapping("/{id}")
public ResponseEntity<Cliente> atualizar(
        @PathVariable Long id,
        @RequestBody Cliente dados) {

    Cliente existente = clienteRepository.findById(id).orElse(null);

    if (existente == null) {
        return ResponseEntity.notFound().build();
    }

    existente.setNome(dados.getNome());
    existente.setClienteDesde(dados.getClienteDesde());

    return ResponseEntity.ok(clienteRepository.save(existente));
}

    @GetMapping
    public List<Cliente> listar() {
        return clienteRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> consultar(@PathVariable Long id) {
        return clienteRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!clienteRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        clienteRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
