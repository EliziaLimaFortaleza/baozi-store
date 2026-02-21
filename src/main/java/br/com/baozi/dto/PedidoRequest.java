package br.com.baozi.dto;

/**
 * DTO para criação de pedido.
 * Aceita IDs do cliente e produto em vez dos objetos completos.
 */
public class PedidoRequest {

    private Long clienteId;
    private Long produtoId;
    private Integer quantidade;

    public PedidoRequest() {
    }

    public PedidoRequest(Long clienteId, Long produtoId, Integer quantidade) {
        this.clienteId = clienteId;
        this.produtoId = produtoId;
        this.quantidade = quantidade;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
