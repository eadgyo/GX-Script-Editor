package org.eadge;

/**
 * Created by eadgyo on 16/02/17.
 */
public interface ConstantsView
{
    String MAIN_WINDOW_TITLE = "test.java";
    String PROPERTIES_DIALOG_TITLE = "Propriétés";

    String NAME_RUN_SCRIPT = "Lancer script";
    String DESC_RUN_SCRIPT = "Lancement du script";

    String NAME_VALIDATE_SCRIPT = "Valider script";
    String DESC_VALIDATE_SCRIPT = "Validation du script";

    String NAME_LOAD_FUNCTION = "Charger fonction";
    String DESC_LOAD_FUNCTION = "Charge une fonction pour être ajoutée à notre script";

    String NAME_ADD_ELEMENT = "Ajouter élement";
    String DESC_ADD_ELEMENT = "Ajout d'un élément au script";

    String NAME_DEL_ELEMENT = "Supprimer élément";
    String DESC_DEL_ELEMENT = "Supprimer les éléments sélectionnés";

    String NAME_COPY = "Copier";
    String DESC_COPY = "Copie des éléments sélectionnés";

    String NAME_PASTE = "Coller";
    String DESC_PASTE = "Coller les éléments copiés";

    String NAME_CUT = "Couper";
    String DESC_CUT = "Coupe les éléments sélectionnés";

    String NAME_UNDO = "Revenir en arrière";
    String DESC_UNDO = "Annule l'action passée";

    String NAME_REDO = "Annuler revenir en arrière";
    String DESC_REDO = "Annule l'effet d'une annulation de l'action passée";

    String NAME_ADD_LAYER = "Ajouter couche";
    String DESC_ADD_LAYER = "Ajout d'une nouvelle couche";

    String NAME_REMOVE_NODE = "Supprimer";
    String DESC_REMOVE_NODE = "Suppression des elements selectionnés";

    String NAME_HIDE_LAYER = "Cacher";
    String DESC_HIDE_LAYER = "Affichage de la couche";

    String NAME_PROPERTY_LAYER = "Propriétés";
    String DESC_PROPERTY_LAYER = "Propriétés de la couche";

    String MENU_FILE              = "Fichier";
    String NAME_NEW_FILE          = "Nouveau script";
    String DESC_NEW_FILE      = "Création d'un nouveau script";
    String NAME_SAVE_FILE     = "Enregistrer";
    String DESC_SAVE_FILE     = "Sauvegarde du script";
    String NAME_SAVE_AS_FILE  = "Enregistrer sous";
    String DESC_SAVE_AS_FILE  = "Sauvegarde du script dans un autre repertoire";
    String NAME_OPEN_FILE     = "Ouvrir";
    String DESC_OPEN_FILE     = "Ouvrir un script existant";
    String NAME_EXPORT        = "Exporter";
    String DESC_EXPORT        = "Exporter le script en format compilé";
    String NAME_IMPORT_SCRIPT = "Importer Script";
    String DESC_IMPORT_SCRIPT    = "Importation d'un script compilé";
    String NAME_IMPORT_ELEMENTS  = "Importer éléments";
    String DESC_IMPORT_ELEMENTS  = "Importation d'un ensemble d'éléments";

    String MENU_EDIT = "Edit";

    String MENU_SCRIPT = "Script";

    String TAB_CONSOLE = "Console";
    String TAB_TESTS = "Tests";

    String TEXT_COLOR = "Couleurs: ";

    String NAME_OK = "Ok";
    String NAME_CANCEL = "Annuler";


    String MESSAGE_CONFIRM_NEW_FILE = "Le script existant sera détruit, êtes vous sûr";
    String NAME_DIALOG_OPTION = "Input Option";

    int PREFERRED_DRAW_SIZE_WIDTH = 600;
    int PREFERRED_DRAW_SIZE_HEIGHT = 480;

    String MESSAGE_CANT_EXPORT = "Le script n'est pas valide";
    String MESSAGE_INVALID_FILE = "Le fichier n'est pas lisible";

    String INPUT_ASK_GROUP_NAME = "Nom du groupe pour y insérer le script:";

    String VALIDATE_ENTITIES_PRESENCE = "Entitées présentes";
    String VALIDATE_INPUT = "Entrées valides";
    String VALIDATE_LINKS = "Connections valides";
    String VALIDATE_INTERDEPENDANCE = "Pas d'interderpendance";
    String VALIDATE_IMBRICATIONS = "Structure valide";
    String VALIDATE_PARAMETERS = "Parametres valides";

    String VALIDATE_CODE = "Code valide";

    String COMPILATION_START_MESSAGE = "Début Compilation...";
    String COMPILATION_SUCCESS       = "Compilation réussie";
    String COMPILATION_ECHEC         = "Compilation échouée: ";
    String ERREUR                    = " erreurs";
    String INVALIDATE_SCRIPT         = "Corriger le script";
    String INPUTS_SCRIPT_PRESENT     = "Le script est une fonction";
    String INPUT_ASK_NAME = "Nom du script";
}
