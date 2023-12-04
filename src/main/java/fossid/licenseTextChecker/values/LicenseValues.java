package fossid.licenseTextChecker.values;

import java.io.Serializable;

public class LicenseValues implements Serializable {
    public LicenseValues() {}

    public LicenseValues(String name, String identifier) {
        this.licenseName = name;
        this.licenseIdentifier = identifier;
    }

    private String licenseName;
    private String licenseIdentifier;
    private String text;

    public String getLicenseIdentifier() {
        return licenseIdentifier;
    }

    public void setLicenseIdentifier(String licenseIdentifier) {
        this.licenseIdentifier = licenseIdentifier;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
