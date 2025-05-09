package org.upyog.employee.dasboard.query.constant;

import org.springframework.stereotype.Component;

import lombok.Data;


public class DashboardQueryConstant {

	// Dashboard query constants
	public static StringBuilder DASHBOARD_QUERY_ALL = new StringBuilder("WITH tenant_data AS (\n"
			+ "    SELECT ? AS tenant_id\n"
			+ "),\n"
			+ "Today_Collection AS (\n"
			+ "    SELECT CAST(SUM(amountpaid) AS BIGINT) AS Today_Collection\n"
			+ "    FROM egcl_paymentdetail\n"
			+ "    WHERE createdtime >= EXTRACT(EPOCH FROM CURRENT_DATE) * 1000\n"
			+ "      AND createdtime < EXTRACT(EPOCH FROM (CURRENT_DATE + INTERVAL '1 day')) * 1000\n"
			+ "),\n"
			+ "Total_Collection AS (\n"
			+ "    SELECT CAST(SUM(amountpaid) AS BIGINT) AS Total_Collection\n"
			+ "    FROM egcl_paymentdetail\n"
			+ "    WHERE createdtime >= (\n"
			+ "        CASE\n"
			+ "            WHEN EXTRACT(MONTH FROM NOW()) >= 4\n"
			+ "            THEN EXTRACT(EPOCH FROM DATE_TRUNC('year', NOW()) + INTERVAL '3 months') * 1000\n"
			+ "            ELSE EXTRACT(EPOCH FROM DATE_TRUNC('year', NOW()) - INTERVAL '9 months') * 1000\n"
			+ "        END\n"
			+ "    )\n"
			+ "    AND createdtime < (\n"
			+ "        CASE\n"
			+ "            WHEN EXTRACT(MONTH FROM NOW()) >= 4\n"
			+ "            THEN EXTRACT(EPOCH FROM DATE_TRUNC('year', NOW()) + INTERVAL '15 months') * 1000\n"
			+ "            ELSE EXTRACT(EPOCH FROM DATE_TRUNC('year', NOW()) + INTERVAL '3 months') * 1000\n"
			+ "        END\n"
			+ "    )\n"
			+ "),\n"
			+ "Total_Applications_Received AS (\n"
			+ "    SELECT COUNT(DISTINCT businessid) AS Total_Applications_Received\n"
			+ "    FROM eg_wf_processinstance_v2, tenant_data\n"
			+ "    WHERE tenantid = tenant_data.tenant_id\n"
			+ "      AND createdtime >= (\n"
			+ "        CASE\n"
			+ "            WHEN EXTRACT(MONTH FROM NOW()) >= 4\n"
			+ "            THEN EXTRACT(EPOCH FROM DATE_TRUNC('year', NOW()) + INTERVAL '3 months') * 1000\n"
			+ "            ELSE EXTRACT(EPOCH FROM DATE_TRUNC('year', NOW()) - INTERVAL '9 months') * 1000\n"
			+ "        END\n"
			+ "    )\n"
			+ "    AND createdtime < (\n"
			+ "        CASE\n"
			+ "            WHEN EXTRACT(MONTH FROM NOW()) >= 4\n"
			+ "            THEN EXTRACT(EPOCH FROM DATE_TRUNC('year', NOW()) + INTERVAL '15 months') * 1000\n"
			+ "            ELSE EXTRACT(EPOCH FROM DATE_TRUNC('year', NOW()) + INTERVAL '3 months') * 1000\n"
			+ "        END\n"
			+ "    )\n"
			+ "),\n"
			+ "Total_Application_Approved AS (\n"
			+ "    SELECT COUNT(DISTINCT businessid) AS Total_Application_Approved\n"
			+ "    FROM eg_wf_processinstance_v2, tenant_data\n"
			+ "    WHERE tenantid = tenant_data.tenant_id\n"
			+ "      AND action = 'APPROVE'\n"
			+ "      AND createdtime >= (\n"
			+ "        CASE\n"
			+ "            WHEN EXTRACT(MONTH FROM NOW()) >= 4\n"
			+ "            THEN EXTRACT(EPOCH FROM DATE_TRUNC('year', NOW()) + INTERVAL '3 months') * 1000\n"
			+ "            ELSE EXTRACT(EPOCH FROM DATE_TRUNC('year', NOW()) - INTERVAL '9 months') * 1000\n"
			+ "        END\n"
			+ "    )\n"
			+ "    AND createdtime < (\n"
			+ "        CASE\n"
			+ "            WHEN EXTRACT(MONTH FROM NOW()) >= 4\n"
			+ "            THEN EXTRACT(EPOCH FROM DATE_TRUNC('year', NOW()) + INTERVAL '15 months') * 1000\n"
			+ "            ELSE EXTRACT(EPOCH FROM DATE_TRUNC('year', NOW()) + INTERVAL '3 months') * 1000\n"
			+ "        END\n"
			+ "    )\n"
			+ "),\n"
			+ "Total_Applications_Pending AS (\n"
			+ "    SELECT COUNT(DISTINCT businessid) AS Total_Applications_Pending\n"
			+ "    FROM eg_wf_processinstance_v2, tenant_data\n"
			+ "    WHERE tenantid = tenant_data.tenant_id\n"
			+ "      AND businessid NOT IN (\n"
			+ "        SELECT DISTINCT businessid\n"
			+ "        FROM eg_wf_processinstance_v2\n"
			+ "        WHERE action IN ('ACTIVATE_CONNECTION', 'APPROVE', 'REJECT', 'CANCEL')\n"
			+ "      )\n"
			+ "      AND createdtime >= (\n"
			+ "        CASE\n"
			+ "            WHEN EXTRACT(MONTH FROM NOW()) >= 4\n"
			+ "            THEN EXTRACT(EPOCH FROM DATE_TRUNC('year', NOW()) + INTERVAL '3 months') * 1000\n"
			+ "            ELSE EXTRACT(EPOCH FROM DATE_TRUNC('year', NOW()) - INTERVAL '9 months') * 1000\n"
			+ "        END\n"
			+ "      )\n"
			+ "      AND createdtime < (\n"
			+ "        CASE\n"
			+ "            WHEN EXTRACT(MONTH FROM NOW()) >= 4\n"
			+ "            THEN EXTRACT(EPOCH FROM DATE_TRUNC('year', NOW()) + INTERVAL '15 months') * 1000\n"
			+ "            ELSE EXTRACT(EPOCH FROM DATE_TRUNC('year', NOW()) + INTERVAL '3 months') * 1000\n"
			+ "        END\n"
			+ "      )\n"
			+ ")\n"
			+ "SELECT \n"
			+ "    CAST(tc.Today_Collection AS BIGINT) AS Today_Collection,\n"
			+ "    CAST(tcc.Total_Collection AS BIGINT) AS Total_Collection,\n"
			+ "    tar.Total_Applications_Received,\n"
			+ "    taa.Total_Application_Approved,\n"
			+ "    tap.Total_Applications_Pending\n"
			+ "FROM \n"
			+ "    Today_Collection tc,\n"
			+ "    Total_Collection tcc,\n"
			+ "    Total_Applications_Received tar,\n"
			+ "    Total_Application_Approved taa,\n"
			+ "    Total_Applications_Pending tap;");

