package sio.tp3;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.util.Callback;
import sio.tp3.Model.Tache;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class TP3Controller implements Initializable {
    private HashMap<String, HashMap<String, ArrayList<Tache>>> mesTaches;
    TreeItem racine;
    TreeItem noeudSelectionne;
    String finalNomTache;
    String finalNomDeveloppeur;
    @FXML
    private ListView lstThemes;
    @FXML
    private ListView lstProjets;
    @FXML
    private TreeView tvTaches;
    @FXML
    private ComboBox cboDeveloppeurs;
    @FXML
    private Button cmdValider;
    @FXML
    private TextField txtNomTache;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mesTaches = new HashMap<>();
        racine = new TreeItem("Mes tâches");
        racine.setExpanded(true);
        tvTaches.setRoot(racine);

        cboDeveloppeurs.getItems().addAll("Enzo","Noa","Lilou","Milo");
        cboDeveloppeurs.getSelectionModel().selectFirst();

        lstThemes.getItems().addAll("Mobile","Web","Réseau");

        for(int i = 1 ; i<=10 ; i++)
        {
            lstProjets.getItems().add("Projet n°" + i);
        }
    }

    @FXML
    public void cmdValiderClicked(Event event) {
        if(lstThemes.getSelectionModel().isEmpty())
        {
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("erreur de selection");
            alert.setHeaderText("");
            alert.setContentText("selectionnez un theme");
            alert.showAndWait();
        }
        if(lstProjets.getSelectionModel().isEmpty())
        {
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("erreur de selection");
            alert.setHeaderText("");
            alert.setContentText("selectionnez un project");
            alert.showAndWait();
        }
        if (txtNomTache.getText().equals(""))
        {
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("erreur de selection");
            alert.setHeaderText("");
            alert.setContentText("saisir une tache");
            alert.showAndWait();
        }
        else
        {
            String themeSelect =lstThemes.getSelectionModel().getSelectedItem().toString();
            String numProjetSelect = lstProjets.getSelectionModel().getSelectedItem().toString();


            if(!mesTaches.containsKey(themeSelect))
            {

                HashMap<String,ArrayList<Tache>> lesProjets  = new HashMap<>();
                mesTaches.put(themeSelect,lesProjets);
            }
             if (!mesTaches.get(themeSelect).containsKey(numProjetSelect))
            {
                ArrayList<Tache> lesTaches = new ArrayList<>();
                mesTaches.get(themeSelect).put(numProjetSelect,lesTaches);
            }
             
            mesTaches.get(themeSelect).get(numProjetSelect).add(new Tache(txtNomTache.getText(),cboDeveloppeurs.getSelectionModel().getSelectedItem().toString(),false));

          RemplirTreeViewDesTaches();


        }

    }

    public void RemplirTreeViewDesTaches()
    {
        TreeItem theme = null;
        TreeItem numeroProjet = null;
        TreeItem mission = null;



        //permet d'obtenir l'ensemble de toute les cle present dans la hasMap
        for(String thm :mesTaches.keySet())
        {
            theme = new TreeItem<>(thm);
            theme.setExpanded(true);

            for (String num:mesTaches.get(thm).keySet())
            {
                numeroProjet = new TreeItem<>(num);
                numeroProjet.setExpanded(true);
                theme.getChildren().add(numeroProjet);

                for (Tache tch:mesTaches.get(thm).get(num))
                {
                    mission = new TreeItem(tch.getNomDeveloppeur() +":"+tch.getNomTache());
                    numeroProjet.getChildren().add(mission);
                }
            }
            racine.getChildren().add(theme);
        }
        racine.setExpanded(true);
        tvTaches.setRoot(racine);




    }

    @FXML
    public void tvTachesClicked(Event event)  {

    }
}