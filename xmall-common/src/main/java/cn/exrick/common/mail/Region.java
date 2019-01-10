package cn.exrick.common.mail;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
public class Region {

    private String regionId;

    private String endpointName;

    private String product;

    private String domain;

    public Region(String regionId, String endpointName, String product, String domain) {
        this.regionId = regionId;
        this.endpointName = endpointName;
        this.product = product;
        this.domain = domain;
    }

    public String getEndpointName() {
        return endpointName;
    }

    public String getProduct() {
        return product;
    }

    public String getDomain() {
        return domain;
    }

    public String getRegionId() {
        return regionId;
    }

    public boolean isHanzhouRegin() {
        return true;
    }

    public String getRequestVersion() {
        return "2017-06-22";
    }
}