	public static StringBuilder OBPAS_DASHBOARD_QUERY_ = new StringBuilder("SELECT \n"
			+ "    (SELECT COUNT(DISTINCT applicationno) \n"
			+ "     FROM eg_bpa_buildingplan \n"
			+ "     WHERE businessservice = 'BPA'\n"
			+ "     AND createdtime >= EXTRACT(EPOCH FROM DATE_TRUNC('year', CURRENT_DATE) + INTERVAL '3 months') * 1000  \n"
			+ "     AND createdtime < EXTRACT(EPOCH FROM DATE_TRUNC('year', CURRENT_DATE) + INTERVAL '1 year') * 1000) AS Total_Applications_Received,\n"
			+ "     \n"
			+ "    (SELECT COUNT(DISTINCT applicationno) \n"
			+ "     FROM eg_bpa_buildingplan \n"
			+ "     WHERE businessservice = 'BPA' \n"
			+ "     AND status = 'APPROVED'\n"
			+ "     AND createdtime >= EXTRACT(EPOCH FROM DATE_TRUNC('year', CURRENT_DATE) + INTERVAL '3 months') * 1000  \n"
			+ "     AND createdtime < EXTRACT(EPOCH FROM DATE_TRUNC('year', CURRENT_DATE) + INTERVAL '1 year') * 1000) AS Total_Applications_Approved,\n"
			+ "     \n"
			+ "    (SELECT COUNT(DISTINCT applicationno) \n"
			+ "     FROM eg_bpa_buildingplan \n"
			+ "     WHERE businessservice = 'BPA'\n"
			+ "     AND createdtime >= EXTRACT(EPOCH FROM DATE_TRUNC('year', CURRENT_DATE) + INTERVAL '3 months') * 1000  \n"
			+ "     AND createdtime < EXTRACT(EPOCH FROM DATE_TRUNC('year', CURRENT_DATE) + INTERVAL '1 year') * 1000) - \n"
			+ "    (SELECT COUNT(DISTINCT applicationno) \n"
			+ "     FROM eg_bpa_buildingplan \n"
			+ "     WHERE businessservice = 'BPA' \n"
			+ "     AND status = 'APPROVED'\n"
			+ "     AND createdtime >= EXTRACT(EPOCH FROM DATE_TRUNC('year', CURRENT_DATE) + INTERVAL '3 months') * 1000  \n"
			+ "     AND createdtime < EXTRACT(EPOCH FROM DATE_TRUNC('year', CURRENT_DATE) + INTERVAL '1 year') * 1000) AS Total_Applications_Pending,\n"
			+ "     \n"
			+ "    (SELECT SUM(o.amountpaid) \n"
			+ "     FROM egcl_bill b \n"
			+ "     JOIN egcl_paymentdetail o ON o.billid = b.id \n"
			+ "     WHERE o.businessservice IN (\n"
			+ "         'BPA.NC_OC_APP_FEE', \n"
			+ "         'BPA.NC_SAN_FEE', \n"
			+ "         'BPA.NC_APP_FEE', \n"
			+ "         'BPA.NC_OC_SAN_FEE', \n"
			+ "         'BPA.LOW_RISK_PERMIT_FEE', \n"
			+ "         'BPA.REG'\n"
			+ "     )\n"
			+ "     AND b.createdtime >= EXTRACT(EPOCH FROM DATE_TRUNC('year', CURRENT_DATE) + INTERVAL '3 months') * 1000  \n"
			+ "     AND b.createdtime < EXTRACT(EPOCH FROM DATE_TRUNC('year', CURRENT_DATE) + INTERVAL '1 year') * 1000 \n"
			+ "     AND b.tenantid = ?) AS Total_Amount ");

