package applango.common.services.beans;

import applango.common.SeleniumTestBase;
import org.openqa.selenium.WebDriver;

import java.util.Calendar;

public class SalesforceCustomObject {
    String customObjectLabel;
    String customObjectPluralLabel;
    String objectName;
    String recordName;
    String objectId;

    String trigger;

    boolean allowReport;
    boolean allowActivities;
    boolean trackFieldHistory;

    boolean customObjectDeployed;

    public SalesforceCustomObject() {
    }

    public String getTrigger() {
        return trigger;
    }

    public void setTrigger(String trigger) {
        this.trigger = trigger;
    }


    public SalesforceCustomObject(String customObjectLabel, String customObjectPluralLabel, String objectName, String recordName) {
        this.customObjectLabel = customObjectLabel;
        this.customObjectPluralLabel = customObjectPluralLabel;
        this.objectName = objectName;
        this.recordName = recordName;
        this.allowReport = true;
        this.allowActivities = true;
        this.trackFieldHistory = true;
        this.customObjectDeployed = true;
        this.trigger = "";

    }
    public SalesforceCustomObject(String customObjectLabel) {
        this.customObjectLabel = customObjectLabel;
        this.customObjectPluralLabel = customObjectLabel;
        this.objectName = customObjectLabel;
        this.recordName = customObjectLabel+ " Name";
        this.allowReport = true;
        this.allowActivities = true;
        this.trackFieldHistory = true;
        this.customObjectDeployed = true;
        this.trigger = "";

    }

    public static SalesforceCustomObject setCustomObjectValues(WebDriver driver, SalesforceCustomObject sfCustomObject) {

        CharSequence sObjectName = "CustomObject"+ Calendar.getInstance().getTimeInMillis();
        SeleniumTestBase.logger.info("Set customObject " + sObjectName);

        sfCustomObject.setCustomObjectLabel(sObjectName.toString());
        sfCustomObject.setCustomObjectPluralLabel(sObjectName.toString());
        sfCustomObject.setObjectName(sObjectName.toString());
        sfCustomObject.setRecordName(sObjectName.toString()+ " Name");
        sfCustomObject.setAllowReport(true);
        sfCustomObject.setAllowActivities(true);
        sfCustomObject.setTrackFieldHistory(true);
        sfCustomObject.setCustomObjectDeployed(true);
        return sfCustomObject;
    }

    public String getCustomObjectLabel() {
        return customObjectLabel;
    }

    public void setCustomObjectLabel(String customObjectLabel) {
        this.customObjectLabel = customObjectLabel;
    }

    public String getCustomObjectPluralLabel() {
        return customObjectPluralLabel;
    }

    public void setCustomObjectPluralLabel(String customObjectPluralLabel) {
        this.customObjectPluralLabel = customObjectPluralLabel;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getRecordName() {
        return recordName;
    }

    public void setRecordName(String recordName) {
        this.recordName = recordName;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public boolean isAllowReport() {
        return allowReport;
    }

    public void setAllowReport(boolean allowReport) {
        this.allowReport = allowReport;
    }

    public boolean isAllowActivities() {
        return allowActivities;
    }

    public void setAllowActivities(boolean allowActivities) {
        this.allowActivities = allowActivities;
    }

    public boolean isTrackFieldHistory() {
        return trackFieldHistory;
    }

    public void setTrackFieldHistory(boolean trackFieldHistory) {
        this.trackFieldHistory = trackFieldHistory;
    }

    public boolean isCustomObjectDeployed() {
        return customObjectDeployed;
    }

    public void setCustomObjectDeployed(boolean customObjectDeployed) {
        this.customObjectDeployed = customObjectDeployed;
    }
}
