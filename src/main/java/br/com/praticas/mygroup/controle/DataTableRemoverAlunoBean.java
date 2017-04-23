package br.com.praticas.mygroup.controle;

import br.com.praticas.mygroup.modelo.entidades.Sala;
import br.com.praticas.mygroup.modelo.entidades.Usuario;
import br.com.praticas.mygroup.util.Facade;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "dtRemoverAluno")
@ViewScoped
public class DataTableRemoverAlunoBean {
    
    private Usuario usuarioSelecionado;
    private List<Usuario> usuariosSala;
    private Usuario usuario;
    private Facade facade;
    private Sala sala;
    
    @PostConstruct
    public void init(){
        facade = new Facade();
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Long idSala = (Long) session.getAttribute("idSala");
        if(idSala!=null){
            try {
                sala = facade.buscarSala(idSala);
                usuariosSala = sala.getUsuarios();
                for(Usuario u : usuariosSala){
                    System.out.println("Nome: "+u.getNome());
                }
            } catch (Exception ex) {
                Logger.getLogger(DataTableRemoverAlunoBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Usuario> getUsuariosSala() {
        return usuariosSala;
    }

    public void setUsuariosSala(List<Usuario> usuariosSala) {
        this.usuariosSala = usuariosSala;
    }

    public Usuario getUsuarioSelecionado() {
        return usuarioSelecionado;
    }

    public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
        this.usuarioSelecionado = usuarioSelecionado;
    }
    
    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }
    
    public void removerAluno(){
        try {
            Usuario u = facade.buscarUsuario(usuarioSelecionado.getId());
            u.getSalasParticipando().remove(sala);
            facade.editarUsuario(u);
            
            Sala s = facade.buscarSala(sala.getId());
            s.getUsuarios().remove(u);
            facade.editarSala(s);
        } catch (Exception ex) {
            Logger.getLogger(DataTableRemoverAlunoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void salaSession(Long id){
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.setAttribute("idSala", id);
    }
}
