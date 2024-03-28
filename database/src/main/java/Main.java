import bll.FuncionarioBLL;
import bll.ReciboBLL;
import bll.SocioBLL;
import database.Database;
import entity.Funcionario;
import entity.Recibo;
import entity.Socio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.glassfish.jaxb.core.v2.TODO;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // Test the connection to the database
        EntityManager entityManager = null;
        try {
            entityManager = Database.getEntityManager();
            System.out.println("Connection successful! Created EntityManager");

            Query query = Database.customQuery("SELECT f FROM Funcionario f where f.idTipofuncionario = 1");
            List<Funcionario> resultList = query.getResultList();

            if (!resultList.isEmpty()) {
                System.out.println("Sample query executed successfully");
                for (Funcionario funcionario : resultList) {
                    System.out.println("ID: " + funcionario.getIdFuncionario() + " Nome: " + funcionario.getNome());
                    // Print other attributes as needed
                }
            } else {
                System.out.println("No results found for the query.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error connecting to database!");
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }

        // Create a new Funcionario
        /*try{
            Funcionario funcionario = new Funcionario();
            funcionario.setNome("João");
            funcionario.setContacto("912891881");
            funcionario.setRua("Rua do João");
            funcionario.setnPorta("12");
            funcionario.setCodPostal("4990");
            funcionario.setIdTipofuncionario(1);
            funcionario.setVencimento(BigInteger.valueOf(1000));
            FuncionarioBLL.createFuncionario(funcionario);
        }
        catch (Exception e){
            e.printStackTrace();
            System.err.println("Error connecting to database!");
        }*/

        // Delete a Funcionario
        /*Funcionario funcionario1 = new Funcionario();
        funcionario1.setNome("Rogério");
        funcionario1.setContacto("123456789");
        funcionario1.setRua("Rua SSS");
        funcionario1.setnPorta("11");
        funcionario1.setVencimento(BigInteger.valueOf(3000));
        funcionario1.setCodPostal("4990");
        funcionario1.setIdTipofuncionario(1);

        try {
            FuncionarioBLL.createFuncionario(funcionario1);
            System.out.println("Funcionario criado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error connecting to database!");
        }

        try{
            FuncionarioBLL.deleteFuncionario(funcionario1);
            System.out.println("Funcionario eliminado com sucesso!");
        }
        catch (Exception e){
            e.printStackTrace();
            System.err.println("Error connecting to database!");
        }
        Funcionario funcionario1 = new Funcionario();
        funcionario1 = FuncionarioBLL.getFuncionarioById(5);
        FuncionarioBLL.deleteFuncionario(funcionario1);*/

        // Read all Funcionarios
        /*FuncionarioBLL.readFuncionario();*/

        // Update a Funcionario
        /*Funcionario funcionario1 = new Funcionario();
        funcionario1 = FuncionarioBLL.getFuncionarioById(1);
        funcionario1.setNome("Rogério");
        funcionario1.setContacto("123123123");
        funcionario1.setRua("Rua SSS");
        FuncionarioBLL.updateFuncionario(funcionario1);*/

        //Create a new Socio
        //TODO: Mudar o tipo do contacto para String
        /*try{
            Socio socio = new Socio();
            socio.setNome("João");
            socio.setContacto(912891881);
            socio.setRua("Rua do João");
            socio.setnPorta("12");
            socio.setCodPostal("4990");
            socio.setIdPlano(1);
            SocioBLL.createSocio(socio);
        }
        catch (Exception e){
            e.printStackTrace();
            System.err.println("Error connecting to database!");
        }*/

        //Create a new Recibo
        //TODO: Ver o tipo de data que é suposto ser inserido (Pus a possibilidade de ser NULL para testar)
        /*Recibo recibo = new Recibo();
        recibo.setIdSocio(1);
        recibo.setIdTipopagamento(1);
        recibo.setIdEstadopagamento(1);
        recibo.setIdPlano(1);
        recibo.setIdFuncionario(1);
        recibo.setValor(BigInteger.valueOf(100));
        //recibo.setMes("Janeiro");
        //recibo.setDataHoraEmissao("2021-01-01");
        ReciboBLL.createRecibo(recibo);*/

    }
}

