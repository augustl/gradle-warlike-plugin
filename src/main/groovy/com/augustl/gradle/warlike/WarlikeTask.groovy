package com.augustl.gradle.warlike

import org.gradle.api.tasks.JavaExec

/**
 * Created by augustl on 12.11.14.
 */
class WarlikeTask extends JavaExec {
    String port = 1234

    @Override
    void exec() {
        this.setArgs([this.port])
        super.exec()
    }
}
