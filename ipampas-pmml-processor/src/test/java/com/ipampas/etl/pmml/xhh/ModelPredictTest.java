package com.ipampas.etl.pmml.xhh;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by young on 2017/12/29.
 */
public class ModelPredictTest {

    public static void main(String[] args) {
        Map<String, Object> inputData = new HashMap<String, Object>();
        inputData.put("aa", 1.0);
        inputData.put("bb", 1.0);
        inputData.put("cc", 1.0);
        inputData.put("dd", 1.0);
        inputData.put("ee", 1.0);

        Map<String, Double> resultMap = ModelPredict.predict(inputData);
        double result = resultMap.get("label");

        System.out.println("result:" + result);
    }
}
