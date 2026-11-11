package br.com.splitrecebiveis.domain;
 
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
 
/**
* Uma linha do histórico: quando aconteceu, qual operação, em qual Split e os detalhes.
*/
public record RegistroHistorico(LocalDateTime dataHora, TipoOperacao operacao, long splitId, String descricao) {
 
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
 
    @Override
    public String toString() {
        return dataHora.format(FORMATO) + " | " + operacao + " | Split " + splitId + " | " + descricao;
    }
}