package org.eadge;

/**
 * Created by ronan-j on 16/02/17.
 */
public interface ConstantsView
{
    String MAIN_WINDOW_TITLE = "Mon éditeur de script";
    String PROPERTIES_DIALOG_TITLE = "Propriétés";

    String NAME_LAUNCH_SCRIPT = "Lancement";
    String DESC_LAUNCH_SCRIPT = "Lancement du script";

    String NAME_VALIDATE_SCRIPT = "Valider";
    String DESC_VALIDATE_SCRIPT = "Validation du script";

    String NAME_LOAD_FUNCTION = "Charger fonction";
    String DESC_LOAD_FUNCTION = "Charge une fonction pour être ajoutée à notre script";

    String NAME_ADD_ELEMENT = "Ajouter";
    String DESC_ADD_ELEMENT = "Ajout d'un élément au script";

    String NAME_DEL_ELEMENT = "Supprimer";
    String DESC_DEL_ELEMENT = "Supprimer les éléments sélectionnés";

    String NAME_COPY = "Copie";
    String DESC_COPY = "Copie des éléments sélectionnés";

    String NAME_PASTE = "Coller";
    String DESC_PASTE = "Coller les éléments copiés";

    String NAME_CUT = "Cut";
    String DESC_CUT = "Coupe les éléments sélectionnés";

    String NAME_UNDO = "Revenir en arrière";
    String DESC_UNDO = "Annule l'action passée";

    String NAME_REDO = "Annuler revenir en arrière";
    String DESC_REDO = "Annule l'effet d'une annulation de l'action passée";

    String NAME_ADD_LAYER = "Ajouter";
    String DESC_ADD_LAYER = "Ajout d'une nouvelle couche";

    String NAME_REMOVE_LAYER = "Supprimer";
    String DESC_REMOVE_LAYER = "Suppression des couches selectionnées";

    String NAME_HIDE_LAYER = "Cacher";
    String DESC_HIDE_LAYER = "Affichage de la couche";

    String NAME_PROPERTY_LAYER = "Propriétés";
    String DESC_PROPERTY_LAYER = "Propriétés de la couche";

    String MENU_FILE              = "Fichier";
    String NAME_NEW_FILE          = "Nouveau script";
    String DESC_NEW_FILE          = "Création d'un nouveau script";
    String NAME_SAVE_FILE         = "Enregistrer";
    String DESC_SAVE_FILE    = "Sauvegarde du script";
    String NAME_SAVE_AS_FILE = "Enregistrer sous";

    String MENU_EDIT = "Edit";

    String MENU_SCRIPT = "Script";

    String TAB_CONSOLE = "Console";
    String TAB_TESTS = "Tests";

    String TEXT_COLOR = "Couleurs: ";

    String NAME_OK = "Ok";
    String NAME_CANCEL = "Annuler";


    int PREFERRED_DRAW_SIZE_WIDTH = 600;
    int PREFERRED_DRAW_SIZE_HEIGHT = 480;
}
