
package restClient.soapclasses;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="SoapPhone" type="{http://soapservice.pl/wstest}SoapPhone" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlRootElement(name = "getAllPhonesResponse")
public class GetAllPhonesResponse {

    @XmlElement(name = "SoapPhone")
    protected List<SoapPhone> soapPhone;

    /**
     * Gets the value of the soapPhone property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the soapPhone property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSoapPhone().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SoapPhone }
     * 
     * 
     */
    public List<SoapPhone> getSoapPhone() {
        if (soapPhone == null) {
            soapPhone = new ArrayList<SoapPhone>();
        }
        return this.soapPhone;
    }

}
