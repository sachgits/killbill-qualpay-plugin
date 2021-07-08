
package org.killbill.billing.plugin.qualpay.models;

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

import org.killbill.billing.plugin.qualpay.models.B2C.Result;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "Result"
})
@Generated("jsonschema2pojo")
public class Business2CustomerResponse implements Serializable
{

    @JsonProperty("Result")
    private Result result;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 6822851213639582749L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Business2CustomerResponse() {
    }

    /**
     * 
     * @param result
     */
    public Business2CustomerResponse(Result result) {
        super();
        this.result = result;
    }

    @JsonProperty("Result")
    public Result getResult() {
        return result;
    }

    @JsonProperty("Result")
    public void setResult(Result result) {
        this.result = result;
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
        sb.append(Business2CustomerResponse.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("result");
        sb.append('=');
        sb.append(((this.result == null)?"<null>":this.result));
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