	public static StringBuilder ASSET_DASHBOARD_QUERY_ = new StringBuilder("SELECT \n"
			+ "    COUNT(DISTINCT pi.businessid) AS Total_Applications_Received,\n"
			+ "    SUM(CASE WHEN pi.action = 'APPROVE' THEN 1 ELSE 0 END) AS Total_Applications_Approved,\n"
			+ "    COUNT(DISTINCT CASE \n"
			+ "        WHEN pi.action IN ('SUBMIT', 'SENDBACKTOEMPLOYEE', 'INITIATE', 'VERIFY', 'REJECT') \n"
			+ "        AND pi.businessid NOT IN (\n"
			+ "            SELECT businessid \n"
			+ "            FROM eg_wf_processinstance_v2 \n"
			+ "            WHERE action = 'APPROVE'\n"
			+ "        ) THEN pi.businessid \n"
			+ "    END) AS Total_Applications_Pending,\n"
			+ "    (SELECT SUM(o.amountpaid) \n"
			+ "     FROM egcl_bill b \n"
			+ "     JOIN egcl_paymentdetail o ON o.billid = b.id \n"
			+ "     WHERE o.businessservice IN ('asset-create')\n"
			+ "     AND b.createdtime >= EXTRACT(EPOCH FROM DATE_TRUNC('year', CURRENT_DATE) + INTERVAL '3 months') * 1000  \n"
			+ "     AND b.createdtime < EXTRACT(EPOCH FROM DATE_TRUNC('year', CURRENT_DATE) + INTERVAL '1 year') * 1000  \n"
			+ "    ) AS Total_Amount \n"
			+ "FROM \n"
			+ "    eg_wf_processinstance_v2 pi \n"
			+ "WHERE \n"
			+ "    pi.businessservice = 'asset-create'\n"
			+ "    AND pi.createdtime >= EXTRACT(EPOCH FROM DATE_TRUNC('year', CURRENT_DATE) + INTERVAL '3 months') * 1000  \n"
			+ "    AND pi.createdtime < EXTRACT(EPOCH FROM DATE_TRUNC('year', CURRENT_DATE) + INTERVAL '1 year') * 1000 \n"
	        + "     AND pi.tenantid = ?");


