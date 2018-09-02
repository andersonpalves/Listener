package main;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.stream.Stream;

import utils.ProcessaOperacoes;
import utils.Validacoes;

public class RunApplication {
	
	public static String DADOS_VENDEDOR = "001";
	public static String DADOS_CLIENTE = "002";
	public static String DADOS_VENDAS = "003";

	@SuppressWarnings({ "unchecked"})
	public static void main(String[] args) throws IOException {
		
		Path pastaOrigem = Files.createDirectories(Paths.get("data/in"));
		Path pastaProcessando = Files.createDirectories(pastaOrigem.getParent().resolve("processing"));
		Path pastaProcessados = Files.createDirectories(pastaOrigem.getParent().resolve("out"));	
		Path pastaErroDeFormato = Files.createDirectories(pastaOrigem.getParent().resolve("error_format"));

		WatchService watcher = FileSystems.getDefault().newWatchService();

		pastaOrigem.register(watcher, ENTRY_CREATE);

		while (true) {

		    WatchKey wk = null;
		    try {
		        //aguarda algum evento ocorrer
		        System.out.printf("Aguardando arquivos em %s", pastaOrigem);
		        wk = watcher.take();
		    } catch (InterruptedException e) {
		        throw new RuntimeException(e);
		    }

		    //percorre eventos recebidos
		    for (WatchEvent<?> event : wk.pollEvents()) {

		        WatchEvent<Path> ev = (WatchEvent<Path>) event;
		        Path nomeArquivo = ev.context();
		        
		        Path arquivoOrigem = pastaOrigem.resolve(nomeArquivo);
		        System.out.printf("Arquivo criado: %s\n", arquivoOrigem);
		        
		        boolean validaFormato = Validacoes.validaExtensaoArquivo(nomeArquivo.toString());
		        
		        if(!validaFormato){
		            System.out.println("Arquivo no formato inválido");
		            
		            Path arquivoProcessando = pastaErroDeFormato.resolve(nomeArquivo);
			        System.out.printf("Movendo para: %s\n", arquivoProcessando);
			        Files.move(arquivoOrigem, arquivoProcessando);
		            
		            continue;
		        }
		        
		        String quebraLinha = System.getProperty("line.separator");
		        
		        FileOutputStream fosVendedor = new FileOutputStream("data/out/flat_dados_vendedor.done.dat");
		        DataOutputStream outStreamVendedor = new DataOutputStream(new BufferedOutputStream(fosVendedor));
		        outStreamVendedor.writeBytes(ProcessaOperacoes.geraCabecalhoVendedor());
		        outStreamVendedor.writeBytes(quebraLinha);
		        
		        FileOutputStream fosCliente = new FileOutputStream("data/out/flat_dados_cliente.done.dat");
		        DataOutputStream outStreamCliente = new DataOutputStream(new BufferedOutputStream(fosCliente));
		        outStreamCliente.writeBytes(ProcessaOperacoes.geraCabecalhoCliente());
		        outStreamCliente.writeBytes(quebraLinha);
		        
		        FileOutputStream fosVenda = new FileOutputStream("data/out/flat_dados_venda.done.dat");
		        DataOutputStream outStreamVenda = new DataOutputStream(new BufferedOutputStream(fosVenda));
		        outStreamVenda.writeBytes(ProcessaOperacoes.geraCabecalhoVenda());
		        outStreamVenda.writeBytes(quebraLinha);
		        
		        try (Stream<String> stream = Files.lines(arquivoOrigem)) {
					stream.forEach(
							linha -> {
								ProcessaOperacoes.processaDados(outStreamVendedor, outStreamCliente, outStreamVenda, linha);
							});
				} catch (IOException e) {
					e.printStackTrace();
				}

		        ProcessaOperacoes.acrescentaQuantidadeClientes(outStreamCliente);
		        ProcessaOperacoes.acrescentaQuantidadeVendedores(outStreamVendedor);
		        ProcessaOperacoes.maiorVendaEPiorVendedor(outStreamVenda);
		        
		        outStreamVendedor.close();
		        outStreamCliente.close();
		        outStreamVenda.close();

		        Path arquivoProcessando = pastaProcessando.resolve(nomeArquivo);
		        System.out.printf("Movendo para: %s\n", arquivoProcessando);
		        Files.move(arquivoOrigem, arquivoProcessando);

		        System.out.printf("Processando arquivo %s\n", arquivoProcessando);

		        Path arquivoProcessado = pastaProcessados.resolve(nomeArquivo);
		        System.out.printf("Movendo para: %s\n", arquivoProcessado);
		        Files.move(arquivoProcessando, arquivoProcessado);

		    }

		    if (!wk.reset()) {
		        break;
		    }
		}
	}
}