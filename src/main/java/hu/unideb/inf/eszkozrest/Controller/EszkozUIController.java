package hu.unideb.inf.eszkozrest.Controller;

import hu.unideb.inf.eszkozrest.Dto.EszkozDto;

import hu.unideb.inf.eszkozrest.Service.EszkozService;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.List;

@Controller("eszkozUIController")
@RequestScoped
@Getter
@Setter
public class EszkozUIController {

        private static EszkozUIController instance = null;

        public EszkozUIController() {
                instance = this;
        }

        public static EszkozUIController getInstance() {
            return instance;
        }

        private String marka = "";
        private String tipus;
        private String leiras;
        private String jelleg;
        private String error;
        private boolean hasError = false;
        EszkozDto eszkozEntity;
        private long activeTool;

        private List<EszkozDto> products;

        @Autowired
        private EszkozService eszkozService;

        public void deleteTool(int id) {
                eszkozService.deleteById(id);
                refreshProductsList();

                try {
                        FacesContext.getCurrentInstance().getExternalContext().redirect("/eszkoz-lista.jsf");
                } catch (IOException e) {
                        throw new RuntimeException(e);
                }
        }

        public void editTool(int id) {
                eszkozEntity = eszkozService.findById(id);
                marka = eszkozEntity.getMarka();
                tipus = eszkozEntity.getTipus();
                leiras = eszkozEntity.getLeiras();
                jelleg = eszkozEntity.getJelleg();

                try {
                        FacesContext.getCurrentInstance().getExternalContext().redirect("/eszkoz-edit.jsf");
                } catch (IOException e) {
                        throw new RuntimeException(e);
                }
        }

        public void saveChanges() {
                eszkozEntity.setMarka(marka);
                eszkozEntity.setTipus(tipus);
                eszkozEntity.setLeiras(leiras);
                eszkozEntity.setJelleg(jelleg);

                eszkozService.save(eszkozEntity);
                refreshProductsList();

                try {
                        FacesContext.getCurrentInstance().getExternalContext().redirect("/eszkoz-lista.jsf");
                } catch (IOException e) {
                        throw new RuntimeException(e);
                }
        }

        public void save() {
                eszkozService.save(new EszkozDto(marka, tipus, leiras, jelleg, null));
                refreshProductsList();
                try {
                        FacesContext.getCurrentInstance().getExternalContext().redirect("/eszkoz-lista.jsf");
                } catch (IOException e) {
                        throw new RuntimeException(e);
                }
        }

        @PostConstruct
        public void initController() {
            refreshProductsList();
        }

        public void refreshProductsList() {
            products = eszkozService.listAll();
        }
}