	public static StringBuilder FSM_DASHBOARD_QUERY_ = new StringBuilder("SELECT \n"
			+ "    COUNT(DISTINCT pi.businessid) AS Total_Applications_Received,\n"
			+ "    SUM(CASE WHEN pi.action = 'COMPLETED' THEN 1 ELSE 0 END) AS Total_Applications_Approved,\n"
			+ "    COUNT(DISTINCT CASE \n"
			+ "        WHEN pi.action IN ('DECLINE', 'CANCEL', 'SENDBACK', 'SUBMIT_FEEDBACK', 'SUBMIT', 'DSO_ACCEPT', 'ASSIGN', 'APPLY', 'CREATE', 'REJECT', 'REASSIGN', 'PAY', 'DSO_REJECT', 'RATE') \n"
			+ "        AND pi.businessid NOT IN (\n"
			+ "            SELECT businessid \n"
			+ "            FROM eg_wf_processinstance_v2 \n"
			+ "            WHERE action = 'COMPLETED'\n"
			+ "        ) THEN pi.businessid \n"
			+ "    END) AS Total_Applications_Pending,\n"
			+ "    (SELECT SUM(o.amountpaid) \n"
			+ "     FROM egcl_bill b \n"
			+ "     JOIN egcl_paymentdetail o ON o.billid = b.id \n"
			+ "     WHERE o.businessservice IN ('FSM')\n"
			+ "     AND b.createdtime >= EXTRACT(EPOCH FROM DATE_TRUNC('year', CURRENT_DATE) + INTERVAL '3 months') * 1000  \n"
			+ "     AND b.createdtime < EXTRACT(EPOCH FROM DATE_TRUNC('year', CURRENT_DATE) + INTERVAL '1 year') * 1000  \n"
			+ "    ) AS Total_Amount \n"
			+ "FROM \n"
			+ "    eg_wf_processinstance_v2 pi \n"
			+ "WHERE \n"
			+ "    pi.businessservice = 'FSM'\n"
			+ "    AND pi.createdtime >= EXTRACT(EPOCH FROM DATE_TRUNC('year', CURRENT_DATE) + INTERVAL '3 months') * 1000  \n"
			+ "    AND pi.createdtime < EXTRACT(EPOCH FROM DATE_TRUNC('year', CURRENT_DATE) + INTERVAL '1 year') * 1000 \n"
			+ "    AND pi.tenantid = ?");


