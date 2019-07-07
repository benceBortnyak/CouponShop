package com.codecool.web.model;

import java.util.Objects;

public final class Coupon extends AbstractModel {

    private final String name;
    private final int percentage;
    private final int user_id;

    public Coupon(int id, String name, int percentage, int user_id) {
        super(id);
        this.name = name;
        this.percentage = percentage;
        this.user_id = user_id;
    }

    public Coupon(int id, String name, int percentage) {
        super(id);
        this.name = name;
        this.percentage = percentage;
        user_id = 0;
    }

    public String getName() {
        return name;
    }

    public int getPercentage() {
        return percentage;
    }

    public int getUser_id() {
        return user_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Coupon coupon = (Coupon) o;
        return percentage == coupon.percentage &&
            Objects.equals(name, coupon.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, percentage);
    }
}
