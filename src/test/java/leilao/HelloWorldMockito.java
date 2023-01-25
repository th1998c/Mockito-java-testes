
package leilao;

import br.com.alura.leilao.dao.LeilaoDao;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class HelloWorldMockito {

    @Test
    void hello(){
        var mock = Mockito.mock(LeilaoDao.class);
        var leiloes = mock.buscarTodos();
        Assert.assertTrue(leiloes.isEmpty());
    }

}