	public static StringBuilder PGR_DASHBOARD_QUERY_ = new StringBuilder("SELECT \n"
			+ "    COUNT(DISTINCT pi.businessid) AS Total_Applications_Received,\n"
			+ "    SUM(CASE WHEN pi.action = 'RESOLVE' THEN 1 ELSE 0 END) AS Total_Applications_Approved,\n"
			+ "    COUNT(DISTINCT CASE \n"
			+ "        WHEN pi.action IN ('CANCEL', 'COMMENT', 'ASSIGN', 'EDIT', 'APPLY', 'ASSIGNEDBYAUTOESCALATION', 'REJECT', 'REASSIGN', 'REOPEN', 'RATE', 'FORWARD') \n"
			+ "        AND pi.businessid NOT IN (\n" + "            SELECT businessid \n"
			+ "            FROM eg_wf_processinstance_v2 \n" + "            WHERE action = 'RESOLVE'\n"
			+ "        ) THEN pi.businessid \n" + "    END) AS Total_Applications_Pending,\n"
			+ "    (SELECT SUM(o.amountpaid) \n" + "     FROM egcl_bill b \n"
			+ "     JOIN egcl_paymentdetail o ON o.billid = b.id \n" + "     WHERE o.businessservice IN ('PGR')\n"
			+ "     AND b.createdtime >= EXTRACT(EPOCH FROM DATE_TRUNC('year', CURRENT_DATE) + INTERVAL '3 months') * 1000  \n"
			+ "     AND b.createdtime < EXTRACT(EPOCH FROM DATE_TRUNC('year', CURRENT_DATE) + INTERVAL '1 year') * 1000  \n"
			+ "    ) AS Total_Amount \n" + "FROM \n" + "    eg_wf_processinstance_v2 pi \n" + "WHERE \n"
			+ "    pi.businessservice = 'PGR'\n"
			+ "    AND pi.createdtime >= EXTRACT(EPOCH FROM DATE_TRUNC('year', CURRENT_DATE) + INTERVAL '3 months') * 1000  \n"
			+ "    AND pi.createdtime < EXTRACT(EPOCH FROM DATE_TRUNC('year', CURRENT_DATE) + INTERVAL '1 year') * 1000 \n"
			+ "     AND pi.tenantid = ?");


	public static StringBuilder CHB_DASHBOARD_QUERY_ = new StringBuilder("SELECT\n"
			+ "    COUNT(DISTINCT wf.booking_no) AS Total_Applications_Received,\n"
			+ "    SUM(CASE WHEN wf.booking_status = 'BOOKED' THEN 1 ELSE 0 END) AS Total_Applications_Approved,\n"
			+ "    COUNT(DISTINCT wf.booking_no) - SUM(CASE WHEN wf.booking_status = 'BOOKED' THEN 1 ELSE 0 END) AS Total_Applications_Pending,\n"
			+ "    (SELECT SUM(o.amountpaid)\n"
			+ "     FROM egcl_paymentdetail o\n"
			+ "     JOIN eg_chb_booking_detail wf_approved\n"
			+ "       ON o.receiptnumber = wf_approved.receipt_no\n"
			+ "     WHERE wf_approved.booking_status = 'BOOKED'\n"
			+ "       AND o.businessservice IN ('chb-services')\n"
			+ "       AND wf_approved.createdtime >= EXTRACT(EPOCH FROM (DATE_TRUNC('year', CURRENT_DATE) + INTERVAL '9 months')) * 1000\n"
			+ "       AND wf_approved.createdtime < EXTRACT(EPOCH FROM (DATE_TRUNC('year', CURRENT_DATE) + INTERVAL '1 year' + INTERVAL '9 months')) * 1000\n"
			+ "    ) AS Total_Amount\n"
			+ "FROM\n"
			+ "    eg_chb_booking_detail wf\n"
			+ "WHERE\n"
			+ "    wf.createdtime >= EXTRACT(EPOCH FROM (DATE_TRUNC('year', CURRENT_DATE) + INTERVAL '9 months')) * 1000\n"
			+ "    AND wf.createdtime < EXTRACT(EPOCH FROM (DATE_TRUNC('year', CURRENT_DATE) + INTERVAL '1 year' + INTERVAL '9 months')) * 1000 \n "
			+ "     AND wf.tenant_id = ?");

	

