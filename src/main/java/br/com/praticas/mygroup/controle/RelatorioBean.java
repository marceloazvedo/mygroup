/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.praticas.mygroup.controle;

import br.com.praticas.mygroup.modelo.entidades.Usuario;
import br.com.praticas.mygroup.relatorios.UsuarioREL;
import br.com.praticas.mygroup.util.Facade;
import br.com.praticas.mygroup.util.MensagensUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class RelatorioBean extends Beans {
    
    private String adminLogin;
    private String adminSenha;
    private Facade facade;
    
    @PostConstruct
    public void init() {
        facade = new Facade();
    }
    
    public String getAdminLogin() {
        return adminLogin;
    }
    
    public void setAdminLogin(String adminLogin) {
        this.adminLogin = adminLogin;
    }
    
    public String getAdminSenha() {
        return adminSenha;
    }
    
    public void setAdminSenha(String adminSenha) {
        this.adminSenha = adminSenha;
    }
    
    public void gerarRelatorio() {
        try {
            UsuarioREL relatorio = new UsuarioREL();
            List<Usuario> usuarios = facade.listarUsuarios();
            
            
            if (adminLogin.equals("admin") && adminSenha.equals("admin")) {
                relatorio.imprimir(usuarios);
                showDialog("sucess");
            } else {
                showDialog("error");
            }
        } catch (Exception ex) {
            showMessageDialog("Erro", MensagensUtil.getValor(MensagensUtil.MSG_ERRO));
        }
    }
}
