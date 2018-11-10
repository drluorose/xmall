package cn.exrick.manager.pojo;

import java.io.Serializable;
import lombok.Data;

@Data
public class TransactionPaypalShippingAddress implements Serializable {
    private Long id;

    private String tPaypayId;

    private String recipientName;

    private String line1;

    private String line2;

    private String city;

    private String countryCode;

    private String postalCode;

    private String phone;

    private String state;

    private static final long serialVersionUID = 1L;
}