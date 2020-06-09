package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import conexaojdbc.SingleConnection;
import model.BeanUserFone;
import model.Telefone;
import model.Userposjava;

public class UserPosDAO {

	private Connection connection;
	
	 public UserPosDAO() {
		 connection = SingleConnection.getConnection();
	}
	 
	 public void salvar(Userposjava userposjava) {
		 try {
		 String sql = "insert into userposjava (nome,email) values (?,?)";
		 PreparedStatement insert = connection.prepareStatement(sql);
		 insert.setString(1, userposjava.getNome());
		 insert.setString(2, userposjava.getEmail());
		 insert.execute();
		 connection.commit();//Salva no banco
		 
		 }catch (Exception e) {
			 try {
				connection.rollback();// Reverte operação
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	 }
	 
	 
	 public void salvarTelefone(Telefone telefone) {
		 try {
			String sql = "insert into telefoneuser (numero,tipo,usuariopessoa) values (?,?,?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, telefone.getNumero());
			statement.setString(2, telefone.getTipo());
			statement.setLong(3, telefone.getUsuario());
			statement.execute();
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	 }
	
	 public Userposjava buscar(Long id) throws Exception{
		 Userposjava retorno = new Userposjava();
		 
		 String sql = "select*from userposjava where id = " + id;
		 PreparedStatement statement = connection.prepareStatement(sql);
		 ResultSet resultado = statement.executeQuery();
		 
		 while(resultado.next()) {//Retorna apenas um ou nenhum
			
			 retorno.setId(resultado.getLong("id"));//Capturando os dados e jogando no objeto
			 retorno.setNome(resultado.getString("nome"));
			 retorno.setEmail(resultado.getString("email"));
			
		 }
		 
		 return retorno;
	 }
	 
	 public List<BeanUserFone> listaUserFone(Long id){
		 
		 List<BeanUserFone> beanUserFones = new  ArrayList<BeanUserFone>();
		 
		 String sql = " select nome,numero,email from telefoneuser as fone";
		 sql += " inner join userposjava as userp";
		 sql += " on fone.usuariopessoa = userp.id";
		 sql += " where userp.id  =" +id;// + idUser
		 
		 try {
			 
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				BeanUserFone userFone = new BeanUserFone();
				userFone.setNome(resultSet.getString("nome"));
				userFone.setEmail(resultSet.getString("email"));
				userFone.setNumero(resultSet.getString("numero"));
				beanUserFones.add(userFone);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		 return beanUserFones;
	 }
	 
	 
	 public void atualizar(Userposjava userposjava) {
		 
		 try {
		 String sql = "update userposjava set nome = ? where id = " + userposjava.getId();
		 PreparedStatement statement = connection.prepareStatement(sql);
		 statement.setString(1,userposjava.getNome());
		 
		 statement.execute();
		 connection.commit();
		 }catch (Exception e) {
			 try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			 e.printStackTrace();
		}
		 
	 }
	 
	 public void deletar(Long id) {
		 try {
		
			String sql = "delete from userposjava where id = " + id;//SQL para delete
			PreparedStatement preparedStatement = connection.prepareStatement(sql);//Compilando
			preparedStatement.execute();//Executando o commit gravando no banco de dados
			connection.commit();
			
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	 }
	 
	 
	 public void deleteFonesPorUser(Long id) {
		 
		 String sqlFone="delete from telefoneuser where usuariopessoa = " + id;
		 String sqlUser="delete from userposjava where id =  " + id;
		 
		 try {
			 
			PreparedStatement preparedStatement = connection.prepareStatement(sqlFone);
			preparedStatement.executeUpdate();
			connection.commit();
			
			preparedStatement = connection.prepareStatement(sqlUser);
			preparedStatement.executeUpdate();
			connection.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	 }
	 
	 public List<Userposjava> listar() throws Exception{
		 List<Userposjava> list = new ArrayList<Userposjava>();
		 
		 String sql = "select*from userposjava";
		 PreparedStatement statement = connection.prepareStatement(sql);
		 ResultSet resultado = statement.executeQuery();
		 
		 while(resultado.next()) {
			 Userposjava userposjava = new Userposjava();
			 userposjava.setId(resultado.getLong("id"));
			 userposjava.setNome(resultado.getString("nome"));
			 userposjava.setEmail(resultado.getString("email"));
			 
			 
			 list.add(userposjava);
		 }
		 
		 return list;
	 }
}
