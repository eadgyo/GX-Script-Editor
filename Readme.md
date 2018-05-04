# GX-Script-Editor
![GX-Script](https://raw.githubusercontent.com/eadgyo/GX-Script-Editor/master/img/example.PNG)  
This editor is made to create [GX-Script](https://github.com/eadgyo/GX-Script).

## Default view
![GX-Script](https://raw.githubusercontent.com/eadgyo/GX-Script-Editor/master/img/frame.PNG)
(1) Select the entity you want to add to your script  
(2) Display the tree structure view  
(3) Panel where script will be created  
(4) Console output  
(5) Entities compilation order  
(6) Tests results  

## Creating GX-Script
![box](https://raw.githubusercontent.com/eadgyo/GX-Script-Editor/master/img/box.PNG)

Select entities and add them to the script panel (3). 
Use green and blue rectangle to link them. You can also define default input by clicking on the input green rectangle ![GX-Script](https://raw.githubusercontent.com/eadgyo/GX-Script-Editor/master/img/greenbox.PNG). The value to the right is the default value that will be used if no input is set.

### Example
In this section we will create a simple multiplication script:  
![Multiplication failed](https://raw.githubusercontent.com/eadgyo/GX-Script-Editor/master/img/multiply_failed.PNG)
As you can see, when validating or launching the script, tests failed. We are missing mandatory input entities.

By adding two entities as input, and clicking on green rectangles, we can define input numbers (5 and 2.0 here). When launching the script, tests are all passed and we have the expected result: 10.0.
![Multiplication failed](https://raw.githubusercontent.com/eadgyo/GX-Script-Editor/master/img/multiply_valid.PNG)



## Launching
Click edit button and Launch your script. Output will be displayed in the Console output.  
You can also validate your script before launching, validation results will be displayed in (6) panel.
![Console output](https://raw.githubusercontent.com/eadgyo/GX-Script-Editor/master/img/egx_example_cons.PNG)  
![Passing test](https://raw.githubusercontent.com/eadgyo/GX-Script-Editor/master/img/validation_failed.PNG)

## Layers
![Layers](https://raw.githubusercontent.com/eadgyo/GX-Script-Editor/master/img/LayerPanel.PNG)  
The (2) panel displays the tree hierarchy, to group GX-Entities by layers. You can add layers inside layers. To change their color, you can click on Property.  
This is for example the result on our script panel (3):  
![Layers](https://raw.githubusercontent.com/eadgyo/GX-Script-Editor/master/img/layerDemo.PNG)
By selecting a layer, you can move every entities inside the layer.  

## Importing script inside other script
You can create your own GX-Entities in java, described [here]() . Or create GX-Script from GX-Script-Editor.
This last option will be described here:

### Creating Entities
We will create a GX-Entity adding one from the input. Add Parameter input and output to define GX-Entity inputs and outputs.  
![Adding one](https://raw.githubusercontent.com/eadgyo/GX-Script-Editor/master/img/function.PNG)  
Export your script by clicking on ```File/Export entity```. Choose the name, and group of your entity.  

Create a new script ```File/New script```  
Import your script entity ```File/Import script```  
The entity will be added to his group (here the group is operation).  
![Adding one](https://raw.githubusercontent.com/eadgyo/GX-Script-Editor/master/img/addingOneGroup.PNG)  

Add other entity to test the entity, and launch the full script.  
![Adding one](https://raw.githubusercontent.com/eadgyo/GX-Script-Editor/master/img/addingOne.PNG)  
The result from output is the one expected, as the input is set to 0.  
