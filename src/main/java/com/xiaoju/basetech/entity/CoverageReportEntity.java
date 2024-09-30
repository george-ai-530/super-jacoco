package com.xiaoju.basetech.entity;

import lombok.Data;

import java.util.Date;

/**
 * @description:
 * @author: gaoweiwei_v
 * @time: 2019/7/29 2:29 PM
 */

@Data
public class CoverageReportEntity {

    private Integer id;
    private String uuid;
    private String gitUrl;
    private String baseVersion;
    private String nowVersion;
    /**
     * 1=全量覆盖率 2=增量覆盖率
     */
    private Integer type;
    private Integer requestStatus;
    private String diffMethod = "";
    private String errMsg = "";
    private String reportUrl = "";
    private Double lineCoverage = (double) -1;
    private Double branchCoverage = (double) -1;
    private Date createTime;
    private Date updateTime;
    private String nowLocalPath = "";
    private String baseLocalPath = "";
    private String subModule = "";
    private String codePath;
    private String envType = "";
    private String reportFile;
    private Integer from;
    private String logFile = "";

    public String getJacocoSourcePath()
    {
        String originalPath = nowLocalPath;
        int lastSlashIndex = originalPath.lastIndexOf('/');

        // 拼接新的路径
        return originalPath.substring(0, lastSlashIndex + 1) + "jacocoSource";
    }

    public String getSourceCodePath() {
        String projectName = extractProjectName(gitUrl);
        String env = extractVersion(uuid);
        return "/data/compiled/" + env + "/" + projectName;

    }

    public static String extractVersion(String input) {
        // 定义版本前缀列表
        String[] versionPrefixes = {"beta", "dev"};

        // 遍历所有版本前缀
        for (String prefix : versionPrefixes) {
            // 查找版本前缀的位置
            int prefixIndex = input.lastIndexOf(prefix);

            // 如果找到了前缀
            if (prefixIndex != -1) {
                // 从版本前缀之后开始截取字符串
                String version = input.substring(prefixIndex );
                return version;
            }
        }

        // 如果没有找到任何前缀，则返回空字符串
        return "";
    }

    public static String extractProjectName(String gitUrl) {
        // 找到最后一个斜杠的位置
        int lastSlashIndex = gitUrl.lastIndexOf('/');

        // 如果没有找到斜杠，则返回空字符串
        if (lastSlashIndex == -1) {
            return "";
        }

        // 从最后一个斜杠之后开始截取字符串
        String projectNameWithExtension = gitUrl.substring(lastSlashIndex + 1);

        // 去掉扩展名（如.git）
        int dotIndex = projectNameWithExtension.indexOf('.');
        if (dotIndex != -1) {
            return projectNameWithExtension.substring(0, dotIndex);
        }

        return projectNameWithExtension;
    }
    
}