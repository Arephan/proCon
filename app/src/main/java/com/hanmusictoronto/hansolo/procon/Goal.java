package com.hanmusictoronto.hansolo.procon;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by HanSolo on 15-08-22.
 */
@Table(name = "Goals")
public class Goal extends Model {
    public Goal() {
        super();
    }

    @Column(name = "Name")
    public String name ;

    @Column(name = "Reason")
    public Reason reason ;

    public Goal (String name, Reason reason) {
        this.name = name;
        this.reason = reason;
    }

}
