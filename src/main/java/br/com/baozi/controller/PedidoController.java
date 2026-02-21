package br.com.baozi.controller;

import br.com.baozi.dto.PedidoRequest;
import br.com.baozi.model.Cliente;
import br.com.baozi.model.Pedido;
import br.com.baozi.model.Produto;
import br.com.baozi.repository.ClienteRepository;
import br.com.baozi.repository.PedidoRepository;
import br.com.baozi.repository.ProdutoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoController(PedidoRepository pedidoRepository,
                            ClienteRepository clienteRepository,
                            ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody PedidoRequest request) {
        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElse(null);
        Produto produto = produtoRepository.findById(request.getProdutoId())
                .orElse(null);

        if (cliente == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Cliente não encontrado com id: " + request.getClienteId());
        }
        if (produto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Produto não encontrado com id: " + request.getProdutoId());
        }

        Pedido pedido = new Pedido(cliente, produto, request.getQuantidade());
        Pedido pedidoSalvo = pedidoRepository.save(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoSalvo);
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> listarTodos() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {
        return pedidoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody PedidoRequest request) {
        return pedidoRepository.findById(id)
                .map(pedido -> {
                    Cliente cliente = clienteRepository.findById(request.getClienteId()).orElse(null);
                    Produto produto = produtoRepository.findById(request.getProdutoId()).orElse(null);
                    if (cliente == null) {
                        return ResponseEntity.<Object>status(HttpStatus.BAD_REQUEST)
                                .body("Cliente não encontrado com id: " + request.getClienteId());
                    }
                    if (produto == null) {
                        return ResponseEntity.<Object>status(HttpStatus.BAD_REQUEST)
                                .body("Produto não encontrado com id: " + request.getProdutoId());
                    }
                    pedido.setCliente(cliente);
                    pedido.setProduto(produto);
                    pedido.setQuantidade(request.getQuantidade());
                    return ResponseEntity.ok(pedidoRepository.save(pedido));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagar(@PathVariable Long id) {
        if (!pedidoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        pedidoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
