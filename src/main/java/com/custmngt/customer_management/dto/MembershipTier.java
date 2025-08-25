package com.custmngt.customer_management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum MembershipTier {

    SILVER("Silver", 0, 1000),
    GOLD("Gold", 1000, 10000),
    PLATINUM("Platinum", 10000, Integer.MAX_VALUE);

    private final String displayName;
    private final int minSpend;
    private final int maxSpend;
    public static MembershipTier fromAnnualSpend(Double annualSpend) {
        for (MembershipTier tier : values()) {
            if (annualSpend >= tier.minSpend && annualSpend <= tier.maxSpend) {
                return tier;
            }
        }
        throw new IllegalArgumentException("Invalid annual spend: " + annualSpend);
    }
}
