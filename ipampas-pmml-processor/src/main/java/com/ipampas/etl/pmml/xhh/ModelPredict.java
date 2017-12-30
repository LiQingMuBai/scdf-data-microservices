package com.ipampas.etl.pmml.xhh;
import org.dmg.pmml.FieldName;
import org.dmg.pmml.Model;
import org.dmg.pmml.ModelVerification;
import org.dmg.pmml.PMML;
import org.jpmml.evaluator.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Sets;
import java.io.File;
import java.util.*;

/**
 * Created by young on 2017/12/29.
 */
public class ModelPredict {
    private static final Logger LOG = LoggerFactory.getLogger(ModelPredict.class);
    private static PMML pmml = null;
    private static Evaluator evaluator = null;

    // default load the line regression model
    static {
        File inputModel =
                new File(ModelPredict.class.getClassLoader().getResource("lr.pmml").getFile());
        try {
            pmml = Utils.readPMML(inputModel);
        } catch (Exception e) {
            LOG.error("read pmml exception!");
            e.printStackTrace();
        }

        ModelEvaluatorFactory modelEvaluatorFactory = ModelEvaluatorFactory.newInstance();
        ModelEvaluator<?> modelEvaluator = modelEvaluatorFactory.newModelEvaluator(pmml);
        Model model = modelEvaluator.getModel();
        ModelVerification modelVerification = model.getModelVerification();
        if (modelVerification != null) {
            throw new InvalidFeatureException(modelVerification);
        }
        evaluator = modelEvaluator;
    }

    public static Map<String, Double> predict(Map<String, Object> input) {

        if (evaluator == null) {
            throw new RuntimeException("evaluator is null!");
        }

        LOG.debug("inputRecord:{},summary:{} {}",
                input,
                evaluator.getSummary(),
                evaluator.getMiningFunction());

        // input params
        Map<FieldName, FieldValue> arguments = getFieldArgumentMap(input, evaluator);
        LOG.debug("arguments:{}", arguments);

        // regression or classify
        Map<FieldName, ?> results = evaluator.evaluate(arguments);
        LOG.debug("results:{}", results);

        // output params
        Map<String, Double> retResult = getTargetResultMap(evaluator, results);
        LOG.debug("retResult:{}", retResult);

        return retResult;
    }

    /**
     * check that input parameters are complete
     *
     * @param inputRecord
     * @param inputFields
     */
    private static void
    checkInputArgument(Map<FieldName, ?> inputRecord, List<InputField> inputFields) {
        Sets.SetView<FieldName> missingInputFields
                = Sets.difference(new LinkedHashSet<FieldName>(EvaluatorUtil.getNames(inputFields)), inputRecord.keySet());
        if (missingInputFields.size() > 0) {
            throw new IllegalArgumentException("Missing input field(s): " + missingInputFields.toString());
        }
    }

    /**
     * assemble the input parameters
     *
     * @param input
     * @param evaluator
     * @return
     */
    private static Map<FieldName, FieldValue>
    getFieldArgumentMap(Map<String, Object> input, Evaluator evaluator) {
        Map<FieldName, ?> inputData = Utils.convertInputRecord(input);
        checkInputArgument(inputData, evaluator.getInputFields());

        Map<FieldName, FieldValue> arguments = new HashMap<FieldName, FieldValue>();
        for (InputField inputField : evaluator.getInputFields()) {
            FieldName name = inputField.getName();
            FieldValue value = EvaluatorUtil.prepare(inputField, inputData.get(name));
            arguments.put(name, value);
        }
        return arguments;
    }

    /**
     * assemble the output parameters
     *
     * @param evaluator
     * @param results
     * @return
     */
    private static Map<String, Double>
    getTargetResultMap(Evaluator evaluator, Map<FieldName, ?> results) {

        Map<String, Double> retResult = new HashMap<String, Double>();
        List<TargetField> targetFields = evaluator.getTargetFields();
        for (TargetField targetField : targetFields) {
            FieldName targetFieldName = targetField.getName();
            Object targetFieldValue = results.get(targetFieldName);
            LOG.debug("targetFieldValue: {}", targetFieldValue);
            LOG.debug("class: {}", targetFieldValue.getClass());

            double result = Double.MIN_VALUE;
            if (targetFieldValue instanceof Computable) {
                Computable computable = (Computable) targetFieldValue;
                Object unboxedTargetFieldValue = computable.getResult();
                result = Double.parseDouble(unboxedTargetFieldValue.toString());
                LOG.debug("Computable result: {}", result);
            } else {
                result = Double.parseDouble(targetFieldValue.toString());
                LOG.debug("Normal result: {}", result);
            }
            retResult.put(targetFieldName.toString(), result);
        }
        return retResult;
    }
}
