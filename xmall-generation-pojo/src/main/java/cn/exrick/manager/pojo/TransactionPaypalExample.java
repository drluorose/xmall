package cn.exrick.manager.pojo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TransactionPaypalExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Integer offset;

    public TransactionPaypalExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getOffset() {
        return offset;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andTidIsNull() {
            addCriterion("tid is null");
            return (Criteria) this;
        }

        public Criteria andTidIsNotNull() {
            addCriterion("tid is not null");
            return (Criteria) this;
        }

        public Criteria andTidEqualTo(String value) {
            addCriterion("tid =", value, "tid");
            return (Criteria) this;
        }

        public Criteria andTidNotEqualTo(String value) {
            addCriterion("tid <>", value, "tid");
            return (Criteria) this;
        }

        public Criteria andTidGreaterThan(String value) {
            addCriterion("tid >", value, "tid");
            return (Criteria) this;
        }

        public Criteria andTidGreaterThanOrEqualTo(String value) {
            addCriterion("tid >=", value, "tid");
            return (Criteria) this;
        }

        public Criteria andTidLessThan(String value) {
            addCriterion("tid <", value, "tid");
            return (Criteria) this;
        }

        public Criteria andTidLessThanOrEqualTo(String value) {
            addCriterion("tid <=", value, "tid");
            return (Criteria) this;
        }

        public Criteria andTidLike(String value) {
            addCriterion("tid like", value, "tid");
            return (Criteria) this;
        }

        public Criteria andTidNotLike(String value) {
            addCriterion("tid not like", value, "tid");
            return (Criteria) this;
        }

        public Criteria andTidIn(List<String> values) {
            addCriterion("tid in", values, "tid");
            return (Criteria) this;
        }

        public Criteria andTidNotIn(List<String> values) {
            addCriterion("tid not in", values, "tid");
            return (Criteria) this;
        }

        public Criteria andTidBetween(String value1, String value2) {
            addCriterion("tid between", value1, value2, "tid");
            return (Criteria) this;
        }

        public Criteria andTidNotBetween(String value1, String value2) {
            addCriterion("tid not between", value1, value2, "tid");
            return (Criteria) this;
        }

        public Criteria andTPaypalIdIsNull() {
            addCriterion("t_paypal_id is null");
            return (Criteria) this;
        }

        public Criteria andTPaypalIdIsNotNull() {
            addCriterion("t_paypal_id is not null");
            return (Criteria) this;
        }

        public Criteria andTPaypalIdEqualTo(String value) {
            addCriterion("t_paypal_id =", value, "tPaypalId");
            return (Criteria) this;
        }

        public Criteria andTPaypalIdNotEqualTo(String value) {
            addCriterion("t_paypal_id <>", value, "tPaypalId");
            return (Criteria) this;
        }

        public Criteria andTPaypalIdGreaterThan(String value) {
            addCriterion("t_paypal_id >", value, "tPaypalId");
            return (Criteria) this;
        }

        public Criteria andTPaypalIdGreaterThanOrEqualTo(String value) {
            addCriterion("t_paypal_id >=", value, "tPaypalId");
            return (Criteria) this;
        }

        public Criteria andTPaypalIdLessThan(String value) {
            addCriterion("t_paypal_id <", value, "tPaypalId");
            return (Criteria) this;
        }

        public Criteria andTPaypalIdLessThanOrEqualTo(String value) {
            addCriterion("t_paypal_id <=", value, "tPaypalId");
            return (Criteria) this;
        }

        public Criteria andTPaypalIdLike(String value) {
            addCriterion("t_paypal_id like", value, "tPaypalId");
            return (Criteria) this;
        }

        public Criteria andTPaypalIdNotLike(String value) {
            addCriterion("t_paypal_id not like", value, "tPaypalId");
            return (Criteria) this;
        }

        public Criteria andTPaypalIdIn(List<String> values) {
            addCriterion("t_paypal_id in", values, "tPaypalId");
            return (Criteria) this;
        }

        public Criteria andTPaypalIdNotIn(List<String> values) {
            addCriterion("t_paypal_id not in", values, "tPaypalId");
            return (Criteria) this;
        }

        public Criteria andTPaypalIdBetween(String value1, String value2) {
            addCriterion("t_paypal_id between", value1, value2, "tPaypalId");
            return (Criteria) this;
        }

        public Criteria andTPaypalIdNotBetween(String value1, String value2) {
            addCriterion("t_paypal_id not between", value1, value2, "tPaypalId");
            return (Criteria) this;
        }

        public Criteria andTotalIsNull() {
            addCriterion("total is null");
            return (Criteria) this;
        }

        public Criteria andTotalIsNotNull() {
            addCriterion("total is not null");
            return (Criteria) this;
        }

        public Criteria andTotalEqualTo(BigDecimal value) {
            addCriterion("total =", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalNotEqualTo(BigDecimal value) {
            addCriterion("total <>", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalGreaterThan(BigDecimal value) {
            addCriterion("total >", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("total >=", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalLessThan(BigDecimal value) {
            addCriterion("total <", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("total <=", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalIn(List<BigDecimal> values) {
            addCriterion("total in", values, "total");
            return (Criteria) this;
        }

        public Criteria andTotalNotIn(List<BigDecimal> values) {
            addCriterion("total not in", values, "total");
            return (Criteria) this;
        }

        public Criteria andTotalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total between", value1, value2, "total");
            return (Criteria) this;
        }

        public Criteria andTotalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total not between", value1, value2, "total");
            return (Criteria) this;
        }

        public Criteria andCurrencyIsNull() {
            addCriterion("currency is null");
            return (Criteria) this;
        }

        public Criteria andCurrencyIsNotNull() {
            addCriterion("currency is not null");
            return (Criteria) this;
        }

        public Criteria andCurrencyEqualTo(String value) {
            addCriterion("currency =", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyNotEqualTo(String value) {
            addCriterion("currency <>", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyGreaterThan(String value) {
            addCriterion("currency >", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyGreaterThanOrEqualTo(String value) {
            addCriterion("currency >=", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyLessThan(String value) {
            addCriterion("currency <", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyLessThanOrEqualTo(String value) {
            addCriterion("currency <=", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyLike(String value) {
            addCriterion("currency like", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyNotLike(String value) {
            addCriterion("currency not like", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyIn(List<String> values) {
            addCriterion("currency in", values, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyNotIn(List<String> values) {
            addCriterion("currency not in", values, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyBetween(String value1, String value2) {
            addCriterion("currency between", value1, value2, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyNotBetween(String value1, String value2) {
            addCriterion("currency not between", value1, value2, "currency");
            return (Criteria) this;
        }

        public Criteria andSubtotalIsNull() {
            addCriterion("subtotal is null");
            return (Criteria) this;
        }

        public Criteria andSubtotalIsNotNull() {
            addCriterion("subtotal is not null");
            return (Criteria) this;
        }

        public Criteria andSubtotalEqualTo(BigDecimal value) {
            addCriterion("subtotal =", value, "subtotal");
            return (Criteria) this;
        }

        public Criteria andSubtotalNotEqualTo(BigDecimal value) {
            addCriterion("subtotal <>", value, "subtotal");
            return (Criteria) this;
        }

        public Criteria andSubtotalGreaterThan(BigDecimal value) {
            addCriterion("subtotal >", value, "subtotal");
            return (Criteria) this;
        }

        public Criteria andSubtotalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("subtotal >=", value, "subtotal");
            return (Criteria) this;
        }

        public Criteria andSubtotalLessThan(BigDecimal value) {
            addCriterion("subtotal <", value, "subtotal");
            return (Criteria) this;
        }

        public Criteria andSubtotalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("subtotal <=", value, "subtotal");
            return (Criteria) this;
        }

        public Criteria andSubtotalIn(List<BigDecimal> values) {
            addCriterion("subtotal in", values, "subtotal");
            return (Criteria) this;
        }

        public Criteria andSubtotalNotIn(List<BigDecimal> values) {
            addCriterion("subtotal not in", values, "subtotal");
            return (Criteria) this;
        }

        public Criteria andSubtotalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("subtotal between", value1, value2, "subtotal");
            return (Criteria) this;
        }

        public Criteria andSubtotalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("subtotal not between", value1, value2, "subtotal");
            return (Criteria) this;
        }

        public Criteria andTaxIsNull() {
            addCriterion("tax is null");
            return (Criteria) this;
        }

        public Criteria andTaxIsNotNull() {
            addCriterion("tax is not null");
            return (Criteria) this;
        }

        public Criteria andTaxEqualTo(BigDecimal value) {
            addCriterion("tax =", value, "tax");
            return (Criteria) this;
        }

        public Criteria andTaxNotEqualTo(BigDecimal value) {
            addCriterion("tax <>", value, "tax");
            return (Criteria) this;
        }

        public Criteria andTaxGreaterThan(BigDecimal value) {
            addCriterion("tax >", value, "tax");
            return (Criteria) this;
        }

        public Criteria andTaxGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("tax >=", value, "tax");
            return (Criteria) this;
        }

        public Criteria andTaxLessThan(BigDecimal value) {
            addCriterion("tax <", value, "tax");
            return (Criteria) this;
        }

        public Criteria andTaxLessThanOrEqualTo(BigDecimal value) {
            addCriterion("tax <=", value, "tax");
            return (Criteria) this;
        }

        public Criteria andTaxIn(List<BigDecimal> values) {
            addCriterion("tax in", values, "tax");
            return (Criteria) this;
        }

        public Criteria andTaxNotIn(List<BigDecimal> values) {
            addCriterion("tax not in", values, "tax");
            return (Criteria) this;
        }

        public Criteria andTaxBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("tax between", value1, value2, "tax");
            return (Criteria) this;
        }

        public Criteria andTaxNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("tax not between", value1, value2, "tax");
            return (Criteria) this;
        }

        public Criteria andShippingIsNull() {
            addCriterion("shipping is null");
            return (Criteria) this;
        }

        public Criteria andShippingIsNotNull() {
            addCriterion("shipping is not null");
            return (Criteria) this;
        }

        public Criteria andShippingEqualTo(BigDecimal value) {
            addCriterion("shipping =", value, "shipping");
            return (Criteria) this;
        }

        public Criteria andShippingNotEqualTo(BigDecimal value) {
            addCriterion("shipping <>", value, "shipping");
            return (Criteria) this;
        }

        public Criteria andShippingGreaterThan(BigDecimal value) {
            addCriterion("shipping >", value, "shipping");
            return (Criteria) this;
        }

        public Criteria andShippingGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("shipping >=", value, "shipping");
            return (Criteria) this;
        }

        public Criteria andShippingLessThan(BigDecimal value) {
            addCriterion("shipping <", value, "shipping");
            return (Criteria) this;
        }

        public Criteria andShippingLessThanOrEqualTo(BigDecimal value) {
            addCriterion("shipping <=", value, "shipping");
            return (Criteria) this;
        }

        public Criteria andShippingIn(List<BigDecimal> values) {
            addCriterion("shipping in", values, "shipping");
            return (Criteria) this;
        }

        public Criteria andShippingNotIn(List<BigDecimal> values) {
            addCriterion("shipping not in", values, "shipping");
            return (Criteria) this;
        }

        public Criteria andShippingBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("shipping between", value1, value2, "shipping");
            return (Criteria) this;
        }

        public Criteria andShippingNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("shipping not between", value1, value2, "shipping");
            return (Criteria) this;
        }

        public Criteria andHandlingFeeIsNull() {
            addCriterion("handling_fee is null");
            return (Criteria) this;
        }

        public Criteria andHandlingFeeIsNotNull() {
            addCriterion("handling_fee is not null");
            return (Criteria) this;
        }

        public Criteria andHandlingFeeEqualTo(BigDecimal value) {
            addCriterion("handling_fee =", value, "handlingFee");
            return (Criteria) this;
        }

        public Criteria andHandlingFeeNotEqualTo(BigDecimal value) {
            addCriterion("handling_fee <>", value, "handlingFee");
            return (Criteria) this;
        }

        public Criteria andHandlingFeeGreaterThan(BigDecimal value) {
            addCriterion("handling_fee >", value, "handlingFee");
            return (Criteria) this;
        }

        public Criteria andHandlingFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("handling_fee >=", value, "handlingFee");
            return (Criteria) this;
        }

        public Criteria andHandlingFeeLessThan(BigDecimal value) {
            addCriterion("handling_fee <", value, "handlingFee");
            return (Criteria) this;
        }

        public Criteria andHandlingFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("handling_fee <=", value, "handlingFee");
            return (Criteria) this;
        }

        public Criteria andHandlingFeeIn(List<BigDecimal> values) {
            addCriterion("handling_fee in", values, "handlingFee");
            return (Criteria) this;
        }

        public Criteria andHandlingFeeNotIn(List<BigDecimal> values) {
            addCriterion("handling_fee not in", values, "handlingFee");
            return (Criteria) this;
        }

        public Criteria andHandlingFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("handling_fee between", value1, value2, "handlingFee");
            return (Criteria) this;
        }

        public Criteria andHandlingFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("handling_fee not between", value1, value2, "handlingFee");
            return (Criteria) this;
        }

        public Criteria andShippingDiscountIsNull() {
            addCriterion("shipping_discount is null");
            return (Criteria) this;
        }

        public Criteria andShippingDiscountIsNotNull() {
            addCriterion("shipping_discount is not null");
            return (Criteria) this;
        }

        public Criteria andShippingDiscountEqualTo(BigDecimal value) {
            addCriterion("shipping_discount =", value, "shippingDiscount");
            return (Criteria) this;
        }

        public Criteria andShippingDiscountNotEqualTo(BigDecimal value) {
            addCriterion("shipping_discount <>", value, "shippingDiscount");
            return (Criteria) this;
        }

        public Criteria andShippingDiscountGreaterThan(BigDecimal value) {
            addCriterion("shipping_discount >", value, "shippingDiscount");
            return (Criteria) this;
        }

        public Criteria andShippingDiscountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("shipping_discount >=", value, "shippingDiscount");
            return (Criteria) this;
        }

        public Criteria andShippingDiscountLessThan(BigDecimal value) {
            addCriterion("shipping_discount <", value, "shippingDiscount");
            return (Criteria) this;
        }

        public Criteria andShippingDiscountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("shipping_discount <=", value, "shippingDiscount");
            return (Criteria) this;
        }

        public Criteria andShippingDiscountIn(List<BigDecimal> values) {
            addCriterion("shipping_discount in", values, "shippingDiscount");
            return (Criteria) this;
        }

        public Criteria andShippingDiscountNotIn(List<BigDecimal> values) {
            addCriterion("shipping_discount not in", values, "shippingDiscount");
            return (Criteria) this;
        }

        public Criteria andShippingDiscountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("shipping_discount between", value1, value2, "shippingDiscount");
            return (Criteria) this;
        }

        public Criteria andShippingDiscountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("shipping_discount not between", value1, value2, "shippingDiscount");
            return (Criteria) this;
        }

        public Criteria andInsuranceIsNull() {
            addCriterion("insurance is null");
            return (Criteria) this;
        }

        public Criteria andInsuranceIsNotNull() {
            addCriterion("insurance is not null");
            return (Criteria) this;
        }

        public Criteria andInsuranceEqualTo(BigDecimal value) {
            addCriterion("insurance =", value, "insurance");
            return (Criteria) this;
        }

        public Criteria andInsuranceNotEqualTo(BigDecimal value) {
            addCriterion("insurance <>", value, "insurance");
            return (Criteria) this;
        }

        public Criteria andInsuranceGreaterThan(BigDecimal value) {
            addCriterion("insurance >", value, "insurance");
            return (Criteria) this;
        }

        public Criteria andInsuranceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("insurance >=", value, "insurance");
            return (Criteria) this;
        }

        public Criteria andInsuranceLessThan(BigDecimal value) {
            addCriterion("insurance <", value, "insurance");
            return (Criteria) this;
        }

        public Criteria andInsuranceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("insurance <=", value, "insurance");
            return (Criteria) this;
        }

        public Criteria andInsuranceIn(List<BigDecimal> values) {
            addCriterion("insurance in", values, "insurance");
            return (Criteria) this;
        }

        public Criteria andInsuranceNotIn(List<BigDecimal> values) {
            addCriterion("insurance not in", values, "insurance");
            return (Criteria) this;
        }

        public Criteria andInsuranceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("insurance between", value1, value2, "insurance");
            return (Criteria) this;
        }

        public Criteria andInsuranceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("insurance not between", value1, value2, "insurance");
            return (Criteria) this;
        }

        public Criteria andSelfUrlIsNull() {
            addCriterion("self_url is null");
            return (Criteria) this;
        }

        public Criteria andSelfUrlIsNotNull() {
            addCriterion("self_url is not null");
            return (Criteria) this;
        }

        public Criteria andSelfUrlEqualTo(String value) {
            addCriterion("self_url =", value, "selfUrl");
            return (Criteria) this;
        }

        public Criteria andSelfUrlNotEqualTo(String value) {
            addCriterion("self_url <>", value, "selfUrl");
            return (Criteria) this;
        }

        public Criteria andSelfUrlGreaterThan(String value) {
            addCriterion("self_url >", value, "selfUrl");
            return (Criteria) this;
        }

        public Criteria andSelfUrlGreaterThanOrEqualTo(String value) {
            addCriterion("self_url >=", value, "selfUrl");
            return (Criteria) this;
        }

        public Criteria andSelfUrlLessThan(String value) {
            addCriterion("self_url <", value, "selfUrl");
            return (Criteria) this;
        }

        public Criteria andSelfUrlLessThanOrEqualTo(String value) {
            addCriterion("self_url <=", value, "selfUrl");
            return (Criteria) this;
        }

        public Criteria andSelfUrlLike(String value) {
            addCriterion("self_url like", value, "selfUrl");
            return (Criteria) this;
        }

        public Criteria andSelfUrlNotLike(String value) {
            addCriterion("self_url not like", value, "selfUrl");
            return (Criteria) this;
        }

        public Criteria andSelfUrlIn(List<String> values) {
            addCriterion("self_url in", values, "selfUrl");
            return (Criteria) this;
        }

        public Criteria andSelfUrlNotIn(List<String> values) {
            addCriterion("self_url not in", values, "selfUrl");
            return (Criteria) this;
        }

        public Criteria andSelfUrlBetween(String value1, String value2) {
            addCriterion("self_url between", value1, value2, "selfUrl");
            return (Criteria) this;
        }

        public Criteria andSelfUrlNotBetween(String value1, String value2) {
            addCriterion("self_url not between", value1, value2, "selfUrl");
            return (Criteria) this;
        }

        public Criteria andApprovalUrlIsNull() {
            addCriterion("approval_url is null");
            return (Criteria) this;
        }

        public Criteria andApprovalUrlIsNotNull() {
            addCriterion("approval_url is not null");
            return (Criteria) this;
        }

        public Criteria andApprovalUrlEqualTo(String value) {
            addCriterion("approval_url =", value, "approvalUrl");
            return (Criteria) this;
        }

        public Criteria andApprovalUrlNotEqualTo(String value) {
            addCriterion("approval_url <>", value, "approvalUrl");
            return (Criteria) this;
        }

        public Criteria andApprovalUrlGreaterThan(String value) {
            addCriterion("approval_url >", value, "approvalUrl");
            return (Criteria) this;
        }

        public Criteria andApprovalUrlGreaterThanOrEqualTo(String value) {
            addCriterion("approval_url >=", value, "approvalUrl");
            return (Criteria) this;
        }

        public Criteria andApprovalUrlLessThan(String value) {
            addCriterion("approval_url <", value, "approvalUrl");
            return (Criteria) this;
        }

        public Criteria andApprovalUrlLessThanOrEqualTo(String value) {
            addCriterion("approval_url <=", value, "approvalUrl");
            return (Criteria) this;
        }

        public Criteria andApprovalUrlLike(String value) {
            addCriterion("approval_url like", value, "approvalUrl");
            return (Criteria) this;
        }

        public Criteria andApprovalUrlNotLike(String value) {
            addCriterion("approval_url not like", value, "approvalUrl");
            return (Criteria) this;
        }

        public Criteria andApprovalUrlIn(List<String> values) {
            addCriterion("approval_url in", values, "approvalUrl");
            return (Criteria) this;
        }

        public Criteria andApprovalUrlNotIn(List<String> values) {
            addCriterion("approval_url not in", values, "approvalUrl");
            return (Criteria) this;
        }

        public Criteria andApprovalUrlBetween(String value1, String value2) {
            addCriterion("approval_url between", value1, value2, "approvalUrl");
            return (Criteria) this;
        }

        public Criteria andApprovalUrlNotBetween(String value1, String value2) {
            addCriterion("approval_url not between", value1, value2, "approvalUrl");
            return (Criteria) this;
        }

        public Criteria andExecuteUrlIsNull() {
            addCriterion("execute_url is null");
            return (Criteria) this;
        }

        public Criteria andExecuteUrlIsNotNull() {
            addCriterion("execute_url is not null");
            return (Criteria) this;
        }

        public Criteria andExecuteUrlEqualTo(String value) {
            addCriterion("execute_url =", value, "executeUrl");
            return (Criteria) this;
        }

        public Criteria andExecuteUrlNotEqualTo(String value) {
            addCriterion("execute_url <>", value, "executeUrl");
            return (Criteria) this;
        }

        public Criteria andExecuteUrlGreaterThan(String value) {
            addCriterion("execute_url >", value, "executeUrl");
            return (Criteria) this;
        }

        public Criteria andExecuteUrlGreaterThanOrEqualTo(String value) {
            addCriterion("execute_url >=", value, "executeUrl");
            return (Criteria) this;
        }

        public Criteria andExecuteUrlLessThan(String value) {
            addCriterion("execute_url <", value, "executeUrl");
            return (Criteria) this;
        }

        public Criteria andExecuteUrlLessThanOrEqualTo(String value) {
            addCriterion("execute_url <=", value, "executeUrl");
            return (Criteria) this;
        }

        public Criteria andExecuteUrlLike(String value) {
            addCriterion("execute_url like", value, "executeUrl");
            return (Criteria) this;
        }

        public Criteria andExecuteUrlNotLike(String value) {
            addCriterion("execute_url not like", value, "executeUrl");
            return (Criteria) this;
        }

        public Criteria andExecuteUrlIn(List<String> values) {
            addCriterion("execute_url in", values, "executeUrl");
            return (Criteria) this;
        }

        public Criteria andExecuteUrlNotIn(List<String> values) {
            addCriterion("execute_url not in", values, "executeUrl");
            return (Criteria) this;
        }

        public Criteria andExecuteUrlBetween(String value1, String value2) {
            addCriterion("execute_url between", value1, value2, "executeUrl");
            return (Criteria) this;
        }

        public Criteria andExecuteUrlNotBetween(String value1, String value2) {
            addCriterion("execute_url not between", value1, value2, "executeUrl");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}