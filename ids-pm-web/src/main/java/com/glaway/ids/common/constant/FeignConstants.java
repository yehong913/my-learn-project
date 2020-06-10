package com.glaway.ids.common.constant;

/**
 * 
 * @author lky
 *
 */
public class FeignConstants {
    /** pm-service */
    public static final String ID_PM_SERVICE = "${server.runtime.pmservice}";

    /** common-service */
    public static final String ID_COMMON_SERVICE = "${server.runtime.commonservice}";

    /** ID_RDFLOW_WEB */
    public static final String ID_RDFLOW_WEB = "${server.runtime.rdflowweb}";

    /** rdflow-service */
    public static final String ID_RDFLOW_SERVICE = "${server.runtime.rdflowservice}";

    /** plm-service */
    public static final String ID_PLM_SERVICE = "${server.runtime.plmservice}";

    /** review-service */
    public static final String IDS_REVIEW_SERVICE = "${server.runtime.reviewservice}";

    /** klm-service */
    public static final String KES_KLM_SERVICE = "${server.runtime.klmservice}";

    /** pm-service */
    public static final String IDS_PM_FEIGN_SERVICE = "${server.pm.service.feign.url}";

    /** common-service */
    public static final String IDS_COMMON_FEIGN_SERVICE = "${server.common.service.feign.url}";

    /** rdflow-service */
    public static final String IDS_RDFLOW_FEIGN_SERVICE = "${server.rdflow.service.feign.url}";

    /** review-service */
    public static final String IDS_REVIEW_FEIGN_SERVICE = "${server.review.service.feign.url}";

    /** klm-service */
    public static final String KES_KLM_FEIGN_SERVICE = "${server.klm.service.feign.url}";
}
