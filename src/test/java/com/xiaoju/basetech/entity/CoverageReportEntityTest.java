package com.xiaoju.basetech.entity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.mockito.Mockito.*;

public class CoverageReportEntityTest {
    @Mock
    Date createTime;
    @Mock
    Date updateTime;
    @InjectMocks
    CoverageReportEntity coverageReportEntity;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetJacocoSourcePath() throws Exception {
        String result = coverageReportEntity.getJacocoSourcePath();
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testGetSourceCodePath() throws Exception {
        String result = coverageReportEntity.getSourceCodePath();
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testExtractVersion() throws Exception {
        String result = CoverageReportEntity.extractVersion("erp_baseinfo_origin_fzz_beta");
        Assert.assertEquals("beta", result);
    }

    @Test
    public void testExtractProjectName() throws Exception {
        String result = CoverageReportEntity.extractProjectName("gitUrl");
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme