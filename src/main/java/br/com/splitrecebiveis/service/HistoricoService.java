package br.com.splitrecebiveis.service;
 
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
 
import br.com.splitrecebiveis.domain.RegistroHistorico;
import br.com.splitrecebiveis.domain.Split;
import br.com.splitrecebiveis.domain.TipoOperacao;
import br.com.splitrecebiveis.infra.HistoricoRepositoryMemoria;
 
public class HistoricoService {
 
    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
 
    private final HistoricoRepositoryMemoria repository;
 
    // Mesmo relógio do SplitService, para a data e a hora do registro.
    private final Clock clock;
 
    public HistoricoService(HistoricoRepositoryMemoria repository, Clock clock) {
        this.repository = repository;
        this.clock = clock;
    }
 
    // Registra uma operação feita em um Split. O detalhe é opcional.
    public void registrar(TipoOperacao operacao, Split split, String detalhe) {
        String descricao = split.getBeneficiario().getNome()
                + " | " + formatarMoeda(split.getValor())
                + " | repasse em " + split.getDataRepasse().format(FORMATO_DATA);
 
        if (detalhe != null && !detalhe.isBlank()) {
            descricao = descricao + " | " + detalhe;
        }
 
        repository.salvar(new RegistroHistorico(LocalDateTime.now(clock), operacao, split.getId(), descricao));
    }
 
    // Lista as operações na ordem em que aconteceram.
    public List<RegistroHistorico> listarTodos() {
        return repository.listarTodos();
    }
 
    private static String formatarMoeda(BigDecimal valor) {
        return String.format(Locale.forLanguageTag("pt-BR"), "R$ %,.2f", valor);
    }
}