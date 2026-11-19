package br.com.splitrecebiveis.service;
 
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
 
import java.util.List;
 
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
 
import br.com.splitrecebiveis.domain.Beneficiario;
import br.com.splitrecebiveis.domain.TipoPessoa;
import br.com.splitrecebiveis.infra.BeneficiarioRepositoryMemoria;
 
class BeneficiarioListarAprovadosTest {
 
    @Test
    @DisplayName("UT-42: listarAprovados retorna apenas beneficiários aprovados")
    void deveListarApenasBeneficiariosAprovados() {
        BeneficiarioService service = new BeneficiarioService(new BeneficiarioRepositoryMemoria());
        Beneficiario maria = service.cadastrar("Maria Silva", TipoPessoa.PF, "12345678901", "001", "1234", "56789-0");
        service.cadastrar("Empresa XYZ", TipoPessoa.PJ, "12345678000199", "237", "4321", "98765-4");
        service.aprovar(maria.getId());
 
        List<Beneficiario> aprovados = service.listarAprovados();
 
        assertEquals(1, aprovados.size());
        assertSame(maria, aprovados.get(0));
    }
}