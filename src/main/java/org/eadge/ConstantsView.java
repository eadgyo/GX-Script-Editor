package org.eadge;

/**
 * Created by eadgyo on 16/02/17.
 */
public interface ConstantsView
{
    String MAIN_WINDOW_TITLE = "GX-Script-Editor";
    String PROPERTIES_DIALOG_TITLE = "Property";

    String NAME_RUN_SCRIPT = "Launch script";
    String DESC_RUN_SCRIPT = "Launch the script";

    String NAME_VALIDATE_SCRIPT = "Validate script";
    String DESC_VALIDATE_SCRIPT = "Validating script";

    String NAME_LOAD_FUNCTION = "Load function ";
    String DESC_LOAD_FUNCTION = "Load function to be added in the script";

    String NAME_ADD_ELEMENT = "Add element";
    String DESC_ADD_ELEMENT = "Add element to the script";

    String NAME_DEL_ELEMENT = "Delete element";
    String DESC_DEL_ELEMENT = "Delete selected elements";

    String NAME_COPY = "Copy";
    String DESC_COPY = "Copy selected elements";

    String NAME_PASTE = "Paste";
    String DESC_PASTE = "Paste copied elements";

    String NAME_CUT = "Cut";
    String DESC_CUT = "Cut selected elements";

    String NAME_UNDO = "Undo";
    String DESC_UNDO = "Undo last action";

    String NAME_REDO = "Redo";
    String DESC_REDO = "Redo last undo action";

    String NAME_ADD_LAYER = "Add layer";
    String DESC_ADD_LAYER = "Add a new layer";

    String NAME_REMOVE_NODE = "Delete";
    String DESC_REMOVE_NODE = "Delete selected elements";

    String NAME_HIDE_LAYER = "Hide layer";
    String DESC_HIDE_LAYER = "Hide/Display selected layer";

    String NAME_PROPERTY_LAYER = "Property";
    String DESC_PROPERTY_LAYER = "Layer Property";

    String MENU_FILE              = "File";
    String NAME_NEW_FILE          = "New script";
    String DESC_NEW_FILE      = "Create new script";
    String NAME_SAVE_FILE     = "Save";
    String DESC_SAVE_FILE     = "Save script";
    String NAME_SAVE_AS_FILE  = "Save as";
    String DESC_SAVE_AS_FILE  = "Save the script in a specified directory";
    String NAME_OPEN_FILE     = "Open";
    String DESC_OPEN_FILE     = "Open an existing script";
    String NAME_EXPORT        = "Export";
    String DESC_EXPORT        = "Export script in compiled format";
    String NAME_IMPORT_SCRIPT = "Import script";
    String DESC_IMPORT_SCRIPT    = "Importation of the compiled script";
    String NAME_IMPORT_ELEMENTS  = "Import entity";
    String DESC_IMPORT_ELEMENTS  = "Importation of GX entities";

    String MENU_EDIT = "Edit";

    String MENU_SCRIPT = "Script";

    String TAB_CONSOLE = "Console";
    String TAB_TESTS = "Tests";

    String TEXT_COLOR = "Couleurs: ";

    String NAME_OK = "Ok";
    String NAME_CANCEL = "Cancel";


    String MESSAGE_CONFIRM_NEW_FILE = "The actual script will be deleted, are you sure?";
    String NAME_DIALOG_OPTION = "Input Option";

    int PREFERRED_DRAW_SIZE_WIDTH = 600;
    int PREFERRED_DRAW_SIZE_HEIGHT = 480;

    String MESSAGE_CANT_EXPORT = "The selected script is not valid";
    String MESSAGE_INVALID_FILE = "The selected file is not readable";

    String INPUT_ASK_GROUP_NAME = "Group name to insert script in:";

    String VALIDATE_ENTITIES_PRESENCE = "Entities";
    String VALIDATE_INPUT = "Valid entities";
    String VALIDATE_LINKS = "Valid Connection";
    String VALIDATE_INTERDEPENDANCE = "Inter-Dependency";
    String VALIDATE_IMBRICATIONS = "Valid structure";
    String VALIDATE_PARAMETERS = "Valid Parameters";

    String VALIDATE_CODE = "Valid Code";

    String COMPILATION_START_MESSAGE = "Starting Compilation...";
    String COMPILATION_SUCCESS       = "Compilation success";
    String COMPILATION_ECHEC         = "Compilation failed: ";
    String ERREUR                    = " errors";
    String INVALIDATE_SCRIPT         = "Correct the script";
    String INPUTS_SCRIPT_PRESENT     = "The script is a function";
    String INPUT_ASK_NAME = "Name of the script";
}