	public static StringBuilder PT_DASHBOARD_QUERY_ = new StringBuilder(
			"SELECT \n"
			+ "    COUNT(DISTINCT wf.propertyid) AS Total_Applications_Received,\n"
			+ "    SUM(CASE WHEN wf.status = 'ACTIVE' THEN 1 ELSE 0 END) AS Total_Applications_Approved,\n"
			+ "    COUNT(DISTINCT CASE WHEN wf.status = 'INWORKFLOW' THEN wf.propertyid END) AS Total_Applications_Pending,\n"
			+ "    (SELECT SUM(o.amountpaid) \n"
			+ "     FROM egcl_bill b \n"
			+ "     JOIN egcl_paymentdetail o ON o.billid = b.id \n"
			+ "     WHERE o.businessservice = 'PT'\n"
			+ "     AND b.createdtime >= EXTRACT(EPOCH FROM DATE_TRUNC('year', CURRENT_DATE) + INTERVAL '3 months') * 1000  \n"
			+ "     AND b.createdtime < EXTRACT(EPOCH FROM DATE_TRUNC('year', CURRENT_DATE) + INTERVAL '1 year') * 1000  \n"
			+ "    ) AS Total_Amount \n"
			+ "FROM \n"
			+ "    eg_pt_property wf\n"
			+ "WHERE \n"
			+ "    wf.createdtime >= EXTRACT(EPOCH FROM DATE_TRUNC('year', CURRENT_DATE) + INTERVAL '3 months') * 1000  \n"
			+ "    AND wf.createdtime < EXTRACT(EPOCH FROM DATE_TRUNC('year', CURRENT_DATE) + INTERVAL '1 year') * 1000 \n"
			+ "     AND wf.tenantid = ?");


	public static StringBuilder PETSERVICES_DASHBOARD_QUERY_ = new StringBuilder(
			"SELECT \n"
			+ "    COUNT(DISTINCT pi.businessid) AS Total_Applications_Received,\n"
			+ "    SUM(CASE WHEN pi.action = 'APPROVE' THEN 1 ELSE 0 END) AS Total_Applications_Approved,\n"
			+ "    COUNT(DISTINCT CASE \n"
			+ "        WHEN pi.action IN ('APPLY', 'VERIFY', 'PAY') \n"
			+ "        AND pi.businessid NOT IN (\n"
			+ "            SELECT businessid \n"
			+ "            FROM eg_wf_processinstance_v2 \n"
			+ "            WHERE action = 'APPROVE'\n"
			+ "        ) THEN pi.businessid \n"
			+ "    END) AS Total_Applications_Pending,\n"
			+ "    (SELECT SUM(o.amountpaid) \n"
			+ "     FROM egcl_bill b \n"
			+ "     JOIN egcl_paymentdetail o ON o.billid = b.id \n"
			+ "     WHERE o.businessservice IN ('pet-services')\n"
			+ "     AND b.createdtime >= EXTRACT(EPOCH FROM DATE_TRUNC('year', CURRENT_DATE) + INTERVAL '3 months') * 1000  \n"
			+ "     AND b.createdtime < EXTRACT(EPOCH FROM DATE_TRUNC('year', CURRENT_DATE) + INTERVAL '1 year') * 1000  \n"
			+ "    ) AS Total_Amount \n"
			+ "FROM \n"
			+ "    eg_wf_processinstance_v2 pi \n"
			+ "WHERE \n"
			+ "    pi.businessservice = 'ptr'\n"
			+ "    AND pi.createdtime >= EXTRACT(EPOCH FROM DATE_TRUNC('year', CURRENT_DATE) + INTERVAL '3 months') * 1000  \n"
			+ "    AND pi.createdtime < EXTRACT(EPOCH FROM DATE_TRUNC('year', CURRENT_DATE) + INTERVAL '1 year') * 1000 \n"
			+ "     AND pi.tenantid = ?");


