package com.project;

import com.project.generated.classes.Application;
import com.project.generated.classes.ApplicationSchema;
import com.project.generated.classes.InputXMLAsAString;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.StringWriter;

public class CDataWorkingDemo {
    public static void main(String[] args) throws IOException {

        ApplicationSchema applicationSchema = new ApplicationSchema();
        applicationSchema.setId(100);
        applicationSchema.setApplicantName("Testing");
        applicationSchema.setAge(29);
        Application application=new Application();
        application.applicationId="123";
        application.name="Pavan";
        applicationSchema.setApplication(application);

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ApplicationSchema.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            StringWriter sw=new StringWriter();
            jaxbMarshaller.marshal(applicationSchema, sw);

            System.out.println(sw.toString());
            sw.close();

            InputXMLAsAString inputXMLAsAString=new InputXMLAsAString();
            inputXMLAsAString.setInputString(sw.toString());


            jaxbContext = JAXBContext.newInstance(InputXMLAsAString.class);
            jaxbMarshaller = jaxbContext.createMarshaller();
            sw=new StringWriter();
            CDATAContentHandler cDataContentHandler=new CDATAContentHandler(sw,"UTF-8");
            jaxbMarshaller.marshal(inputXMLAsAString, cDataContentHandler);

            System.out.println(sw.toString());

            //The CDATA is getting added as per the sample request XML
            /*
            StreamSource source = new StreamSource(new StringReader(sw.toString()));
            StreamResult result = new StreamResult(System.out);
            webServiceTemplate.sendSourceAndReceiveToResult(source, result);
            return result;
            */
            //We can now use the Spring webservicetemplate to sendSourceAndReceiveToResult(inputXMLAsAString);

            sw.close();

        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }
}