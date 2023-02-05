package br.com.alura.leilao.service;

import br.com.alura.leilao.dao.LeilaoDao;
import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

class FinalizarLeilaoServiceTest {
    private FinalizarLeilaoService service;
    @Mock
    private LeilaoDao leilaoDao;

    @BeforeEach
    public void beforeEach(){
        MockitoAnnotations.initMocks(this);
        service = new FinalizarLeilaoService(leilaoDao);
    }

    @Test
    void deveriaFinalizarUmLeilao() {
        var leiloes = leiloes();

        Mockito.when(leilaoDao.buscarLeiloesExpirados()).thenReturn(leiloes);

        service.finalizarLeiloesExpirados();
        var leilao = leiloes.get(0);
        assertTrue(leilao.isFechado());
        assertEquals(new BigDecimal(900), leilao.getLanceVencedor().getValor());

        Mockito.verify(leilaoDao).salvar(leilao);
    }

    private List<Leilao> leiloes () {
        List<Leilao> leiloes = new ArrayList<>();

        var leilao = new Leilao("Celular", new BigDecimal(500), new Usuario("Fulano"));
        var primeiroLance = new Lance(new Usuario("Beltrano"), new BigDecimal(600));
        var segundoLance = new Lance(new Usuario("Ciclano"), new BigDecimal(900));

        leilao.propoe(primeiroLance);
        leilao.propoe(segundoLance);
        leiloes.add(leilao);
        return leiloes;
    }
}