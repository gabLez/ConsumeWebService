/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consumeservicioweb;

import javax.swing.JOptionPane;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author User
 */
public class ConsumeServicioWeb {

    public  static String callWebService(String url, String requestXml)
        {
        String responseXml = null;
        CloseableHttpClient httpClient = null;
        HttpPost httpPost;
        try
        {
          SSLContextBuilder builder = new   SSLContextBuilder();
          builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
          SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(builder.build(),NoopHostnameVerifier.INSTANCE);
          Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create().register("http",new PlainConnectionSocketFactory()).register("https",sslConnectionSocketFactory).build();
          PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);
          cm.setMaxTotal(100);
          httpClient =HttpClients.custom().setSSLSocketFactory(sslConnectionSocketFactory).setConnectionManager(cm).build();  
          httpPost = new HttpPost(url);
          httpPost.setHeader("Content-Type", "text/xml; charset=utf-8");
          StringEntity entiry = new StringEntity(requestXml, "utf-8");
          httpPost.setEntity(entiry);
          HttpResponse response = httpClient.execute(httpPost);
          HttpEntity entity = response.getEntity();
          responseXml = EntityUtils.toString(entity, "utf-8");
        }
        catch (Exception ex)
        {
           JOptionPane.showMessageDialog(null, "Error: "+ex.getMessage()+ex.getLocalizedMessage());
           System.out.println("Error:"+ex.getMessage()+"_"+ex.toString());
        }
        finally
            {
                try 
                    {
                    if (httpClient != null){
                        httpClient.close();
                        System.out.println("Conexion cerrada. "+httpClient.toString());}
                    else{
                        JOptionPane.showMessageDialog(null,"", "Error de conexion: "+httpClient.toString(),JOptionPane.ERROR_MESSAGE);
                        System.out.println("Conexion Nula. HTTPClient:"+httpClient.toString());}
                    }
                catch (Exception ex)
                    {
                        JOptionPane.showMessageDialog(null, "Error: "+ex.getMessage());
                        System.out.println(ex.getMessage());
                    }
            }
        return responseXml;
        }
    
    public static void main(String[] args) throws Exception {
       String url1 = "https://201.151.252.116:9105/IngresoManifestacionImpl/IngresoManifestacionService";
       String req_xml ="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" \n" +
"xmlns:ws=\"http://ws.ingresomanifestacion.manifestacion.www.ventanillaunica.gob.mx\" \n" +
"xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">\n" +
"	<soapenv:Header>\n" +
"		<wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">\n" +
"			<wsse:UsernameToken xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">\n" +
"				<wsse:Username>AAL0409235E6</wsse:Username>\n" +
"				<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">Z6fFB+bqbvqb6yPgstFf0gUYvQx8cAYFzHE2i9cHSWmIiGfgaD3GfyGDHWt9dBxp</wsse:Password>\n" +
"			</wsse:UsernameToken>\n" +
"			<wsu:Timestamp wsu:Id=\"Timestamp-5c9f0ef0-ab45-421d-a633-4c4fad26d945\">\n" +
"				<wsu:Created>2021-08-03T15:13:39Z</wsu:Created>\n" +
"				<wsu:Expires>2021-08-03T16:13:39Z</wsu:Expires>\n" +
"			</wsu:Timestamp>\n" +
"		</wsse:Security>\n" +
"	</soapenv:Header>\n" +
"<soapenv:Body>\n" +
"    <ws:registroManifestacion>\n" +
"      <!--Optional: -->\n" +
"      <informacionManifestacion>\n" +
"        <firmaElectronica>\n" +
"          <certificado>MIIFTDCCBDSgAwIBAgIUMjAwMDEwMDAwMDAxMDAwMDE4MTUwDQYJKoZIhvcNAQEFBQAwggFvMRgw\n" +
"            FgYDVQQDDA9BLkMuIGRlIHBydWViYXMxLzAtBgNVBAoMJlNlcnZpY2lvIGRlIEFkbWluaXN0cmFj\n" +
"            acOzbiBUcmlidXRhcmlhMTgwNgYDVQQLDC9BZG1pbmlzdHJhY2nDs24gZGUgU2VndXJpZGFkIGRl\n" +
"            IGxhIEluZm9ybWFjacOzbjEpMCcGCSqGSIb3DQEJARYaYXNpc25ldEBwcnVlYmFzLnNhdC5nb2Iu\n" +
"            bXgxJjAkBgNVBAkMHUF2LiBIaWRhbGdvIDc3LCBDb2wuIEd1ZXJyZXJvMQ4wDAYDVQQRDAUwNjMw\n" +
"            MDELMAkGA1UEBhMCTVgxGTAXBgNVBAgMEERpc3RyaXRvIEZlZGVyYWwxEjAQBgNVBAcMCUNveW9h\n" +
"            Y8OhbjEVMBMGA1UELRMMU0FUOTcwNzAxTk4zMTIwMAYJKoZIhvcNAQkCDCNSZXNwb25zYWJsZTog\n" +
"            SMOpY3RvciBPcm5lbGFzIEFyY2lnYTAeFw0xMDEyMjQxOTA1NTlaFw0xMzAzMjMxOTA1NTlaMIHG\n" +
"            MSQwIgYDVQQDExtBR1JJQ09MQSBBTFBFIFMgREUgUkwgREUgQ1YxJDAiBgNVBCkTG0FHUklDT0xB\n" +
"            IEFMUEUgUyBERSBSTCBERSBDVjEkMCIGA1UEChMbQUdSSUNPTEEgQUxQRSBTIERFIFJMIERFIENW\n" +
"            MQswCQYDVQQGEwJNWDElMCMGA1UELRMcQUFMMDQwOTIzNUU2IC8gQlVSSTY5MDEyOFRWMDEeMBwG\n" +
"            A1UEBRMVIC8gQlVSSTY5MDEyOEhERk5NRzA1MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC/\n" +
"            T5OIWfoQnYsMacvTIUbC7Lc0SkaFLs7w2gyvJGp3kGB5e+fxjF3uwdkgsBWBw6GPOs7TYbXNvMrN\n" +
"            2wO8DIPhTu/K0klAqr/ofSD4GBMuuafcsVymcFEeSQHshVo7vruDuRrl7viHL77bVpGLPNqkYn2m\n" +
"            CYvyEB/O/51muTjYIQIDAQABo4IBCDCCAQQwDAYDVR0TAQH/BAIwADALBgNVHQ8EBAMCA9gwEQYJ\n" +
"            YIZIAYb4QgEBBAQDAgWgMB0GA1UdDgQWBBS0nGHAfbh87ltfURaINqtBbvxbXDAuBgNVHR8EJzAl\n" +
"            MCOgIaAfhh1odHRwOi8vcGtpLnNhdC5nb2IubXgvc2F0LmNybDAzBggrBgEFBQcBAQQnMCUwIwYI\n" +
"            KwYBBQUHMAGGF2h0dHA6Ly9vY3NwLnNhdC5nb2IubXgvMB8GA1UdIwQYMBaAFOtZfQQimlONnnEa\n" +
"            oFiWKfU54KDFMBAGA1UdIAQJMAcwBQYDKgMEMB0GA1UdJQQWMBQGCCsGAQUFBwMEBggrBgEFBQcD\n" +
"            AjANBgkqhkiG9w0BAQUFAAOCAQEAwNsi5H5mxDhGX/1w67YxvGg6XegxjHx9VMyF0DVHUrBGsXb5\n" +
"            1t1Y4FSjqKGFiw64cLNIFl1N1MnQHAXYg1G87zWpFmod6fcrBIMb0zgu3suU940hgS+hc6b+VJXz\n" +
"            LrFT6yKmmW+hFA7Dbof23OUTMw7juxiiACOUSvJ2j08izBlrJFsr5sYrztLbIh7eXoaRg02psRfy\n" +
"            /pBeQGe4IvErPhPDtTW5CqFdE5LOBVD1bcYyrJVGOAG1y77+zsW3IKf/V2uxgONJPXE6LpW/JwDp\n" +
"            DdwsEBgOl4WK2vu7yoZH0LWPFpMuRg3hW4dTxbMu+6x+VrCDpx4ijT1WHSa3JLpFOg==</certificado>\n" +
"          <cadenaOriginal>|AAL0409235E6|AAL970927390|AFC000526BJ2|ASA060425JV0|1|043613006EQH1|0170200EXZDU3|0170200EXZDR8|COVE2000034Z1|PEDIMENTO|PATENTE|020|PEDIMENTOX1|PATENTEX1|060|10/08/2020|1000.0|FORPAG.CC|otra forma de pago 1|04/09/2019|595023.54|PENDIENTE DE PAGO|FORPAG.OT|otra forma de pago 2|05/08/2019|124789.29|PENDIENTE SU PAGO EN TIEMPO|FORPAG.CH|01/01/2020|MOTIVO|MERCANCIA|FORPAG.LC|VALADU.VPV|INCRE.CE|10/08/2020|100.0|1|INCRE.CG|01/01/2020|10000.0|0|INCRE.GS|12/02/2020|45100.05|1|INCRE.GT|13/03/2019|672100.31|0|INCRE.HM|14/06/2019|984100.46|1|INCRE.MC|15/05/2019|2564223.05|0|INCRE.MP|16/04/2019|9564123.48|1|INCRE.RD|17/03/2019|155123.09|0|INCRE.TI|18/02/2019|456333.07|1|INCRE.VC|19/01/2019|84654.16|0|DECRE.GR|10/08/2020|200.0|DECRE.GT|11/07/2020|1200.0|DECRE.ID|12/06/2020|21200.2|DECRE.PI|13/05/2020|621200.15|DECRE.RC|14/04/2020|7621200.99|DECRE.RP|15/03/2020|9621200.55|COVE12004TH16|10/08/2020|28000.8|POR PAGAR|FORPAG.EF|VALADU.VMS|INCRE.CE|10/08/2020|6500.0|1|INCRE.TI|18/05/2019|56987.99|0|DECRE.PI|10/08/2020|200.0|58100.51|952200.13|76500.35|635008.41|354100.28|</cadenaOriginal>\n" +
"          <firma>Equa2Y7Ir5di9B/OkifnAnS6Pe8BmoZ4fMerDTJWessmD0497IcnrrnAnq7J2G0LBlt0w3wUJOb8BB9XkwqLeZHymecSrqc8cZ7cEl+VcXCMPl0sr7vaSiXi0gcELt/XlPSb4wsiTVzh1d5pkzZJViewpsEUfeIvyi22sOLXiss=</firma>\n" +
"        </firmaElectronica>\n" +
"        <importador-exportador>\n" +
"          <rfc>AAL0409235E6</rfc>\n" +
"        </importador-exportador>\n" +
"        <datosManifestacionValor>\n" +
"          <personaConsulta>\n" +
"            <rfc>AAL970927390</rfc>\n" +
"          </personaConsulta>\n" +
"          <personaConsulta>\n" +
"            <rfc>AFC000526BJ2</rfc>\n" +
"          </personaConsulta>\n" +
"          <personaConsulta>\n" +
"            <rfc>ASA060425JV0</rfc>\n" +
"          </personaConsulta>\n" +
"          <existeVinculacion>1</existeVinculacion>\n" +
"          <!--Optional: -->\n" +
"          <documentos>\n" +
"            <!--Optional: -->\n" +
"            <eDocument>043613006EQH1</eDocument><!--String -->\n" +
"          </documentos>\n" +
"          <documentos>\n" +
"            <!--Optional: -->\n" +
"            <eDocument>0170200EXZDU3</eDocument><!--String -->\n" +
"          </documentos>\n" +
"          <documentos>\n" +
"            <!--Optional: -->\n" +
"            <eDocument>0170200EXZDR8</eDocument><!--String -->\n" +
"          </documentos>\n" +
"          <!--1 or more repetitions: -->\n" +
"          <informacionCove>\n" +
"            <cove>COVE2000034Z1</cove>\n" +
"            <!--Zero or more repetitions: -->\n" +
"            <pedimento>\n" +
"              <!--Optional: -->\n" +
"              <pedimento>PEDIMENTO</pedimento>\n" +
"              <!--Optional: -->\n" +
"              <patente>PATENTE</patente>\n" +
"              <!--Optional: -->\n" +
"              <aduana>020</aduana>\n" +
"            </pedimento>\n" +
"            <pedimento>\n" +
"              <!--Optional: -->\n" +
"              <pedimento>PEDIMENTOX1</pedimento>\n" +
"              <!--Optional: -->\n" +
"              <patente>PATENTEX1</patente>\n" +
"              <!--Optional: -->\n" +
"              <aduana>060</aduana>\n" +
"            </pedimento>\n" +
"            <!--1 or more repetitions: -->\n" +
"            <precioPagado>\n" +
"              <fechaPago>2020-08-10T09:00:00Z</fechaPago>\n" +
"              <total>1000.0</total>\n" +
"              <tipoPago>FORPAG.CC</tipoPago>\n" +
"              <especifique>otra forma de pago 1</especifique>\n" +
"            </precioPagado>\n" +
"            <!--1 or more repetitions: -->\n" +
"            <precioPorPagar>\n" +
"              <fechaPago>2019-09-04T13:10:58Z</fechaPago>\n" +
"              <total>595023.54</total>\n" +
"              <situacionNofechaPago>PENDIENTE DE PAGO</situacionNofechaPago>\n" +
"              <tipoPago>FORPAG.OT</tipoPago>\n" +
"              <especifique>otra forma de pago 2</especifique>\n" +
"            </precioPorPagar>\n" +
"            <precioPorPagar>\n" +
"              <fechaPago>2019-08-05T14:20:23Z</fechaPago>\n" +
"              <total>124789.29</total>\n" +
"              <situacionNofechaPago>PENDIENTE SU PAGO EN TIEMPO</situacionNofechaPago>\n" +
"              <tipoPago>FORPAG.CH</tipoPago>\n" +
"            </precioPorPagar>\n" +
"            <!--1 or more repetitions: -->\n" +
"            <compensoPago>\n" +
"              <fecha>2020-01-01T10:10:32Z</fecha>\n" +
"              <motivo>MOTIVO</motivo>\n" +
"              <prestacionMercancia>MERCANCIA</prestacionMercancia>\n" +
"              <tipoPago>FORPAG.LC</tipoPago>\n" +
"            </compensoPago>\n" +
"            <metodoValoracion>VALADU.VPV</metodoValoracion>\n" +
"            <!--1 or more repetitions: -->\n" +
"            <incrementables>\n" +
"              <tipoIncrementable>INCRE.CE</tipoIncrementable>\n" +
"              <fechaErogacion>2020-08-10T23:59:59Z</fechaErogacion>\n" +
"              <importe>100.0</importe>\n" +
"              <aCargoImportador>1</aCargoImportador>\n" +
"            </incrementables>\n" +
"            <incrementables>\n" +
"              <tipoIncrementable>INCRE.CG</tipoIncrementable>\n" +
"              <fechaErogacion>2020-01-01T09:05:10Z</fechaErogacion>\n" +
"              <importe>10000.0</importe>\n" +
"              <aCargoImportador>0</aCargoImportador>\n" +
"            </incrementables>\n" +
"            <incrementables>\n" +
"              <tipoIncrementable>INCRE.GS</tipoIncrementable>\n" +
"              <fechaErogacion>2020-02-12T10:25:40Z</fechaErogacion>\n" +
"              <importe>45100.05</importe>\n" +
"              <aCargoImportador>1</aCargoImportador>\n" +
"            </incrementables>\n" +
"            <incrementables>\n" +
"              <tipoIncrementable>INCRE.GT</tipoIncrementable>\n" +
"              <fechaErogacion>2019-03-13T11:30:45Z</fechaErogacion>\n" +
"              <importe>672100.31</importe>\n" +
"              <aCargoImportador>0</aCargoImportador>\n" +
"            </incrementables>\n" +
"            <incrementables>\n" +
"              <tipoIncrementable>INCRE.HM</tipoIncrementable>\n" +
"              <fechaErogacion>2019-06-14T12:35:50Z</fechaErogacion>\n" +
"              <importe>984100.46</importe>\n" +
"              <aCargoImportador>1</aCargoImportador>\n" +
"            </incrementables>\n" +
"            <incrementables>\n" +
"              <tipoIncrementable>INCRE.MC</tipoIncrementable>\n" +
"              <fechaErogacion>2019-05-15T13:10:22Z</fechaErogacion>\n" +
"              <importe>2564223.05</importe>\n" +
"              <aCargoImportador>0</aCargoImportador>\n" +
"            </incrementables>\n" +
"            <incrementables>\n" +
"              <tipoIncrementable>INCRE.MP</tipoIncrementable>\n" +
"              <fechaErogacion>2019-04-16T14:32:20Z</fechaErogacion>\n" +
"              <importe>9564123.48</importe>\n" +
"              <aCargoImportador>1</aCargoImportador>\n" +
"            </incrementables>\n" +
"            <incrementables>\n" +
"              <tipoIncrementable>INCRE.RD</tipoIncrementable>\n" +
"              <fechaErogacion>2019-03-17T15:41:29Z</fechaErogacion>\n" +
"              <importe>155123.09</importe>\n" +
"              <aCargoImportador>0</aCargoImportador>\n" +
"            </incrementables>\n" +
"            <incrementables>\n" +
"              <tipoIncrementable>INCRE.TI</tipoIncrementable>\n" +
"              <fechaErogacion>2019-02-18T16:01:11Z</fechaErogacion>\n" +
"              <importe>456333.07</importe>\n" +
"              <aCargoImportador>1</aCargoImportador>\n" +
"            </incrementables>\n" +
"            <incrementables>\n" +
"              <tipoIncrementable>INCRE.VC</tipoIncrementable>\n" +
"              <fechaErogacion>2019-01-19T10:14:25Z</fechaErogacion>\n" +
"              <importe>84654.16</importe>\n" +
"              <aCargoImportador>0</aCargoImportador>\n" +
"            </incrementables>\n" +
"            <!--1 or more repetitions: -->\n" +
"            <decrementables>\n" +
"              <tipoDecrementable>DECRE.GR</tipoDecrementable>\n" +
"              <fechaErogacion>2020-08-10T23:59:59Z</fechaErogacion>\n" +
"              <importe>200.0</importe>\n" +
"            </decrementables>\n" +
"            <decrementables>\n" +
"              <tipoDecrementable>DECRE.GT</tipoDecrementable>\n" +
"              <fechaErogacion>2020-07-11T10:20:21Z</fechaErogacion>\n" +
"              <importe>1200.0</importe>\n" +
"            </decrementables>\n" +
"            <decrementables>\n" +
"              <tipoDecrementable>DECRE.ID</tipoDecrementable>\n" +
"              <fechaErogacion>2020-06-12T11:45:08Z</fechaErogacion>\n" +
"              <importe>21200.2</importe>\n" +
"            </decrementables>\n" +
"            <decrementables>\n" +
"              <tipoDecrementable>DECRE.PI</tipoDecrementable>\n" +
"              <fechaErogacion>2020-05-13T12:30:23Z</fechaErogacion>\n" +
"              <importe>621200.15</importe>\n" +
"            </decrementables>\n" +
"            <decrementables>\n" +
"              <tipoDecrementable>DECRE.RC</tipoDecrementable>\n" +
"              <fechaErogacion>2020-04-14T13:19:51Z</fechaErogacion>\n" +
"              <importe>7621200.99</importe>\n" +
"            </decrementables>\n" +
"            <decrementables>\n" +
"              <tipoDecrementable>DECRE.RP</tipoDecrementable>\n" +
"              <fechaErogacion>2020-03-15T14:35:23Z</fechaErogacion>\n" +
"              <importe>9621200.55</importe>\n" +
"            </decrementables>\n" +
"          </informacionCove>\n" +
"          <informacionCove>\n" +
"            <cove>COVE12004TH16</cove>\n" +
"            <!--Zero or more repetitions: -->\n" +
"            <precioPorPagar>\n" +
"              <fechaPago>2020-08-10T23:59:59Z</fechaPago>\n" +
"              <total>28000.8</total>\n" +
"              <situacionNofechaPago>POR PAGAR</situacionNofechaPago>\n" +
"              <tipoPago>FORPAG.EF</tipoPago>\n" +
"            </precioPorPagar>\n" +
"            <!--1 or more repetitions: -->\n" +
"            <metodoValoracion>VALADU.VMS</metodoValoracion>\n" +
"            <!--1 or more repetitions: -->\n" +
"            <incrementables>\n" +
"              <tipoIncrementable>INCRE.CE</tipoIncrementable>\n" +
"              <fechaErogacion>2020-08-10T23:59:59Z</fechaErogacion>\n" +
"              <importe>6500.0</importe>\n" +
"              <aCargoImportador>1</aCargoImportador>\n" +
"            </incrementables>\n" +
"            <incrementables>\n" +
"              <tipoIncrementable>INCRE.TI</tipoIncrementable>\n" +
"              <fechaErogacion>2019-05-18T15:54:12Z</fechaErogacion>\n" +
"              <importe>56987.99</importe>\n" +
"              <aCargoImportador>0</aCargoImportador>\n" +
"            </incrementables>\n" +
"            <!--1 or more repetitions: -->\n" +
"            <decrementables>\n" +
"              <tipoDecrementable>DECRE.PI</tipoDecrementable>\n" +
"              <fechaErogacion>2020-08-10T23:59:59Z</fechaErogacion>\n" +
"              <importe>200.0</importe>\n" +
"            </decrementables>\n" +
"          </informacionCove>\n" +
"          <valorEnAduana>\n" +
"            <totalPrecioPagado>58100.51</totalPrecioPagado>\n" +
"            <totalPrecioPorPagar>952200.13</totalPrecioPorPagar>\n" +
"            <totalIncrementables>76500.35</totalIncrementables>\n" +
"            <totalDecrementables>635008.41</totalDecrementables>\n" +
"            <totalValorAduana>354100.28</totalValorAduana>\n" +
"          </valorEnAduana>\n" +
"        </datosManifestacionValor>\n" +
"      </informacionManifestacion>\n" +
"    </ws:registroManifestacion>\n" +
"  </soapenv:Body>\n" +
"</soapenv:Envelope>";
       
String respuesta_xml =callWebService(url1,req_xml);
System.out.println(respuesta_xml);
    }
}