	public static StringBuilder EWASTE_DASHBOARD_QUERY_ = new StringBuilder(
			"SELECT \n"
			+ "    COUNT(DISTINCT pi.businessid) AS Total_Applications_Received,\n"
			+ "    SUM(CASE WHEN pi.action = 'COMPLETEREQUEST' THEN 1 ELSE 0 END) AS Total_Applications_Approved,\n"
			+ "    COUNT(DISTINCT CASE \n"
			+ "        WHEN pi.action IN ('SENDPICKUPALERT', 'CREATE', 'VERIFYPRODUCT') \n"
			+ "        AND pi.businessid NOT IN (\n"
			+ "            SELECT businessid \n"
			+ "            FROM eg_wf_processinstance_v2 \n"
			+ "            WHERE action = 'COMPLETEREQUEST'\n"
			+ "        ) THEN pi.businessid \n"
			+ "    END) AS Total_Applications_Pending,\n"
			+ "    (SELECT SUM(o.amountpaid) \n"
			+ "     FROM egcl_bill b \n"
			+ "     JOIN egcl_paymentdetail o ON o.billid = b.id \n"
			+ "     WHERE o.businessservice IN ('ewst')\n"
			+ "     AND b.createdtime >= EXTRACT(EPOCH FROM DATE_TRUNC('year', CURRENT_DATE) + INTERVAL '3 months') * 1000  \n"
			+ "     AND b.createdtime < EXTRACT(EPOCH FROM DATE_TRUNC('year', CURRENT_DATE) + INTERVAL '1 year') * 1000  \n"
			+ "    ) AS Total_Amount \n"
			+ "FROM \n"
			+ "    eg_wf_processinstance_v2 pi \n"
			+ "WHERE \n"
			+ "    pi.businessservice = 'ewst'\n"
			+ "    AND pi.createdtime >= EXTRACT(EPOCH FROM DATE_TRUNC('year', CURRENT_DATE) + INTERVAL '3 months') * 1000  \n"
			+ "    AND pi.createdtime < EXTRACT(EPOCH FROM DATE_TRUNC('year', CURRENT_DATE) + INTERVAL '1 year') * 1000 \n"
			+ "     AND pi.tenantid = ?");


	public static StringBuilder TL_DASHBOARD_QUERY_ = new StringBuilder(
			"SELECT \n"
			+ "    COUNT(DISTINCT wf.applicationnumber) AS Total_Application_Received,\n"
			+ "    SUM(CASE WHEN wf.status = 'APPROVED' THEN 1 ELSE 0 END) AS Total_Applications_Approved,\n"
			+ "    COUNT(DISTINCT CASE \n"
			+ "        WHEN wf.action IN ('SENDBACKTOCITIZEN', 'APPLY', 'INITIATE', 'FORWARD', 'NOWORKFLOW', 'RESUBMIT', 'SENDBACK') \n"
			+ "        THEN wf.applicationnumber \n"
			+ "    END) AS Total_Applications_Pending,\n"
			+ "    (SELECT SUM(o.amountpaid) \n"
			+ "     FROM egcl_bill b \n"
			+ "     JOIN egcl_paymentdetail o ON o.billid = b.id \n"
			+ "     WHERE o.businessservice = 'TL'\n"
			+ "     AND b.createdtime >= EXTRACT(EPOCH FROM DATE_TRUNC('year', CURRENT_DATE) + INTERVAL '3 months') * 1000  \n"
			+ "     AND b.createdtime < EXTRACT(EPOCH FROM DATE_TRUNC('year', CURRENT_DATE) + INTERVAL '1 year') * 1000  \n"
			+ "    ) AS Total_Amount \n"
			+ "FROM \n"
			+ "    eg_tl_tradelicense wf \n"
			+ "WHERE \n"
			+ "    wf.createdtime >= EXTRACT(EPOCH FROM DATE_TRUNC('year', CURRENT_DATE) + INTERVAL '3 months') * 1000  \n"
			+ "    AND wf.createdtime < EXTRACT(EPOCH FROM DATE_TRUNC('year', CURRENT_DATE) + INTERVAL '1 year') * 1000 \n"
			+ "     AND wf.tenantid = ?");


