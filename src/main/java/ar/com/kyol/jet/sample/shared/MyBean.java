package ar.com.kyol.jet.sample.shared;

import java.io.Serializable;
import java.util.Date;

import ar.com.kyol.jet.client.Reflection;

public class MyBean implements Reflection, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Date someDay;
	private String someText;
	private Integer someInt;
	private float someFloat;
	private boolean someBoolean;
	private Boolean someObjBoolean;
	private Float negativefloat;
	private String truefalse2string;
	private String integer2string;
	private int timesSent;
	
	public Date getSomeDay() {
		return someDay;
	}
	public void setSomeDay(Date someDay) {
		this.someDay = someDay;
	}
	public String getSomeText() {
		return someText;
	}
	public void setSomeText(String someText) {
		this.someText = someText;
	}
	public Integer getSomeInt() {
		return someInt;
	}
	public void setSomeInt(Integer someInt) {
		this.someInt = someInt;
	}
	public int getTimesSent() {
		return timesSent;
	}
	public void setTimesSent(int timesSent) {
		this.timesSent = timesSent;
	}
	public float getSomeFloat() {
		return someFloat;
	}
	public void setSomeFloat(float someFloat) {
		this.someFloat = someFloat;
	}
	public String getTruefalse2string() {
		return truefalse2string;
	}
	public void setTruefalse2string(String truefalse2string) {
		this.truefalse2string = truefalse2string;
	}
	public String getInteger2string() {
		return integer2string;
	}
	public void setInteger2string(String integer2string) {
		this.integer2string = integer2string;
	}
	public boolean getSomeBoolean() {
		return someBoolean;
	}
	public void setSomeBoolean(boolean someBoolean) {
		this.someBoolean = someBoolean;
	}
	public Boolean getSomeObjBoolean() {
		return someObjBoolean;
	}
	public void setSomeObjBoolean(Boolean someObjBoolean) {
		this.someObjBoolean = someObjBoolean;
	}
	public Float getNegativefloat() {
		return negativefloat;
	}
	public void setNegativefloat(Float negativefloat) {
		this.negativefloat = negativefloat;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

}
