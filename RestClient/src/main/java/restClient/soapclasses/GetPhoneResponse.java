
package restClient.soapclasses;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SoapPhone" type="{http://soapservice.pl/wstest}SoapPhone"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "soapPhone"
})
@XmlRootElement(name = "getPhoneResponse")
public class GetPhoneResponse {

    @XmlElement(name = "SoapPhone", required = true)
    protected SoapPhone soapPhone;

    /**
     * Gets the value of the soapPhone property.
     * 
     * @return
     *     possible object is
     *     {@link SoapPhone }
     *     
     */
    public SoapPhone getSoapPhone() {
        return soapPhone;
    }

    /**
     * Sets the value of the soapPhone property.
     * 
     * @param value
     *     allowed object is
     *     {@link SoapPhone }
     *     
     */
    public void setSoapPhone(SoapPhone value) {
        this.soapPhone = value;
    }

}
