package com.szmytek.srir_webservice_skos_miner;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.UnknownHostException;

/**
 * Created by Szmytek on 2015-06-18.
 */
public class WebServiceCall {
    private final String SOAP_ACTION = "http://tempuri.org/getInformationAbout";

    private final String OPERATION_NAME = "getInformationAbout";

    private final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";

    private final String SOAP_ADDRESS;
    public WebServiceCall(String address)
    {
        SOAP_ADDRESS = address;
    }
    public String Call(String name,String surname) throws XmlPullParserException, IOException {
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
            httpTransport.call(SOAP_ACTION, envelope);
            response = envelope.getResponse();
        return response.toString();
    }
}
