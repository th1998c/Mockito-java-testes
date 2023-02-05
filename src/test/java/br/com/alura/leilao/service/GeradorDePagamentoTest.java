package br.com.alura.leilao.service;

import br.com.alura.leilao.dao.PagamentoDao;
import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Pagamento;
import br.com.alura.leilao.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.*;

class GeradorDePagamentoTest {
    private GeradorDePagamento geradorDePagamento;
    @Mock
    private PagamentoDao pagamentoDao;

    @Captor
    private ArgumentCaptor<Pagamento> pagamento;

    @BeforeEach
    public void beforeEach(){
        MockitoAnnotations.initMocks(this);
        geradorDePagamento = new GeradorDePagamento(pagamentoDao);
    }

    @Test
    void deveriaCriarPagamentoParaVencedorDoLeilão(){
        var leilao = leilao();

        var lanceVencedor = leilao.getLanceVencedor();

        geradorDePagamento.gerarPagamento(lanceVencedor);

        Mockito.verify(pagamentoDao).salvar(pagamento.capture());

        var pagamento1 = pagamento.getValue();
        assertEquals(pagamento1.getVencimento(), LocalDate.now().plusDays(1));
        assertEquals(pagamento1.getPago(), false);
        assertEquals(pagamento1.getValor(), leilao.getLanceVencedor().getValor());
        assertEquals(pagamento1.getLeilao(), leilao);
        assertEquals(pagamento1.getUsuario(), lanceVencedor.getUsuario());
    }

    private Leilao leilao() {
        var leilao = new Leilao("Celular", new BigDecimal(500), new Usuario("Fulano"));
        var lance = new Lance(new Usuario("Ciclano"), new BigDecimal(900));
        leilao.propoe(lance);
        leilao.setLanceVencedor(lance);
        return leilao;
    }
}