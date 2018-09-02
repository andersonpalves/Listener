package utils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import main.RunApplication;
import modelos.Cliente;
import modelos.Item;
import modelos.Venda;
import modelos.VendaVendendorDTO;
import modelos.Vendedor;

public abstract class ProcessaOperacoes {

	private static String CARATER_SEPARADOR = "ç";
	private static String quebraLinha = System.getProperty("line.separator");
	private static List<VendaVendendorDTO> listaTodosValoresEVendedores = new ArrayList<>();
	private static int totalClientes = 0;
	private static int totalVendedores = 0;
	
	public static void processaDados(DataOutputStream outStreamVendedor, DataOutputStream outStreamCliente, DataOutputStream outStreamVenda, String linha) {
		
		if (linha.startsWith(RunApplication.DADOS_VENDEDOR)) {
			Vendedor vendedor = operacaoDadosVendedor(linha);
			
			try {
				outStreamVendedor.writeBytes(vendedor.toString());
				outStreamVendedor.writeBytes(quebraLinha);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (linha.startsWith(RunApplication.DADOS_CLIENTE)) {
			Cliente cliente = operacaoDadosCliente(linha);
			
			try {
				outStreamCliente.writeBytes(cliente.toString());
				outStreamCliente.writeBytes(quebraLinha);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (linha.startsWith(RunApplication.DADOS_VENDAS)) {
			Venda venda = operacaoDadosVendas(linha);
			
			try {
				outStreamVenda.writeBytes(venda.toString());
				outStreamVenda.writeBytes(quebraLinha);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			try {
				throw new Exception("Erro ao reconhecer valor do arquivo");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Vendedor operacaoDadosVendedor(String linha) {
		
		String[] linhaFormatada = linha.split(CARATER_SEPARADOR);
		
		Vendedor vendedor = new Vendedor(Integer.valueOf(linhaFormatada[0]), linhaFormatada[1], linhaFormatada[2], new BigDecimal(linhaFormatada[3]));
		
		totalVendedores++;
		
		return vendedor;
	}
	
	public static Cliente operacaoDadosCliente(String linha) {
		
		String[] linhaFormatada = linha.split(CARATER_SEPARADOR);
		
		Cliente cliente = new Cliente(Integer.valueOf(linhaFormatada[0]), linhaFormatada[1], linhaFormatada[2], linhaFormatada[3]);
		
		totalClientes++;
		
		return cliente;
	}
	
	public static Venda operacaoDadosVendas(String linha) {
		
		String[] linhaFormatada = linha.split(CARATER_SEPARADOR);
		String totalItems = linhaFormatada[2];
		String totalItemString = totalItems.substring(1, totalItems.length()-1);
		String[] stringListaItems = totalItemString.split(",");
		
		List<Item> listaItems = new ArrayList<>();
		
		for (int i = 0; i < stringListaItems.length; i++) {
			String stringItem = stringListaItems[i];
			String[] item = stringItem.split("-");
			
			listaItems.add(new Item(Integer.valueOf(item[0]), Integer.valueOf(item[1]), new BigDecimal(item[2])));
		}
		
		Venda venda = new Venda(Integer.valueOf(linhaFormatada[0]), listaItems, linhaFormatada[3]);
		listaTodosValoresEVendedores.add(new VendaVendendorDTO(venda.getValorTotal(), venda.getNomeVendedor()));
		
		return venda;
	}
	
	public static void acrescentaQuantidadeClientes(DataOutputStream outStreamCliente) throws IOException {
		outStreamCliente.writeBytes(quebraLinha);
		outStreamCliente.writeBytes("Quantidade de clientes: " + totalClientes);
	}
	
	public static void acrescentaQuantidadeVendedores(DataOutputStream outStreamVendedor) throws IOException {
		outStreamVendedor.writeBytes(quebraLinha);
		outStreamVendedor.writeBytes("Quantidade de vendedores: " + totalVendedores);
	}
	
	public static void maiorVendaEPiorVendedor(DataOutputStream outStreamVenda) throws IOException {
		outStreamVenda.writeBytes(quebraLinha);
		outStreamVenda.writeBytes("ID da venda mais cara: " + getMaiorVenda());
		outStreamVenda.writeBytes(quebraLinha);
		outStreamVenda.writeBytes("Pior Vendedor: " + getPiorVendedor());
		outStreamVenda.writeBytes(quebraLinha);
	}

	private static String getMaiorVenda() {
		
		BigDecimal maiorValor = new BigDecimal(0);
		
		for (VendaVendendorDTO venda : listaTodosValoresEVendedores) {	
			if (venda.getValor().signum() > maiorValor.signum()) {
				maiorValor = venda.getValor();
			}
		}
		
		return String.valueOf(maiorValor);
	}
	
	private static String getPiorVendedor() {
		
		BigDecimal maiorValor = new BigDecimal(100000000);
		String nomeVendedor = null;
		
		for (VendaVendendorDTO venda : listaTodosValoresEVendedores) {
			if (venda.getValor().signum() <= maiorValor.signum()) {
				maiorValor = venda.getValor();
				nomeVendedor = venda.getNomeVendedor();
			}
		}
		
		return nomeVendedor;
	}

	public static String geraCabecalhoVendedor() {
		return "ID - NOME - CPF - SALARIO";
	}
	
	public static String geraCabecalhoCliente() {
		return "ID - NOME - CNPJ - AREA";
	}
	
	public static String geraCabecalhoVenda() {
		return "ID - VALOR TOTAL - NOME DO VENDEDOR";
	}

}
