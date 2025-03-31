package com.supermercado.sistema.util;

import com.opencsv.CSVWriter;
import com.supermercado.sistema.model.Produto;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvExporter {

    public static void exportarProdutos(List<Produto> produtos, String caminhoArquivo) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(caminhoArquivo))) {
            String[] cabecalho = {"ID", "Nome", "Categoria", "Quantidade", "Preço Compra", "Preço Venda"};
            writer.writeNext(cabecalho);

            for (Produto p : produtos) {
                String[] dados = {
                    p.getId().toString(),
                    p.getNome(),
                    p.getCategoria(),
                    p.getQuantidade().toString(),
                    String.format("%.2f", p.getPrecoCompra()),
                    String.format("%.2f", p.getPrecoVenda())
                };
                writer.writeNext(dados);
            }

            System.out.println("✅ Relatório gerado com sucesso em: " + caminhoArquivo);

        } catch (IOException e) {
            System.out.println("❌ Erro ao gerar CSV: " + e.getMessage());
        }
    }
}
