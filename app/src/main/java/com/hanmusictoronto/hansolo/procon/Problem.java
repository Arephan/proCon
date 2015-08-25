package com.hanmusictoronto.hansolo.procon;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by HanSolo on 15-08-17.
 */

@Table(name = "Problems")
public class Problem extends Model {
    public Problem() {
        super();
    }

    @Column(name = "Name")
    public String name;

    @Column(name = "ExpiryDate")
    public String expiryDate;


    public Problem(String name, String expiryDate) {
        super();
        this.name = name;
        this.expiryDate = expiryDate;
    }

}
