package pos_java_jdbc.pos_java_jdbc;

import java.util.List;

import org.junit.Test;

import dao.UserPosDAO;
import model.BeanUserFone;
import model.Telefone;
import model.Userposjava;

public class TesteBancoJdbc{
	
	@Test
		public void initBanco() {
		UserPosDAO userPosDAO = new UserPosDAO();
		Userposjava userposjava = new Userposjava();
		
		
		userposjava.setNome("tom");
		userposjava.setEmail("gomes10@hotmmail.com");
		
		
		userPosDAO.salvar(userposjava);
		
	}
	
	@Test
	public void initListar() {
		UserPosDAO dao = new UserPosDAO();
		
		try {
			List<Userposjava> list = dao.listar();
			
			for (Userposjava userposjava : list) {
				System.out.println(userposjava.getNome());
				System.out.println("--------------------------------------------------------------------------");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void initBuscar() {
		UserPosDAO dao = new UserPosDAO();
		try {
			Userposjava userposjava = dao.buscar(5L);
			System.out.println(userposjava);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void innitAtualizar() {
		try {
			UserPosDAO dao = new UserPosDAO();

			Userposjava objetoBanco = dao.buscar(6L);

			objetoBanco.setNome("serejo");

			dao.atualizar(objetoBanco);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void initDeletar() {
		try {
			UserPosDAO dao = new UserPosDAO();
			dao.deletar(6L);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testeInsertTelefone() {
		Telefone telefone = new Telefone();
		telefone.setNumero("(87) 1234-5679");
		telefone.setTipo("Casa");
		telefone.setUsuario(1L);
		
		UserPosDAO dao = new UserPosDAO();
		dao.salvarTelefone(telefone);
	}
	
	@Test
	public void testeCarregaFonesUser()  {
		UserPosDAO dao = new UserPosDAO();
		List<BeanUserFone> beanUserFones = dao.listaUserFone(10L);
		
		for (BeanUserFone beanUserFone : beanUserFones) {
			System.out.println(beanUserFone);
			System.out.println("-----------------------------------------------------------------------------");
		}
	}
	
	@Test
	public void testeDeleteUserFone() {
		UserPosDAO dao = new UserPosDAO();
		dao.deleteFonesPorUser(9L);
	}
}
