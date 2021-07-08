
package org.killbill.billing.plugin.qualpay.models.B2C;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "ReferenceItem"
})
@Generated("jsonschema2pojo")
public class ReferenceData implements Serializable
{

    @JsonProperty("ReferenceItem")
    private ReferenceItem referenceItem;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -8678700063213962921L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ReferenceData() {
    }

    /**
     * 
     * @param referenceItem
     */
    public ReferenceData(ReferenceItem referenceItem) {
        super();
        this.referenceItem = referenceItem;
    }

    @JsonProperty("ReferenceItem")
    public ReferenceItem getReferenceItem() {
        return referenceItem;
    }

    @JsonProperty("ReferenceItem")
    public void setReferenceItem(ReferenceItem referenceItem) {
        this.referenceItem = referenceItem;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ReferenceData.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("referenceItem");
        sb.append('=');
        sb.append(((this.referenceItem == null)?"<null>":this.referenceItem));
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(((this.additionalProperties == null)?"<null>":this.additionalProperties));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
