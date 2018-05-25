package com.assassin.traceless.core.external;

import android.util.Log;

import com.assassin.traceless.annotations.weaving.Burying;
import com.assassin.traceless.core.utils.MethodMatchUtils;
import com.assassin.traceless.rules.ClazzRule;

import org.aspectj.lang.ProceedingJoinPoint;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * BuryingDispatcher
 * Created by Qulit on 2018/3/19.
 */
public class BuryingDispatcher {

    private static final String TAG = "BuryingDispatcher";

    private static final Map<String, Object> schemas = new LinkedHashMap<>();
    private static final Map<String, BuryingTransfer> assists = new LinkedHashMap<>();

    public static void dispatcher(final ProceedingJoinPoint joinPoint, Burying burying) {
        String assistName = ClazzRule.BURYING_PREFIX + burying.name() + ClazzRule.ASSIST_SUFFIX;
        String schemaName = ClazzRule.BURYING_PREFIX + burying.name() + ClazzRule.SCHEMA_SUFFIX;

        Log.d(TAG, "[assistName]->" + assistName);
        Log.d(TAG, "[schemaName]->" + schemaName);

        try {
            BuryingTransfer transfer = assists.get(assistName);
            if (transfer == null) {
                Class<?> xClass = Class.forName(ClazzRule.BURYING_PACKAGE_NAME + "." + assistName);
                transfer = (BuryingTransfer) xClass.newInstance();
                assists.put(assistName, transfer);
            }

            Object schema = schemas.get(schemaName);

            if (schema == null) {
                schema = burying.clazz().newInstance();
                schemas.put(schemaName, schema);
            }

            String invokeMethod = MethodMatchUtils.methodNameMaker(joinPoint);
            Log.d(TAG, "[invokeMethod]->" + invokeMethod);

            if (transfer != null && schema != null) {
                transfer.process(joinPoint.getTarget(), invokeMethod, joinPoint.getArgs(), schema);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
