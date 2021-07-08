
package org.killbill.billing.plugin.qualpay.models.B2C;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
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
    "ResultParameter"
})
@Generated("jsonschema2pojo")
public class ResultParameters implements Serializable
{

    @JsonProperty("ResultParameter")
    private List<ResultParameter> resultParameter = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -673520633694942404L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ResultParameters() {
    }

    /**
     * 
     * @param resultParameter
     */
    public ResultParameters(List<ResultParameter> resultParameter) {
        super();
        this.resultParameter = resultParameter;
    }

    @JsonProperty("ResultParameter")
    public List<ResultParameter> getResultParameter() {
        return resultParameter;
    }

    @JsonProperty("ResultParameter")
    public void setResultParameter(List<ResultParameter> resultParameter) {
        this.resultParameter = resultParameter;
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
        sb.append(ResultParameters.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("resultParameter");
        sb.append('=');
        sb.append(((this.resultParameter == null)?"<null>":this.resultParameter));
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
