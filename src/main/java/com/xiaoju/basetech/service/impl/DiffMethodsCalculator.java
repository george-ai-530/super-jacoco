package com.xiaoju.basetech.service.impl;

import com.xiaoju.basetech.entity.CoverageReportEntity;
import com.xiaoju.basetech.util.JDiffFiles;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class DiffMethodsCalculator {

    /**
     * 下载代码并计算diff方法
     *
     * @param coverageReport 覆盖率报告实体
     */
    public String executeDiffMethods(CoverageReportEntity coverageReport) {
        StringBuilder diffFile = new StringBuilder();
        long s = System.currentTimeMillis();
        String result = "";
        HashMap<String, String> map = JDiffFiles.diffMethodsListNew(coverageReport);
        if (!CollectionUtils.isEmpty(map)) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                diffFile.append(entry.getKey()).append(":").append(entry.getValue()).append("%");
            }
            result = diffFile.toString();
            coverageReport.setDiffMethod(result);
        }

        log.info("uuid={} 增量计算耗时：{}", coverageReport.getUuid(), (System.currentTimeMillis() - s));
        return result;
    }

    public String executeDiffMethodsForEnv(String baseVersionPath, String nowVersionPath, String baseVersion, String nowVersion) {
        if (baseVersionPath.equals(nowVersionPath)) {
            return null;
        }
        StringBuilder diffFile = new StringBuilder();
        long ms = System.currentTimeMillis();
        HashMap<String, String> map = JDiffFiles.diffMethodsListForEnv(baseVersionPath, nowVersionPath, baseVersion, nowVersion);
        if (map != null && !map.isEmpty()) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                diffFile.append(entry.getKey()).append(":").append(entry.getValue()).append("%");
            }
            return diffFile.toString();
        }

        log.info("增量计算耗时：{}", (System.currentTimeMillis() - ms));
        return null;
    }
}
