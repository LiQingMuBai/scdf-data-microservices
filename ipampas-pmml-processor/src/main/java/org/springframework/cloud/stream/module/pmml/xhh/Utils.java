package org.springframework.cloud.stream.module.pmml.xhh;
import org.dmg.pmml.FieldName;
import org.dmg.pmml.PMML;
import org.jpmml.model.PMMLUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
/**
 * Created by young on 2017/12/29.
 */
public class Utils {
    private Utils() {
    }

    /**
     * read the pmml file into memory
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static PMML readPMML(File file) throws Exception {
        InputStream is = new FileInputStream(file);
        return PMMLUtil.unmarshal(is);
    }

    public static Map<FieldName, ?> convertInputRecord(Map<String, Object> inputRecord) {
        Map<FieldName, Object> outputRecord = new HashMap<FieldName, Object>();
        for (Map.Entry<String, ?> input : inputRecord.entrySet()) {
            FieldName featureFieldName = FieldName.create(input.getKey());
            outputRecord.put(featureFieldName, input.getValue());
        }
        return outputRecord;
    }
}
