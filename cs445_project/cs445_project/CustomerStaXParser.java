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

public class CustomerStaXParser implements java.io.Serializable {
  /**
	 * 
	 */
	private static final long serialVersionUID = 166030980738761521L;
  static final String SUBSCRIBER = "subscriber";
  static final String NAME = "name";
  static final String EMAIL = "email";
  static final String PHONE = "phone";
  static final String STREET = "street";
  static final String CITY = "city";
  static final String STATE = "state";
  static final String ZIP = "zip";
  static final String FACEBOOK = "facebook";
  static final String TWITTER = "twitter";

  public Subscriber readCustomer(String customerFile) {
      Subscriber subscriber = null;
      Address address = null;
    try {
      // First, create a new XMLInputFactory
      XMLInputFactory inputFactory = XMLInputFactory.newInstance();
      // Setup a new eventReader
      InputStream in = new FileInputStream(customerFile);
      XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
      // read the XML document
      while (eventReader.hasNext()) {
        XMLEvent event = eventReader.nextEvent();

        if (event.isStartElement()) {
          StartElement startElement = event.asStartElement();
          // If we have a subscriber element, we create a new subscriber
          if (startElement.getName().getLocalPart() == (SUBSCRIBER)) {
            subscriber = new Subscriber();
            continue;
          }

          else if (event.isStartElement()) {
            if (event.asStartElement().getName().getLocalPart()
                .equals(NAME)) {
              event = eventReader.nextEvent();
              subscriber.setName(event.asCharacters().getData());
              continue;
            }
          
          else if (event.asStartElement().getName().getLocalPart()
              .equals(STREET)) {
        	address = new Address();
            event = eventReader.nextEvent();
            address.setStreet(event.asCharacters().getData());
            continue;
          }
          
          else if (event.asStartElement().getName().getLocalPart()
                  .equals(CITY)) {
                event = eventReader.nextEvent();
                address.setCity(event.asCharacters().getData());
                continue;
          }
          
          else if (event.asStartElement().getName().getLocalPart()
                  .equals(STATE)) {
                event = eventReader.nextEvent();
                address.setState(event.asCharacters().getData());
                continue;
          }

          else if (event.asStartElement().getName().getLocalPart()
              .equals(ZIP)) {
            event = eventReader.nextEvent();
            address.setZip(event.asCharacters().getData());
            subscriber.setAddress(address);
            continue;
          }

          else if (event.asStartElement().getName().getLocalPart()
              .equals(PHONE)) {
            event = eventReader.nextEvent();
            subscriber.setPhone(event.asCharacters().getData());
            continue;
          }
          
          else if (event.asStartElement().getName().getLocalPart()
                  .equals(EMAIL)) {
                event = eventReader.nextEvent();
                subscriber.setEmail(event.asCharacters().getData());
                continue;
          }
          
          else if (event.asStartElement().getName().getLocalPart()
                  .equals(FACEBOOK)) {
                event = eventReader.nextEvent();
                if(event.isCharacters()){
                    Characters cdata = event.asCharacters();
                    if(cdata.isWhiteSpace()) 
                    	continue;
                    else
                    	subscriber.setFacebook(event.asCharacters().getData());
                }
                continue;
          }
          
          else if(event.asStartElement().getName().getLocalPart()
                  .equals(TWITTER)) {
                event = eventReader.nextEvent();
                if(event.isCharacters()){
                    Characters cdata = event.asCharacters();
                    if(cdata.isWhiteSpace()) 
                    	continue;
                    else 
                    	subscriber.setTwitter(event.asCharacters().getData());
                }
                continue;
          }
          }
        }
        // If we reach the end of an item element, we add it to the list
        else if (event.isEndElement()) {
          EndElement endElement = event.asEndElement();
          if (endElement.getName().getLocalPart() == (SUBSCRIBER)) {
        	  return subscriber;
          }
        }

      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (XMLStreamException e) {
      e.printStackTrace();
    }
	return subscriber; 
  }

}
