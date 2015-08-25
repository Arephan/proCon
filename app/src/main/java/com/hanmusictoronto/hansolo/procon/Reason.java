package com.hanmusictoronto.hansolo.procon;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by HanSolo on 15-08-17.
 */
@Table(name = "Reasons")
public class Reason extends Model {
    public Reason() {
        super();
    }

    @Column(name = "Polarity")
    public Integer polarity;

    @Column(name = "Name")
    public String name;

    @Column(name = "Weight")
    public Integer weight;

    @Column(name="Problem")
    public Problem problem;

    public Reason (Problem problem, Integer polarity, String name, Integer weight) {
        super();
        this.polarity = polarity;
        this.name = name;
        this.weight = weight;
        this.problem = problem;
    }
}