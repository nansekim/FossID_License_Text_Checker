package fossid.licenseTextChecker.values;

import java.util.ArrayList;

public class LicenseListValues {

    private static final LicenseListValues values = new LicenseListValues();

    private LicenseListValues() {}

    public static LicenseListValues getInstance() {
        return values;
    }

    private final ArrayList<LicenseValues> allLicenseList = new ArrayList<>();
    private final ArrayList<LicenseValues> textLicenseList = new ArrayList<>();
    private final ArrayList<LicenseValues> noTextLicenseList = new ArrayList<>();

    public ArrayList<LicenseValues> getAllLicenseList() {
        return allLicenseList;
    }
    public void setAllLicenseList(LicenseValues componentValues) {
        this.allLicenseList.add(componentValues);
    }

    public ArrayList<LicenseValues> getTextLicenseList() {
        return textLicenseList;
    }
    public void setTextLicenseList(LicenseValues componentValues) {
        this.textLicenseList.add(componentValues);
    }

    public ArrayList<LicenseValues> getNoTextLicenseList() {
        return noTextLicenseList;
    }
    public void setNoTextLicenseList(LicenseValues componentValues) {
        this.noTextLicenseList.add(componentValues);
    }
}
