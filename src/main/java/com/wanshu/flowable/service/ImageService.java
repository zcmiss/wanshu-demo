package com.wanshu.flowable.service;

public interface ImageService {
    /**
     * 根据流程实例标识，生成流程跟踪图示（高亮）
     * 生成的图片是一个流程跟踪图示，这个图示是一个高亮的流程跟踪图示
     *
     * @param procInstId 流程实例标识 procInstId
     * @return 流程跟踪图示
     * @throws Exception 异常
     */
    byte[] generateImageByProcInstId(String procInstId) throws Exception;
}
