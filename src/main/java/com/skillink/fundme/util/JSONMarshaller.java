package com.skillink.fundme.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JSONMarshaller {

	private static final Log LOG = LogFactory.getLog(JSONMarshaller.class);

	private static ObjectMapper mapper = new ObjectMapper();
	
	public static String marshall(Object object) {
		try {
			mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//			mapper.enableDefaultTyping(DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
			String str = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
			return str;
		} catch (Exception ex) {
			LOG.error("Error occured while marshalling to JSON: " + ex);
		}

		return null;
	}

	public static Object unmarshall(String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			String className = "";
			String jsonList = "";
			String[] strList = null;
			Object object = null;
			if (json.endsWith("]")) {
				jsonList = json.substring(1, json.length() - 1);
				jsonList = "{" + jsonList + "}";
				jsonList = jsonList.replaceFirst(",", ":");
				jsonList = jsonList.substring(jsonList.indexOf("[") + 1, jsonList.lastIndexOf("]"));
				strList = jsonList.split("\\}, \\{");
			}
			mapper.configure(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY, false);
			// JSONObject jsonObject = JSONObject.fromObject(json);
			// Object object = JSONObject.toBean(jsonObject, objectClass);
			Object map = mapper.readValue(json, Object.class);
			if (map instanceof HashMap) {
				HashMap<?, ?> hashmap = (HashMap<?, ?>) map;
				className = String.valueOf(hashmap.get("@class"));
				object = mapper.readValue(json, Class.forName(className));
			} else if (map instanceof ArrayList && strList != null) {
				List<Object> returnList = new ArrayList<Object>();
				int listSize = strList.length;
				HashMap<?, ?> maps = null;
				
				for (int i = 0; i < listSize; i++) {
					Object objectList = null;
					if (i == 0) {
						if(!Util.isEmptyString(strList[i].trim())) {
							maps = (HashMap<?, ?>) mapper.readValue(strList[i] + "}", Object.class);
							className = String.valueOf(maps.get("@class"));
							objectList = mapper.readValue(strList[i] + "}", Class.forName(className));
						}else {
							objectList = "";
						}
					} else if (i == listSize - 1) {
						maps = (HashMap<?, ?>) mapper.readValue("{" + strList[i], Object.class);
						className = String.valueOf(maps.get("@class"));
						objectList = mapper.readValue("{" + strList[i], Class.forName(className));
					} else {
						maps = (HashMap<?, ?>) mapper.readValue("{" + strList[i] + "}", Object.class);
						className = String.valueOf(maps.get("@class"));
						objectList = mapper.readValue("{" + strList[i] + "}", Class.forName(className));
					}
					returnList.add(objectList);
				}

				object = returnList;

			}

			return object;
		} catch (Exception ex) {
			LOG.error("Error occured while unmarshalling to Object: " + ex);
		}

		return null;
	}
	
	public static Object unmarshall(String json, Class<?> objectClass)
    {
            try
            {
                    Object object = mapper.readValue(json, objectClass);
                    
                    return object;
            }
            catch(Exception ex)
            {
                    LOG.error(ex);
                    return null;
            }               
    }
	
	public static Object unmarshall(String json, JavaType objectClass)
    {
            try
            {
                    ObjectMapper mapper = new ObjectMapper();
                    Object object = mapper.readValue(json, objectClass);
                    
                    return object;
            }
            catch(Exception ex)
            {
                    LOG.error(ex);
                    return null;
            }               
    }

	
}