	public static StringBuilder WATER_DASHBOARD_QUERY_ = new StringBuilder(
			"\n"
			+ "SELECT \n"
			+ "    COUNT(DISTINCT pi.businessid) AS Total_Applications_Received,\n"
			+ "    SUM(CASE WHEN pi.action = 'ACTIVATE_CONNECTION' THEN 1 ELSE 0 END) AS Total_Applications_Approved,\n"
			+ "    COUNT(DISTINCT CASE \n"
			+ "        WHEN pi.action IN ('SEND_BACK_TO_CITIZEN', 'SEND_BACK_FOR_DOCUMENT_VERIFICATION', 'SUBMIT_APPLICATION', 'APPROVE_FOR_CONNECTION', 'SEND_BACK_FOR_FIELD_INSPECTION', 'VERIFY_AND_FORWARD', 'INITIATE', 'PAY', 'RESUBMIT_APPLICATION') \n"
			+ "        AND pi.businessid NOT IN (\n"
			+ "            SELECT businessid \n"
			+ "            FROM eg_wf_processinstance_v2 \n"
			+ "            WHERE action = 'ACTIVATE_CONNECTION'\n"
			+ "        ) THEN pi.businessid \n"
			+ "    END) AS Total_Applications_Pending,\n"
			+ "    (SELECT SUM(o.amountpaid) \n"
			+ "     FROM egcl_bill b \n"
			+ "     JOIN egcl_paymentdetail o ON o.billid = b.id \n"
			+ "     WHERE o.businessservice IN ('WS', 'WS.ONE_TIME_FEE')\n"
			+ "     AND b.createdtime >= EXTRACT(EPOCH FROM DATE_TRUNC('year', CURRENT_DATE) + INTERVAL '3 months') * 1000  \n"
			+ "     AND b.createdtime < EXTRACT(EPOCH FROM DATE_TRUNC('year', CURRENT_DATE) + INTERVAL '1 year') * 1000  \n"
			+ "    ) AS Total_Amount \n"
			+ "FROM \n"
			+ "    eg_wf_processinstance_v2 pi \n"
			+ "WHERE \n"
			+ "    pi.businessservice = 'NewWS1'\n"
			+ "    AND pi.createdtime >= EXTRACT(EPOCH FROM DATE_TRUNC('year', CURRENT_DATE) + INTERVAL '3 months') * 1000  \n"
			+ "    AND pi.createdtime < EXTRACT(EPOCH FROM DATE_TRUNC('year', CURRENT_DATE) + INTERVAL '1 year') * 1000\n"
			+ "     AND pi.tenantid = ?");

	public static StringBuilder SEWERAGE_DASHBOARD_QUERY_ = new StringBuilder("SELECT \n"
			+ "    COUNT(DISTINCT pi.businessid) AS Total_Applications_Received,\n"
			+ "    SUM(CASE WHEN pi.action = 'ACTIVATE_CONNECTION' THEN 1 ELSE 0 END) AS Total_Applications_Approved,\n"
			+ "    COUNT(DISTINCT CASE \n"
			+ "        WHEN pi.action IN ('SEND_BACK_TO_CITIZEN', 'SEND_BACK_FOR_DOCUMENT_VERIFICATION', 'SUBMIT_APPLICATION', 'APPROVE_FOR_CONNECTION', 'SEND_BACK_FOR_FIELD_INSPECTION', 'VERIFY_AND_FORWARD', 'INITIATE', 'PAY', 'RESUBMIT_APPLICATION') \n"
			+ "        AND pi.businessid NOT IN (\n"
			+ "            SELECT businessid \n"
			+ "            FROM eg_wf_processinstance_v2 \n"
			+ "            WHERE action = 'ACTIVATE_CONNECTION'\n"
			+ "        ) THEN pi.businessid \n"
			+ "    END) AS Total_Applications_Pending,\n"
			+ "    (SELECT SUM(o.amountpaid) \n"
			+ "     FROM egcl_bill b \n"
			+ "     JOIN egcl_paymentdetail o ON o.billid = b.id \n"
			+ "     WHERE o.businessservice IN ('SW', 'SW.ONE_TIME_FEE')\n"
			+ "     AND b.createdtime >= EXTRACT(EPOCH FROM DATE_TRUNC('year', CURRENT_DATE) + INTERVAL '3 months') * 1000  \n"
			+ "     AND b.createdtime < EXTRACT(EPOCH FROM DATE_TRUNC('year', CURRENT_DATE) + INTERVAL '1 year') * 1000  \n"
			+ "    ) AS Total_Amount \n"
			+ "FROM \n"
			+ "    eg_wf_processinstance_v2 pi \n"
			+ "WHERE \n"
			+ "    pi.businessservice = 'NewSW1'\n"
			+ "    AND pi.createdtime >= EXTRACT(EPOCH FROM DATE_TRUNC('year', CURRENT_DATE) + INTERVAL '3 months') * 1000  \n"
			+ "    AND pi.createdtime < EXTRACT(EPOCH FROM DATE_TRUNC('year', CURRENT_DATE) + INTERVAL '1 year') * 1000 \n"
			+ "     AND pi.tenantid = ?");

	
	
	

}
