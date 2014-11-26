package cs445_project;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class RecipeStaXParser implements java.io.Serializable{
  /**
	 * 
	 */
	private static final long serialVersionUID = 7768947784872851913L;
  static final String RECIPE = "recipe";
  static final String NAME = "name";
  static final String INGREDIENT = "ingredient";
  static final String AMOUNT = "amount";
  static final String MEASURING_UNIT = "measuring_unit";
  static final String TYPE = "type";
  static final String INSTRUCTIONS = "instructions";
  static final String UNITS_MADE = "units_made";

  public Recipe readRecipe(String recipeFile) {
	Recipe recipe = null;
    ArrayList<Ingredient> ingres = new ArrayList<Ingredient>();
    try {
      // First, create a new XMLInputFactory
      XMLInputFactory inputFactory = XMLInputFactory.newInstance();
      // Setup a new eventReader
      InputStream in = new FileInputStream(recipeFile);
      XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
      // read the XML document
      
      Ingredient ingredient = null;
      boolean flag = false;
      while (eventReader.hasNext()) {
        XMLEvent event = eventReader.nextEvent();
        
        if (event.isStartElement()) {
          StartElement startElement = event.asStartElement();
          // If we have a recipe element, we create a new recipe
          if (startElement.getName().getLocalPart() == (RECIPE)) {
            recipe = new Recipe();
            continue;
          }
          else if (startElement.getName().getLocalPart() == (INGREDIENT)) {
              ingredient = new Ingredient();
              flag = true;
              continue;
          }

          if (event.isStartElement()) {
            if (event.asStartElement().getName().getLocalPart()
                .equals(NAME) && !flag) {
              event = eventReader.nextEvent();
              recipe.setRName(event.asCharacters().getData());
              continue;
            }
          }
          if (event.asStartElement().getName().getLocalPart()
                  .equals(NAME) && flag) {
                event = eventReader.nextEvent();
                ingredient.setIName(event.asCharacters().getData());
                continue;
          }
          
          if (event.asStartElement().getName().getLocalPart()
                  .equals(AMOUNT)) {
                event = eventReader.nextEvent();
                ingredient.setAmount(Double.parseDouble(event.asCharacters().getData()));
                continue;
          }
          
          if (event.asStartElement().getName().getLocalPart()
                  .equals(MEASURING_UNIT)) {
                event = eventReader.nextEvent();
                ingredient.setMeasuring_unit(event.asCharacters().getData());
                continue;
          }
          
          if (event.asStartElement().getName().getLocalPart()
                  .equals(TYPE)) {
                event = eventReader.nextEvent();
                if(event.isCharacters()){
                    Characters cdata = event.asCharacters();
                    if(cdata.isWhiteSpace()) continue;
                    ingredient.setType(event.asCharacters().getData());
                }
                continue;
          }
           
          if (event.asStartElement().getName().getLocalPart()
              .equals(INSTRUCTIONS)) {
            event = eventReader.nextEvent();
            recipe.setInstructions(event.asCharacters().getData());
            continue;
          }

          if (event.asStartElement().getName().getLocalPart()
              .equals(UNITS_MADE)) {
            event = eventReader.nextEvent();
            recipe.setUnits_made(Integer.parseInt(event.asCharacters().getData()));
            continue;
          }
                    
        }
        // If we reach the end of an item element, we add it to the list
        if (event.isEndElement()) {
          EndElement endElement = event.asEndElement();
          if (endElement.getName().getLocalPart() == (RECIPE)) {
        	  recipe.setIngres(ingres);
          }
          else if (endElement.getName().getLocalPart() == (INGREDIENT)) {
              ingres.add(ingredient);
              flag = false;
          }
        }

      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (XMLStreamException e) {
      e.printStackTrace();
    }
    return recipe;
  }

}
