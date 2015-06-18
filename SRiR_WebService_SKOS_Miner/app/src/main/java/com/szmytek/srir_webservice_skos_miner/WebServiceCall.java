package com.szmytek.srir_webservice_skos_miner;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by Szmytek on 2015-06-18.
 */
public class WebServiceCall {
    public final String SOAP_ACTION = "http://tempuri.org/getInformationAbout";

    public  final String OPERATION_NAME = "getInformationAbout";

    public  final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";

    public  final String SOAP_ADDRESS = "http://webserviceskosminer.gear.host/WebService.asmx";
    public WebServiceCall()
    {
    }
    public String Call(String name,String surname)
    {
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
        PropertyInfo pi=new PropertyInfo();
        pi.setName("name");
        pi.setValue(name);
        pi.setType(String.class);
        request.addProperty(pi);
        pi=new PropertyInfo();
        pi.setName("surname");
        pi.setValue(surname);
        pi.setType(String.class);
        request.addProperty(pi);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
        Object response=null;
        try
        {
            httpTransport.call(SOAP_ACTION, envelope);
            response = envelope.getResponse();
        }
        catch (Exception exception)
        {
            response=exception.toString();
        }
        return response.toString();
    }
}
